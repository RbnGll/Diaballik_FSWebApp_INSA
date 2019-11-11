package diaballik.model.player;

import org.junit.jupiter.api.Test;

import java.awt.Color;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AIPlayerTest {

    @Test
    public void constructionTest() {
        AIPlayer aip = new AIPlayer("Robebs", Color.WHITE,AIType.NOOB);

        assertEquals("Robebs", aip.getName());
        assertEquals(aip.getColor(), Color.WHITE);
        assertEquals(aip.getAIType(), AIType.NOOB);
        assertFalse(aip.victory);

        assertNull(aip.getPieces());
        assertNull(aip.getBall());
    }


}
