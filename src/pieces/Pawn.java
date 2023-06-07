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
public class Pawn extends ChessPiece {

    public static List<Coordinates> updateAvailableMoves(Coordinates p, WorB c, Coordinates pin) {
        pin = Game.checkForPin(p,c);
        List<Coordinates> coords = new ArrayList<>();
        int yMovement = (c == WorB.BLACK ? 1 : -1);
        Coordinates move = new Coordinates(0, yMovement);
        if (pin != null && !(move.equals(pin) || move.equals(new Coordinates(pin.x, pin.y*-1))))
            move = null;
        Set<Coordinates> goodMoves = (Game.getCheckedKing() == c ? Game.getGoodMoves():null);
        tryMoves(coords, move, p, c, goodMoves);

        tryTake(coords, p, c);
        return coords;
    }
    
    public static List<Coordinates> updateAttackedCells(Coordinates p, WorB c, Coordinates pin) {
        List<Coordinates> coords = new ArrayList<>();
        tryTake(coords, p, c);
        return coords;
    }

    private static boolean isFirstMove(Coordinates p, WorB c) {
        return (p.y == 6 && c.equals(WorB.WHITE)) || (p.y == 1 && c.equals(WorB.BLACK));
    }

    private static void tryTake(List<Coordinates> coords, Coordinates p, WorB c) {
        int yMovement = (c == WorB.BLACK ? 1 : -1);
        GameBoard gameBoard = Game.getGameBoard();
        Coordinates newPos;
        ChessPiece piece;
        // try one direction
        try {
            newPos = p.clone();
            newPos.sum(1, yMovement);
            piece = gameBoard.at(newPos);
            if (piece != null /*&& !piece.isColor(c)*/) {
                coords.add(newPos.clone());
            }
        } catch (IndexOutOfBoundsException ex) {}
        //try the other direction
        try {
            newPos = p.clone();
            newPos.sum(-1, yMovement);
            piece = gameBoard.at(newPos);
            if (piece != null /*&& !piece.isColor(c)*/) {
                coords.add(newPos.clone());
            }
        } catch (IndexOutOfBoundsException ex) {}

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
        if (move == null || move.equals(0,0))
            return;
        try {
            GameBoard gameBoard = Game.getGameBoard();
            Coordinates newPos = pos.clone();
            newPos.sum(move);
            if (gameBoard.at(newPos) == null) {
                if (goodMoves == null || goodMoves.contains(newPos))
                    coords.add(newPos.clone());
                newPos.sum(move);
                if (isFirstMove(pos, color) && gameBoard.at(newPos) == null) {
                    if (goodMoves == null || goodMoves.contains(newPos))
                        coords.add(newPos.clone());
                }
            }
        } catch (IndexOutOfBoundsException ex) {
        }
    }

    private static final String className = "P";
    private static final int v = 1;

    public Pawn(WorB color) {
        super(color);
        name = className;
        value = v;
    }

    @Override
    public List<Coordinates> updateAvailableMoves() {
        return Pawn.updateAvailableMoves(pos, color, pinned);
    }
    
    @Override
    public List<Coordinates> updateAttackedCells() {
        return Pawn.updateAttackedCells(pos, color, pinned);
    }

}
