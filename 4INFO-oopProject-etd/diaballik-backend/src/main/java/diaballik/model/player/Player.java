package diaballik.model.player;

import diaballik.resource.adapters.ColorAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.awt.Color;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({HumanPlayer.class, AIPlayer.class})
public abstract class Player {

    @XmlID
    protected String name;

    @XmlJavaTypeAdapter(ColorAdapter.class)
    protected java.awt.Color color;

    protected boolean victory;

    protected List<Piece> pieces;

    private Ball ball;

    // Constructeur sans paramètres pour utiliser REST
    Player() {

    }

    public Player(final String name, final Color color) {
        this.name = name;
        this.color = color;
        this.victory = false;
    }

    public boolean isVictory() {
        return victory;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public void setVictory(final boolean victory) {
        this.victory = victory;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(final List<Piece> p) {
        pieces = p;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(final Ball ball) {
        this.ball = ball;
    }

}
