package diaballik.model.control;

import diaballik.model.game.Game;
import diaballik.model.player.Piece;
import diaballik.model.player.Player;

import java.util.Optional;

public class MovePiece extends Command {

    private Optional<Piece> pieceToMove;

    public MovePiece(final int x1, final int y1, final int x2, final int y2, final Game game) {
        super(x1, y1, x2, y2, game);
    }

    @Override
    public boolean exe() {
        game.getGameboard().movePiece(x1, y1, x2, y2);
        return true;
    }

    @Override
    public boolean canDo() {

        return game.getGameboard().ifWithinBounds(x1, y1)
                && game.getGameboard().ifWithinBounds(x2, y2)
                && ifPresentPiece()
                && ifBelongsToCurrentPlayer()
                && ifFreePosition()
                && ifNotContainsBall()
                && ifCorrectPath();
    }

    public boolean canDoForPlayer(final Player p) {

        return game.getGameboard().ifWithinBounds(x1, y1)
                && game.getGameboard().ifWithinBounds(x2, y2)
                && ifPresentPiece()
                && ifBelongsToPlayer(p)
                && ifFreePosition()
                && ifNotContainsBall()
                && ifCorrectPath();
    }

    @Override
    public void redo() {
        this.exe();
    }

    @Override
    public void undo() {
        game.getGameboard().movePiece(x2, y2, x1, y1);
    }

    public boolean ifPresentPiece() {
        return pieceToMove.isPresent();
    }

    public boolean ifFreePosition() {
        return game.getGameboard().getTile(x2, y2).getPiece().isEmpty();
    }

    public boolean ifBelongsToCurrentPlayer() {
        return game.getCurrentPlayer().getPieces().contains(pieceToMove.get());
    }

    public boolean ifBelongsToPlayer(final Player p) {
        return p.getPieces().contains(pieceToMove.get());
    }

    public boolean ifNotContainsBall() {
        return !pieceToMove.get().hasBall();
    }

    public boolean ifCorrectPath() {
        final int dx = Math.abs(x2 - x1);
        final int dy = Math.abs(y2 - y1);

        // Si la pièce est déplacée de plus de 2 cases ou en diagonale
        // ou si elle n'est pas déplacée du tout
        return (dx + dy) < 2 && (dx + dy) != 0;
    }

    @Override
    public void setCurrentState() {
        // Détermination de la pièce à bouger
        if (game.getGameboard().ifWithinBounds(x1, y1)) {
            this.pieceToMove = game.getGameboard().getTile(x1, y1).getPiece();
        } else {
            this.pieceToMove = Optional.empty();
        }
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public Optional<Piece> getPieceToMove() {
        return pieceToMove;
    }

    public Game getGame() {
        return game;
    }
}
