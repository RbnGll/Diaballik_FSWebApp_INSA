package diaballik.resource;

import com.github.hanleyt.JerseyExtension;
import diaballik.model.game.Game;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.awt.Color;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestGameResource {
    static final Logger log = Logger.getLogger(TestGameResource.class.getSimpleName());

    @SuppressWarnings("unused")
    @RegisterExtension
    JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);

    Application configureJersey() {
        return new ResourceConfig(GameResource.class)
                .register(MyExceptionMapper.class)
                .register(MoxyJsonFeature.class);
    }

    <T> T LogJSONAndUnmarshallValue(final Response res, final Class<T> classToRead) {
        res.bufferEntity();
        final String json = res.readEntity(String.class);
        log.log(Level.INFO, "JSON received: " + json);
        final T obj = res.readEntity(classToRead);
        res.close();
        return obj;
    }

    @Test
    void exampleMethod(final Client client, final URI baseUri) {
        // final Response res = client
        // 	.target(baseUri)
        // 	.path("TODO")
        // 	.request()
        // 	.post(Entity.text(""));

        // assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        // final Game game = LogJSONAndUnmarshallValue(res, Game.class);

        // assertNotNull(game);
        // etc..
    }

    @Test
    void testNewValidGamePvP(final Client client, final URI baseUri) {
        final Response response = client
                .target(baseUri)
                .path("game/newPvP/Robin/0/Ronan/1")
                .request()
                .post(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        final Game game = LogJSONAndUnmarshallValue(response, Game.class);

        assertNotNull(game);

        assertEquals("Robin", game.getPlayer1().getName());
        assertEquals(Color.BLACK, game.getPlayer1().getColor());
        assertEquals("Ronan", game.getPlayer2().getName());
        assertEquals(Color.WHITE, game.getPlayer2().getColor());
    }

    @Test
    void testNewGamePvPInvalidColor(final Client client, final URI baseUri) {
        final Response response = client
                .target(baseUri)
                .path("game/newPvP/Robin/0/Ronan/2")
                .request()
                .post(Entity.text(""));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testNewGamePvPSameColor(final Client client, final URI baseUri) {
        final Response response = client
                .target(baseUri)
                .path("game/newPvP/Robin/1/Ronan/1")
                .request()
                .post(Entity.text(""));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testNewGamePvAI(final Client client, final URI baseUri) {
        // TODO
    }

    @Test
    void testNewGamePvAIInvalidColor(final Client client, final URI baseUri) {
        // TODO
    }

    @Test
    void testNewGamePvAISameColor(final Client client, final URI baseUri) {
        // TODO
    }

    @Test
    void testNewGamePvAIInvalidStrategy(final Client client, final URI baseUri) {
        // TODO
    }

    @Test
    void testStartNoGame(final Client client, final URI baseUri) {
        final Response response = client
                .target(baseUri)
                .path("game/start")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testPassBallNoGame(final Client client, final URI baseUri) {

        final Response response = client
                .target(baseUri)
                .path("game/action/passBall/3/0/0/0")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testMovePieceNoGame(final Client client, final URI baseUri) {
        final Response response = client
                .target(baseUri)
                .path("game/action/movePiece/0/0/0/1")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testRedoNoGame(final Client client, final URI baseUri) {
        final Response response = client
                .target(baseUri)
                .path("game/action/redo")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testUndoNoGame(final Client client, final URI baseUri) {
        final Response response = client
                .target(baseUri)
                .path("game/action/undo")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testEndTurnNoGame(final Client client, final URI baseUri) {
        final Response response = client
                .target(baseUri)
                .path("game/action/endTurn")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testGetCurrentPlayerNoGame(final Client client, final URI baseUri) {
        final Response response = client
                .target(baseUri)
                .path("game/get/currentPlayer")
                .request()
                .get();

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testGetGameNull(final Client client, final URI baseUri) {
        final Response response = client
                .target(baseUri)
                .path("game/get/game")
                .request()
                .get();

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}
