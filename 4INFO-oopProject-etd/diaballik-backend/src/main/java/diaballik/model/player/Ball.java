package diaballik.model.player;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.awt.Color;
import java.util.Optional;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Ball {

    @XmlTransient
    private Color color;

    private Piece piece;

    // Constructeur sans paramètres pour utiliser REST
    Ball() {

    }

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
