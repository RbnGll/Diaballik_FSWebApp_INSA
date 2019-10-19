package diaballik.model.player;

import diaballik.model.game.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Optional;

public class PieceTest {

    @Test
    public void constructionTest() {
        Tile t = new Tile(2, 3);
        Piece p = new Piece(Color.WHITE, t);

        assertEquals(p.getTile(), t);
        assertEquals(t.getPiece(), Optional.of(p));
    }
}
