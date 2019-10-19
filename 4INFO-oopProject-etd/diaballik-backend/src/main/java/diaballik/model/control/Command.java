package diaballik.model.control;

public abstract class Command implements Undoable, Action {

    // private Game game;

    @Override
    public abstract boolean exe();

    @Override
    public abstract boolean canDo();

    @Override
    public abstract void redo();

    @Override
    public abstract void undo();
}
