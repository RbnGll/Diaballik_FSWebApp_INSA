package diaballik.specification;

import diaballik.model.game.Board;
import diaballik.model.game.Game;
import diaballik.model.game.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SpecificationTest {

    Game g;
    Board b;

    @BeforeEach
    void init() {
        g = new Game(Color.WHITE, "Robin", Color.BLACK, "Ronan");
        b = new Board();
    }

    @Test
    void R21_1_GAME_PLAYERS() {
        assertEquals(Color.WHITE, g.getPlayer1().getColor());
        assertEquals(Color.BLACK, g.getPlayer2().getColor());
        assertEquals("Robin", g.getPlayer1().getName());
        assertEquals("Ronan", g.getPlayer2().getName());
    }

    @Test
    void R21_2_GAME_BOARD() {
        assertEquals(49, b.getBoard().size());
        for (int i = 0; i < 49; i++) {
            Tile tile = b.getBoard().get(i);

            assertEquals(i % 7, tile.getX());
            assertEquals(i / 7, tile.getY());
        }
    }

    @Test
    void R21_2_GAME_PIECES() {
        assertEquals(Board.BOARDSIZE, g.getPlayer1().getPieces().size());
        assertEquals(Board.BOARDSIZE, g.getPlayer2().getPieces().size());

        g.getPlayer1().getPieces().forEach(p -> assertEquals(Color.WHITE, p.getColor()));
        g.getPlayer2().getPieces().forEach(p -> assertEquals(Color.BLACK, p.getColor()));
    }

    @Test
    void R21_4_GAME_BALL() {
        assertNotNull(g.getPlayer1().getBall());
        assertNotNull(g.getPlayer2().getBall());

        assertNotNull(g.getPlayer1().getBall().getPiece());
        assertNotNull(g.getPlayer2().getBall().getPiece());
    }

    @Test
    void R21_5_GAME_COLOUR() {
        this.R21_1_GAME_PLAYERS();

        // Cf GameRessource testNewGamePvPSameColor

        assertEquals(g.getPlayer1().getColor(), g.getPlayer1().getBall().getColor());
        assertEquals(g.getPlayer2().getColor(), g.getPlayer2().getBall().getColor());
    }

    @Test
    void R21_6_GAMEPLAY_TURN() {
        // Pas possible de le tester
    }

    @Test
    void R21_8_GAMEPLAY_ACTIONS() {
        // Cf TurnTest invokeCommandTurnEnd
        // Cf TurnTest checkEndTurnFalse
    }

    @Test
    void R21_9_GAMEPLAY_MOVE_BALL() {
        // Cf méthodes canDo dans PassBallTest
    }

    @Test
    void R21_10_GAMEPLAY_MOVE_PIECE() {
        /*
        notCorrectPathDx
        correctPathDx
        notCorrectPathDy
        correctPathDy
        freePosition
        occupiedPosition

        dans MovePieceTest
         */
    }

    @Test
    void R21_11_GAMEPLAY_MOVE_PIECE_WITH_BALL() {
        // Cf containsBall et notContainsBall dans MovePieceTest
    }

    @Test
    void R21_12_GAMEPLAY_HOW_START() {
        // Cf start dans GameTest
    }

    @Test
    void R21_13_GAME_TURN_ORDER() {
        // Cf endTurnTrue dans GameTest
    }

    @Test
    void R21_14_GAME_TURN_AUTO() {
        // Le joueur clique sur un bouton "valider" afin de mettre fin au tour
        // Sinon impossible de "undo" la dernière action

        // Cf endTurnTrue dans GameTest
    }

    @Test
    void R21_15_GAME_NO_TURN_LIMIT() {
        // Impossible à tester
    }

    @Test
    void R21_16_GAME_UNDO_REDO() {
        // Cf undo & redo dans GameTest
    }

    @Test
    void R23_1_PLAYER_KINDS() {
        // TODO
    }

    @Test
    void R23_2_IA_LEVELS() {
        // TODO
    }

    @Test
    void R23_3_AI_LEVEL_NOOB() {
        // TODO
    }

    @Test
    void R23_4_AI_LEVEL_STARTING() {
        // TODO
    }

    @Test
    void R23_5_AI_LEVEL_PROGRESSIVE() {
        // TODO
    }

    @Test
    void R24_1_VICTORY() {
        // Cf checkVictoryTrue et checkVictoryFalseInit dans GameTest
    }
}
