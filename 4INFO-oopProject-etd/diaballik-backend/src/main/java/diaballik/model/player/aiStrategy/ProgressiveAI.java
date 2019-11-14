package diaballik.model.player.aiStrategy;

import diaballik.model.control.Command;
import diaballik.model.game.Game;

public class ProgressiveAI extends AIStrategy {

    public static final int CHANGE_STRATEGY_LIMIT = 5;
    private AIStrategy starting;
    private AIStrategy noob;
    private AIStrategy activeAI;

    public ProgressiveAI(final Game g) {
        super(g);
        this.noob = new NoobAI(g);
        this.starting = new StartingAI(g);
        this.activeAI = noob;
    }

    @Override
    public Command execute() {
        if (game.getTurnCount() >= CHANGE_STRATEGY_LIMIT) {
            activeAI = starting;
        } else {
            activeAI = noob;
        }
        return activeAI.execute();
    }

    public AIStrategy getActiveAI() {
        return activeAI;
    }
}
