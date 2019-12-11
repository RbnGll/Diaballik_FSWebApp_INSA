package diaballik.model.player.aiStrategy;

import diaballik.model.control.Command;
import diaballik.model.control.MovePiece;
import diaballik.model.control.PassBall;
import diaballik.model.exception.CommandException;
import diaballik.model.game.Board;
import diaballik.model.game.Game;
import diaballik.model.player.Piece;
import diaballik.model.player.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class AIStrategy {

    Game game;

    public AIStrategy(final Game g) {
        game = g;
    }

    public abstract Command execute();

    public List<Command> getPossiblePassBallForPiece(final Piece p) {
        final int x = p.getTile().getX();
        final int y = p.getTile().getY();
        return IntStream.range(1, Board.BOARDSIZE) //Make a list of every possible pass for each tile of the gameboard
                .mapToObj(i -> {
                    final List<Command> commands = Arrays.asList(
                            // Every vertical and horizontal pass
                            new PassBall(x, y, x + i, y, game),
                            new PassBall(x, y, x - i, y, game),
                            new PassBall(x, y, x, y + i, game),
                            new PassBall(x, y, x, y - i, game),
                            // Every diagonal pass
                            new PassBall(x, y, x + i, y + i, game),
                            new PassBall(x, y, x - i, y + i, game),
                            new PassBall(x, y, x - i, y - i, game),
                            new PassBall(x, y, x + i, y - i, game));
                    return commands.stream()
                            .peek(Command::setCurrentState)
                            .filter(command ->  {
                                try {
                                    return command.canDo();
                                } catch (CommandException e) {
                                    return false;
                                }
                            }) //Filter the pass which can't be execute at this point of the game
                            .collect(Collectors.toList());
                }).flatMap(List::stream).collect(Collectors.toList());
    }

    public List<Command> getPossibleMovePieceForPlayer(final Player p) {
        final List<Piece> pieces = p.getPieces();
        final List<Command> commands = pieces.stream().map(piece -> {
            final int x = piece.getTile().getX();
            final int y = piece.getTile().getY();
            //Return a list of every move for this piece
            return Arrays.<Command>asList(
                    new MovePiece(x, y, x + 1, y, game),
                    new MovePiece(x, y, x - 1, y, game),
                    new MovePiece(x, y, x, y + 1, game),
                    new MovePiece(x, y, x, y - 1, game)
            );
        }).flatMap(List::stream).peek(Command::setCurrentState).collect(Collectors.toList());
        return commands.stream().filter(command ->  {
            try {
                return command.canDo();
            } catch (CommandException e) {
                return false;
            }
        }).collect(Collectors.toList()); //Filter those moves with the Command.canDo()
    }

    public List<Command> getPossibleActionsForPlayer(final Player p) {
        final List<Command> actions = getPossibleMovePieceForPlayer(p);
        actions.addAll(getPossiblePassBallForPiece(p.getBall().getPiece()));
        return actions;
    }
}
