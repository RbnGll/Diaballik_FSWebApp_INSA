package diaballik.model.player;

import diaballik.model.game.Game;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AIPlayerTest {

    @Test
    void constructionTestNOOB() {
        AIPlayer aip = new AIPlayer("Robebs", Color.WHITE, AIType.NOOB, 2, new Game());

        assertEquals("Robebs", aip.getName());
        assertEquals(Color.WHITE, aip.getColor());
        assertEquals(AIType.NOOB, aip.getAIType());
        assertFalse(aip.victory);

        assertNull(aip.getPieces());
        assertNull(aip.getBall());
    }

    @Test
    void constructionTestStarting() {
        AIPlayer aip = new AIPlayer("Robebs", Color.WHITE, AIType.STARTING, 2, new Game());

        assertEquals("Robebs", aip.getName());
        assertEquals(Color.WHITE, aip.getColor());
        assertEquals(AIType.STARTING, aip.getAIType());
        assertFalse(aip.victory);

        assertNull(aip.getPieces());
        assertNull(aip.getBall());
    }

    @Test
    void constructionTestProgressive() {
        AIPlayer aip = new AIPlayer("Robebs", Color.WHITE, AIType.PROGRESSIVE, 2, new Game());

        assertEquals("Robebs", aip.getName());
        assertEquals(Color.WHITE, aip.getColor());
        assertEquals(AIType.PROGRESSIVE, aip.getAIType());
        assertFalse(aip.victory);

        assertNull(aip.getPieces());
        assertNull(aip.getBall());
    }
}
