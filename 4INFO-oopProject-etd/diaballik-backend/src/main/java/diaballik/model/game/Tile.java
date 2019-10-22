package diaballik.model.game;

import diaballik.model.player.Piece;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Optional;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Tile {

    private int x;

    private int y;

    @XmlTransient
    private Optional<Piece> piece;

    // Constructeur sans paramètres pour utiliser REST
    public Tile() {

    }

    public Tile(final int x, final int y) {
        this.x = x;
        this.y = y;
        this.piece = Optional.empty();
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public Optional<Piece> getPiece() {
        return piece;
    }

    public void setPiece(final Optional<Piece> piece) {
        this.piece = piece;
        if (piece.isPresent()) {
            piece.get().setTile(this);
        }
    }

    @Override
    public String toString() {
        return "Tile : (" + this.x + ", " + this.y + ')';
    }
}
