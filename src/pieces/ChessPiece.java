package pieces;

import utilities.Coordinates;
import java.util.List;
import java.util.Set;
import utilities.WorB;

/**
 *
 * @author Ángel Marqués García 
 */
public abstract class ChessPiece implements Comparable<ChessPiece>{

    static void cullMoveSet(Set<Coordinates> moves, Coordinates pin) {
        if (pin != null){
            if (moves.contains(pin) || moves.contains(pin.mult(-1))){
                moves.clear();
                moves.add(pin.clone());
                moves.add(pin.clone().mult(-1));
            } else{
                moves.clear();
            }
        }
    }
    protected Coordinates pos;
    private Coordinates prevPos;
    protected WorB color;
    protected String name;
    protected int value;
    protected Coordinates pinned;
    
    public ChessPiece(WorB c){
        pos = new Coordinates(9,9);
        prevPos = new Coordinates(9,9);
        color = c;
        name = "";
        pinned = null;
    }
    
    public abstract List<Coordinates> updateAvailableMoves();
    
    public void move(Coordinates newPos){
        prevPos.copy(pos);
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
        return pos.clone();
    }
    
    public Coordinates getPrevPos(){
        return prevPos.clone();
    }

    @Override
    public int compareTo(ChessPiece piece) {
        return Integer.compare(value, piece.value);
    }

    public List<Coordinates> updateAttackedCells() {
        return updateAvailableMoves();
    }
}
