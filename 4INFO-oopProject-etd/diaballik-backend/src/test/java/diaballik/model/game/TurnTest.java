package diaballik.model.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TurnTest {

    Turn t;

    @BeforeEach
    void init() {
        t = new Turn();
    }

    @Test
    void construction() {
        assertNotNull(t.getUndoDeque());
        assertNotNull(t.getRedoDeque());
        assertFalse(t.isTurnEnd());
    }

    @Test
    void invokeCommand() {
        // TODO : Commandes
    }
}
