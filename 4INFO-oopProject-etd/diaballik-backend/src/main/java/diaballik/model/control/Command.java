package diaballik.model.control;

public class Command implements Undoable, Action {

    // private Game game;

    @Override
    public boolean exe() {
        return false;
    }

    @Override
    public boolean canDo() {
        return false;
    }

    @Override
    public void redo() {

    }

    @Override
    public void undo() {

    }
}
