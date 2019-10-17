package diaballik.model.game;

import diaballik.model.player.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    public static final int BOARDSIZE = 7;

    private List<Tile> board;

    public Board() {
        board = new ArrayList<Tile>();
        board = IntStream
                .range(0, BOARDSIZE * BOARDSIZE)
                .mapToObj(i -> new Tile(i % 7, i / 7))
                .collect(Collectors.toList());
    }

    public List<Tile> getBoard() {
        return board;
    }

    public Tile getTile(final int x, final int y) {
        return board.get(x + BOARDSIZE * y);
    }

    public void addPiece(final int x, final int y, final Piece p) {
        final int i = x + BOARDSIZE * y;
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
