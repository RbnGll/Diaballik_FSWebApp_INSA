package diaballik.model.game;

import diaballik.model.control.Command;

import java.util.ArrayDeque;
import java.util.Deque;

public class Turn {

    private Deque<Command> undoDeque;

    private Deque<Command> redoDeque;

    private boolean turnEnd;

    public Turn() {
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

    public Deque<Command> getUndoDeque() {
        return undoDeque;
    }

    public void setUndoDeque(final Deque<Command> undoDeque) {
        this.undoDeque = undoDeque;
    }

    public Deque<Command> getRedoDeque() {
        return redoDeque;
    }

    public void setRedoDeque(final Deque<Command> redoDeque) {
        this.redoDeque = redoDeque;
    }

    public boolean isTurnEnd() {
        return turnEnd;
    }

    public void setTurnEnd(final boolean turnEnd) {
        this.turnEnd = turnEnd;
    }
}
