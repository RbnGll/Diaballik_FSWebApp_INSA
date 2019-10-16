package diaballik.model.player;

import java.awt.Color;

public class AIPlayer extends Player {

    private AIStrategy strategy;

    public AIPlayer(final String name, final Color color, final AIType strategy) {
        super(name, color);
        switch (strategy) {
            case NOOB:
                this.strategy = new NoobAI();
                break;
            case STARTING:
                this.strategy = new StartingAI();
                break;
            case PROGRESSIVE:
                this.strategy = new ProgressiveAI();
                break;
            default:
                break;
        }
    }
}
