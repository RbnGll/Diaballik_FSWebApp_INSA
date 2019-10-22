package diaballik.model.player;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.awt.Color;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HumanPlayer extends Player {

    // Constructeur sans paramètres pour utiliser REST
    HumanPlayer() {

    }

    public HumanPlayer(final String name, final Color color) {
        super(name, color);
    }
}
