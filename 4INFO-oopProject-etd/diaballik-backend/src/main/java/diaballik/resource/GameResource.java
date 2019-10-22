package diaballik.resource;

import diaballik.model.game.Game;
import diaballik.model.player.AIType;
import io.swagger.annotations.Api;

import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.Color;

@Singleton
@Path("game")
@Api(value = "game")
public class GameResource {

    Game game;

    public GameResource() {
        super();
    }

    @POST
    @Path("newPvP/{name1}/{color1}/{name2}/{color2}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postNewPvP(@PathParam("name1") final String name1, @PathParam("color1") final int color1, @PathParam("name2") final String name2, @PathParam("color2") final int color2) {

        // Déterminer les couleurs
        final Color player1Color = color1 == 1 ? Color.WHITE : Color.BLACK;
        final Color player2Color = color2 == 1 ? Color.WHITE : Color.BLACK;

        game = new Game(player1Color, name1, player2Color, name2);

        final Response response = Response.status(Response.Status.OK).entity(game).build();

        return response;
    }

//    POST /game/newPvAI/{name}/{color}/{strategy}
//    name (String) : Nom du joueur
//    color (int) : Couleur du joueur (0 : Noir ; 1 : Blanc)
//    strategy (int) : Niveau de l'IA choisi (0 : NoobAI ; 1 : StartingAI ; 2 : ProgressiveAI)

    @POST
    @Path("newPvAI/{name}/{color}/{strategy}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postNewPvAI(@PathParam("name") final String name, @PathParam("color") final int color, @PathParam("strategy") final int strategy) {

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

        game.passBall(x1, y1, x2, y2);

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

        game.movePiece(x1, y1, x2, y2);

        return Response.status(Response.Status.OK).entity(game).build();
    }

//    Retour arrière
//    PUT /game/action/undo

    @PUT
    @Path("action/undo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response undo() {
        game.undo();

        return Response.status(Response.Status.OK).entity(game).build();
    }
//
//    Rétablir commande
//    PUT /game/action/redo

    @PUT
    @Path("action/redo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response redo() {
        game.redo();

        return Response.status(Response.Status.OK).entity(game).build();
    }
//
//    Fin de tour
//    PUT /game/action/endTurn

    @PUT
    @Path("action/endTurn")
    @Produces(MediaType.APPLICATION_JSON)
    public Response endTurn() {
        game.endTurn();

        return Response.status(Response.Status.OK).entity(game).build();
    }

//    Sortir du jeu
//    DELETE /game/delete

    @DELETE
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete() {
        game = null;

        return Response.status(Response.Status.OK).entity(game).build();
    }

    // Démarrer le jeu
    //PUT /game/start

    @PUT
    @Path("start")
    @Produces(MediaType.APPLICATION_JSON)
    public Response start() {
        game.start();

        return Response.status(Response.Status.OK).entity(game).build();
    }
//
//    Get current player
//    GET /game/player/current

//
//    Get ball (id)
//    GET /game/ball/{id}
//    id (int) : Identifiant de la balle à récupérer
//
//    Get ball (player)
//    GET /game/ball/{player}
//    player (int) : Identifiant du joueur dont on veut récupérer la balle
//
//    Get piece (id)
//    GET /game/piece/{id}
//    id (int) : Identifiant de la pièce à récupérer
//
//    Get pieces (player)
//    GET /game/piece/{player}
//    player (int) : Identifiant du joueur dont on veut récupérer toutes les pièces
//
//    Get game
//    GET /game
}
