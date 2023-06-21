package Game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import pieces.ChessPiece;
import utilities.Coordinates;
import utilities.WorB;

/**
 * This is basically a strut.
 * @author Ángel Marqués García 
 */
public class MatchState {
    public ChessPiece selectedPiece;
    public List<Coordinates> availableMoves;
    public Set<Coordinates> goodMoves;
    public List<ChessPiece> checkers;
    public WorB checkedKing;
    
    public MatchState(){
        selectedPiece = null;
        availableMoves = new ArrayList<>();
        goodMoves = new HashSet<>();
        checkers = new ArrayList<>();
        checkedKing = null;
    }
}
