package diaballik.model.player.aiStrategy;

import diaballik.model.control.Command;
import diaballik.model.game.Game;

import java.util.List;
import java.util.Random;

public class NoobAI extends AIStrategy {

    private Random r;

    public NoobAI(final Game g) {
        super(g);
        r = new Random();
    }


    @Override
    public Command execute() {
        final List<Command> actions = getPossibleActionsForPlayer(game.getCurrentPlayer());
        return actions.get(r.nextInt(actions.size()));
    }

}
