package diaballik.model.player.aiStrategy;

import diaballik.model.control.Command;
import diaballik.model.game.Game;

import java.util.List;
import java.util.Random;

public class NoobAI extends AIStrategy {

    private Random r;

    public NoobAI() {
        super();
        r = new Random();
    }


    @Override
    public Command execute(Game g) {
        List<Command> actions = getPossibleActionsForPlayer(game.getCurrentPlayer());
        return actions.get(r.nextInt(actions.size()));
    }

}
