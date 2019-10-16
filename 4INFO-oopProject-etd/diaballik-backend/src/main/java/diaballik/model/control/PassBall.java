package diaballik.model.control;

import diaballik.model.game.Board;
import diaballik.model.game.Game;
import diaballik.model.player.Piece;

public class PassBall extends Command {

    private Piece fromPiece;

    private Piece toPiece;

    private Board board;

    private Game game;

    public PassBall(final Board b, final Piece from, final Piece to, final Game g) {
        fromPiece = from;
        toPiece = to;
        game = g;
        board = b;
    }

    @Override
    public boolean exe() {
        return game.getCurrentPlayer().getBall().move(toPiece);
    }

    @Override
    public boolean canDo() {
        return false;
    }

}
