package diaballik.model.player;

import diaballik.model.control.Command;
import diaballik.model.game.Game;
import diaballik.model.player.aiStrategy.AIStrategy;
import diaballik.model.player.aiStrategy.NoobAI;
import diaballik.model.player.aiStrategy.ProgressiveAI;
import diaballik.model.player.aiStrategy.StartingAI;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.awt.Color;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AIPlayer extends Player {

    @XmlTransient
    private AIStrategy strategy;

    // Constructeur sans paramètres pour utiliser REST
    AIPlayer() {

    }

    public AIPlayer(final String name, final Color color, final AIType strategy, final int playerNumber) {
        super(name, color, playerNumber);
        switch (strategy) {
            case NOOB:
                this.strategy = new NoobAI(game);
                break;
            case STARTING:
                this.strategy = new StartingAI(game);
                break;
            case PROGRESSIVE:
                this.strategy = new ProgressiveAI(game);
                break;
            default:
                break;
        }
    }

    public AIType getAIType() {
        final AIType aiType;
        if (strategy.getClass().equals(NoobAI.class)) {
            aiType = AIType.NOOB;
        } else if (strategy.getClass().equals(StartingAI.class)) {
            aiType = AIType.STARTING;
        } else if (strategy.getClass().equals(ProgressiveAI.class)) {
            aiType = AIType.PROGRESSIVE;
        } else {
            aiType = null;
        }
        return aiType;
    }

    public Command getCommand() {
        return strategy.execute();
    }

    public AIStrategy getStrategy() {
        return strategy;
    }
}
