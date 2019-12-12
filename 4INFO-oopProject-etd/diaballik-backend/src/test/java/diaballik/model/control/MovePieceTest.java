package diaballik.model.control;

import diaballik.model.game.Board;
import diaballik.model.game.Game;
import diaballik.model.game.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovePieceTest {

    Game game;

    @BeforeEach
    void init() {
        game = new Game(Color.WHITE, "Robin", Color.BLACK, "Ronan");
        game.start();
    }

    @Test
    void construction() {
        MovePiece command = new MovePiece(0, 0, 0, 1, game);
        command.setCurrentState();

        assertEquals(0, command.getX1());
        assertEquals(0, command.getY1());
        assertEquals(0, command.getX2());
        assertEquals(1, command.getY2());
        assertEquals(game, command.getGame());

        assertTrue(command.getPieceToMove().isPresent());
    }

    @Test
    void presentPiece() {
        MovePiece command = new MovePiece(3, 3, 3, 4, game);
        command.setCurrentState();
        assertFalse(command.ifPresentPiece());
    }

    @Test
    void absentPiece() {
        MovePiece command = new MovePiece(0, 0, 3, 4, game);
        command.setCurrentState();
        assertTrue(command.ifPresentPiece());
    }

    @Test
    void freePosition() {
        MovePiece command = new MovePiece(0, 0, 1, 0, game);
        command.setCurrentState();
        assertFalse(command.ifFreePosition());
    }

    @Test
    void occupiedPosition() {
        MovePiece command = new MovePiece(0, 0, 0, 1, game);
        command.setCurrentState();
        assertTrue(command.ifFreePosition());
    }

    @Test
    void notBelongstoCurrentPlayer() {
        MovePiece command = new MovePiece(0, 6, 0, 5, game);
        command.setCurrentState();
        assertFalse(command.ifBelongsToCurrentPlayer());
    }

    @Test
    void belongstoCurrentPlayer() {
        MovePiece command = new MovePiece(0, 0, 0, 5, game);
        command.setCurrentState();
        assertTrue(command.ifBelongsToCurrentPlayer());
    }

    @Test
    void containsBall() {
        MovePiece command = new MovePiece(Board.BOARDSIZE / 2, 0, Board.BOARDSIZE / 2, 1, game);
        command.setCurrentState();
        assertFalse(command.ifNotContainsBall());
    }

    @Test
    void notContainsBall() {
        MovePiece command = new MovePiece(2, 0, 0, 1, game);
        command.setCurrentState();
        assertTrue(command.ifNotContainsBall());
    }

    @Test
    void notCorrectPathDx() {
        game.getGameboard().movePiece(0, 0, 0, 1);
        MovePiece command = new MovePiece(0, 1, 2, 1, game);
        command.setCurrentState();
        assertFalse(command.ifCorrectPath());
    }

    @Test
    void correctPathDx() {
        game.getGameboard().movePiece(0, 0, 0, 1);
        MovePiece command = new MovePiece(0, 1, 1, 1, game);
        command.setCurrentState();
        assertTrue(command.ifCorrectPath());
    }

    @Test
    void notCorrectPathDy() {
        MovePiece command = new MovePiece(0, 0, 0, 2, game);
        command.setCurrentState();
        assertFalse(command.ifCorrectPath());
    }

    @Test
    void correctPathDy() {
        MovePiece command = new MovePiece(0, 0, 0, 1, game);
        command.setCurrentState();
        assertTrue(command.ifCorrectPath());
    }

    @Test
    void pieceNotMoved() {
        MovePiece command = new MovePiece(0, 0, 0, 0, game);
        command.setCurrentState();
        assertFalse(command.ifCorrectPath());
    }

    @Test
    void canDoDx() {
        game.getGameboard().movePiece(0, 0, 0, 1);
        MovePiece command = new MovePiece(0, 1, 1, 1, game);
        command.setCurrentState();
        assertTrue(command.canDo());
    }

    @Test
    void canDoDy() {
        MovePiece command = new MovePiece(0, 0, 0, 1, game);
        command.setCurrentState();
        assertTrue(command.canDo());
    }

    @Test
    void execution() {
        MovePiece command = new MovePiece(0, 0, 0, 1, game);
        command.setCurrentState();

        assertTrue(command.getPieceToMove().isPresent());
        Tile from = command.getPieceToMove().get().getTile();

        assertEquals(0, from.getX());
        assertEquals(0, from.getY());

        assertTrue(command.exe());
        assertTrue(from.getPiece().isEmpty());

        Tile to = command.getPieceToMove().get().getTile();

        assertEquals(0, to.getX());
        assertEquals(1, to.getY());
    }

    @Test
    void undo() {
        MovePiece command = new MovePiece(0, 0, 0, 1, game);
        command.setCurrentState();

        Tile from = command.getPieceToMove().get().getTile();

        assertEquals(0, from.getX());
        assertEquals(0, from.getY());

        command.exe();

        assertTrue(from.getPiece().isEmpty());
        Tile to = command.getPieceToMove().get().getTile();
        assertTrue(to.getPiece().isPresent());

        assertEquals(0, to.getX());
        assertEquals(1, to.getY());

        command.undo();

        assertTrue(from.getPiece().isPresent());
        assertTrue(to.getPiece().isEmpty());

        assertEquals(from, command.getPieceToMove().get().getTile());
    }

    @Test
    void redo() {
        MovePiece command = new MovePiece(0, 0, 0, 1, game);
        command.setCurrentState();

        Tile from = command.getPieceToMove().get().getTile();

        command.exe();

        Tile to = command.getPieceToMove().get().getTile();

        command.undo();

        command.redo();

        assertTrue(from.getPiece().isEmpty());
        assertTrue(to.getPiece().isPresent());
    }

}
