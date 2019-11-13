package diaballik.model;

import diaballik.model.game.Game;
import diaballik.model.game.Tile;
import diaballik.model.game.Turn;
import diaballik.model.player.Ball;
import diaballik.model.player.HumanPlayer;
import diaballik.model.player.Piece;
import diaballik.model.player.Player;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.Color;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;


public class TestMarshalling {
    <T> T marshall(final T objectToMarshall) throws IOException, JAXBException {
        final Map<String, Object> properties = new HashMap<>();
        properties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
        properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, Boolean.TRUE);

        final JAXBContext ctx = JAXBContextFactory.createContext(new Class[] {objectToMarshall.getClass()}, properties);
        final Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        final StringWriter sw = new StringWriter();
        marshaller.marshal(objectToMarshall, sw);
        marshaller.marshal(objectToMarshall, System.out);

        final Unmarshaller unmarshaller = ctx.createUnmarshaller();
        final StringReader reader = new StringReader(sw.toString());
        final T o = (T) unmarshaller.unmarshal(reader);

        sw.close();
        reader.close();

        return o;
    }

    Tile t;
    Piece piece;
    Ball b;
    Player hp;
    Game g;
    Turn turn;

    @BeforeEach
    void setUp() {
        t = new Tile(0, 1);
        piece = new Piece(Color.WHITE, t);
        b = new Ball(piece);
        g = new Game(Color.BLACK, "Robin", Color.WHITE, "Ronan");
        hp = g.getPlayer1();

        g.start();
        turn = g.getCurrentTurn();
    }

    @Test
    void testTile() throws IOException, JAXBException {
        final Tile tile = marshall(t);
        assertEquals(0, tile.getX());
        assertEquals(1, tile.getY());
        assertNull(tile.getPiece());
    }

    @Test
    void testPiece() throws IOException, JAXBException {
        final Piece p = marshall(piece);

        assertNull(p.getBall());
        assertNull(p.getColor());
        assertEquals(0, p.getTile().getX());
        assertEquals(1, p.getTile().getY());
    }

    @Test
    void testBall() throws IOException, JAXBException {
        final Ball ball = marshall(b);

        assertNull(ball.getColor());
        assertEquals(0, ball.getPiece().getTile().getX());
        assertEquals(1, ball.getPiece().getTile().getY());
    }

    @Test
    void testHumanPlayer() throws IOException, JAXBException {
        Player humanPlayer = marshall(hp);

        assertEquals("Robin", humanPlayer.getName());
        assertEquals(Color.BLACK, humanPlayer.getColor());
        assertFalse(humanPlayer.isVictory());
        assertEquals(7, humanPlayer.getPieces().size());
        assertEquals(3, humanPlayer.getBall().getPiece().getTile().getX());
        assertEquals(0, humanPlayer.getBall().getPiece().getTile().getY());

        assertEquals(HumanPlayer.class, humanPlayer.getClass());
    }

    @Test
    void testAIPlayer() throws IOException, JAXBException {
        // TODO
    }

    @Test
    void testTurn() throws IOException, JAXBException {
        Turn t = marshall(turn);
        assertEquals(0, t.getUndoDeque().size());
        assertEquals(0, t.getRedoDeque().size());

        assertFalse(t.isTurnEnd());
    }

    @Test
    void testGame() throws IOException, JAXBException {
        Game game = marshall(g);

        assertEquals("Robin", game.getPlayer1().getName());
        assertEquals("Ronan", game.getPlayer2().getName());

        assertFalse(game.getCurrentTurn().isTurnEnd());

        assertEquals("Robin", game.getCurrentPlayer().getName());
        assertEquals(Color.BLACK, game.getCurrentPlayer().getColor());
    }
}
