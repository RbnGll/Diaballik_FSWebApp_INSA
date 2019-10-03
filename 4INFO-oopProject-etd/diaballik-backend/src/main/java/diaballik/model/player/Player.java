package diaballik.model.player;

import java.awt.Color;

public abstract class Player {

    private String name;

    private java.awt.Color color;

    private boolean victory;

    private Piece[] pieces;

    public Player(String name, Color color) {
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

    public void setVictory(boolean victory) {
        this.victory = victory;
    }

    public Piece[] getPieces() {
        return pieces;
    }

    public void setPieces(Piece[] pieces) {
        this.pieces = pieces;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    private Ball ball;

}
