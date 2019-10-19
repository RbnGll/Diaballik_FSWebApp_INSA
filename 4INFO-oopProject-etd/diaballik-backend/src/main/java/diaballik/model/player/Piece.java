package diaballik.model.player;

import diaballik.model.game.Tile;

import java.awt.Color;
import java.util.Optional;

public class Piece {

    private Color color;

    private Tile tile;

    private Optional<Ball> ball;

    public Piece(final Color c, final Tile tile) {
        color = c;
        this.tile = tile;
        ball = Optional.empty();
        tile.setPiece(Optional.of(this));
    }

    public Tile getTile() {
        return tile;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasBall() {
        return ball.isPresent();
    }

    public Optional<Ball> getBall() {
        return ball;
    }

    public void setBall(final Optional<Ball> ball) {
        this.ball = ball;
    }

    public void setTile(final Tile tile) {
        this.tile = tile;
    }
}
