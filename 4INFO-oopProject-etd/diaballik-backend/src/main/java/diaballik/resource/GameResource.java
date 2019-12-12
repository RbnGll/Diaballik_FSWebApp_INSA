package diaballik.resource;

import diaballik.model.exception.CommandException;
import diaballik.model.exception.turn.TurnException;
import diaballik.model.game.Game;
import diaballik.model.game.Tile;
import diaballik.model.player.AIType;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.Range;

import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.Color;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Path("game")
@Api(value = "game")
public class GameResource {

    private Game game;
    private Logger logger= Logger.getLogger(GameResource.class.getName());

    public GameResource() {
        super();
    }

//    POST /game/newPvP/{name1}/{color1}/{name2}/{color2}
//    name1 (String) : Nom du 1er joueur
//    color1 (int) : Couleur du 1er joueur (0 : Noir ; 1 : Blanc)
//    name2 (String) : Nom du 2ème joueur
//    color2 (int) : Couleur du 2ème joueur (0 : Noir ; 1 : Blanc)

    @POST
    @Path("newPvP/{name1}/{color1}/{name2}/{color2}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postNewPvP(@PathParam("name1") final String name1, @PathParam("color1") final int color1, @PathParam("name2") final String name2, @PathParam("color2") final int color2) {

        if (!checkColors(color1, color2)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // Déterminer les couleurs
        final Color player1Color = color1 == 1 ? Color.WHITE : Color.BLACK;
        final Color player2Color = color2 == 1 ? Color.WHITE : Color.BLACK;

        game = new Game(player1Color, name1, player2Color, name2);

        // TODO : Ajout car bug sur le front sinon
        game.start();

        return Response.status(Response.Status.OK).entity(game).build();
    }

    private boolean checkColors(final int color1, final int color2) {
        final Range colorRange = Range.between(0, 1);

        return color1 != color2 && colorRange.contains(color1) && colorRange.contains(color2);
    }

//    POST /game/newPvAI/{name}/{color}/{strategy}
//    name (String) : Nom du joueur
//    color (int) : Couleur du joueur (0 : Noir ; 1 : Blanc)
//    strategy (int) : Niveau de l'IA choisi (0 : NoobAI ; 1 : StartingAI ; 2 : ProgressiveAI)

    @POST
    @Path("newPvAI/{name}/{color}/{strategy}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postNewPvAI(@PathParam("name") final String name, @PathParam("color") final int color, @PathParam("strategy") final int strategy) {

        // Validation de la stratégie
        if (!checkStrategy(strategy)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // Validation de la couleur
        if (color != 0 && color != 1) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // Déterminer la couleur et la stratégie
        final Color playerColor = color == 1 ? Color.WHITE : Color.BLACK;
        final AIType aiType;
        switch (strategy) {
            case 0:
                aiType = AIType.NOOB;
                break;
            case 1:
                aiType = AIType.STARTING;
                break;
            case 2:
                aiType = AIType.PROGRESSIVE;
                break;
            default:
                aiType = AIType.PROGRESSIVE;
        }

        game = new Game(playerColor, name, aiType);

        // TODO : Ajout car bug sur le front sinon
        game.start();

        return Response.status(Response.Status.OK).entity(game).build();
    }

    private boolean checkStrategy(final int strategy) {
        final Range strategyRange = Range.between(0, 2);

        return strategyRange.contains(strategy);
    }

    // Démarrer le jeu
    //PUT /game/start

    @PUT
    @Path("start")
    @Produces(MediaType.APPLICATION_JSON)
    public Response start() {

        // Si une partie est déjà en cours
        if (game != null && game.getCurrentTurn() != null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            game.start();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.OK).entity(game).build();
    }
//
//    Faire une passe
//    PUT /game/action/{playerID}/passBall/{pieceID1}/{pieceID2}
//    playerID (int) : Identifiant du joueur voulant effectuer le déplacement
//    pieceID1 (int) : Pièce lanceuse
//    pieceID2 (int) : Pièce receveuse

    @PUT
    @Path("action/passBall/{x1}/{y1}/{x2}/{y2}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response passBall(@PathParam("x1") final int x1, @PathParam("y1") final int y1, @PathParam("x2") final int x2, @PathParam("y2") final int y2) {

        try {
            game.passBall(x1, y1, x2, y2);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (TurnException | CommandException e) {
            logger.log(Level.WARNING, e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).entity(game).build();
    }

//    Bouger une pièce
//    PUT /game/action/{playerID}/movePiece/{pieceID}/{x}/{y}
//    playerID (int) : Identifiant du joueur voulant effectuer le déplacement
//    pieceID (int) : Pièce à déplacer
//    x (int) : Nouvelle position selon l'axe des abscisses
//    y (int) : Nouvelle Position selon l'axe des ordonnées

    @PUT
    @Path("action/movePiece/{x1}/{y1}/{x2}/{y2}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response movePiece(@PathParam("x1") final int x1, @PathParam("y1") final int y1, @PathParam("x2") final int x2, @PathParam("y2") final int y2) {

        try {
            game.movePiece(x1, y1, x2, y2);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (TurnException | CommandException e) {
            logger.log(Level.WARNING, e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).entity(game).build();
    }

//    Retour arrière
//    PUT /game/action/undo

    @PUT
    @Path("action/undo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response undo() {
        try {
            game.undo();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (TurnException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.OK).entity(game).build();
    }
//
//    Rétablir commande
//    PUT /game/action/redo

    @PUT
    @Path("action/redo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response redo() {

        try {
            game.redo();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (TurnException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.OK).entity(game).build();
    }
//
//    Fin de tour
//    PUT /game/action/endTurn

    @PUT
    @Path("action/endTurn")
    @Produces(MediaType.APPLICATION_JSON)
    public Response endTurn() {

        try {
            game.endTurn();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (TurnException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.OK).entity(game).build();
    }

//    Sortir du jeu
//    DELETE /game/delete

    @DELETE
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete() {
        game = null;

        return Response.status(Response.Status.OK).build();
    }
//
//    Get current player
//    GET /game/player/current

    @GET
    @Path("get/currentPlayer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentPlayer() {
        if (game != null && game.getCurrentPlayer() != null) {
            return Response.status(Response.Status.OK).entity(game.getCurrentPlayer()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

//      Get game
////    GET /game

    @GET
    @Path("get/game")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGame() {
        if (game != null) {
            return Response.status(Response.Status.OK).entity(game).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    // Get des tiles possibles avec une commande movePiece
    @GET
    @Path("get/tiles/movePiece/{x1}/{y1}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tile> getMovePieceTiles(@PathParam("x1") final int x1, @PathParam("y1") final int y1) {
        final List<Tile> tiles;
        try {
            tiles = game.getMovePieceTiles(x1, y1);
        } catch (NullPointerException e) {
            e.printStackTrace();
            //return Response.status(Response.Status.BAD_REQUEST).build();
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        return tiles;
    }

    // Get des tiles possibles avec une commande passBall
    @GET
    @Path("get/tiles/passBall/{x1}/{y1}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tile> getPassBallTiles(@PathParam("x1") final int x1, @PathParam("y1") final int y1) {
        final List<Tile> tiles;
        try {
            tiles = game.getPassBallTiles(x1, y1);
        } catch (NullPointerException e) {
            e.printStackTrace();
            //return Response.status(Response.Status.BAD_REQUEST).build();
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        //return Response.status(Response.Status.OK).entity(tiles).build();
        return tiles;
    }
}
