package diaballik.model.game;

import diaballik.model.control.Command;
import diaballik.model.control.MovePiece;
import diaballik.model.control.PassBall;
import diaballik.model.exception.CommandException;
import diaballik.model.exception.turn.EndTurnException;
import diaballik.model.exception.turn.TurnException;
import diaballik.model.exception.turn.UnstartedGameException;
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

    private boolean aiGame;

    private int turnCount;

    @XmlIDREF
    private Player currentPlayer;

    // Constructeur sans paramètres pour utiliser REST
    public Game() {

    }

    public Game(final Color c1, final String name1, final Color c2, final String name2) {
        gameboard = new Board();
        turnCount = 1;
        aiGame = false;

        // Création des joueurs
        player1 = new HumanPlayer(name1, c1, 1);
        player2 = new HumanPlayer(name2, c2, 2);

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
        turnCount = 1;
        aiGame = true;

        // Création des joueurs
        player1 = new HumanPlayer(name, c, 1);
        final Color cAI;
        if (c.equals(Color.BLACK)) {
            cAI = Color.WHITE;
        } else {
            cAI = Color.BLACK;
        }
        player2 = new AIPlayer("Computer", cAI, aiLevel, 2, this);

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

    public void movePiece(final int x1, final int y1, final int x2, final int y2) throws TurnException, CommandException {
        final Command c = new MovePiece(x1, y1, x2, y2, this);

        try {
            currentTurn.invokeCommand(c);
        } catch (NullPointerException e) {
            throw new UnstartedGameException();
        }
    }

    public void passBall(final int x1, final int y1, final int x2, final int y2) throws TurnException, CommandException {
        final Command c = new PassBall(x1, y1, x2, y2, this);

        if (currentTurn == null) {
            throw new UnstartedGameException();
        }

        if (currentTurn.invokeCommand(c)) {
            if (checkVictory().isPresent()) {
                victory(currentPlayer);
            }
        }
    }

    public void aiActions(final AIPlayer p) {
        IntStream.range(0, 3).forEach(i -> {
            try {
                final Command c = p.getCommand();
                if (c.getClass() == PassBall.class) {
                    final Tile from = ((PassBall) c).getFromPiece().get().getTile();
                    final Tile to = ((PassBall) c).getToPiece().get().getTile();
                    passBall(from.getX(), from.getY(), to.getX(), to.getY());
                } else {
                    final MovePiece move = (MovePiece) c;
                    movePiece(move.getX1(), move.getY1(), move.getX2(), move.getY2());
                }
            } catch (TurnException | CommandException e) {
                e.printStackTrace();
            }
        });
    }

    public void undo() throws TurnException {
        try {
            currentTurn.undo();
        } catch (NullPointerException e) {
            throw new UnstartedGameException();
        }
    }

    public void redo() throws TurnException {
        try {
            currentTurn.redo();
        } catch (NullPointerException e) {
            throw new UnstartedGameException();
        }
    }

    public void endTurn() throws TurnException {

        if (currentTurn == null) {
            throw new UnstartedGameException();
        }

        if (currentTurn.checkEndTurn()) {
            if (aiGame) {
                turnCount++;
                swapCurrentPlayer();
                currentTurn = new Turn();
                aiActions((AIPlayer) getCurrentPlayer());
                turnCount++;
                swapCurrentPlayer();
                currentTurn = new Turn();
            } else {
                turnCount++;
                swapCurrentPlayer();
                currentTurn = new Turn();
            }
        } else {
            throw new EndTurnException();
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

    public int getTurnCount() {
        return turnCount;
    }

    public void setAiGame(final boolean b) {
        this.aiGame = b;
    }

}
