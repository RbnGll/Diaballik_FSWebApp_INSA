package diaballik.model.game;

import diaballik.model.player.Piece;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TileTest {

    @Test
    public void constructorTest() {
        Tile t = new Tile(2, 5);
        assertEquals(t.getX(), 2, 0.001);
        assertEquals(t.getY(), 5, 0.001);
    }

    @Test
    public void setPieceTest() {
        Tile t = new Tile(2, 5);

        assertTrue(t.getPiece().isEmpty());
        Piece piece = new Piece(Color.WHITE, t);
        assertTrue(t.getPiece().isPresent());
    }
}
