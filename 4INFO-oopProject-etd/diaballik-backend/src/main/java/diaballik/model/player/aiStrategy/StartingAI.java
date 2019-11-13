package diaballik.model.player.aiStrategy;

import diaballik.model.control.Command;
import diaballik.model.control.MovePiece;
import diaballik.model.control.PassBall;
import diaballik.model.game.Game;
import diaballik.model.game.Tile;
import diaballik.model.player.Player;
import diaballik.model.player.Piece;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

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
        if (nonNull(bestAction)) {
            // If there is no best action, return a random action
            bestAction = new NoobAI(game).execute();
        }
        return bestAction;
    }

    private Command getBestAction() {
        Command bestAction = tryToBlockPass();
        if (bestAction == null) {
            bestAction = tryToBlockForwardMoves();
        }
        if (bestAction == null) {
            bestAction = tryToPassForward();
        }
        if (bestAction == null) {
            bestAction = tryToGoForward();
        }

        return bestAction;
    }

    private Command tryToBlockPass() {
        final Map<Tile, Command> moves = getPossiblePosition(game.getCurrentPlayer());
        final List<Tile> positionToBlockPass = getPassesPath(opponent);
        final List<Command> blockingPassPosition = positionToBlockPass.stream()
                .map(moves::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (blockingPassPosition.isEmpty()) {
            return null;
        }
        return blockingPassPosition.get(r.nextInt(blockingPassPosition.size()));
    }

    private Command tryToBlockForwardMoves() {
        final Map<Tile, Command> aiMoves = getPossiblePosition(game.getCurrentPlayer());
        final List<Command> actions = getPossibleActionsForPlayer(opponent);
        final List<Command> opponentMoves = actions.stream()
                .filter(command -> command.getClass() == MovePiece.class)
                .collect(Collectors.toList());
        final List<Command> blockingMoves = opponentMoves.stream().map(command -> {
            final MovePiece move = (MovePiece) command;
            if ((move.getY2() - move.getY1() ^ -1) > 0) {
                return aiMoves.get(new Tile(move.getX2(), move.getY2()));
            } else {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
        return blockingMoves.get(r.nextInt(blockingMoves.size()));
    }

    private Command tryToGoForward() {
        final List<Command> actions = getPossibleActionsForPlayer(game.getCurrentPlayer());
        final List<Command> moves = actions.stream()
                .filter(command -> command.getClass() == MovePiece.class)
                .collect(Collectors.toList());
        final List<Command> forwardMoves = moves.stream().map(command -> {
            final MovePiece move = (MovePiece) command;
            if ((move.getY2() - move.getY1() ^ 1) > 0) {
                return move;
            } else {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
        return forwardMoves.get(r.nextInt(forwardMoves.size()));
    }

    private Command tryToPassForward() {
        final List<Command> actions = getPossibleActionsForPlayer(game.getCurrentPlayer());
        final List<Command> passes = actions.stream()
                .filter(command -> command.getClass() == PassBall.class)
                .collect(Collectors.toList());
        final List<Command> forwardPasses = passes.stream().map(command -> {
            final PassBall pass = (PassBall) command;
            final Piece toPiece = ((PassBall) command).getToPiece().orElse(null);
            final Piece fromPiece = ((PassBall) command).getFromPiece().orElse(null);
            if ((toPiece.getTile().getY() - fromPiece.getTile().getY() ^ 1) > 0) {
                return pass;
            } else {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
        return forwardPasses.get(r.nextInt(forwardPasses.size()));
    }

    private Player getOpponent() {
        return game.getCurrentPlayer() == game.getPlayer1() ? game.getPlayer2() : game.getPlayer1();
    }

    private List<Tile> getPassesPath(final Player player) {
        final List<Command> actions = getPossibleActionsForPlayer(player);
        final List<Command> passes = actions.stream()
                .filter(command -> command.getClass() == PassBall.class)
                .collect(Collectors.toList());
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
        moves.stream().peek(command -> {
            final MovePiece move = (MovePiece) command;
            final Tile t = new Tile(move.getX2(), move.getY2());
            indexedMoves.put(t, command);
        }).close();
        return indexedMoves;
    }
}
