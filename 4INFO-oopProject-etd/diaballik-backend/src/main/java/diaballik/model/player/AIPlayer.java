package diaballik.model.player;

import diaballik.model.player.aiStrategy.*;


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
