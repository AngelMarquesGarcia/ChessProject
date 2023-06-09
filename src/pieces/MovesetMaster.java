package pieces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import utilities.Coordinates;

/**
 * Should most likely be absorbed into MoveUpdater
 * @author Ángel Marqués García 
 */
public class MovesetMaster {
    
    static Set<PieceMove> getMoveset(ChessPiece piece) {
        Set<PieceMove> moveset;
        switch(piece.getName()){
            case "Pawn" ->  moveset = getPawnMoveset(piece);
            case "Bishop" -> moveset = getBishopMoveset();
            case "Knight" -> moveset = getKnightMoveset();
            case "Rook" -> moveset  = getRookMoveset();
            case "King" -> moveset = getKingMoveset();
            case "Queen" -> moveset = getQueenMoveset();
            default -> moveset = new HashSet<>(); //because null might cause problems
        }
        return moveset;
    }
    
    //////////////////////////Get Piece Movement//////////////////////////
    //////////////////////////Get Piece Movement//////////////////////////
    //all of these are quite similar. King and queen are equal, they only
    //differ on n. And those are the same as Bishop + Rook movesets
    //I feel compelled to add getHorizontal, getVertical, getDiagonalLR,
    //getDiagonalRL, that take an int as parameter, and return a list of
    //piecemoves. The problem with this is I feel it would be inefficient,
    //since you'd be adding the items to a list, then iterating through 
    //that list to add them to ANOTEHR list, then iterating through that.
    public static Set<PieceMove> getPawnMoveset(ChessPiece p) {
        int n = 1;
        int x = 0;
        int y = (p.isWhite()? -1:1);
        Coordinates pos = p.getPos();
        if ((pos.y == 6 && p.isWhite()) || (pos.y == 1 && !p.isWhite())){ //could be replaced by a property isFirstMove
            n++;
        }
        Set<PieceMove> moveset = new HashSet<>();
        moveset.add(new PieceMove(n, x, y));
        return moveset;
    }

    private static Set<PieceMove> getBishopMoveset() {
        int n = 7;
        Set<PieceMove> moveset = new HashSet<>();
        moveset.add(new PieceMove(n, 1, 1));
        moveset.add(new PieceMove(n, 1, -1));
        moveset.add(new PieceMove(n, -1, 1));
        moveset.add(new PieceMove(n, -1, -1));
        return moveset;
    }

    private static Set<PieceMove> getKnightMoveset() {
        int n = 1;
        Set<PieceMove> moveset = new HashSet<>();
        moveset.add(new PieceMove(n, 1, 2));
        moveset.add(new PieceMove(n, 1, -2));
        moveset.add(new PieceMove(n, -1, 2));
        moveset.add(new PieceMove(n, -1, -2));
        moveset.add(new PieceMove(n, 2, 1));
        moveset.add(new PieceMove(n, 2, -1));
        moveset.add(new PieceMove(n, -2, 1));
        moveset.add(new PieceMove(n, -2, -1));
        return moveset;
    }

    private static Set<PieceMove> getRookMoveset() {
        int n = 7;
        Set<PieceMove> moveset = new HashSet<>();
        moveset.add(new PieceMove(n, 0, 1));
        moveset.add(new PieceMove(n, 0, -1));
        moveset.add(new PieceMove(n, -1, 0));
        moveset.add(new PieceMove(n, -1, 0));
        return moveset;
    }

    private static Set<PieceMove> getKingMoveset() {
        int n = 1;
        Set<PieceMove> moveset = new HashSet<>();
        moveset.add(new PieceMove(n, 0, 1));
        moveset.add(new PieceMove(n, 0, -1));
        moveset.add(new PieceMove(n, -1, 0));
        moveset.add(new PieceMove(n, -1, 0));
        moveset.add(new PieceMove(n, 1, 1));
        moveset.add(new PieceMove(n, 1, -1));
        moveset.add(new PieceMove(n, -1, 1));
        moveset.add(new PieceMove(n, -1, -1));
        return moveset;
    }

    private static Set<PieceMove> getQueenMoveset() {
        int n = 9;
        Set<PieceMove> moveset = new HashSet<>();
        moveset.add(new PieceMove(n, 0, 1));
        moveset.add(new PieceMove(n, 0, -1));
        moveset.add(new PieceMove(n, -1, 0));
        moveset.add(new PieceMove(n, -1, 0));
        moveset.add(new PieceMove(n, 1, 1));
        moveset.add(new PieceMove(n, 1, -1));
        moveset.add(new PieceMove(n, -1, 1));
        moveset.add(new PieceMove(n, -1, -1));
        return moveset;
    }

    

}
