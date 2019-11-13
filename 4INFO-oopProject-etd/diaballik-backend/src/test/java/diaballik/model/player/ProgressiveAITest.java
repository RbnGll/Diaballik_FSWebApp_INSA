package diaballik.model.player;

import diaballik.model.control.Command;
import diaballik.model.control.MovePiece;
import diaballik.model.control.PassBall;
import diaballik.model.game.Game;
import diaballik.model.player.aiStrategy.NoobAI;
import diaballik.model.player.aiStrategy.StartingAI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProgressiveAITest {

    private Game g;

    @BeforeEach
    void init(){
        g = new Game(Color.WHITE, "Robebs", AIType.PROGRESSIVE);
        g.start();
        g.getCurrentTurn().invokeCommand(new MovePiece(0,0,0,1,g));
        g.getCurrentTurn().invokeCommand(new MovePiece(6,0,6,1,g));
        g.getCurrentTurn().invokeCommand(new MovePiece(2,0,2,1,g));
        g.endTurn();

    }

    @Test
    void executeTest(){
        AIPlayer aip = (AIPlayer) g.getPlayer2();
        assertEquals(aip.getStrategy().getClass(), NoobAI.class);
        g.getCurrentTurn().invokeCommand(new PassBall(3,0,1,0,g));
        g.getCurrentTurn().invokeCommand(new PassBall(1,0,2,1,g));
        g.getCurrentTurn().invokeCommand(new PassBall(2,1,3,0,g));
        g.endTurn();
        g.getCurrentTurn().invokeCommand(new PassBall(3,0,1,0,g));
        g.getCurrentTurn().invokeCommand(new PassBall(1,0,2,1,g));
        g.getCurrentTurn().invokeCommand(new PassBall(2,1,3,0,g));
        g.endTurn();
        System.out.println(g.getTurnCount()+">5");
        assertEquals(aip.getStrategy().getClass(), StartingAI.class);
    }
}
