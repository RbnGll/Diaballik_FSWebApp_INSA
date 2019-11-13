package diaballik.model.game;

import diaballik.model.exception.CommandException;
import diaballik.model.exception.turn.EndTurnException;
import diaballik.model.exception.turn.TurnException;
import diaballik.model.exception.turn.UnstartedGameException;
import diaballik.model.player.*;
import diaballik.model.player.aiStrategy.AIStrategy;
import diaballik.model.player.aiStrategy.NoobAI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game g;

    @BeforeEach
    void init() {
        g = new Game(Color.WHITE, "Robin", Color.BLACK, "Ronan");
    }

    @Test
    void constructionPvP() {

        // Construction des joueurs
        assertEquals(Color.WHITE, g.getPlayer1().getColor());
        assertEquals(Color.BLACK, g.getPlayer2().getColor());
        assertEquals("Robin", g.getPlayer1().getName());
        assertEquals("Ronan", g.getPlayer2().getName());

        List<Piece> piecesP1 = g.getPlayer1().getPieces();
        List<Piece> pieceP2 = g.getPlayer2().getPieces();

        for (int i = 0; i < Board.BOARDSIZE; i++) {
            Tile t1 = piecesP1.get(i).getTile();
            Tile t2 = pieceP2.get(i).getTile();

            assertEquals(i, t1.getX());
            assertEquals(0, t1.getY());
            assertEquals(6, t2.getY());

            //System.out.println("T1: " + t1.toString());
            //System.out.println("T2 " + t2.toString());
        }

        assertEquals(g.getPlayer1().getPieces().get(Board.BOARDSIZE / 2), g.getPlayer1().getBall().getPiece());
        assertEquals(g.getPlayer2().getPieces().get(Board.BOARDSIZE / 2), g.getPlayer2().getBall().getPiece());
    }

    @Test
    void constructionPvAI() {
        g = new Game(Color.WHITE, "Robebs", AIType.NOOB);

        // Construction des joueurs
        assertEquals(Color.WHITE, g.getPlayer1().getColor());
        assertEquals(Color.BLACK, g.getPlayer2().getColor());
        assertEquals("Robebs", g.getPlayer1().getName());
        assertEquals("Computer", g.getPlayer2().getName());
        assertEquals(HumanPlayer.class,g.getPlayer1().getClass());
        assertEquals(AIPlayer.class,g.getPlayer2().getClass());

        List<Piece> piecesP1 = g.getPlayer1().getPieces();
        List<Piece> pieceP2 = g.getPlayer2().getPieces();

        for (int i = 0; i < Board.BOARDSIZE; i++) {
            Tile t1 = piecesP1.get(i).getTile();
            Tile t2 = pieceP2.get(i).getTile();

            assertEquals(i, t1.getX());
            assertEquals(0, t1.getY());
            assertEquals(6, t2.getY());

            //System.out.println("T1: " + t1.toString());
            //System.out.println("T2 " + t2.toString());
        }

        assertEquals(g.getPlayer1().getPieces().get(Board.BOARDSIZE / 2), g.getPlayer1().getBall().getPiece());
        assertEquals(g.getPlayer2().getPieces().get(Board.BOARDSIZE / 2), g.getPlayer2().getBall().getPiece());
    }

    @Test
    void start() {
        g.start();
        assertNotNull(g.getCurrentTurn());
    }

    @Test
    void swapCurrentPlayer() {
        g.start();
        assertEquals(g.getPlayer1(), g.getCurrentPlayer());
        g.swapCurrentPlayer();
        assertEquals(g.getPlayer2(), g.getCurrentPlayer());
    }

    @Test
    void checkVictoryTrue() {
        g.getGameboard().movePiece(0, 6, 3, 3);
        g.getGameboard().movePiece(0, 0, 0, 6);
        g.getGameboard().movePiece(Board.BOARDSIZE / 2, 0, 0, 0);
        g.getPlayer1().getBall().move(g.getGameboard().getTile(0, 6).getPiece().get());

        assertTrue(g.checkVictory().isPresent());
    }

    @Test
    void checkVictoryFalseInit() {
        assertTrue(g.checkVictory().isEmpty());
    }

    @Test
    void checkVictoryFalseDuringGame() {
        g.getGameboard().movePiece(0, 6, 3, 3);
        g.getGameboard().movePiece(0, 0, 0, 6);
        g.getGameboard().movePiece(Board.BOARDSIZE / 2, 0, 0, 0);

        assertTrue(g.checkVictory().isEmpty());
    }

    @Test
    void movePieceUnstartedGame() throws UnstartedGameException {
        assertThrows(UnstartedGameException.class, () -> g.movePiece(0, 0, 0, 1));
    }

    @Test
    void movePieceDy() throws TurnException, CommandException {
        g.start();

        assertTrue(g.getGameboard().getTile(0, 0).getPiece().isPresent());
        assertTrue(g.getGameboard().getTile(0, 1).getPiece().isEmpty());

        g.movePiece(0, 0, 0, 1);

        assertTrue(g.getGameboard().getTile(0, 0).getPiece().isEmpty());
        assertTrue(g.getGameboard().getTile(0, 1).getPiece().isPresent());
    }

    @Test
    void movePieceDx() throws TurnException, CommandException {
        g.start();

        g.movePiece(0, 0, 0, 1);

        assertTrue(g.getGameboard().getTile(0, 1).getPiece().isPresent());
        assertTrue(g.getGameboard().getTile(1, 1).getPiece().isEmpty());

        g.movePiece(0, 1, 1, 1);

        assertTrue(g.getGameboard().getTile(0, 1).getPiece().isEmpty());
        assertTrue(g.getGameboard().getTile(1, 1).getPiece().isPresent());
    }

    @Test
    void movePieceDiag() throws TurnException, CommandException {
        g.start();

        assertTrue(g.getGameboard().getTile(0, 0).getPiece().isPresent());
        assertTrue(g.getGameboard().getTile(1, 1).getPiece().isEmpty());

        assertThrows(CommandException.class, () -> g.movePiece(0, 0, 1, 1));

        assertTrue(g.getGameboard().getTile(0, 0).getPiece().isPresent());
        assertTrue(g.getGameboard().getTile(1, 1).getPiece().isEmpty());
    }

    @Test
    void passBallUnstartedGame() throws UnstartedGameException {
        assertThrows(UnstartedGameException.class, () -> g.passBall(0, 3, 0, 0));
    }

    @Test
    void passBallDx() throws TurnException, CommandException {
        g.start();

        assertFalse(g.getGameboard().getTile(0, 0).getPiece().get().hasBall());
        assertTrue(g.getGameboard().getTile(g.getGameboard().BOARDSIZE / 2, 0).getPiece().get().hasBall());

        g.passBall(g.getGameboard().BOARDSIZE / 2, 0, 0, 0);

        assertTrue(g.getGameboard().getTile(0, 0).getPiece().get().hasBall());
        assertFalse(g.getGameboard().getTile(g.getGameboard().BOARDSIZE / 2, 0).getPiece().get().hasBall());
    }

    @Test
    void passBallDy() throws TurnException, CommandException {
        // Déplacer le pion de manière à pouvoir faire un Dy
        g.getGameboard().movePiece(0, 0, Board.BOARDSIZE / 2, Board.BOARDSIZE / 2);

        g.start();

        assertFalse(g.getGameboard().getTile(Board.BOARDSIZE / 2, Board.BOARDSIZE / 2).getPiece().get().hasBall());
        assertTrue(g.getGameboard().getTile(g.getGameboard().BOARDSIZE / 2, 0).getPiece().get().hasBall());

        g.passBall(g.getGameboard().BOARDSIZE / 2, 0, Board.BOARDSIZE / 2, Board.BOARDSIZE / 2);

        assertTrue(g.getGameboard().getTile(Board.BOARDSIZE / 2, Board.BOARDSIZE / 2).getPiece().get().hasBall());
        assertFalse(g.getGameboard().getTile(g.getGameboard().BOARDSIZE / 2, 0).getPiece().get().hasBall());
    }

    @Test
    void passBallDiag() throws TurnException, CommandException {
        // déplacer un pion pour faire une passe en diagonale
        g.getGameboard().movePiece(6, 0, 3, 3);

        g.start();

        g.passBall(Board.BOARDSIZE / 2, 0, 0, 0);

        assertFalse(g.getGameboard().getTile(3, 3).getPiece().get().hasBall());
        assertTrue(g.getGameboard().getTile(0, 0).getPiece().get().hasBall());

        g.passBall(0, 0, 3, 3);

        assertTrue(g.getGameboard().getTile(3, 3).getPiece().get().hasBall());
        assertFalse(g.getGameboard().getTile(0, 0).getPiece().get().hasBall());
    }

    @Test
    void passBallOpponentOnPath() throws TurnException, CommandException {
        // Placer l'adversaire au milieu du plateau

        g.getGameboard().movePiece(6, 6, 3, 3);
        g.getGameboard().movePiece(6, 0, 5, 5);

        g.start();

        g.passBall(Board.BOARDSIZE / 2, 0, 0, 0);

        assertFalse(g.getGameboard().getTile(5, 5).getPiece().get().hasBall());
        assertTrue(g.getGameboard().getTile(0, 0).getPiece().get().hasBall());

        assertThrows(CommandException.class, () -> g.passBall(0, 0, 5, 5));

        assertFalse(g.getGameboard().getTile(5, 5).getPiece().get().hasBall());
        assertTrue(g.getGameboard().getTile(0, 0).getPiece().get().hasBall());
    }

    @Test
    void endTurnUnstartedGame() throws UnstartedGameException {
        assertThrows(UnstartedGameException.class, () -> g.endTurn());
    }

    @Test
    void endTurnFalse() throws EndTurnException {
        g.start();

        // Pas 3 actions donc fin de tour pas possible

        Player currentPlayer = g.getCurrentPlayer();
        Turn currentTurn = g.getCurrentTurn();

        assertThrows(EndTurnException.class, () -> g.endTurn());

        assertEquals(currentPlayer, g.getCurrentPlayer());
        assertEquals(currentTurn, g.getCurrentTurn());
    }

    @Test
    void endTurnTrue() throws TurnException, CommandException {
        g.start();

        g.passBall(Board.BOARDSIZE / 2, 0, 0, 0);
        g.passBall(0, 0, 1, 0);
        g.movePiece(0, 0, 0, 1);

        Player currentPlayer = g.getCurrentPlayer();
        Turn currentTurn = g.getCurrentTurn();

        g.endTurn();

        assertNotEquals(currentPlayer, g.getCurrentPlayer());
        assertNotEquals(currentTurn, g.getCurrentTurn());

    }

    @Test
    void undoUnstartedTurn() throws UnstartedGameException {
        assertThrows(UnstartedGameException.class, () -> g.undo());
    }

    @Test
    void undo() throws TurnException, CommandException {
        g.start();

        g.movePiece(0, 0, 0, 1);
        g.undo();

        assertTrue(g.getGameboard().getTile(0, 0).getPiece().isPresent());
        assertTrue(g.getGameboard().getTile(0, 1).getPiece().isEmpty());
    }

    @Test
    void redoUnstartedTurn() throws UnstartedGameException {
        assertThrows(UnstartedGameException.class, () -> g.redo());
    }

    @Test
    void redo() throws TurnException, CommandException {
        g.start();

        g.movePiece(0, 0, 0, 1);

        g.undo();
        g.redo();

        assertTrue(g.getGameboard().getTile(0, 1).getPiece().isPresent());
        assertTrue(g.getGameboard().getTile(0, 0).getPiece().isEmpty());
    }

    @Test
    void entireGame() throws TurnException, CommandException {
        g.start();

        // 1st Turn (Player 1)
        g.movePiece(0, 0, 0, 1);
        g.movePiece(0, 1, 0, 2);
        g.movePiece(0, 2, 0, 3);

        assertTrue(g.getGameboard().getTile(0, 3).getPiece().isPresent());

        // End Turn
        g.endTurn();

        //2nd Turn (Player 2)
        g.movePiece(0, 6, 0, 5);
        g.movePiece(0, 5, 1, 5);
        g.passBall(3, 6, 6, 6);

        assertTrue(g.getGameboard().getTile(1, 5).getPiece().isPresent());
        assertEquals(6, g.getPlayer2().getBall().getPiece().getTile().getX());
        assertEquals(6, g.getPlayer2().getBall().getPiece().getTile().getY());

        // End turn
        g.endTurn();

        // 3rd turn (Player 1)
        g.movePiece(0, 3, 0, 4);
        g.movePiece(0, 4, 0, 5);
        g.movePiece(0, 5, 0, 6);

        assertTrue(g.getGameboard().getTile(0, 6).getPiece().isPresent());

        // End turn
        g.endTurn();

        // 4th turn (Player 2)
        g.movePiece(5, 6, 5, 5);
        g.movePiece(5, 5, 5, 4);
        g.movePiece(5, 4, 5, 3);

        assertTrue(g.getGameboard().getTile(5, 3).getPiece().isPresent());

        // End turn
        g.endTurn();

        //5th turn and victory (Player 1)
        g.movePiece(1, 0, 0, 0);
        g.passBall(3, 0, 0, 0);
        g.passBall(0, 0, 0, 6);

        // Le joueur 1 est vainqueur
        assertTrue(g.getPlayer1().isVictory());
    }
}
