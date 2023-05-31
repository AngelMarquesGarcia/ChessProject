/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pieces;

import utilities.Coordinates;
import java.util.List;
import utilities.WorB;

/**
 *
 * @author Ángel Marqués García 
 */
public abstract class ChessPiece implements Comparable<ChessPiece>{
    protected Coordinates pos;
    protected WorB color;
    protected String name;
    protected int value;
    
    public ChessPiece(WorB c){
        pos = new Coordinates(9,9,false);
        color = c;
        name = "";
    }
    
    public abstract List<Coordinates> updateAvailableMoves();
    
    public void move(Coordinates newPos){
        pos.copy(newPos);
        //updateAvailableMoves();
    }
    
    public boolean isWhite(){
        return color == WorB.WHITE;
    }
    
    public boolean isColor(WorB color){
        return this.color == color;
    }

    public String getName() {
        return (isWhite() ? name:name.toLowerCase());
    }
    
    public Coordinates getPos(){
        return pos;
    }

    @Override
    public int compareTo(ChessPiece piece) {
        return Integer.compare(value, piece.value);
    }
}
