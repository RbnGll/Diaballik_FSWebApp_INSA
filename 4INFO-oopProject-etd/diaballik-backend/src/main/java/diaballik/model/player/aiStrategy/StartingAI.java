package diaballik.model.player.aiStrategy;

import diaballik.model.control.Command;
import diaballik.model.control.MovePiece;
import diaballik.model.control.PassBall;
import diaballik.model.game.Board;
import diaballik.model.game.Game;
import diaballik.model.game.Tile;
import diaballik.model.player.Piece;
import diaballik.model.player.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;

public class StartingAI extends AIStrategy {

    private Random r;
    private Player opponent;

    public StartingAI(final Game g) {
        super(g);
        opponent = getOpponent();
        r = new Random();

    }

    @Override
    public Command execute() {
        Command bestAction = getBestAction();
        if (bestAction == null) {
            // If there is no best action, return a random action
            bestAction = new NoobAI(game).execute();
        }
        return bestAction;
    }

    public Command getBestAction() {
        //Try to block a pass
        Command bestAction = tryToBlockPass();
        //If no pass can be blocked, try to stop the opponent to go forward
        if (bestAction == null) {
            bestAction = tryToBlockForwardMoves();
        }
        //If there is no blocking move possible, return bestAction as null
        return bestAction;
    }

    public Command tryToBlockPass() {
        final Map<Tile, Command> moves = getPossiblePosition(game.getCurrentPlayer());
        final List<Tile> positionToBlockPass = getPassesPath(opponent);
        final List<Command> blockingPassMove = positionToBlockPass.stream()
                .map(moves::get) //Get every move from "moves" indexed by a tile which is in "positionToBlockPass" list
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (blockingPassMove.isEmpty()) {
            return null;
        }
        //Choose a random move from "blockingPassPosition" move list
        return blockingPassMove.get(r.nextInt(blockingPassMove.size()));
    }

    public Command tryToBlockForwardMoves() {
        final Map<Tile, Command> aiMoves = getPossiblePosition(game.getCurrentPlayer());
        final List<Command> opponentMoves = getPossibleMovePieceNotCurrentPlayer(opponent);
        final List<Command> blockingMoves = opponentMoves.stream().map(command -> {
            final MovePiece move = (MovePiece) command;
            if ((move.getY2() - move.getY1()) > 0) { // Verify if the opponent moves were going forward
                return aiMoves.get(game.getGameboard().getTile(move.getX2(), move.getY2())); //Get move from "aiMoves" indexed by a tile which is in "opponentMoves"
            } else {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
        if (blockingMoves.isEmpty()) {
            //If there is no blocking move possible, return null
            return null;
        }
        return blockingMoves.get(r.nextInt(blockingMoves.size()));
    }

    private Player getOpponent() {
        return game.getCurrentPlayer() == game.getPlayer1() ? game.getPlayer2() : game.getPlayer1();
    }

    private List<Tile> getPassesPath(final Player player) {
        final List<Command> passes = getPossiblePassBallNotCurrentPlayer(player);
        return passes.stream().map(command -> {
            final PassBall pass = (PassBall) command;
            return pass.getPathTiles();
        }).flatMap(List::stream).collect(Collectors.toList());
    }

    private Map<Tile, Command> getPossiblePosition(final Player player) {
        final List<Command> actions = getPossibleActionsForPlayer(player);
        final List<Command> moves = actions.stream()
                .filter(command -> command.getClass() == MovePiece.class)
                .collect(Collectors.toList());
        final Map<Tile, Command> indexedMoves = new HashMap<>();
        moves.stream().forEach(command -> {
            final MovePiece move = (MovePiece) command;
            final Tile t = game.getGameboard().getTile(move.getX2(), move.getY2());
            indexedMoves.put(t, command);
        });
        return indexedMoves;
    }

    public List<Command> getPossiblePassBallNotCurrentPlayer(final Player player) {
        final Piece p = player.getBall().getPiece();
        final int x = p.getTile().getX();
        final int y = p.getTile().getY();
        return IntStream.range(1, Board.BOARDSIZE)
                .mapToObj(i -> {
                    final List<Command> commands = Arrays.asList(
                            // Toutes les lignes droites que peut faire une balle
                            new PassBall(x, y, x + i, y, game),
                            new PassBall(x, y, x - i, y, game),
                            new PassBall(x, y, x, y + i, game),
                            new PassBall(x, y, x, y - i, game),
                            // Toutes les diagonales droites que peut faire une balle
                            new PassBall(x, y, x + i, y + i, game),
                            new PassBall(x, y, x - i, y + i, game),
                            new PassBall(x, y, x - i, y - i, game),
                            new PassBall(x, y, x + i, y - i, game));
                    return commands.stream()
                            .peek(Command::setCurrentState)
                            .filter(command -> {
                                final PassBall pass = (PassBall) command;
                                return pass.canDoForAIPlayer(player);
                            })
                            .collect(Collectors.toList());
                }).flatMap(List::stream).collect(Collectors.toList());
    }

    public List<Command> getPossibleMovePieceNotCurrentPlayer(final Player p) {
        final List<Piece> pieces = p.getPieces();
        final List<Command> commands = pieces.stream().map(piece -> {
            final int x = piece.getTile().getX();
            final int y = piece.getTile().getY();
            return Arrays.<Command>asList(
                    new MovePiece(x, y, x + 1, y, game),
                    new MovePiece(x, y, x - 1, y, game),
                    new MovePiece(x, y, x, y + 1, game),
                    new MovePiece(x, y, x, y - 1, game)
            );
        }).flatMap(List::stream).peek(Command::setCurrentState).collect(Collectors.toList());
        return commands.stream().filter(command -> {
            final MovePiece move = (MovePiece) command;
            return move.canDoForPlayer(p);
        }).collect(Collectors.toList());
    }

    //This function would have been useful if we wanted the AI to try to win
    public Command tryToGoForward() {
        final List<Command> actions = getPossibleActionsForPlayer(game.getCurrentPlayer());
        final List<Command> moves = actions.stream()
                .filter(command -> command.getClass() == MovePiece.class)
                .collect(Collectors.toList());
        final List<Command> forwardMoves = moves.stream().map(command -> {
            final MovePiece move = (MovePiece) command;
            if (move.getY2() - move.getY1() < 0) {
                return move;
            } else {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
        if (forwardMoves.isEmpty()) {
            return null;
        }
        return forwardMoves.get(r.nextInt(forwardMoves.size()));
    }

    //This function would have been useful if we wanted the AI to try to win
    public Command tryToPassForward() {
        final List<Command> actions = getPossibleActionsForPlayer(game.getCurrentPlayer());
        final List<Command> passes = actions.stream()
                .filter(command -> command.getClass() == PassBall.class)
                .collect(Collectors.toList());
        final List<Command> forwardPasses = passes.stream().map(command -> {
            final PassBall pass = (PassBall) command;
            final Piece toPiece = ((PassBall) command).getToPiece().orElse(null);
            final Piece fromPiece = ((PassBall) command).getFromPiece().orElse(null);
            if (toPiece.getTile().getY() - fromPiece.getTile().getY() < 0) {
                return pass;
            } else {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
        if (forwardPasses.isEmpty()) {
            return null;
        }
        return forwardPasses.get(r.nextInt(forwardPasses.size()));
    }
}
