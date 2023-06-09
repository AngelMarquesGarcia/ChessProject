package pieces;

import chessproject.Game;
import chessproject.GameBoard;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import utilities.Coordinates;

/**
 *
 * @author Ángel Marqués García 
 */
public class MoveUpdater {
    
    public static List<Coordinates> getPossibleMoves(ChessPiece piece, GameBoard board){
        return getSeenCells(piece, board, false);
    }
    
    public static List<Coordinates> getAttackedCells(ChessPiece piece, GameBoard board){
        return getSeenCells(piece, board, true);
    }
    
    private static List<Coordinates> getSeenCells(ChessPiece piece, GameBoard board, boolean includeSameColor){
        //Still left to consider:
        //    1. pins (cullMoves)
        //    2. check (goodMoves)
        //    3. and maybe other stuff
        Coordinates pin = Game.checkForPin(piece.getPos(), piece.color);
        
        List<Coordinates> coords = new ArrayList<>();
        Set<PieceMove> moveset = MovesetMaster.getMoveset(piece);
        moveset = removeMovesIfPinned(moveset, pin);
        for (PieceMove pieceMove: moveset){
            for (Coordinates move:pieceMove){
                if (!GameBoard.isLegal(move)) break;
                ChessPiece pieceAt = board.at(move);
                if (pieceAt != null){
                    if (includeSameColor || pieceAt.isWhite() != piece.isWhite()){
                        coords.add(move);
                    }
                    break;
                } else {
                    coords.add(move);
                }
            }
        }
        Set<Coordinates> goodMoves = (Game.getCheckedKing() == piece.color ? Game.getGoodMoves() : null);
        if (goodMoves != null){
            coords.retainAll(goodMoves); //this is leaves in coords the intersection of coords and goodMoves.
        }
        return coords;
    }

    private static Set<PieceMove> removeMovesIfPinned(Set<PieceMove> moves, Coordinates pin) {
        if (pin != null) {
            int counter = 0;
            Set<PieceMove> newMoves = new HashSet<>();
            for (PieceMove move: moves){
                int n = move.n;
                Coordinates c = move.move;
                if (c.equals(pin) || c.equals(pin.mult(-1))){
                    newMoves.add(move); //might be better to add a clone or create a new one.
                    counter++;
                }
                if (counter==2) //counter==2 when we've found that we are able tom move in both directions
                    break;
            }
            return newMoves;
        }
    return moves;
    }

}
