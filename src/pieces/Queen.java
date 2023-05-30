/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pieces;

import java.util.ArrayList;
import java.util.List;
import utilities.Coordinates;
import utilities.WorB;

/**
 *
 * @author Ángel Marqués García 
 */
public class Queen extends ChessPiece {
    
    public static List<Coordinates> updateAvailableMoves(Coordinates p, WorB c){
        List<Coordinates> coords = new ArrayList<>();
        coords.addAll(Rook.updateAvailableMoves(p, c));
        coords.addAll(Bishop.updateAvailableMoves(p, c));
        return coords;
    }

    private static final String className = "Q";
    
    public Queen(WorB color) {
        super(color);
        name = className;
    }
    
    @Override
    public void updateAvailableMoves() {
        List<Coordinates> moves = Queen.updateAvailableMoves(pos, color);
        availableMoves.clear();
        availableMoves.addAll(moves);
    }

}
