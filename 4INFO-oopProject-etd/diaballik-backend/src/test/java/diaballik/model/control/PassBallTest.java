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
        assertFalse(command.ifPiecesExists());
    }

    @Test
    void piecesExists() {
        PassBall command = new PassBall(0, 0, 6, 0, game);
        assertTrue(command.ifPiecesExists());
    }

    @Test
    void notBelongToCurrentPlayer() {
        PassBall command = new PassBall(0, 6, 6, 0, game);
        assertFalse(command.ifBelongToCurrentPlayer());
    }

    @Test
    void belongToCurrentPlayer() {
        PassBall command = new PassBall(0, 0, 6, 0, game);
        assertTrue(command.ifBelongToCurrentPlayer());
    }

    @Test
    void notContainsBall() {
        PassBall command = new PassBall(0, 0, 6, 0, game);
        assertFalse(command.ifPieceHasBall());
    }

    @Test
    void containsBall() {
        PassBall command = new PassBall(Board.BOARDSIZE / 2, 0, 6, 0, game);
        assertTrue(command.ifPieceHasBall());
    }

    @Test
    void notCorrectPath() {
        game.getGameboard().movePiece(6, 0, 3, 4);

        PassBall command = new PassBall(0, 0, 3, 4, game);
        assertFalse(command.ifCorrectPath());
    }

    @Test
    void correcPathDx() {
        PassBall command = new PassBall(0, 0, 3, 0, game);
        assertTrue(command.ifCorrectPath());
    }

    @Test
    void correcPathDy() {
        game.getGameboard().movePiece(6, 0, 0, 3);

        PassBall command = new PassBall(0, 0, 0, 3, game);
        assertTrue(command.ifCorrectPath());
    }

    @Test
    void correcPathDiag() {
        game.getGameboard().movePiece(6, 0, 3, 3);

        PassBall command = new PassBall(0, 0, 3, 3, game);
        assertTrue(command.ifCorrectPath());
    }

    @Test
    void correcPathDiagNegativeDx() {
        game.getGameboard().movePiece(0, 0, 0, 3);

        PassBall command = new PassBall(3, 0, 0, 3, game);
        assertTrue(command.ifCorrectPath());
    }

    @Test
    void getPathTilesDx() {
        PassBall command = new PassBall(0, 0, 6, 0, game);
        List<Tile> path = command.getPathTiles(command.getFromPiece().get(), command.getToPiece().get());

        assertTrue(
                IntStream
                        .range(0, 5)
                        .allMatch(i -> path.get(i).getX() == (i + 1) && path.get(i).getY() == 0)
        );
    }

    @Test
    void getPathTileDy() {
        PassBall command = new PassBall(0, 0, 0, 6, game);

        List<Tile> path = command.getPathTiles(command.getFromPiece().get(), command.getToPiece().get());

        assertTrue(
                IntStream
                        .range(0, 5)
                        .allMatch(i -> path.get(i).getX() == 0 && path.get(i).getY() == (i + 1))
        );
    }

    @Test
    void getPathTileDiagonal() {
        PassBall command = new PassBall(0, 0, 6, 6, game);

        List<Tile> path = command.getPathTiles(command.getFromPiece().get(), command.getToPiece().get());

        assertTrue(
                IntStream
                        .range(0, 5)
                        .allMatch(i -> path.get(i).getX() == (i + 1) && path.get(i).getY() == (i + 1))
        );
    }

    @Test
    void opponentOnPath() {
        game.getGameboard().movePiece(0, 6, 3, 3);

        PassBall command = new PassBall(0, 0, 6, 6, game);
        assertFalse(command.ifNoOpponentOnPath());
    }

    @Test
    void noOpponentOnPath() {
        PassBall command = new PassBall(0, 0, 6, 0, game);
        assertTrue(command.ifNoOpponentOnPath());
    }

    @Test
    void candDoDx() {
        PassBall command = new PassBall(Board.BOARDSIZE / 2, 0, 6, 0, game);
        assertTrue(command.canDo());
    }

    @Test
    void canDoDy() {
        game.getGameboard().movePiece(0, 0, Board.BOARDSIZE / 2, 5);

        PassBall command = new PassBall(Board.BOARDSIZE / 2, 0, Board.BOARDSIZE / 2, 5, game);
        assertTrue(command.canDo());
    }

    @Test
    void candDoDiag() {
        game.getGameboard().movePiece(0, 0, 0, 3);

        PassBall command = new PassBall(Board.BOARDSIZE / 2, 0, 0, 3, game);
        assertTrue(command.canDo());
    }

    @Test
    void execution() {
        PassBall command = new PassBall(Board.BOARDSIZE / 2, 0, 6, 0, game);

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

        Ball ball = game.getPlayer1().getBall();

        command.exe();

        command.undo();

        command.redo();

        assertFalse(command.getFromPiece().get().hasBall());
        assertTrue(command.getToPiece().get().hasBall());
        assertEquals(ball, command.getToPiece().get().getBall().get());
    }
}
