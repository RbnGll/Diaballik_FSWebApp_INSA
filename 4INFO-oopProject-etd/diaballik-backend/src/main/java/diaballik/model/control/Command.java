package diaballik.model.control;

import diaballik.model.game.Game;

public abstract class Command implements Undoable, Action {

    protected int x1;
    protected int y1;
    protected int x2;
    protected int y2;

    protected Game game;

    public Command(final int x1, final int y1, final int x2, final int y2, final Game game) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        this.game = game;
    }

    @Override
    public abstract boolean exe();

    @Override
    public abstract boolean canDo();

    @Override
    public abstract void redo();

    @Override
    public abstract void undo();

    public abstract void setCurrentState();
}
