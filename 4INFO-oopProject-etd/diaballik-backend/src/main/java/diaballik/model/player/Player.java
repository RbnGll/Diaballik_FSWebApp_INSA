package diaballik.model.player;

import java.awt.Color;
import java.util.List;

public abstract class Player {

    protected String name;

    protected java.awt.Color color;

    protected boolean victory;

    protected List<Piece> pieces;

    private Ball ball;

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
