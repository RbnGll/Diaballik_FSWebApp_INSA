package diaballik.model.game;

import diaballik.model.player.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game g;

    @BeforeEach
    void init() {
        g = new Game(Color.WHITE, "Robin", Color.BLACK, "Ronan");
    }

    @Test
    void constructionPvP() {

        // Construction des joueurs
        assertEquals(Color.WHITE, g.getPlayer1().getColor());
        assertEquals(Color.BLACK, g.getPlayer2().getColor());
        assertEquals("Robin", g.getPlayer1().getName());
        assertEquals("Ronan", g.getPlayer2().getName());

        List<Piece> piecesP1 = g.getPlayer1().getPieces();
        List<Piece> pieceP2 = g.getPlayer2().getPieces();

        for (int i = 0; i < Board.BOARDSIZE; i++) {
            Tile t1 = piecesP1.get(i).getTile();
            Tile t2 = pieceP2.get(i).getTile();

            assertEquals(i, t1.getX());
            assertEquals(0, t1.getY());
            assertEquals(6, t2.getY());

            //System.out.println("T1: " + t1.toString());
            //System.out.println("T2 " + t2.toString());
        }

        assertEquals(g.getPlayer1().getPieces().get(Board.BOARDSIZE / 2), g.getPlayer1().getBall().getPiece());
        assertEquals(g.getPlayer2().getPieces().get(Board.BOARDSIZE / 2), g.getPlayer2().getBall().getPiece());
    }

    @Test
    void start() {
        g.start();
        assertNotNull(g.getCurrentTurn());
    }

    @Test
    void swapCurrentPlayer() {
        g.start();
        assertEquals(g.getPlayer1(), g.getCurrentPlayer());
        g.swapCurrentPlayer();
        assertEquals(g.getPlayer2(), g.getCurrentPlayer());
    }

    @Test
    void checkVictoryTrue() {
        // Se mettre en condition pour une victoire
        //TODO

        //assertEquals(g.getPlayer1(), g.checkVictory().get());
    }

    @Test
    void checkVictoryFalse() {
        assertTrue(g.checkVictory().isEmpty());
    }

    @Test
    void movePiece() {
        // TODO
    }

    @Test
    void passBall() {
        //TODO
    }

    @Test
    void endTurn() {
        // TODO avec TURN fait
    }
}
