package diaballik.model.player.aiStrategy;

import diaballik.model.control.Command;
import diaballik.model.control.PassBall;
import diaballik.model.game.Board;
import diaballik.model.game.Game;
import diaballik.model.player.Piece;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class AIStrategy {

    Game game;

    public void execute(Game g) {
        game = g;
    }

    public List<Command> getPossiblePassBallForPiece(final Piece p) {
        int x = p.getTile().getX();
        int y = p.getTile().getY();
        return IntStream.range(1, Board.BOARDSIZE)
                .mapToObj(i -> {
                    final List<PassBall> commands = Arrays.asList(
                            new PassBall(x, y, x + i, y + i, game),
                            new PassBall(x, y, x - i, y + i, game),
                            new PassBall(x, y, x - i, y - i, game),
                            new PassBall(x, y, x + i, y - i, game),
                            new PassBall(x, y, x + i, y, game),
                            new PassBall(x, y, x - i, y, game),
                            new PassBall(x, y, x, y + i, game),
                            new PassBall(x, y, x, y - i, game));
                    return commands.stream().filter(Command.canDo());

                }

    }

}
