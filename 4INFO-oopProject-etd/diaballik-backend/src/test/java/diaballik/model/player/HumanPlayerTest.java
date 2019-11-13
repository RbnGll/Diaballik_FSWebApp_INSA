package diaballik.model.player;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class HumanPlayerTest {

    @Test
    public void constructionTest() {
        HumanPlayer hp = new HumanPlayer("Robebs", Color.WHITE, 1);

        assertEquals("Robebs", hp.getName());
        assertEquals(hp.getColor(), Color.WHITE);
        assertFalse(hp.victory);

        assertNull(hp.getPieces());
        assertNull(hp.getBall());
    }
}
