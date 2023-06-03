/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
public class Bishop extends ChessPiece {

    public static List<Coordinates> updateAvailableMoves(Coordinates p, WorB c, Coordinates pin) {
        
        List<Coordinates> coords = new ArrayList<>();
        if (Game.getCheckers().size() == 2){
            return coords;
        }
        Set<Coordinates> moves = getMoveset();
        ChessPiece.cullMoveSet(moves, pin);
        Set<Coordinates> goodMoves = Game.getGoodMoves();
        for (Coordinates move : moves) {
            tryMoves(coords, move, p, c, goodMoves);
        }
        return coords;
    }

    /**
     * returns an array of 4 coordinates corresponding to moving one space diagonally.
     * If the piece is pinned, and is pinned in a diagonal, 
     * then it returns the direction it's pinned from, 
     * since it will be able to move along that diagonal while still protecting the king
     * @return 
     */
    private static Set<Coordinates>getMoveset() {
        Set<Coordinates> moveset = new HashSet<>();
        moveset.add(new Coordinates(1, 1, false));
        moveset.add(new Coordinates(1, -1, false));
        moveset.add(new Coordinates(-1, 1, false));
        moveset.add(new Coordinates(-1, -1, false));
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
    private static void tryMoves(List<Coordinates> coords, Coordinates move, Coordinates pos, WorB color, Set<Coordinates> goodMoves) {
        if (move == null || move.equals(0,0))
            return;
        try {
            GameBoard gameBoard = Game.getGameBoard();
            Coordinates newPos = pos.clone();
            newPos.sum(move);
            while (gameBoard.at(newPos) == null){
                if (goodMoves == null || goodMoves.contains(newPos))
                    coords.add(newPos.clone());
                newPos.sum(move);
            }
            if (!gameBoard.at(newPos).isColor(color)){
                coords.add(newPos);
            }
        } catch (IndexOutOfBoundsException ex) {

        }
    }

    private static final String className = "B";
    private static final int v = 3;

    public Bishop(WorB color) {
        super(color);
        name = className;
        value = v;
    }

    @Override
    public List<Coordinates> updateAvailableMoves() {
        return Bishop.updateAvailableMoves(pos, color, pinned);
    }

}
