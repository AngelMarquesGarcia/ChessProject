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
    public ChessPiece selectedPiece = null;
    public List<Coordinates> availableMoves = new ArrayList<>();
    public Set<Coordinates> goodMoves = new HashSet<>();
    public List<ChessPiece> checkers = new ArrayList<>();
    public WorB checkedKing = null;
}
