package diaballik.model.game;

import diaballik.model.control.Command;
import diaballik.model.player.Player;

import java.util.Deque;

public class Turn {

    private Deque<Command> undoDeque;

    private Deque<Command> redoDeque;

    private boolean turnEnd;

    private Player player;

    public Turn() {

    }

    public boolean invokeCommand(final Command c) {
        return false;
    }

    public void undo() {

    }

    public void redo() {

    }

    public boolean checkEndTurn() {
        return false;
    }

}
