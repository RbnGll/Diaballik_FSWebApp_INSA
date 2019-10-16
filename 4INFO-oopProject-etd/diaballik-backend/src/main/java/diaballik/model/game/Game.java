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

    public Game(final Color c1, final String name1, final Color c2, final String name2) {

    }

    public Game(final Color c1, final String name1, final AIType aiLevel) {

    }

    public void start() {

    }

    public Optional<Player> checkVictory() {
        return Optional.empty();
    }

    public void exit() {

    }

    public void movePiece(final Piece p, final int x, final int y) {

    }

    public void passBall(final Piece p1, final Piece p2) {

    }

    public void endTurn() {

    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

}
