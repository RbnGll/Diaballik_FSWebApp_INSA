package diaballik.model;

public class PassBall extends Command {

	private Piece fromPiece;

	private Command command;

	private Piece piece;

	private Piece toPiece;

	public PassBall(Board b, Piece from, Piece to, Game game) {

	}

	public boolean exe() {
		return false;
	}

	public boolean canDo() {
		return false;
	}

}
