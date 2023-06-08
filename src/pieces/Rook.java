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
public class Rook extends ChessPiece {

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
        moveset.add(new Coordinates(0, 1));
        moveset.add(new Coordinates(1, 0));
        moveset.add(new Coordinates(0, -1));
        moveset.add(new Coordinates(-1, 0));
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
        try {
            GameBoard gameBoard = Game.getGameBoard();
            Coordinates newPos = pos.clone();
            newPos.sum(move);
            while (gameBoard.at(newPos) == null) {
                if (goodMoves == null || goodMoves.contains(newPos)) {
                    coords.add(newPos.clone());
                }
                newPos.sum(move);
            }
            if (goodMoves == null || goodMoves.contains(newPos)) { //!gameBoard.at(newPos).isColor(color) && (goodMoves == null || goodMoves.contains(newPos))
                coords.add(newPos);
            }
        } catch (IndexOutOfBoundsException ex) {

        }
    }

    private static final String className = "R";
    private static final int v = 5;

    public Rook(WorB color) {
        super(color);
        name = className;
        value = v;
    }

    @Override
    public List<Coordinates> updateAvailableMoves() {
        return Rook.updateAvailableMoves(pos, color, pinned);
    }

}
