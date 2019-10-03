package diaballik.model;

public class Command implements Undoable, Action {

	private Game game;

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
