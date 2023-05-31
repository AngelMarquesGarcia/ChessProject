/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pieces;

import chessproject.Game;
import chessproject.GameBoard;
import java.util.ArrayList;
import java.util.List;
import utilities.Coordinates;
import utilities.WorB;

/**
 *
 * @author Ángel Marqués García 
 */
public class Rook extends ChessPiece {
    
    public static List<Coordinates> updateAvailableMoves(Coordinates p, WorB c) {
        List<Coordinates> coords = new ArrayList<>();
        Coordinates[] moves = getMoveset();
        for (Coordinates move : moves) {
            tryMoves(coords, move, p, c);
        }
        return coords;
    }

    private static Coordinates[] getMoveset() {
        Coordinates[] moveset = new Coordinates[4];
        moveset[0] = new Coordinates(0, 1, false);
        moveset[1] = new Coordinates(1, 0, false);
        moveset[2] = new Coordinates(0, -1, false);
        moveset[3] = new Coordinates(-1, 0, false);
        return moveset;
    }

    /**
     * Adds to coords all the Coordinates that follow pos+n*move, where
     * n takes integer values 1,2,3...
     * Stops when a piece is found or when trying to access inexistent coordinates
     * If a piece is found, it checks its color. If it's different from color,
     * then that piece's position is added to coords.
     * 
     * @param coords
     * @param move
     * @param pos
     * @param color 
     */
    private static void tryMoves(List<Coordinates> coords, Coordinates move, Coordinates pos, WorB color) {
        try {
            GameBoard gameBoard = Game.getGameBoard();
            Coordinates newPos = pos.clone();
            newPos.sum(move);
            while (gameBoard.at(newPos) == null){
                coords.add(newPos.clone());
                newPos.sum(move);
            }
            if (!gameBoard.at(newPos).isColor(color)){
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
        return Rook.updateAvailableMoves(pos, color);
    }

}
