package diaballik.model.player;

import diaballik.model.control.Command;
import diaballik.model.control.MovePiece;
import diaballik.model.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StartingAITest {

    private Game g;

    @BeforeEach
    void init(){
        g = new Game(Color.WHITE, "Robebs", AIType.NOOB);
        g.start();
        g.getCurrentTurn().invokeCommand(new MovePiece(0,0,0,1,g));
        g.getCurrentTurn().invokeCommand(new MovePiece(1,0,1,1,g));
        g.getCurrentTurn().invokeCommand(new MovePiece(2,0,2,1,g));
        g.setAiGame(false);
        g.endTurn();

    }

    @Test
    void executeTest(){
        AIPlayer aip = (AIPlayer) g.getCurrentPlayer();
        Command c = aip.getCommand();
        g.getCurrentTurn().invokeCommand(c);
        assertTrue(g.getCurrentTurn().getUndoDeque().contains(c));
        assertNotEquals(aip.getCommand(),aip.getCommand());
    }
}
