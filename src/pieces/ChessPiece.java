/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pieces;

import utilities.Coordinates;
import java.util.ArrayList;
import java.util.List;
import utilities.WorB;

/**
 *
 * @author Ángel Marqués García 
 */
public abstract class ChessPiece {
    protected Coordinates pos;
    //private String sprite;
    protected WorB color;
    protected String name;
    protected List<Coordinates> availableMoves;
    
    public ChessPiece(WorB c){
        pos = new Coordinates(9,9,false);
        color = c;
        name = "";
        availableMoves = new ArrayList<>();
    }
    
    public List<Coordinates> getAvailableMoves(){
        return availableMoves;
    }
    
    
    public abstract void updateAvailableMoves();
    
    public void move(Coordinates newPos){
        pos.copy(newPos);
        updateAvailableMoves();
    }
    
    public boolean isWhite(){
        return color == WorB.WHITE;
    }

    public String getName() {
        return (isWhite() ? name:name.toLowerCase());
    }
    
    public Coordinates getPos(){
        return pos;
    }
}
