package diaballik.resource.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.awt.Color;

public class ColorAdapter extends XmlAdapter<Integer, Color> {

    @Override
    public Color unmarshal(final Integer v) throws Exception {
        switch (v) {
            case 0:
                return Color.WHITE;
            case 1:
                return Color.BLACK;

            default:
                return Color.WHITE;
        }
    }

    @Override
    public Integer marshal(final Color v) throws Exception {
        // Seulement 2 couleurs possibles : Blanc et noir
        if (v.equals(Color.WHITE)) {
            return 0;
        } else {
            return 1;
        }
    }
}
