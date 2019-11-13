package diaballik.model.game;

import diaballik.model.player.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {

    Board b;

    @BeforeEach
    public void init() {
        b = new Board();
    }

    @Test
    public void construction() {

        assertEquals(49, b.getBoard().size());
        for (int i = 0; i < 49; i++) {
            Tile tile = b.getBoard().get(i);

            assertEquals(i % 7, tile.getX());
            assertEquals(i / 7, tile.getY());

            //System.out.println(i + " " + tile.toString());
        }
    }

    @Test
    public void getTile() {
        assertEquals(3, b.getTile(3, 5).getX());
        assertEquals(2, b.getTile(3, 2).getY());
        assertEquals(0, b.getTile(7, 4).getX());
    }

    @Test
    public void move() {
        Tile from = b.getTile(0, 0);
        Tile to = b.getTile(5, 3);
        Piece p = new Piece(Color.WHITE, from);

        assertTrue(from.getPiece().isPresent());
        assertTrue(to.getPiece().isEmpty());

        b.movePiece(0, 0, 5, 3);

        assertTrue(from.getPiece().isEmpty());
        assertTrue(to.getPiece().isPresent());
        assertEquals(p, to.getPiece().get());
    }

    @Test
    void outOfBounds() {
        assertFalse(b.ifWithinBounds(-1, 9));
    }

    @Test
    void withinBounds() {
        assertTrue(b.ifWithinBounds(5, 3));
    }
}
