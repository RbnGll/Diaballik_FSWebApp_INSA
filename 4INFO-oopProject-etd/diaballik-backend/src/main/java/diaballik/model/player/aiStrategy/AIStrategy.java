package diaballik.model.player.aiStrategy;

import diaballik.model.control.Command;
import diaballik.model.control.PassBall;
import diaballik.model.game.Board;
import diaballik.model.game.Game;
import diaballik.model.player.Piece;
import diaballik.model.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AIStrategy {

    Game game;

    public void execute(Game g) {
        game = g;
    }

    public List<Command> getPossiblePassBallForPiece(final Piece p) {
        int x = p.getTile().getX();
        int y = p.getTile().getY();
        List<Command> commands = IntStream.range(1, Board.BOARDSIZE)
                .mapToObj(i -> {
                    final List<Command> temp = Arrays.asList(
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
                    return temp;
                });
        return commands.stream().filter(command -> command.canDo()).collect(Collectors.toList());
    }

    public List<Command> getPossibleMovePieceForPlayer(final Player p){

    }
}
