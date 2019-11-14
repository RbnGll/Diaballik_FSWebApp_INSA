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

    public PassBall(final int x1, final int y1, final int x2, final int y2, final Game g) {
        super(x1, y1, x2, y2, g);
    }

    @Override
    public boolean exe() {
        return game.getCurrentPlayer().getBall().move(toPiece.get());
    }

    @Override
    public boolean canDo() {
        return ifPiecesExists() && ifBelongToCurrentPlayer() && ifPieceHasBall() && ifCorrectPath() && ifNoPieceOnPath();
    }

    public boolean canDoForAIPlayer(final Player p) {
        return ifPiecesExists() && ifBelongToPlayer(p) && ifPieceHasBall() && ifCorrectPath() && ifNoPieceOnPath();
    }

    @Override
    public void redo() {
        this.exe();
    }

    @Override
    public void undo() {
        game.getCurrentPlayer().getBall().move(fromPiece.get());
    }

    public List<Tile> getPathTiles() {
        final List<Tile> path;

        final Tile fromTile = fromPiece.orElse(null).getTile();
        final Tile toTile = toPiece.orElse(null).getTile();

        if (fromTile != null && toTile != null) {

            final int dx = toTile.getX() - fromTile.getX();
            final int dy = toTile.getY() - fromTile.getY();

            // Si déplacement selon Y uniquement
            if (dx == 0) {

                final int uy = dy / Math.abs(dy);

                path = IntStream.range(1, Math.abs(dy))
                        .mapToObj(i -> {
                            final int y = fromTile.getY() + i * uy;
                            return game.getGameboard().getTile(fromTile.getX(), y);
                        })
                        .collect(Collectors.toList());
            } else if (dy == 0) {
                // Si déplacement selon X uniquement
                final int ux = dx / Math.abs(dx);

                path = IntStream.range(1, Math.abs(dx))
                        .mapToObj(i -> {
                            final int x = fromTile.getX() + i * ux;
                            return game.getGameboard().getTile(x, fromTile.getY());
                        })
                        .collect(Collectors.toList());
            } else {
                // Si déplacement en diagonale, dans ce cas on a abs(dx) = abs(dx) car diagonale donc un seul stream suffit
                final int ux = dx / Math.abs(dx);
                final int uy = dy / Math.abs(dy);

                path = IntStream.range(1, Math.abs(dx))
                        .mapToObj(i -> {
                            final int x = fromTile.getX() + i * ux;
                            final int y = fromTile.getY() + i * uy;
                            return game.getGameboard().getTile(x, y);
                        })
                        .collect(Collectors.toList());
            }
            return path;
        }
        return null;
    }

    public boolean ifPiecesExists() {
        return fromPiece.isPresent() && toPiece.isPresent();
    }

    public boolean ifBelongToCurrentPlayer() {
        final List<Piece> currentPlayerPieces = game.getCurrentPlayer().getPieces();
        return currentPlayerPieces.contains(fromPiece.get()) && currentPlayerPieces.contains(toPiece.get());
    }

    public boolean ifBelongToPlayer(final Player p) {
        final List<Piece> PlayerPieces = p.getPieces();
        return PlayerPieces.contains(fromPiece.get()) && PlayerPieces.contains(toPiece.get());
    }

    public boolean ifPieceHasBall() {
        return fromPiece.get().hasBall();
    }

    public boolean ifCorrectPath() {
        final int dx = toPiece.get().getTile().getX() - fromPiece.get().getTile().getX();
        final int dy = toPiece.get().getTile().getY() - fromPiece.get().getTile().getY();

        return (dx != 0 && dy == 0) || (dx == 0 && dy != 0) || (Math.abs(dx) == Math.abs(dy) && dx != 0);
    }

    public boolean ifNoPieceOnPath() {
        final List<Tile> path = getPathTiles();
        return path.stream().noneMatch(tile -> tile.getPiece().isPresent());
    }

    @Override
    public void setCurrentState() {
        // On regarde si les pièces sont bien dans les limites de la board ici !
        if (game.getGameboard().ifWithinBounds(x1, y1) && game.getGameboard().ifWithinBounds(x2, y2)) {
            fromPiece = game.getGameboard().getTile(x1, y1).getPiece();
            toPiece = game.getGameboard().getTile(x2, y2).getPiece();
        } else {
            fromPiece = Optional.empty();
            toPiece = Optional.empty();
        }
    }

    public Optional<Piece> getFromPiece() {
        return fromPiece;
    }

    public Optional<Piece> getToPiece() {
        return toPiece;
    }

    @Override
    public String toString() {
        return "from" + fromPiece.get().getTile() + "to" + toPiece.get().getTile();
    }
}
