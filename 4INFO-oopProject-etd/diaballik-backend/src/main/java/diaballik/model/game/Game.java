package diaballik.model.game;

import diaballik.model.control.Command;
import diaballik.model.control.MovePiece;
import diaballik.model.control.PassBall;
import diaballik.model.player.AIType;
import diaballik.model.player.Ball;
import diaballik.model.player.HumanPlayer;
import diaballik.model.player.Piece;
import diaballik.model.player.Player;

import java.awt.Color;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {

    private Player player2;

    private Player player1;

    private Board gameboard;

    private Turn currentTurn;

    private Player currentPlayer;

    public Game(final Color c1, final String name1, final Color c2, final String name2) {
        gameboard = new Board();
        player1 = new HumanPlayer(name1, c1);
        player2 = new HumanPlayer(name2, c2);
        currentPlayer = player1;
        player1.setPieces(IntStream
                .range(0, 7)
                .mapToObj(i -> new Piece(c1, gameboard.getTile(i % 7, i / 7)))
                .collect(Collectors.toList()));
        player2.setPieces(IntStream
                .range(42, 49)
                .mapToObj(i -> new Piece(c2, gameboard.getTile(i % 7, i / 7)))
                .collect(Collectors.toList()));
        player1.setBall(new Ball(player1.getPieces().get(Board.BOARDSIZE / 2)));
        player2.setBall(new Ball(player2.getPieces().get(Board.BOARDSIZE / 2)));
    }

    public Game(final Color c1, final String name1, final AIType aiLevel) {

    }

    public void start() {
        currentTurn = new Turn(currentPlayer);
    }

    public Optional<Player> checkVictory() {
        final Tile t1 = player1.getBall().getPiece().getTile();
        if (t1.getY() == Board.BOARDSIZE - 1) {
            return Optional.of(player1);
        }
        final Tile t2 = player2.getBall().getPiece().getTile();
        if (t2.getY() == 0) {
            return Optional.of(player2);
        }
        return Optional.empty();
    }

    public void victory(final Player p) {
        p.setVictory(true);
    }

    public void movePiece(final int x1, final int y1, final int x2, final int y2) {
        final Command c = new MovePiece(x1, y1, x2, y2, this);
        currentTurn.invokeCommand(c);
    }

    public void passBall(final Piece p1, final Piece p2) {
        final Command c = new PassBall(p1, p2, this);
        if (currentTurn.invokeCommand(c)) {
            if (checkVictory().isPresent()) {
                victory(currentPlayer);
            }
        }
    }

    public void endTurn() {
        if (currentTurn.checkEndTurn()) {
            swapCurrentPlayer();
            currentTurn = new Turn(currentPlayer);
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void swapCurrentPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

}
