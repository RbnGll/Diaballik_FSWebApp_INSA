package diaballik.model.player;

import java.awt.Color;
import java.util.Optional;

public class Ball {

    private Color color;

    private Piece piece;

    public Ball(final Piece p) {
        piece = p;
        color = piece.getColor();
        p.setBall(Optional.of(this));
    }

    public boolean move(final Piece newPiece) {
        piece.setBall(Optional.empty());
        newPiece.setBall(Optional.of(this));
        piece = newPiece;
        return true;
    }

    public Piece getPiece() {
        return piece;
    }
}
