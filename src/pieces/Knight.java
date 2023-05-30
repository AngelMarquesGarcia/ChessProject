/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pieces;

import chessproject.GameBoard;
import java.util.ArrayList;
import java.util.List;
import utilities.Coordinates;
import utilities.WorB;

/**
 *
 * @author Ángel Marqués García
 */
public class Knight extends ChessPiece {

    public static List<Coordinates> updateAvailableMoves(Coordinates p, WorB c) {
        List<Coordinates> coords = new ArrayList<>();
        Coordinates[] moves = getMoveset();
        for (Coordinates move : moves) {
            tryMoves(coords, move, p, c);
        }
        return coords;
    }

    private static Coordinates[] getMoveset() {
        Coordinates[] moveset = new Coordinates[2];
        moveset[0] = new Coordinates(2, 1, false);
        moveset[1] = new Coordinates(1, 2, false);

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
    private static void tryMoves(List<Coordinates> coords, Coordinates move, Coordinates pos, WorB color) {
            Coordinates newPos = pos.clone();
            int x = move.x;
            int y = move.y;
            int b0 = 1;
            int b1 = 1;
            do {
                newPos.sum(b1 * x, b0 * y);
                try { 
                    ChessPiece piece = GameBoard.at(newPos);
                    if (piece == null || piece.color != color) {
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

    public Knight(WorB color) {
        super(color);
        name = className;
    }

    @Override
    public List<Coordinates> updateAvailableMoves() {
        return Knight.updateAvailableMoves(pos, color);
    }

}
