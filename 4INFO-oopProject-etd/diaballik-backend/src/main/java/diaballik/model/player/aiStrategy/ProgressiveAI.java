package diaballik.model.player.aiStrategy;

import diaballik.model.control.Command;
import diaballik.model.game.Game;

public class ProgressiveAI extends AIStrategy {

    private static final int SWITCH_THRESHOLD = 5;
    private AIStrategy starting;
    private AIStrategy noob;

    public ProgressiveAI() {
        super();
        this.noob = new NoobAI();
        this.starting = new StartingAI();
    }

    @Override
    public Command execute(Game g) {
        if (game.getTurnCount()>5){
            return starting.execute(g);
        }else{
            return noob.execute(g);
        }
    }

}
