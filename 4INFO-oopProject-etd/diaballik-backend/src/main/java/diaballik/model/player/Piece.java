package diaballik.model.player;

import diaballik.model.game.Tile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.awt.Color;
import java.util.Optional;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Piece {

    @XmlTransient
    private Color color;

    private Tile tile;

    @XmlTransient
    private Optional<Ball> ball;

    // Constructeur sans paramètres pour utiliser REST
    Piece() {

    }

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

    @Override
    public String toString() {
        return tile.toString();
    }
}
