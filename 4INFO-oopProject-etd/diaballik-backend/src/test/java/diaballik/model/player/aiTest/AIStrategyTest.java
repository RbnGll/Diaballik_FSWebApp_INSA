package diaballik.model.player.aiTest;

import diaballik.model.control.Command;
import diaballik.model.control.MovePiece;
import diaballik.model.exception.CommandException;
import diaballik.model.exception.turn.TurnException;
import diaballik.model.game.Game;
import diaballik.model.player.AIPlayer;
import diaballik.model.player.AIType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AIStrategyTest {

    private Game g;

    @BeforeEach
    void init() throws TurnException, CommandException {
        g = new Game(Color.WHITE, "Robebs", AIType.NOOB);
        g.start();
        g.getCurrentTurn().invokeCommand(new MovePiece(0, 0, 0, 1, g));
        g.getCurrentTurn().invokeCommand(new MovePiece(1, 0, 1, 1, g));
        g.getCurrentTurn().invokeCommand(new MovePiece(2, 0, 2, 1, g));
        g.setAiGame(false);
        g.endTurn();

    }

    @Test
    void possibleMovePieceTest() {
        AIPlayer ai = (AIPlayer) g.getCurrentPlayer();
        List<Command> moves = ai.getStrategy().getPossibleMovePieceForPlayer(g.getCurrentPlayer());
        assertEquals(6, moves.size());
    }

    @Test
    void possiblePassBallTest() {
        AIPlayer ai = (AIPlayer) g.getCurrentPlayer();
        List<Command> passes = ai.getStrategy().getPossiblePassBallForPiece(g.getCurrentPlayer().getBall().getPiece());
        assertEquals(2, passes.size());
    }

    @Test
    void possibleActionsTest() {
        AIPlayer ai = (AIPlayer) g.getPlayer2();
        List<Command> actions = ai.getStrategy().getPossibleActionsForPlayer(g.getCurrentPlayer());
        assertEquals(8, actions.size());
    }


}
