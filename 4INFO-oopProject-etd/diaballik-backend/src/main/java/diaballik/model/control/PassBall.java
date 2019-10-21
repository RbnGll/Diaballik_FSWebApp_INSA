package diaballik.model.control;

import diaballik.model.game.Game;
import diaballik.model.game.Tile;
import diaballik.model.player.Piece;
import diaballik.model.player.Player;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PassBall extends Command {

    private Optional<Piece> fromPiece;

    private Optional<Piece> toPiece;

    private Game game;

    public PassBall(final int x1, final int y1, final int x2, final int y2, final Game g) {
        game = g;

        if (game.getGameboard().ifWithinBounds(x1, y1) && game.getGameboard().ifWithinBounds(x2, y2)) {
            fromPiece = game.getGameboard().getTile(x1, y1).getPiece();
            toPiece = game.getGameboard().getTile(x2, y2).getPiece();
        } else {
            fromPiece = Optional.empty();
            toPiece = Optional.empty();
        }

    }

    @Override
    public boolean exe() {
        return game.getCurrentPlayer().getBall().move(toPiece.get());
    }

    @Override
    public boolean canDo() {
        // Vérifier également si les coordonnées "from" et "to" sont dans le plateau ??
        return ifPiecesExists() && ifBelongToCurrentPlayer() && ifPieceHasBall() && ifCorrectPath() && ifNoOpponentOnPath();
    }

    @Override
    public void redo() {
        this.exe();
    }

    @Override
    public void undo() {
        game.getCurrentPlayer().getBall().move(fromPiece.get());
    }

    public List<Tile> getPathTiles(final Piece fromPiece, final Piece toPiece) {
        final List<Tile> path;

        final Tile fromTile = fromPiece.getTile();
        final Tile toTile = toPiece.getTile();

        final int dx = toTile.getX() - fromTile.getX();
        final int dy = toTile.getY() - fromTile.getY();

        // On suppose la trajectoire correcte (vérifié dans canDo())
        if (dx == 0) {
            path = IntStream
                    .range(fromTile.getY() + 1, toTile.getY())
                    .mapToObj(i -> game.getGameboard().getTile(fromTile.getX(), i))
                    .collect(Collectors.toList());
        } else if (dy == 0) {
            path = IntStream
                    .range(fromTile.getX() + 1, toTile.getX())
                    .mapToObj(i -> game.getGameboard().getTile(i, fromTile.getY()))
                    .collect(Collectors.toList());
        } else {
            path = IntStream
                    .range(fromTile.getX() + 1, toTile.getX())
                    .mapToObj(i -> game.getGameboard().getTile(i, i))
                    .collect(Collectors.toList());
        }

        return path;
    }

    // TODO : Méthode ifWithinbounds ??

    public boolean ifPiecesExists() {
        return fromPiece.isPresent() && toPiece.isPresent();
    }

    public boolean ifBelongToCurrentPlayer() {
        final List<Piece> currentPlayerPieces = game.getCurrentPlayer().getPieces();
        return currentPlayerPieces.contains(fromPiece.get()) && currentPlayerPieces.contains(toPiece.get());
    }

    public boolean ifPieceHasBall() {
        return fromPiece.get().hasBall();
    }

    public boolean ifCorrectPath() {
        final int dx = toPiece.get().getTile().getX() - fromPiece.get().getTile().getX();
        final int dy = toPiece.get().getTile().getY() - fromPiece.get().getTile().getY();

        return (dx != 0 && dy == 0) || (dx == 0 && dy != 0) || (Math.abs(dx) == Math.abs(dy) && dx != 0);
    }

    public boolean ifNoOpponentOnPath() {
        final List<Tile> path = getPathTiles(fromPiece.get(), toPiece.get());
        final Player opponent = game.getCurrentPlayer() == game.getPlayer1() ? game.getPlayer2() : game.getPlayer1();

        return !path.stream().anyMatch(tile -> tile.getPiece().isPresent() && opponent.getPieces().contains(tile.getPiece().get()));
    }

    public Optional<Piece> getFromPiece() {
        return fromPiece;
    }

    public Optional<Piece> getToPiece() {
        return toPiece;
    }
}
