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
public class Pawn extends ChessPiece {

    public static List<Coordinates> updateAvailableMoves(Coordinates p, WorB c) {
        List<Coordinates> coords = new ArrayList<>();
        int yMovement = (c == WorB.BLACK ? 1 : -1);
        Coordinates move = new Coordinates(0, yMovement, false);
        tryMoves(coords, move, p, c);

        tryTake(coords, p, c);
        return coords;
    }

    private static boolean isFirstMove(Coordinates p, WorB c) {
        return (p.y == 6 && c.equals(WorB.WHITE)) || (p.y == 1 && c.equals(WorB.BLACK));
    }

    private static void tryTake(List<Coordinates> coords, Coordinates p, WorB c) {
        int yMovement = (c == WorB.BLACK ? 1 : -1);
        Coordinates newPos;
        ChessPiece piece;
        // try one direction
        try {
            newPos = p.clone();
            newPos.sum(1, yMovement);
            piece = GameBoard.at(newPos);
            if (piece != null && !piece.isColor(c)) {
                coords.add(newPos.clone());
            }
        } catch (IndexOutOfBoundsException ex) {}
        //try the other direction
        try {
            newPos = p.clone();
            newPos.sum(-1, yMovement);
            piece = GameBoard.at(newPos);
            if (piece != null && !piece.isColor(c)) {
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
    private static void tryMoves(List<Coordinates> coords, Coordinates move, Coordinates pos, WorB color) {
        try {
            Coordinates newPos = pos.clone();
            newPos.sum(move);
            if (GameBoard.at(newPos) == null) {
                coords.add(newPos.clone());
                newPos.sum(move);
                if (isFirstMove(pos, color) && GameBoard.at(newPos) == null) {
                    coords.add(newPos);
                }
            }
        } catch (IndexOutOfBoundsException ex) {
        }
    }

    private static final String className = "P";

    public Pawn(WorB color) {
        super(color);
        name = className;
    }

    @Override
    public List<Coordinates> updateAvailableMoves() {
        return Pawn.updateAvailableMoves(pos, color);
    }

}
