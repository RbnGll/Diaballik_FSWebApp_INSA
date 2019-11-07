package diaballik.model.player;

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

    public AIPlayer(final String name, final Color color, final AIType strategy, final Game g) {
        super(name, color);
        switch (strategy) {
            case NOOB:
                this.strategy = new NoobAI(g);
                break;
            case STARTING:
                this.strategy = new StartingAI(g);
                break;
            case PROGRESSIVE:
                this.strategy = new ProgressiveAI(g);
                break;
            default:
                break;
        }
    }
}
