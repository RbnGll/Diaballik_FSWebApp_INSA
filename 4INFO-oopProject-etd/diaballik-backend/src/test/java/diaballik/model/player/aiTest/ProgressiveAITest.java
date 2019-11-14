package diaballik.model.player.aiTest;

import diaballik.model.control.MovePiece;
import diaballik.model.control.PassBall;
import diaballik.model.exception.CommandException;
import diaballik.model.exception.turn.TurnException;
import diaballik.model.game.Game;
import diaballik.model.player.AIPlayer;
import diaballik.model.player.AIType;
import diaballik.model.player.aiStrategy.NoobAI;
import diaballik.model.player.aiStrategy.ProgressiveAI;
import diaballik.model.player.aiStrategy.StartingAI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.Assert.assertEquals;

public class ProgressiveAITest {

    private Game g;

    @BeforeEach
    void init() throws TurnException, CommandException {
        g = new Game(Color.WHITE, "Robebs", AIType.PROGRESSIVE);
        g.start();
        g.getCurrentTurn().invokeCommand(new MovePiece(0, 0, 0, 1, g));
        g.getCurrentTurn().invokeCommand(new MovePiece(6, 0, 6, 1, g));
        g.getCurrentTurn().invokeCommand(new MovePiece(2, 0, 2, 1, g));
        g.endTurn();

    }

    @Test
    void executeTest() throws TurnException, CommandException {
        AIPlayer aip = (AIPlayer) g.getPlayer2();
        assertEquals(NoobAI.class, ((ProgressiveAI) aip.getStrategy()).getActiveAI().getClass());
        g.getCurrentTurn().invokeCommand(new PassBall(3, 0, 1, 0, g));
        g.getCurrentTurn().invokeCommand(new PassBall(1, 0, 2, 1, g));
        g.getCurrentTurn().invokeCommand(new PassBall(2, 1, 3, 0, g));
        g.endTurn();
        g.getCurrentTurn().invokeCommand(new PassBall(3, 0, 1, 0, g));
        g.getCurrentTurn().invokeCommand(new PassBall(1, 0, 2, 1, g));
        g.getCurrentTurn().invokeCommand(new PassBall(2, 1, 3, 0, g));
        g.endTurn();
        assertEquals(StartingAI.class, ((ProgressiveAI) aip.getStrategy()).getActiveAI().getClass());
    }
}
