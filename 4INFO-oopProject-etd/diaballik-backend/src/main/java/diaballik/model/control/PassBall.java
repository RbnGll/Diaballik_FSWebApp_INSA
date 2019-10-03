package diaballik.model.control;

import diaballik.model.game.Board;
import diaballik.model.game.Game;
import diaballik.model.player.Piece;

public class PassBall extends Command {

    private Piece fromPiece;

    private Command command;

    private Piece piece;

    private Piece toPiece;

    public PassBall(Board b, Piece from, Piece to, Game game) {

    }

    @Override
    public boolean exe() {
        return false;
    }

    @Override
    public boolean canDo() {
        return false;
    }

}
