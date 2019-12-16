//package diaballik.resource;
//
//import com.github.hanleyt.JerseyExtension;
//import diaballik.model.game.Game;
//import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
//import org.glassfish.jersey.server.ResourceConfig;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.RegisterExtension;
//
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.core.Application;
//import javax.ws.rs.core.Response;
//import java.net.URI;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//
//public class TestGameRessourceGameCreated {
//
//    static final Logger log = Logger.getLogger(TestGameResource.class.getSimpleName());
//    Game game;
//
//    @SuppressWarnings("unused")
//    @RegisterExtension
//    JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);
//
//    Application configureJersey() {
//        return new ResourceConfig(GameResource.class)
//                .register(MyExceptionMapper.class)
//                .register(MoxyJsonFeature.class);
//    }
//
//    <T> T LogJSONAndUnmarshallValue(final Response res, final Class<T> classToRead) {
//        res.bufferEntity();
//        final String json = res.readEntity(String.class);
//        log.log(Level.INFO, "JSON received: " + json);
//        final T obj = res.readEntity(classToRead);
//        res.close();
//        return obj;
//    }
//
//    @BeforeEach
//    void setUp(final Client client, final URI baseUri) {
//
//        // Création du game sur le serveur et start avec cette méthode
//        Response response = client
//                .target(baseUri)
//                .path("game/newPvP/Robin/0/Ronan/1")
//                .request()
//                .post(Entity.text(""));
//
//        game = LogJSONAndUnmarshallValue(response, Game.class);
//
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//    }
//
//    // TODO : A modifier car gameDéjà Crée avec un new PvP
//
//    @Test
//    void testStart(final Client client, final URI baseUri) {
//
//        // Vérification que la game crée n'est pas déjà lancée
//        assertNotNull(game);
//        assertNull(game.getCurrentTurn());
//        assertNull(game.getCurrentPlayer());
//
//        // Start du game
//        Response response = client
//                .target(baseUri)
//                .path("game/start")
//                .request()
//                .put(Entity.text(""));
//
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//
//        // Vérification que la game crée est lancée
//        game = LogJSONAndUnmarshallValue(response, Game.class);
//        assertNotNull(game);
//        assertEquals(game.getPlayer1(), game.getCurrentPlayer());
//        assertNotNull(game.getCurrentTurn());
//    }*/
//
//    /*@Test
//    void testPassBallUnstartedGame(final Client client, final URI baseUri) {
//
//        // Envoi de la requête passBall
//        Response response = client
//                .target(baseUri)
//                .path("game/action/passBall/3/0/0/0")
//                .request()
//                .put(Entity.text(""));
//
//        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
//    }
//
//    @Test
//    void testMovePieceUnstartedGame(final Client client, final URI baseUri) {
//
//        // Envoi de la requête MovePiece
//        Response response = client
//                .target(baseUri)
//                .path("game/action/movePiece/0/0/0/1")
//                .request()
//                .put(Entity.text(""));
//
//        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
//
//    }
//
//    @Test
//    void testUndoUnstartedGame(final Client client, final URI baseUri) {
//
//        // Envoi de la requête Undo
//        Response response = client
//                .target(baseUri)
//                .path("game/action/undo")
//                .request()
//                .put(Entity.text(""));
//
//        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
//    }
//
//    @Test
//    void testRedoUnstartedGame(final Client client, final URI baseUri) {
//
//        // Envoi de la requête Redo
//        Response response = client
//                .target(baseUri)
//                .path("game/action/redo")
//                .request()
//                .put(Entity.text(""));
//
//        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
//    }
//
//    @Test
//    void endTurn(final Client client, final URI baseUri) {
//
//        // Envoi de la requête Undo
//        Response response = client
//                .target(baseUri)
//                .path("game/action/endTurn")
//                .request()
//                .put(Entity.text(""));
//
//        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
//    }
//
//    @Test
//    void testGetCurrentPlayerUnstartedGame(final Client client, final URI baseUri) {
//
//        // Envoi de la requête Get currentPlayer
//        Response response = client
//                .target(baseUri)
//                .path("game/get/currentPlayer")
//                .request()
//                .get();
//
//        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
//    }
//
//    @Test
//    void testGetGameUnstartedGame(final Client client, final URI baseUri) {
//
//        // Envoi de la requête Get Game
//        Response response = client
//                .target(baseUri)
//                .path("game/get/game")
//                .request()
//                .get();
//
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//
//        Game g = LogJSONAndUnmarshallValue(response, Game.class);
//
//        assertNotNull(g);
//        assertNull(g.getCurrentTurn());
//        assertNull(g.getCurrentPlayer());
//    }
//
//    // TODO : Faire pour les get/tiles ?
//}
