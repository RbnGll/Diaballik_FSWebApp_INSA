package diaballik.model.game;

import diaballik.model.control.Command;
import diaballik.model.control.MovePiece;
import diaballik.model.exception.CommandException;
import diaballik.model.exception.turn.EndTurnException;
import diaballik.model.exception.turn.TurnException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class TurnTest {

    Turn turn;
    Game game;

    @BeforeEach
    void init() {
        game = new Game(Color.BLACK, "Robin", Color.WHITE, "Ronan");
        game.start();
        turn = game.getCurrentTurn();
    }

    @Test
    void construction() {
        assertNotNull(turn.getUndoDeque());
        assertNotNull(turn.getRedoDeque());
        assertFalse(turn.isTurnEnd());
    }

    @Test
    void checkEndTurnTrue() {
        Command command = new MovePiece(0, 0, 0, 1, game);

        IntStream.range(0, 3).forEach(i -> turn.getUndoDeque().add(command));
        assertTrue(turn.checkEndTurn());
    }

    @Test
    void checkEndTurnFalse() {
        Command command = new MovePiece(0, 0, 0, 1, game);

        IntStream.range(0, 2).forEach(i -> turn.getUndoDeque().add(command));
        assertFalse(turn.checkEndTurn());
    }

    @Test
    void invokeFirstCommand() throws TurnException, CommandException {
        Command command = new MovePiece(0, 0, 0, 1, game);

        assertEquals(0, turn.getUndoDeque().size());
        assertEquals(0, turn.getRedoDeque().size());

        assertTrue(turn.invokeCommand(command));

        assertEquals(1, turn.getUndoDeque().size());
        assertEquals(0, turn.getRedoDeque().size());
        assertFalse(turn.isTurnEnd());
    }

    @Test
    void invokeCommandTurnEnd() throws TurnException, CommandException {
        Command command1 = new MovePiece(0, 0, 0, 1, game);
        assertTrue(turn.invokeCommand(command1));
        Command command2 = new MovePiece(0, 1, 0, 2, game);
        assertTrue(turn.invokeCommand(command2));
        Command command3 = new MovePiece(0, 2, 0, 3, game);
        assertTrue(turn.invokeCommand(command3));
        Command command4 = new MovePiece(0, 3, 0, 4, game);
        assertThrows(TurnException.class, () -> turn.invokeCommand(command4));
    }

    @Test
    void invokeCommandCantDo() {
        Command command = new MovePiece(0, 0, 0, 0, game);
        assertThrows(CommandException.class, () -> turn.invokeCommand(command));
    }

    @Test
    void undo() throws TurnException, CommandException {
        Command command = new MovePiece(0, 0, 0, 1, game);

        assertTrue(turn.invokeCommand(command));

        assertTrue(game.getGameboard().getTile(0, 0).getPiece().isEmpty());

        turn.undo();

        assertEquals(0, turn.getUndoDeque().size());
        assertEquals(1, turn.getRedoDeque().size());
        assertTrue(game.getGameboard().getTile(0, 0).getPiece().isPresent());
    }

    @Test
    void redo() throws TurnException, CommandException {
        Command command = new MovePiece(0, 0, 0, 1, game);
        assertTrue(turn.invokeCommand(command));

        assertEquals(1, turn.getUndoDeque().size());
        assertEquals(0, turn.getRedoDeque().size());
        assertTrue(game.getGameboard().getTile(0, 0).getPiece().isEmpty());
        assertTrue(game.getGameboard().getTile(0, 1).getPiece().isPresent());
        assertTrue(game.getGameboard().getTile(0, 2).getPiece().isEmpty());

        // Obligé de le faire dans cet ordre car l'état est donné à la création de
        // La commande et non à son invocation
        Command command2 = new MovePiece(0, 1, 0, 2, game);

        assertTrue(turn.invokeCommand(command2));

        assertEquals(2, turn.getUndoDeque().size());
        assertEquals(0, turn.getRedoDeque().size());
        assertTrue(game.getGameboard().getTile(0, 0).getPiece().isEmpty());
        assertTrue(game.getGameboard().getTile(0, 1).getPiece().isEmpty());
        assertTrue(game.getGameboard().getTile(0, 2).getPiece().isPresent());

        turn.undo();

        assertEquals(1, turn.getUndoDeque().size());
        assertEquals(1, turn.getRedoDeque().size());
        assertTrue(game.getGameboard().getTile(0, 0).getPiece().isEmpty());
        assertTrue(game.getGameboard().getTile(0, 1).getPiece().isPresent());
        assertTrue(game.getGameboard().getTile(0, 2).getPiece().isEmpty());

        turn.redo();

        assertEquals(2, turn.getUndoDeque().size());
        assertEquals(0, turn.getRedoDeque().size());
        assertTrue(game.getGameboard().getTile(0, 0).getPiece().isEmpty());
        assertTrue(game.getGameboard().getTile(0, 1).getPiece().isEmpty());
        assertTrue(game.getGameboard().getTile(0, 2).getPiece().isPresent());
    }
}
