package diaballik.model.game;

import diaballik.model.control.Command;
import diaballik.model.player.Player;

import java.util.ArrayDeque;
import java.util.Deque;

public class Turn {

    private Deque<Command> undoDeque;

    private Deque<Command> redoDeque;

    private boolean turnEnd;

    public Turn(final Player p) {
        undoDeque = new ArrayDeque<>();
        redoDeque = new ArrayDeque<>();
        turnEnd = false;
    }

    public boolean invokeCommand(final Command c) {
        if (c.canDo() && !turnEnd) {
            if (c.exe()) {
                undoDeque.add(c);
                redoDeque.clear();
                turnEnd = checkEndTurn();
                return true;
            }
        }
        return false;
    }

    public void undo() {
        final Command c = undoDeque.pop();
        c.undo();
        redoDeque.add(c);
    }

    public void redo() {
        final Command c = redoDeque.pop();
        c.exe();
        undoDeque.add(c);
    }

    public boolean checkEndTurn() {
        return undoDeque.size() == 3;
    }

}
