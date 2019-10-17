package diaballik.model.player;

import diaballik.model.game.Tile;

import java.awt.Color;
import java.util.Optional;

public class Piece {

    private Color color;

    private Tile tile;

    public Piece(final Color c, final Tile tile) {
        color = c;
        this.tile = tile;
        tile.setPiece(Optional.of(this));

    }

    public Tile getTile() {
        return tile;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasBall() {
        return false;
    }

}
