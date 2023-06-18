package pieces;

import Game.ChessMatch;
import Game.MovementManager;
import chessproject.ChessApp;
import chessproject.ChessBoard;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import utilities.Coordinates;
import utilities.WorB;

/**
 *
 * @author Ángel Marqués García 
 */
public class MoveUpdater {
    
    ////////////////////////////////GENERIC////////////////////////////////
    ////////////////////////////////GENERIC////////////////////////////////
    public static List<Coordinates> getPossibleMoves(ChessPiece piece, ChessBoard board){
        if (piece.getName().equals("Pawn")){
            return getSeenCellsPawn(piece, board, false);
        } else if (piece.getName().equals("King")){
            return getSeenCellsKing(piece, board, false);
        }
        return getSeenCellsGeneric(piece, board, false);
    }
    
    public static List<Coordinates> getAttackedCells(ChessPiece piece, ChessBoard board){
        if (piece.getName().equals("Pawn")){
            return getSeenCellsPawn(piece, board, true);
        } else if (piece.getName().equals("King")){
            return getSeenCellsKing(piece, board, true);
        }
        return getSeenCellsGeneric(piece, board, true);
    }
    
    private static List<Coordinates> getSeenCellsGeneric(ChessPiece piece, ChessBoard board, boolean includeSameColor){
        //Still left to consider:
        //    1. pins (cullMoves)
        //    2. check (goodMoves)
        //    3. and maybe other stuff
        ChessMatch match = ChessApp.getChessApp().getCurrentMatch();
        MovementManager mover = new MovementManager(match);
        Coordinates pin = mover.checkForPin(piece.getPos(), piece.color);
        
        List<Coordinates> coords = new ArrayList<>();
        Set<PieceMove> moveset = MovesetMaster.getMoveset(piece);
        moveset = removeMovesIfPinned(moveset, pin);
        Coordinates newPos;
        for (PieceMove pieceMove: moveset){
            newPos = piece.getPos();
            for (Coordinates move:pieceMove){
                newPos = newPos.sum(move);
                if (!ChessBoard.isLegal(newPos)) break;
                ChessPiece pieceAt = board.at(newPos);
                if (pieceAt != null){
                    if (includeSameColor || pieceAt.isWhite() != piece.isWhite()){
                        coords.add(newPos);
                    }
                    break;
                } else {
                    coords.add(newPos);
                }
            }
        }
        Set<Coordinates> goodMoves = (match.getCheckedKing() == piece.color ? match.getGoodMoves() : null);
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

    ////////////////////////////////PAWN////////////////////////////////
    ////////////////////////////////PAWN////////////////////////////////
    /**
     * it would be interesting to implement something like a getAttackMoveset on the MovesetMaster 
     * for pieces like pawns which move and attack differently. This would be handy if many pieces
     * attacked and moved differently. For now, since only pawns make this distinction, this is not implemented.
     * @param piece
     * @param board
     * @param includeSameColor
     * @return 
     */
    private static List<Coordinates> getSeenCellsPawn(ChessPiece piece, ChessBoard board, boolean includeSameColor) {
        List<Coordinates> coords = getSeenCellsGeneric(piece, board, includeSameColor);
        int yMovement = (piece.getColor() == WorB.BLACK ? 1 : -1);
        // try one direction
        Coordinates newPos = piece.getPos().clone();
        newPos.sum(1, yMovement);
        tryTakePawn(coords, newPos, piece, board, includeSameColor);
        //try the other direction
        newPos = piece.getPos().clone();
        newPos.sum(-1, yMovement);
        tryTakePawn(coords, newPos, piece, board, includeSameColor);
        return coords;
    }
    
    private static void tryTakePawn(List<Coordinates> coords, Coordinates newPos, ChessPiece piece, ChessBoard board, boolean includeSameColor) {
        if (!ChessBoard.isLegal(newPos)){return;}
        ChessMatch match = ChessApp.getChessApp().getCurrentMatch();
        ChessPiece pieceToTake = board.at(newPos);
        if (pieceToTake != null && (includeSameColor || pieceToTake.isWhite() != piece.isWhite())) {
            coords.add(newPos.clone());
        } else if (newPos.equals(match.getEnPassant())) {
            coords.add(newPos.clone());
        }
        
    }

    ////////////////////////////////KING////////////////////////////////
    ////////////////////////////////KING////////////////////////////////
    private static List<Coordinates> getSeenCellsKing(ChessPiece piece, ChessBoard board, boolean includeSameColor) {
        ChessMatch match = ChessApp.getChessApp().getCurrentMatch();
        Set<Coordinates> attackedCells = match.getChessBoard().getAllAttackedCells(WorB.not(piece.getColor()));
        cantBeCloseToEnemyKing(attackedCells, piece.getColor(), board);
        List<Coordinates> coords = new ArrayList<>();
        Set<PieceMove> moveset = MovesetMaster.getMoveset(piece);
  
        for (PieceMove pieceMove: moveset){
            if (!ChessBoard.isLegal(pieceMove.move)) break;
            if (attackedCells.contains(pieceMove.move)) break;
            ChessPiece pieceAt = board.at(pieceMove.move);
            if (pieceAt != null){
                if (includeSameColor || pieceAt.isWhite() != piece.isWhite()){
                    coords.add(pieceMove.move);
                }
                break;
            } else {
                coords.add(pieceMove.move);
            }
        }
        tryCastle(coords, piece, board, attackedCells);
        return coords;
    }
    
    private static void cantBeCloseToEnemyKing(Set<Coordinates> attackedCells, WorB c, ChessBoard board) {
        ChessPiece enemyKing = board.getKing(WorB.not(c));
        Coordinates kingPos = enemyKing.getPos();
        Set<PieceMove> coords = MovesetMaster.getMoveset(enemyKing);
        for (PieceMove move: coords){
            attackedCells.add(kingPos.clone().sum(move.move));
        }
    }
    
    private static void tryCastle(List<Coordinates> coords, ChessPiece piece, ChessBoard board, Set<Coordinates> attackedCells) {
        ChessMatch match = ChessApp.getChessApp().getCurrentMatch();
        if (match.getCheckedKing() == piece.getColor()) return;
        boolean[] castlingRights = match.getCastle(piece.getColor());
        int y = (piece.isWhite() ? 7:0);
        List<Coordinates> line;
        boolean canCastle = true;
        if (castlingRights[0]){
            line = Coordinates.getLine(piece.getPos(), new Coordinates(7,y), false);
            line.remove(0); 
            for (Coordinates coord:line){
                if (attackedCells.contains(coord) || board.at(coord) != null){
                    canCastle = false;
                    break;
                }
            }
            if (canCastle){
                coords.add(new Coordinates(6,y));
            }
        }
        if (castlingRights[1]){
            line = Coordinates.getLine(piece.getPos(), new Coordinates(0,y), false);
            line.remove(0);
            int counter = 0;
            for (Coordinates coord:line){
                if (board.at(coord) != null){
                    canCastle = false;
                    break;
                }
                if (counter < 2 && attackedCells.contains(line.get(counter))){
                    canCastle = false;
                    break;
                }
                counter++;
            }
            if (canCastle){
                coords.add(new Coordinates(2,y));
            }
        }
    }

    /**
     * Iterates through availableMoves, and removes ilegal moves
     * @param availableMoves
     * @param selectedPiece
     */
    public static void cullAvailableMoves(List<Coordinates> availableMoves, ChessPiece selectedPiece) {
        //System.out.println("Game.cullAvailableMoves not supported yet. And most likely it will never be.");
        ChessMatch match = ChessApp.getChessApp().getCurrentMatch();
        ChessBoard gameBoard = match.getChessBoard();
        ArrayList<Coordinates>  copy = (ArrayList<Coordinates>) availableMoves;
        copy = (ArrayList<Coordinates>) copy.clone();
        for (Coordinates move:copy){
            if (gameBoard.at(move) != null && gameBoard.at(move).isWhite() == selectedPiece.isWhite()){
                availableMoves.remove(move);
            }
        }
    }

}
