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

    public void addPiece(int x, int y, Piece p) {
        int i = x * BOARDSIZE + y;

        board.get(i).setPiece(Optional.of(p));
    }

    public void move(int x1, int x2, int y1, int y2) {
        int from = x1 * BOARDSIZE + y1;
        int to = x2 * BOARDSIZE + y2;
        Optional<Piece> p = board.get(from).getPiece();
        board.get(from).setPiece(Optional.empty());
        board.get(to).setPiece(p);
    }

}
