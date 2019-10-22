package diaballik.model.game;

import diaballik.model.player.Piece;
import diaballik.model.player.Player;
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
    void movePieceDy() {
        g.start();

        assertTrue(g.getGameboard().getTile(0, 0).getPiece().isPresent());
        assertTrue(g.getGameboard().getTile(0, 1).getPiece().isEmpty());

        g.movePiece(0, 0, 0, 1);

        assertTrue(g.getGameboard().getTile(0, 0).getPiece().isEmpty());
        assertTrue(g.getGameboard().getTile(0, 1).getPiece().isPresent());
    }

    @Test
    void movePieceDx() {
        g.start();

        g.movePiece(0, 0, 0, 1);

        assertTrue(g.getGameboard().getTile(0, 1).getPiece().isPresent());
        assertTrue(g.getGameboard().getTile(1, 1).getPiece().isEmpty());

        g.movePiece(0, 1, 1, 1);

        assertTrue(g.getGameboard().getTile(0, 1).getPiece().isEmpty());
        assertTrue(g.getGameboard().getTile(1, 1).getPiece().isPresent());
    }

    @Test
    void movePieceDiag() {
        g.start();

        assertTrue(g.getGameboard().getTile(0, 0).getPiece().isPresent());
        assertTrue(g.getGameboard().getTile(1, 1).getPiece().isEmpty());

        g.movePiece(0, 0, 1, 1);

        assertTrue(g.getGameboard().getTile(0, 0).getPiece().isPresent());
        assertTrue(g.getGameboard().getTile(1, 1).getPiece().isEmpty());
    }

    @Test
    void passBallDx() {
        g.start();

        assertFalse(g.getGameboard().getTile(0, 0).getPiece().get().hasBall());
        assertTrue(g.getGameboard().getTile(g.getGameboard().BOARDSIZE / 2, 0).getPiece().get().hasBall());

        g.passBall(g.getGameboard().BOARDSIZE / 2, 0, 0, 0);

        assertTrue(g.getGameboard().getTile(0, 0).getPiece().get().hasBall());
        assertFalse(g.getGameboard().getTile(g.getGameboard().BOARDSIZE / 2, 0).getPiece().get().hasBall());
    }

    @Test
    void passBallDy() {
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
    void passBallDiag() {
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
    void passBallOpponentOnPath() {
        // Placer l'adversaire au milieu du plateau

        g.getGameboard().movePiece(6, 6, 3, 3);
        g.getGameboard().movePiece(6, 0, 5, 5);

        g.start();

        g.passBall(Board.BOARDSIZE / 2, 0, 0, 0);

        assertFalse(g.getGameboard().getTile(5, 5).getPiece().get().hasBall());
        assertTrue(g.getGameboard().getTile(0, 0).getPiece().get().hasBall());

        g.passBall(0, 0, 5, 5);

        assertFalse(g.getGameboard().getTile(5, 5).getPiece().get().hasBall());
        assertTrue(g.getGameboard().getTile(0, 0).getPiece().get().hasBall());
    }

    @Test
    void endTurnTrue() {
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
    void endTurnFalse() {
        g.start();

        // Pas 3 actions donc fin de tour pas possible

        Player currentPlayer = g.getCurrentPlayer();
        Turn currentTurn = g.getCurrentTurn();

        g.endTurn();

        assertEquals(currentPlayer, g.getCurrentPlayer());
        assertEquals(currentTurn, g.getCurrentTurn());
    }
}
