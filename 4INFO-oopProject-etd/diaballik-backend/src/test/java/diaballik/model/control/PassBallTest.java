package diaballik.model.control;

import diaballik.model.game.Board;
import diaballik.model.game.Game;
import diaballik.model.game.Tile;
import diaballik.model.player.Ball;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassBallTest {

    Game game;

    @BeforeEach
    void init() {
        game = new Game(Color.WHITE, "Robin", Color.BLACK, "Ronan");
        game.start();
    }

    @Test
    void construction() {
        PassBall command = new PassBall(0, 0, 6, 0, game);
        command.setCurrentState();

        assertTrue(command.getFromPiece().isPresent());
        assertTrue(command.getToPiece().isPresent());

        assertEquals(0, command.getFromPiece().get().getTile().getX());
        assertEquals(0, command.getFromPiece().get().getTile().getY());
        assertEquals(6, command.getToPiece().get().getTile().getX());
        assertEquals(0, command.getToPiece().get().getTile().getY());
    }

    @Test
    void piecesNotExists() {
        PassBall command = new PassBall(0, 0, 6, 1, game);
        command.setCurrentState();
        assertFalse(command.ifPiecesExists());
    }

    @Test
    void piecesExists() {
        PassBall command = new PassBall(0, 0, 6, 0, game);
        command.setCurrentState();
        assertTrue(command.ifPiecesExists());
    }

    @Test
    void notBelongToCurrentPlayer() {
        PassBall command = new PassBall(0, 6, 6, 0, game);
        command.setCurrentState();
        assertFalse(command.ifBelongToCurrentPlayer());
    }

    @Test
    void belongToCurrentPlayer() {
        PassBall command = new PassBall(0, 0, 6, 0, game);
        command.setCurrentState();
        assertTrue(command.ifBelongToCurrentPlayer());
    }

    @Test
    void notContainsBall() {
        PassBall command = new PassBall(0, 0, 6, 0, game);
        command.setCurrentState();
        assertFalse(command.ifPieceHasBall());
    }

    @Test
    void containsBall() {
        PassBall command = new PassBall(Board.BOARDSIZE / 2, 0, 6, 0, game);
        command.setCurrentState();
        assertTrue(command.ifPieceHasBall());
    }

    @Test
    void notCorrectPath() {
        game.getGameboard().movePiece(6, 0, 3, 4);

        PassBall command = new PassBall(0, 0, 3, 4, game);
        command.setCurrentState();
        assertFalse(command.ifCorrectPath());
    }

    @Test
    void correcPathDx() {
        PassBall command = new PassBall(0, 0, 3, 0, game);
        command.setCurrentState();
        assertTrue(command.ifCorrectPath());
    }

    @Test
    void correcPathDy() {
        game.getGameboard().movePiece(6, 0, 0, 3);

        PassBall command = new PassBall(0, 0, 0, 3, game);
        command.setCurrentState();
        assertTrue(command.ifCorrectPath());
    }

    @Test
    void correcPathDiag() {
        game.getGameboard().movePiece(6, 0, 3, 3);

        PassBall command = new PassBall(0, 0, 3, 3, game);
        command.setCurrentState();
        assertTrue(command.ifCorrectPath());
    }

    @Test
    void correcPathDiagNegativeDx() {
        game.getGameboard().movePiece(0, 0, 0, 3);

        PassBall command = new PassBall(3, 0, 0, 3, game);
        command.setCurrentState();
        assertTrue(command.ifCorrectPath());
    }

    @Test
    void getPathTilesDx() {
        PassBall command = new PassBall(0, 0, 6, 0, game);
        command.setCurrentState();
        List<Tile> path = command.getPathTiles();

        assertTrue(
                IntStream
                        .range(0, 5)
                        .allMatch(i -> path.get(i).getX() == (i + 1) && path.get(i).getY() == 0)
        );
    }

    @Test
    void getPathTileDy() {
        PassBall command = new PassBall(0, 0, 0, 6, game);
        command.setCurrentState();

        List<Tile> path = command.getPathTiles();

        assertTrue(
                IntStream
                        .range(0, 5)
                        .allMatch(i -> path.get(i).getX() == 0 && path.get(i).getY() == (i + 1))
        );
    }

    @Test
    void getPathTileDyBis() {
        PassBall command = new PassBall(3, 0, 1, 0, game);
        command.setCurrentState();

        List<Tile> path = command.getPathTiles();

        assertEquals(1, path.size());
        assertEquals(2, path.get(0).getX());
        assertEquals(0, path.get(0).getY());
    }

    @Test
    void getPathTileDiagonal() {
        game.getGameboard().movePiece(6, 6, 6, 5);
        PassBall command = new PassBall(6, 5, 1, 0, game);
        command.setCurrentState();

        List<Tile> path = command.getPathTiles();

        System.out.println(path);

        for (int i = 0; i < 4; i++) {
            assertTrue(path.get(i).getX() == (5 - i) && path.get(i).getY() == (5 - i - 1));
        }
    }

    @Test
    void opponentOnPath() {
        game.getGameboard().movePiece(0, 6, 3, 3);

        PassBall command = new PassBall(0, 0, 6, 6, game);
        command.setCurrentState();
        assertFalse(command.ifNoPieceOnPath());
    }

    @Test
    void friendOnPath() {
        PassBall command = new PassBall(0, 0, 6, 0, game);
        command.setCurrentState();
        assertFalse(command.ifNoPieceOnPath());
    }

    @Test
    void friendOnPathBis() {
        PassBall command = new PassBall(3, 0, 0, 0, game);
        command.setCurrentState();
        assertFalse(command.ifNoPieceOnPath());
    }

    @Test
    void noOpponentOnPath() {
        game.getGameboard().movePiece(6, 0, 3, 2);
        PassBall command = new PassBall(3, 2, 5, 0, game);
        command.setCurrentState();
        assertTrue(command.ifNoPieceOnPath());
    }

    @Test
    void candDoDx() {
        PassBall command = new PassBall(Board.BOARDSIZE / 2, 0, 4, 0, game);
        command.setCurrentState();
        assertTrue(command.canDo());
    }

    @Test
    void canDoDy() {
        game.getGameboard().movePiece(0, 0, Board.BOARDSIZE / 2, 5);

        PassBall command = new PassBall(Board.BOARDSIZE / 2, 0, Board.BOARDSIZE / 2, 5, game);
        command.setCurrentState();
        assertTrue(command.canDo());
    }

    @Test
    void candDoDiag() {
        game.getGameboard().movePiece(0, 0, 0, 3);

        PassBall command = new PassBall(Board.BOARDSIZE / 2, 0, 0, 3, game);
        command.setCurrentState();
        assertTrue(command.canDo());
    }

    @Test
    void execution() {
        PassBall command = new PassBall(Board.BOARDSIZE / 2, 0, 6, 0, game);
        command.setCurrentState();

        Ball ball = game.getPlayer1().getBall();

        assertTrue(command.getFromPiece().get().hasBall());
        assertEquals(ball, command.getFromPiece().get().getBall().get());
        assertFalse(command.getToPiece().get().hasBall());

        // Exécution de la commande
        assertTrue(command.exe());

        assertFalse(command.getFromPiece().get().hasBall());
        assertTrue(command.getToPiece().get().hasBall());
        assertEquals(ball, command.getToPiece().get().getBall().get());
    }

    @Test
    void undo() {
        PassBall command = new PassBall(Board.BOARDSIZE / 2, 0, 6, 0, game);
        command.setCurrentState();

        Ball ball = game.getPlayer1().getBall();

        command.exe();

        command.undo();

        assertTrue(command.getFromPiece().get().hasBall());
        assertEquals(ball, command.getFromPiece().get().getBall().get());
        assertFalse(command.getToPiece().get().hasBall());
    }

    @Test
    void redo() {
        PassBall command = new PassBall(Board.BOARDSIZE / 2, 0, 6, 0, game);
        command.setCurrentState();

        Ball ball = game.getPlayer1().getBall();

        command.exe();

        command.undo();

        command.redo();

        assertFalse(command.getFromPiece().get().hasBall());
        assertTrue(command.getToPiece().get().hasBall());
        assertEquals(ball, command.getToPiece().get().getBall().get());
    }
}