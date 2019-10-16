package diaballik.model.player;

import java.awt.Color;

public class Ball {

    private Color color;

    private Piece piece;

    public Ball(final Piece p) {
        piece = p;
    }

    public boolean move(final Piece newPiece) {
        piece = newPiece;
        return true;
    }

}
