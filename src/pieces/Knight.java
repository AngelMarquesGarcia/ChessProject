package pieces;

import chessproject.Game;
import chessproject.GameBoard;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import utilities.Coordinates;
import utilities.WorB;

/**
 *
 * @author Ángel Marqués García
 */
public class Knight extends ChessPiece {

    public static List<Coordinates> updateAvailableMoves(Coordinates p, WorB c, Coordinates pin) {
        pin = Game.checkForPin(p, c);
        List<Coordinates> coords = new ArrayList<>();
        Set<Coordinates> moves = getMoveset();
        ChessPiece.cullMoveSet(moves, pin);
        Set<Coordinates> goodMoves = (Game.getCheckedKing() == c ? Game.getGoodMoves() : null);
        for (Coordinates move : moves) {
            tryMoves(coords, move, p, c, goodMoves);
        }
        return coords;
    }

    private static Set<Coordinates> getMoveset() {
        Set<Coordinates> moveset = new HashSet<>();
        moveset.add(new Coordinates(2, 1));
        moveset.add(new Coordinates(1, 2));

        return moveset;
    }

    /**
     * Adds to coords all the Coordinates that follow pos+n*move, where n takes
     * integer values 1,2,3... Stops when a piece is found or when trying to
     * access inexistent coordinates If a piece is found, it checks its color.
     * If it's different from color, then that piece's position is added to
     * coords.
     *
     * @param coords
     * @param move
     * @param pos
     * @param color
     */
    private static void tryMoves(List<Coordinates> coords, Coordinates move, Coordinates pos, WorB color, Set<Coordinates> goodMoves) {
        if (move == null || move.equals(0, 0)) {
            return;
        }
        Coordinates newPos = pos.clone();
        int x = move.x;
        int y = move.y;
        int b0 = 1;
        int b1 = 1;
        GameBoard gameBoard = Game.getGameBoard();
        do {
            newPos.sum(b1 * x, b0 * y);
            try {
                ChessPiece piece = gameBoard.at(newPos);
                /*if (piece == null || piece.color != color) {
                    if (goodMoves == null || goodMoves.contains(newPos))
                        coords.add(newPos.clone());
                }*/
                if (goodMoves == null || goodMoves.contains(newPos)) {
                    coords.add(newPos.clone());
                }
            } catch (IndexOutOfBoundsException ex) {
            }
            newPos.copy(pos);
            b0 *= -1;
            if (b0 > 0) {
                b1 *= -1;
            }
        } while (b1 != 1 || b0 != 1);
    }

    private static final String className = "N";
    private static final int v = 3;

    public Knight(WorB color) {
        super(color);
        name = className;
        value = v;
    }

    @Override
    public List<Coordinates> updateAvailableMoves() {
        return Knight.updateAvailableMoves(pos, color, pinned);
    }

}
