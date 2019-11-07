package diaballik.model.player;

import diaballik.model.game.Tile;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PieceTest {

    @Test
    public void constructionTest() {
        Tile t = new Tile(2, 3);
        Piece p = new Piece(Color.WHITE, t);

        assertEquals(p.getTile(), t);
        assertEquals(t.getPiece(), Optional.of(p));
    }
}
