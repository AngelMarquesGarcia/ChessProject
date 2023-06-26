package Game;

import chessproject.ChessApp;
import chessproject.ChessBoard;
import chessproject.ChessMove;
import chessproject.ChessTurn;
import java.util.List;
import java.util.Set;
import pieces.ChessPiece;
import static pieces.MoveUpdater.getPossibleMoves;
import utilities.Coordinates;
import utilities.MyUtilities;
import utilities.WorB;
import views.containers.AppContainer;

/**
 *
 * @author Ángel Marqués García 
 */
public class CheckDrawDetector {
    
    // <editor-fold desc="CHECK DETECTION">
    /**
     * Returns true if the king of color is under check, 
     * while adding the pieces checking it to a field.
     * 
     * <p>The only two pieces which can check are the piece that was just moved and
     * the first piece behind it. Thus, we check if the piece that was just moved
     * is delivering a check. After that, we obtain the positions behind it.
     * Out of those, we take the one which is closest to the king AND has a piece,
     * and we check if that piece is delivering a check.
     * @param color
     * @param matchState
     * @param gameBoard
     * @return 
     */
    public static boolean isInCheck(WorB color, MatchState matchState, ChessBoard gameBoard){
        List<ChessPiece> checkers = matchState.checkers;
        ChessPiece selectedPiece = matchState.selectedPiece;
        checkers.clear();
        ChessPiece king = gameBoard.getKing(color);
        boolean isCheck = false;
        
        //we check the piece we just moved
        if (getPossibleMoves(selectedPiece, gameBoard).contains(king.getPos())){
            isCheck = true;
            checkers.add(selectedPiece);
        }
        //we check any pieces that might have been behind it
        List<Coordinates> line = Coordinates.getLine(selectedPiece.getPrevPos(), king.getPos(), true);
        ChessPiece piece = ChessBoard.getClosestPiece(king.getPos(), line, gameBoard);
        
        if (piece != null && ! piece.isColor(color) && getPossibleMoves(piece, gameBoard).contains(king.getPos())){
            isCheck = true;
            checkers.add(piece);
        }
        return isCheck;
    }
    
    public static boolean isCheckMate(WorB color, MatchState matchState, ChessBoard gameBoard){
        List<ChessPiece> checkers = matchState.checkers;
        Set<Coordinates> goodMoves = matchState.goodMoves;
        ChessPiece king = gameBoard.getKing(color);
        if (!getPossibleMoves(king, gameBoard).isEmpty())
            return false; //false not checkmate, King moves available
        if (checkers.size()==2){
            return true; //true, checkmate. King is checked by two pieces and can't move
        }
        ChessPiece attacker = checkers.get(0);
        goodMoves.clear();
        goodMoves.addAll(Coordinates.getLine(attacker.getPos(), king.getPos(), false));
        Set<Coordinates> allMoves = gameBoard.getAllMoves(color);
        goodMoves = allMoves; //MIGHT improve performance on succesive updateAvailableMoves(). Maybe not since it's a set.
        return allMoves.isEmpty();
    }
    // </editor-fold>
    
    // <editor-fold desc="DRAW DETECTION">    
    public static void staleMate(WorB color) {
        AppContainer.showStaleMate(color);
    }

    public static void check50MoveRule(int moves) {
        if (moves >= 50){
            ChessApp.getChessApp().getCurrentMatch().lock();
            AppContainer.show50MoveDraw();
        }
    }

    public static void check3FoldRepetition(List<ChessTurn> history) {
        String currentPos = ChessApp.getChessApp().getCurrentMatch().toString();
        currentPos = MyUtilities.removeNumbers(currentPos);
        int counter = 0;
        for (ChessTurn turn: history){
            if (counter >= 3) break;
            ChessMove move = turn.getBlackMove();
            if (checkEqualBoards(move, currentPos)) counter++;
            move = turn.getWhiteMove();
            if (checkEqualBoards(move, currentPos)) counter++;
        }
        if (counter >= 3){
            AppContainer.show3FoldDraw();
            ChessApp.getChessApp().getCurrentMatch().lock();
        }
    }
    
    public static boolean checkEqualBoards(ChessMove move, String currentPos){
        if (move != null){
            String board = move.getFenBoardAfter();
            board = MyUtilities.removeNumbers(board);
            if (board.equals(currentPos))
                return true;
        }
        return false;
    }
    //</editor-fold>
}
