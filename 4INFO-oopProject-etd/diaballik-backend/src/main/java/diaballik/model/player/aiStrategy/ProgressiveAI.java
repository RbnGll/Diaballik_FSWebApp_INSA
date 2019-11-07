package diaballik.model.player.aiStrategy;

import diaballik.model.control.Command;
import diaballik.model.game.Game;

public class ProgressiveAI extends AIStrategy {

    public static final int CHANGE_STRATEGY_LIMIT = 5;
    private AIStrategy starting;
    private AIStrategy noob;

    public ProgressiveAI(final Game g) {
        super(g);
        this.noob = new NoobAI(g);
        this.starting = new StartingAI(g);
    }

    @Override
    public Command execute() {
        if (game.getTurnCount() > CHANGE_STRATEGY_LIMIT) {
            return starting.execute();
        } else {
            return noob.execute();
        }
    }
}
