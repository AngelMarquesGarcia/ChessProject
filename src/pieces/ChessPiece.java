package pieces;

import chessproject.ChessBoard;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import utilities.Coordinates;
import java.util.List;
import javax.imageio.ImageIO;
import utilities.WorB;

/**
 *
 * @author Ángel Marqués García 
 */
public abstract class ChessPiece implements Comparable<ChessPiece>{

    protected Coordinates pos;
    private Coordinates prevPos;
    protected final WorB color;
    protected final String name;
    protected final String representation;
    protected final Image sprite; 
    protected int value;
    
    /**
     * In its children, sp is calculated one of two ways. Either have the full path to both sprites and choose the correct one, or
     * Have the beggining and the end of the path as a constant, generating the file name from the color and piece name
     * I'm not super sure which would be best, so I left a couple of both.
     * King, Queen, Pawn, have both sprites.
     * Bishop, Knight, Rook, have start and end.
     * @param n
     * @param rep
     * @param sp
     * @param c 
     */
    public ChessPiece(String n, String rep, String sp, WorB c){
        pos = new Coordinates(9,9);
        prevPos = new Coordinates(9,9);
        color = c;
        representation = (color == WorB.WHITE ? rep:rep.toLowerCase());
        name = n;
        Image im;
        try {
            im = ImageIO.read(new File(sp));
        } catch (IOException ex) {
            im = null;
            System.out.println("Couldn't find the file at " + sp);
        }
        sprite = im;
        
    }
        
    public void move(Coordinates newPos){
        prevPos.copy(pos);
        pos.copy(newPos);
        //updateAvailableMoves();
    }
    
    public WorB getColor(){
        return color;
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
}
