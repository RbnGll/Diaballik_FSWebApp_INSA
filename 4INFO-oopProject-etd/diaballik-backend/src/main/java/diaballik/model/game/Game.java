package diaballik.model.game;

import diaballik.model.player.AIType;
import diaballik.model.player.Piece;
import diaballik.model.player.Player;
import org.eclipse.persistence.sessions.coordination.Command;

import java.awt.Color;
import java.util.Optional;

public class Game {

    private Player player2;

    private Player player1;

    private Board gameboard;

    private Turn currentTurn;

    private Command[] command;

    private Player currentPlayer;

    public Game(Color c1, String name1, Color c2, String name2) {

    }

    public Game(Color c1, String name1, AIType aiLevel) {

    }

    public void start() {

    }

    public Optional<Player> checkVictory() {
        return null;
    }

    public void exit() {

    }

    public void movePiece(Piece p, int x, int y) {

    }

    public void passBall(Piece p1, Piece p2) {

    }

    public void endTurn() {

    }

}
