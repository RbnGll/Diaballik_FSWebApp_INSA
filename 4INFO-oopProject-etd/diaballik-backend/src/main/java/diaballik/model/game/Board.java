package diaballik.model.game;

import diaballik.model.player.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Board {

    private static int BOARDSIZE = 7;

    private List<Tile> board;

    public Board() {
        board = new ArrayList<Tile>(BOARDSIZE * BOARDSIZE);
    }

    public List<Tile> getBoard() {
        return board;
    }

    public void addPiece(final int x, final int y, final Piece p) {
        final int i = x * BOARDSIZE + y;

        board.get(i).setPiece(Optional.of(p));
    }

    public void move(final int x1, final int x2, final int y1, final int y2) {
        final int from = x1 * BOARDSIZE + y1;
        final int to = x2 * BOARDSIZE + y2;
        final Optional<Piece> p = board.get(from).getPiece();
        board.get(from).setPiece(Optional.empty());
        board.get(to).setPiece(p);
    }

}
