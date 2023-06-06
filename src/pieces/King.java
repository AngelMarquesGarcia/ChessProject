/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pieces;

import chessproject.Game;
import chessproject.GameBoard;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import utilities.Coordinates;
import utilities.WorB;

/**
 *
 * @author Ángel Marqués García 
 */
public class King extends ChessPiece {
    
    private static final String className = "K";
    private static final int v = 4;
    
    public static List<Coordinates> updateAvailableMoves(Coordinates p, WorB c, Coordinates pinned){
        List<Coordinates> coords = new ArrayList<>();
        Coordinates[] moves = getMoveset();
        Set<Coordinates> attackedCells = Game.getGameBoard().getAllAttackedCells(WorB.opposite(c));
        cantBeCloseToEnemyKing(attackedCells, c);
        for (Coordinates move : moves) {
            tryMoves(coords, move, p, c, attackedCells);
        }
        return coords;
    }

    private static Coordinates[] getMoveset() {
        Coordinates[] moveset = new Coordinates[8];
        moveset[0] = new Coordinates(1, 1, false);
        moveset[1] = new Coordinates(1, -1, false);
        moveset[2] = new Coordinates(-1, 1, false);
        moveset[3] = new Coordinates(-1, -1, false);
        moveset[4] = new Coordinates(0, 1, false);
        moveset[5] = new Coordinates(0, -1, false);
        moveset[6] = new Coordinates(-1, 0, false);
        moveset[7] = new Coordinates(1, 0, false);
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
    private static void tryMoves(List<Coordinates> coords, Coordinates move, Coordinates pos, WorB color, Set<Coordinates> attackedCells) {
        GameBoard gameBoard = Game.getGameBoard();
        Coordinates newPos = pos.clone();
        newPos.sum(move);
        if (attackedCells.contains(newPos))
            return;
        try { //probablemente sea más eficiente gameBoard.isLegal(newPos) que lanzar y pillar una excepción
            ChessPiece piece = gameBoard.at(newPos);
            if (piece == null || ! piece.isColor(color)){ 
                coords.add(newPos);
            }
            //coords.add(newPos);
        } catch (IndexOutOfBoundsException ex) {

        }
    }

    private static void cantBeCloseToEnemyKing(Set<Coordinates> attackedCells, WorB c) {
        Coordinates kingPos = Game.getGameBoard().getKing(WorB.opposite(c)).getPos();
        Coordinates[] coords = getMoveset();
        for (Coordinates move: coords){
            attackedCells.add(kingPos.clone().sum(move));
        }
    }
    
    //////////////////////////////INSTANCE METHODS//////////////////////////////
    //////////////////////////////INSTANCE METHODS//////////////////////////////
    //////////////////////////////INSTANCE METHODS//////////////////////////////
    public King(WorB color) {
        super(color);
        name = className;
        value = v;
    }
    
    @Override
    public List<Coordinates> updateAvailableMoves() {
        return King.updateAvailableMoves(pos, color, pinned);
    }

}
