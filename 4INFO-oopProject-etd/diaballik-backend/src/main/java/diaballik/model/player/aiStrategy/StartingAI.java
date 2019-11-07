package diaballik.model.player.aiStrategy;

import diaballik.model.control.Command;
import diaballik.model.control.PassBall;
import diaballik.model.game.Game;
import diaballik.model.game.Tile;
import diaballik.model.player.Player;

import java.util.List;

public class StartingAI extends AIStrategy {

    public StartingAI(final Game g) {
        super(g);
    }

    @Override
    public Command execute() {
        final Command bestAction = getBestAction(game);
        if (bestAction == null) {
            // If there is no best action, return a random action
            return new NoobAI(game).execute();
        }
        return bestAction;
    }

    public Player getOpponent() {
        final Player opponent;
        if (game.getCurrentPlayer() == game.getPlayer1()) {
            opponent = game.getPlayer2();
        } else {
            opponent = game.getPlayer1();
        }
        return opponent;
    }

    public Command getBestAction(final Game g) {
        final List<Command> actions = getPossibleActionsForPlayer(g.getCurrentPlayer());
        final Player opponent = getOpponent();
        List<Tile> passesPath = actions.stream().map(command -> {
            if (command.getClass() == PassBall.class) {
                PassBall pass = (PassBall) command;
                return pass.getPathTiles();
            }
        });
        return null;
    }


}
