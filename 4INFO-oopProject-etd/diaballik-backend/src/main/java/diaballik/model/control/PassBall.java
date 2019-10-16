package diaballik.model.control;

import diaballik.model.game.Board;
import diaballik.model.game.Game;
import diaballik.model.player.Piece;

public class PassBall extends Command {

    private Piece fromPiece;

    private Piece toPiece;

    private Board board;

    private Game game;

    public PassBall(Board b, Piece from, Piece to, Game g) {
        fromPiece = from;
        toPiece = to;
        board = b;
        game = g;
    }

    @Override
    public boolean exe() {
        return game.getCurrentPlayer().getBall().move(toPiece);
    }

    @Override
    public boolean canDo() {
        for (int i = 0; i < 7; i++) {
            if (!(game.getCurrentPlayer().getPieces()[i].equals(fromPiece))) {
                return false;
            }
        }

        if (!fromPiece.hasBall()) {
            return false;
        }
        for (int i = 0; i < 7; i++) {
            if (!(game.getCurrentPlayer().getPieces()[i].equals(toPiece))) {
                return false;
            }
            /*TODO contrainte pour une pièce au milieu de la passe*/
        }
        return true;
    }
}
