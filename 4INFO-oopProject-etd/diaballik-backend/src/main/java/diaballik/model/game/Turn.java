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

        // TODO : On set l'état des commandes à leur création et non à leur invocation
        // Ici il faudrait lancer un commande.setState avant exécution pour que la commande recherche ensuite la pièce qui correspond etc
        // Et qu'elle ne fasse pas cela à sa création

        if (c.canDo() && !turnEnd) {
            if (c.exe()) {
                undoDeque.addFirst(c);
                redoDeque.clear();
                turnEnd = checkEndTurn();
                return true;
            }
        }
        return false;
    }

    public void undo() {
        final Command c = undoDeque.removeFirst();
        c.undo();
        redoDeque.addFirst(c);
    }

    public void redo() {
        final Command c = redoDeque.removeFirst();
        c.exe();
        undoDeque.addFirst(c);
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
