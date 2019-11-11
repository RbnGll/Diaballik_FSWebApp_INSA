package diaballik.model.game;

import diaballik.model.control.Command;
import diaballik.model.control.MovePiece;
import diaballik.model.control.PassBall;
import diaballik.model.player.AIPlayer;
import diaballik.model.player.AIType;
import diaballik.model.player.Ball;
import diaballik.model.player.HumanPlayer;
import diaballik.model.player.Piece;
import diaballik.model.player.Player;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.awt.Color;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Game {

    private Player player1;

    private Player player2;

    @XmlTransient
    private Board gameboard;

    private Turn currentTurn;

    @XmlIDREF
    private Player currentPlayer;

    // Constructeur sans paramètres pour utiliser REST
    public Game() {

    }

    public Game(final Color c1, final String name1, final Color c2, final String name2) {
        gameboard = new Board();

        // Création des joueurs
        player1 = new HumanPlayer(name1, c1);
        player2 = new HumanPlayer(name2, c2);

        // Créations des pièces des joueurs
        player1.setPieces(IntStream
                .range(0, 7)
                .mapToObj(i -> new Piece(c1, gameboard.getTile(i % 7, i / 7)))
                .collect(Collectors.toList()));

        player2.setPieces(IntStream
                .range(42, 49)
                .mapToObj(i -> new Piece(c2, gameboard.getTile(i % 7, i / 7)))
                .collect(Collectors.toList()));

        // Création des balles des joueurs
        player1.setBall(new Ball(player1.getPieces().get(Board.BOARDSIZE / 2)));
        player2.setBall(new Ball(player2.getPieces().get(Board.BOARDSIZE / 2)));
    }

    public Game(final Color c, final String name, final AIType aiLevel) {
        gameboard = new Board();
        // Création des joueurs
        player1 = new HumanPlayer(name, c);
        final Color cAI;
        if (c.equals(Color.BLACK)) {
            cAI = Color.WHITE;
        }else {
            cAI = Color.BLACK;
        }
        player2 = new AIPlayer("Computer", cAI, aiLevel);

        // Créations des pièces des joueurs
        player1.setPieces(IntStream
                .range(0, 7)
                .mapToObj(i -> new Piece(c, gameboard.getTile(i % 7, i / 7)))
                .collect(Collectors.toList()));

        player2.setPieces(IntStream
                .range(42, 49)
                .mapToObj(i -> new Piece(cAI, gameboard.getTile(i % 7, i / 7)))
                .collect(Collectors.toList()));

        // Création des balles des joueurs
        player1.setBall(new Ball(player1.getPieces().get(Board.BOARDSIZE / 2)));
        player2.setBall(new Ball(player2.getPieces().get(Board.BOARDSIZE / 2)));
    }

    public void start() {
        currentPlayer = player1;
        currentTurn = new Turn();
    }

    public Optional<Player> checkVictory() {

        //TODO : Normalement on peut encore optimiser la méthode en fonction du joueur qui joue uniquement

        // Si la balle du joueur 1 est sur la dernière rangée
        final Tile t1 = player1.getBall().getPiece().getTile();
        if (t1.getY() == Board.BOARDSIZE - 1) {
            return Optional.of(player1);
        }

        // Si la balle du joueur 2 est sur la 1ère rangée
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

    public void passBall(final int x1, final int y1, final int x2, final int y2) {
        final Command c = new PassBall(x1, y1, x2, y2, this);
        if (currentTurn.invokeCommand(c)) {
            if (checkVictory().isPresent()) {
                victory(currentPlayer);
            }
        }
    }

    public void undo() {
        currentTurn.undo();
    }

    public void redo() {
        currentTurn.redo();
    }

    public void endTurn() {
        // Ou if currentTurn.getTurn
        if (currentTurn.checkEndTurn()) {
            swapCurrentPlayer();
            currentTurn = new Turn();
        }
    }

    public void swapCurrentPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Turn getCurrentTurn() {
        return currentTurn;
    }

    public Board getGameboard() {
        return gameboard;
    }
}
