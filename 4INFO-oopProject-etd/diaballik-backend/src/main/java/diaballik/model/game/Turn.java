package diaballik.model.game;

import diaballik.model.control.Command;
import diaballik.model.exception.CommandException;
import diaballik.model.exception.turn.TurnException;
import diaballik.model.exception.turn.UndoRedoException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Turn {

    @XmlTransient
    private Deque<Command> undoDeque;

    @XmlTransient
    private Deque<Command> redoDeque;

    private boolean turnEnd;

    private int actionCount;
    private int redoCount;

    public Turn() {
        undoDeque = new ArrayDeque<>();
        redoDeque = new ArrayDeque<>();
        turnEnd = false;
        actionCount = 0;
        redoCount = 0;
    }

    public boolean invokeCommand(final Command c) throws TurnException, CommandException {

        // On set l'état des commandes à leur invocation (et non à leur création)
        c.setCurrentState();

        try {
            if (c.canDo()) {
                if (!turnEnd) {
                    if (c.exe()) {
                        undoDeque.addFirst(c);
                        redoDeque.clear();
                        turnEnd = checkEndTurn();
                        actionCount++;
                        redoCount = 0;
                        return true;
                    }
                } else {
                    throw new TurnException("Tu as déjà effectué tes 3 actions");
                }
            }
        } catch (CommandException | TurnException e) {
            throw e;
        }

        return false;
    }

    public void undo() throws UndoRedoException {

        final Command c;

        try {
            c = undoDeque.removeFirst();
        } catch (NoSuchElementException e) {
            throw new UndoRedoException();
        }

        c.undo();
        redoDeque.addFirst(c);
        redoCount++;

        actionCount--;

        // Mise à jour de turnEnd
        turnEnd = checkEndTurn();
    }

    public void redo() throws UndoRedoException {
        final Command c;

        try {
            c = redoDeque.removeFirst();
        } catch (NoSuchElementException e) {
            throw new UndoRedoException();
        }

        c.exe();
        undoDeque.addFirst(c);

        actionCount++;
        redoCount--;

        // Mise à jour de turnEnd
        turnEnd = checkEndTurn();
    }

    public boolean checkEndTurn() {
        return undoDeque.size() == 3;
    }

    public Deque<Command> getUndoDeque() {
        return undoDeque;
    }

    public Deque<Command> getRedoDeque() {
        return redoDeque;
    }

    public boolean isTurnEnd() {
        return turnEnd;
    }

    public void setTurnEnd(final boolean turnEnd) {
        this.turnEnd = turnEnd;
    }
}
