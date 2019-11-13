package diaballik.resource;

import com.github.hanleyt.JerseyExtension;
import diaballik.model.game.Game;
import diaballik.model.game.Tile;
import diaballik.model.player.Piece;
import diaballik.model.player.Player;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestGameRessourceGameStarted {

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

    @BeforeEach
    void setUp(final Client client, final URI baseUri) {

        // Création du game sur le serveur
        client
                .target(baseUri)
                .path("game/newPvP/Robin/0/Ronan/1")
                .request()
                .post(Entity.text(""));

        // Start du game sur le serveur
        Response response = client
                .target(baseUri)
                .path("game/start")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testStartOngoingGame(final Client client, final URI baseUri) {

        Response response = client
                .target(baseUri)
                .path("game/start")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testPassBallTurnException(final Client client, final URI baseUri) {

        // Faire les 3 actions du tour
        for (int i = 0; i < 3; i++) {

            Response response = client
                    .target(baseUri)
                    .path("game/action/movePiece/0/" + i + "/0/" + (i + 1))
                    .request()
                    .put(Entity.text(""));

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        }

        // Une 4e action envoyée par le joueur si pas de "EndTurn" n'est pas correcte
        Response response = client
                .target(baseUri)
                .path("game/action/passBall/3/0/0/3")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testPassBallCommandException(final Client client, final URI baseUri) {

        // Faire une passe impossible
        Response response = client
                .target(baseUri)
                .path("game/action/passBall/0/0/0/0")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testPassBall(final Client client, final URI baseUri) {
        // Faire une passe possible
        Response response = client
                .target(baseUri)
                .path("game/action/passBall/3/0/0/0")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Game game = LogJSONAndUnmarshallValue(response, Game.class);
        Tile ballTile = game.getCurrentPlayer().getBall().getPiece().getTile();
        assertEquals(0, ballTile.getX());
        assertEquals(0, ballTile.getY());
    }

    @Test
    void testMovePieceTurnException(final Client client, final URI baseUri) {
        // Faire les 3 actions du tour
        for (int i = 0; i < 3; i++) {

            Response response = client
                    .target(baseUri)
                    .path("game/action/movePiece/0/" + i + "/0/" + (i + 1))
                    .request()
                    .put(Entity.text(""));

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        }

        // Une 4e action envoyée par le joueur si pas de "EndTurn" n'est pas correcte
        Response response = client
                .target(baseUri)
                .path("game/action/movePiece/0/3/0/4")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testMovePieceCommandException(final Client client, final URI baseUri) {

        // Faire une commande MovePiece impossible
        Response response = client
                .target(baseUri)
                .path("game/action/movePiece/0/0/0/0")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testMovePiece(final Client client, final URI baseUri) {
        // Faire une commande MovePiece possible
        Response response = client
                .target(baseUri)
                .path("game/action/movePiece/0/0/0/1")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Game game = LogJSONAndUnmarshallValue(response, Game.class);

        // On vérifie qu'une pièce est bien placée sur la case 0,1
        List<Piece> pieces = game.getCurrentPlayer().getPieces();
        assertTrue(pieces.stream().anyMatch(p -> p.getTile().getX() == 0 && p.getTile().getY() == 1));
        assertFalse(pieces.stream().anyMatch(p -> p.getTile().getX() == 0 && p.getTile().getY() == 0));
    }

    @Test
    void testUndoTurnException(final Client client, final URI baseUri) {
        // Faire une commande Undo avec une pile vide
        Response response = client
                .target(baseUri)
                .path("game/action/undo")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testUndo(final Client client, final URI baseUri) {
        // Faire une commande MovePiece possible
        client
                .target(baseUri)
                .path("game/action/movePiece/0/0/0/1")
                .request()
                .put(Entity.text(""));

        // Faire une commande Undo
        Response response = client
                .target(baseUri)
                .path("game/action/undo")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        // On vérifie que la commande a bien été annulée
        Game game = LogJSONAndUnmarshallValue(response, Game.class);
        List<Piece> pieces = game.getCurrentPlayer().getPieces();
        assertFalse(pieces.stream().anyMatch(p -> p.getTile().getX() == 0 && p.getTile().getY() == 1));
        assertTrue(pieces.stream().anyMatch(p -> p.getTile().getX() == 0 && p.getTile().getY() == 0));
    }

    @Test
    void testRedoTurnException(final Client client, final URI baseUri) {
        // Faire une commande Redo avec une pile vide
        Response response = client
                .target(baseUri)
                .path("game/action/redo")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testRedo(final Client client, final URI baseUri) {
        // Faire une commande MovePiece possible
        client
                .target(baseUri)
                .path("game/action/movePiece/0/0/0/1")
                .request()
                .put(Entity.text(""));

        // Faire une commande Undo
        client
                .target(baseUri)
                .path("game/action/undo")
                .request()
                .put(Entity.text(""));

        // Faire une commande Redo
        Response response = client
                .target(baseUri)
                .path("game/action/redo")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        // On vérifie que la commande a bien été Redo
        Game game = LogJSONAndUnmarshallValue(response, Game.class);
        List<Piece> pieces = game.getCurrentPlayer().getPieces();
        assertFalse(pieces.stream().anyMatch(p -> p.getTile().getX() == 0 && p.getTile().getY() == 0));
        assertTrue(pieces.stream().anyMatch(p -> p.getTile().getX() == 0 && p.getTile().getY() == 1));
    }

    @Test
    void testEndTurnTurnException(final Client client, final URI baseUri) {

        // Commande EndTurn alors que les 3 commandes n'ont pas été lancées
        Response response = client
                .target(baseUri)
                .path("game/action/endTurn")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void testEndTurn(final Client client, final URI baseUri) {

        // Faire les 3 actions nécessaires
        for (int i = 0; i < 3; i++) {

            Response response = client
                    .target(baseUri)
                    .path("game/action/movePiece/0/" + i + "/0/" + (i + 1))
                    .request()
                    .put(Entity.text(""));

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        }

        // Envoi d'une requête endTurn
        Response response = client
                .target(baseUri)
                .path("game/action/endTurn")
                .request()
                .put(Entity.text(""));

        // Vérifier si c'est bien passé au tour suivant
        Game game = LogJSONAndUnmarshallValue(response, Game.class);
        assertEquals(game.getCurrentPlayer(), game.getPlayer2());
    }

    @Test
    void testDelete(final Client client, final URI baseUri) {
        final Response response = client
                .target(baseUri)
                .path("game/delete")
                .request()
                .delete();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        final Game game = LogJSONAndUnmarshallValue(response, Game.class);

        assertNull(game);
    }

    @Test
    void testGetCurrentPlayer(final Client client, final URI baseUri) {

        final Response response = client
                .target(baseUri)
                .path("game/get/currentPlayer")
                .request()
                .get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Player player = LogJSONAndUnmarshallValue(response, Player.class);
        assertEquals("Robin", player.getName());
    }

    @Test
    void testGetGame(final Client client, final URI baseUri) {

        final Response response = client
                .target(baseUri)
                .path("game/get/game")
                .request()
                .get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Game game = LogJSONAndUnmarshallValue(response, Game.class);
        assertNotNull(game);
    }
}
