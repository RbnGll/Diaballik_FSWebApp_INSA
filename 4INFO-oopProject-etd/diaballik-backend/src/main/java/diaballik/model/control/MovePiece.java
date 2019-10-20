package diaballik.model.control;

import diaballik.model.game.Game;
import diaballik.model.player.Piece;
import org.apache.commons.lang3.Range;

import java.util.Optional;

public class MovePiece extends Command {

    private int x1;

    private int y1;

    private int x2;

    private int y2;

    private Optional<Piece> pieceToMove;

    private Game game;

    public MovePiece(final int x1, final int y1, final int x2, final int y2, final Game game) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.game = game;

        // Détermination de la pièce à bouger
        // TODO : Vérifier avant si x et y sont dans l'intervalle !!
        // Sinon le vérifier dans la fonction !
        this.pieceToMove = game.getGameboard().getTile(x1, y1).getPiece();
    }

    @Override
    public boolean exe() {
        game.getGameboard().movePiece(x1, y1, x2, y2);
        return true;
    }

    @Override
    public boolean canDo() {
        return ifWithinBounds()
                && ifPresentPiece()
                && ifBelongstoCurrentPlayer()
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

    public boolean ifWithinBounds() {
        final Range<Integer> myRange = Range.between(0, 6);
        return myRange.contains(x1) && myRange.contains(y1) && myRange.contains(x2) && myRange.contains(y2);
    }

    public boolean ifPresentPiece() {
        return pieceToMove.isPresent();
    }

    public boolean ifFreePosition() {
        return game.getGameboard().getTile(x2, y2).getPiece().isEmpty();
    }

    public boolean ifBelongstoCurrentPlayer() {
        return game.getCurrentPlayer().getPieces().contains(pieceToMove.get());
    }

    public boolean ifNotContainsBall() {
        return !pieceToMove.get().hasBall();
    }

    public boolean ifCorrectPath() {
        final int dx = x2 - x1;
        final int dy = y2 - y1;

        // Si la pièce est déplacée de plus de 2 cases ou en diagonale
        // ou si elle n'est pas déplacée du tout
        return (dx + dy) < 2 && (dx + dy) != 0;
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
