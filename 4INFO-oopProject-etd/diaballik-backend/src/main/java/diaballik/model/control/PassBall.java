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

    public PassBall(final Optional<Piece> from, final Optional<Piece> to, final Game g) {
        fromPiece = from;
        toPiece = to;
        game = g;
    }

    @Override
    public boolean exe() {
        return game.getCurrentPlayer().getBall().move(toPiece.get());
    }

    @Override
    public boolean canDo() {
        return ifPiecesExists() && ifBelongToCurrentPlayer() && ifCorrectPath() && ifNoOpponentOnPath();
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

    private boolean ifPiecesExists() {
        return (fromPiece.isPresent() || toPiece.isPresent());
    }

    private boolean ifBelongToCurrentPlayer() {
        final List<Piece> currentPlayerPieces = game.getCurrentPlayer().getPieces();
        return currentPlayerPieces.contains(fromPiece.get()) && currentPlayerPieces.contains(toPiece.get());
    }

    private boolean ifCorrectPath() {
        final int dx = toPiece.get().getTile().getX() - fromPiece.get().getTile().getX();
        final int dy = toPiece.get().getTile().getY() - fromPiece.get().getTile().getY();

        return (dx != 0 && dy == 0) || (dx == 0 && dy != 0) || (dx == dy && dx != 0);
    }

    private boolean ifNoOpponentOnPath() {
        final List<Tile> path = getPathTiles(fromPiece.get(), toPiece.get());
        final Player opponent = game.getCurrentPlayer() == game.getPlayer1() ? game.getPlayer1() : game.getPlayer2();

        return path.stream().anyMatch(tile -> tile.getPiece().isPresent() && opponent.getPieces().contains(tile.getPiece().get()));
    }

}
