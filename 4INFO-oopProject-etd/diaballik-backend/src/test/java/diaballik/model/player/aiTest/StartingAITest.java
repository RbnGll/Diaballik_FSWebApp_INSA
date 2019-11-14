package diaballik.model.player.aiTest;

import diaballik.model.control.Command;
import diaballik.model.control.MovePiece;
import diaballik.model.control.PassBall;
import diaballik.model.exception.CommandException;
import diaballik.model.exception.turn.TurnException;
import diaballik.model.game.Game;
import diaballik.model.player.AIPlayer;
import diaballik.model.player.AIType;
import diaballik.model.player.aiStrategy.StartingAI;
import org.checkerframework.common.value.qual.StaticallyExecutable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StartingAITest {

    private Game g;
    StartingAI ais;

    @BeforeEach
    void init() throws TurnException, CommandException {
        g = new Game(Color.WHITE, "Robebs", AIType.STARTING);
        g.start();
        ais = (StartingAI) ((AIPlayer) g.getPlayer2()).getStrategy();
    }

    @Test
    void tryToBlockPassTest() throws TurnException, CommandException {
        g.swapCurrentPlayer();
        Command c = ais.tryToBlockPass();
        g.swapCurrentPlayer();

        assertNull(c);

        //Scenario setup
        g.getGameboard().movePiece(0, 0, 0, 3);
        g.getGameboard().movePiece(6, 0, 6, 3);
        g.getGameboard().movePiece(0, 6, 0, 5);
        g.getGameboard().movePiece(1, 6, 1, 3);
        g.getGameboard().movePiece(2, 6, 3, 2);
        g.getGameboard().movePiece(4, 6, 4, 2);
        g.getGameboard().movePiece(5, 6, 5, 4);
        g.swapCurrentPlayer();

        int nbPassBefore = ais.getPossiblePassBallNotCurrentPlayer(g.getPlayer1()).size();

        c = ais.tryToBlockPass();
        g.getCurrentTurn().invokeCommand(c);

        int nbPassAfter = ais.getPossiblePassBallNotCurrentPlayer(g.getPlayer1()).size();

        assertTrue(nbPassAfter < nbPassBefore);
    }

    @Test
    void tryToBlockForwardMoves() throws TurnException, CommandException {

        g.swapCurrentPlayer();
        Command c = ais.tryToBlockForwardMoves();
        g.swapCurrentPlayer();

        assertNull(c);

        //Scenario setup
        g.getGameboard().movePiece(0, 0, 0, 3);
        g.getGameboard().movePiece(6, 0, 6, 3);
        g.getGameboard().movePiece(0, 6, 0, 5);
        g.getGameboard().movePiece(1, 6, 1, 3);
        g.getGameboard().movePiece(2, 6, 3, 2);
        g.getGameboard().movePiece(4, 6, 4, 2);
        g.getGameboard().movePiece(5, 6, 5, 4);
        g.swapCurrentPlayer();

        int nbMovesBefore = ais.getPossibleMovePieceNotCurrentPlayer(g.getPlayer1()).size();

        c = ais.tryToBlockForwardMoves();
        g.getCurrentTurn().invokeCommand(c);

        int nbMovesAfter = ais.getPossibleMovePieceNotCurrentPlayer(g.getPlayer1()).size();

        assertTrue(nbMovesAfter < nbMovesBefore);
    }

    @Test
    void tryToPassForwardTest() {
        g.swapCurrentPlayer();
        Command c = ais.tryToPassForward();
        g.swapCurrentPlayer();

        assertNull(c);

        //Scenario setup
        g.getGameboard().movePiece(5, 6, 5, 4);
        g.swapCurrentPlayer();

        c = ais.tryToPassForward();
        PassBall pass = (PassBall) c;

        assertEquals(g.getGameboard().getTile(3,6).getPiece(),pass.getFromPiece());
        assertEquals(g.getGameboard().getTile(5,4).getPiece(),pass.getToPiece());

    }

    @Test
    void tryToGoForwardTest() {

        //Scenario setup
        g.getGameboard().movePiece(5, 6, 5, 4);
        g.swapCurrentPlayer();


    }

    @Test
    void executeTest() {

    }
}
