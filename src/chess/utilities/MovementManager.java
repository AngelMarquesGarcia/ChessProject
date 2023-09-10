/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package chess.utilities;

import chess.match.ChessMatch;
import chess.ChessGame;
import chess.match.ChessBoard;
import chess.match.logging.ChessMove;
import chess.match.logging.ChessTurn;
import java.util.List;
import chess.pieces.Bishop;
import chess.pieces.ChessPiece;
import chess.pieces.Knight;
import chess.pieces.Queen;
import chess.pieces.Rook;
import utilities.Coordinates;
import utilities.WorB;
import views.containers.AppContainer;
import views.information.GameMenu;

/**
 *
 * @author Ángel Marqués García 
 */
public class MovementManager {
    private final ChessMatch match;
    
    private final ChessBoard gameBoard;
    
    public MovementManager(ChessMatch m){
        match = m;
        gameBoard = match.getChessBoard();
    }

    public void move(Coordinates cell) {
        boolean whiteToPlay = match.getWhiteToPlay();
        ChessTurn currentTurn = match.getCurrentTurn();
        //First we check if the selected piece can move to the given cell
        // <editor-fold desc="1st">
        if (!match.getAvailableMoves().contains(cell)){ //does this ever happen? I don't think it should. It does happen, and I think it's wrong
            match.setSelectedPiece(null);
            AppContainer.getAppContainer().repaint();
            return; 
        } 
        // </editor-fold>
        //Second, we update flags, and create a new ChessMove (the one the user just did)
        // <editor-fold desc="2nd">
        match.setMovesWithNoDev(match.getMovesWithNoDev()+1);
        ChessMove currentMove = new ChessMove(match.getSelectedPiece().getPos(), cell, match.getSelectedPiece());
        match.updateEnPassant(cell, currentMove);
        
        ChessPiece pieceToMove = match.getSelectedPiece();
        if (pieceToMove.getName().equals("Pawn")){
            match.setMovesWithNoDev(0);
        }
        // </editor-fold>
        //Third, we check if this move is taking a piece
        // <editor-fold desc="3rd">
        ChessPiece pieceAtCell = gameBoard.at(cell);
        if (pieceAtCell != null){
            match.setMovesWithNoDev(0);
            match.pieceTaken(pieceAtCell);
            currentMove.setTakenPiece(pieceAtCell);
        }
        // </editor-fold>
        //Fourth, we move the piece (checking to see if the move was castling)
        // <editor-fold desc="4th">
        gameBoard.move(pieceToMove.getPos(), cell);
        AppContainer.getAppContainer().repaint();
        pieceToMove.move(cell);
        int isCastle = checkCastle(pieceToMove); //previously st.selectedPiece
        doCastling(isCastle);
        currentMove.setCastle(isCastle);
        // </editor-fold>
        //Fifth, we save the board to currentMove
        // <editor-fold desc="5th">
        match.toggleWhiteToPlay();
        currentMove.setFenBoardAfter(match.toString());
        match.toggleWhiteToPlay();
        // </editor-fold>
        //Sixth, we check if the move is promoting a pawn
        // <editor-fold desc="6th">
        ChessPiece promoted = null;
        if (((whiteToPlay && cell.y == 0) || (!whiteToPlay && cell.y == 7)) && pieceToMove.getName().equals("Pawn")){
            promoted = promotePawn(pieceToMove);
            match.setSelectedPiece(promoted); // I think this line should be left out. 
            //It might cause a bug where the promoted piece's movements are shown when they shouldn't be.
            //Though if removed, it might cause trouble with undoing
        }
        currentMove.setPromotion(promoted);
        // </editor-fold>
        //Seventh, we update CurrentTurn
        // <editor-fold desc="7th">
        if (match.getWhiteToPlay()){
            match.incrementCurrentMoveNum();
            currentTurn = new ChessTurn(match.getCurrentMoveNum());
            match.getHistory().add(currentTurn);
            currentTurn.setWhiteMove(currentMove);
            match.setCurrentTurn(currentTurn);
        } else {
            currentTurn.setBlackMove(currentMove);
        }
        // </editor-fold>
        //Eighth, we reset move-based variables
        // <editor-fold desc="8th">
        match.setCheckedKing(null);
        match.getGoodMoves().clear();
        // </editor-fold>
        //Ninth, we check for stalemate and/or checks
        // <editor-fold desc="9th">
        WorB color = (pieceToMove.isWhite() ? WorB.BLACK:WorB.WHITE);
        if (gameBoard.getAllMoves(color).isEmpty()){
            CheckDrawDetector.staleMate(color);
        } 
        if (whiteToPlay && CheckDrawDetector.isInCheck(WorB.BLACK, gameBoard, match.getCheckers(), match.getSelectedPiece())){
            currentMove.setIsCheck(true);
            match.kingChecked(WorB.BLACK);
        } else if (!whiteToPlay && CheckDrawDetector.isInCheck(WorB.WHITE, gameBoard, match.getCheckers(), match.getSelectedPiece())){
            currentMove.setIsCheck(true);
            match.kingChecked(WorB.WHITE);
        }
        // </editor-fold>
        //Tenth, we update/reset some more move-based variables
        // <editor-fold desc="10th">
        match.updateCastlingRightsAfterMove(cell); //this one should most likely be taken up to the eighth step
        
        match.setSelectedPiece(null); //goodMoves = null;
        match.getAvailableMoves().clear();
        match.toggleWhiteToPlay();
        
        GameMenu.addHalfMove(currentMove);
        // </editor-fold>
        //Lastly, we check for draw
        // <editor-fold desc="Last">
        CheckDrawDetector.check50MoveRule(match.getMovesWithNoDev());
        CheckDrawDetector.check3FoldRepetition(match.getHistory());
        // </editor-fold>
        //System.out.println("Just did a move. Current move: " + Integer.toString(currentMoveNum));
        System.out.println(match.toString());
    }
    
    public Coordinates checkForPin(Coordinates p, WorB c) {        
        Coordinates enemyKingPos = gameBoard.getKing(c).getPos();
        List<Coordinates> line = Coordinates.getLine(p, enemyKingPos, false);
        if (!line.isEmpty()) line.remove(0);
        for (Coordinates pos : line) {
            ChessPiece piece = gameBoard.at(pos);
            if (piece != null) { //look between me and my king. When you find a piece, check if it is my king. If it's not, I can't be pinned
                if (pos.equals(enemyKingPos)) {
                    break;
                } else return null;
            }
        }
        
        line = Coordinates.getLine(p, gameBoard.getKing(c).getPos(), true);
        if (!line.isEmpty()) line.remove(0);
        Coordinates dir = Coordinates.getDir(p, gameBoard.getKing(c).getPos());
        for (Coordinates pos : line) {
            ChessPiece piece = gameBoard.at(pos);
            if (piece != null) {
                if (piece.isColor(c)) {
                    break;
                } else {
                    String pieceName = piece.getName();
                    if ((dir.x == 0 || dir.y == 0) && (pieceName.equals("Rook") || "Queen".equals(pieceName))) {
                        return dir;
                    } else if ((Math.abs(dir.x) == 1 && Math.abs(dir.y) == 1) && (pieceName.equals("Bishop") || "Queen".equals(pieceName))) {
                        return dir;
                    }
                    break;
                }
            }
        }
        return null;
    }
    
    /**
     * Checks if the move that was just done is castling. if it is not, returns 0.
     * If it is a short castle, returns 1. If it is a long castle, returns 2.
     * @param selectedPiece
     * @return 
     */
    public int checkCastle(ChessPiece selectedPiece) {
        boolean[] castlingRights = match.getCastle(selectedPiece.getColor());
        if (!castlingRights[0] && !castlingRights[1]) return 0; //if can't castle return 0
        if (!selectedPiece.getName().equals("King")) return 0; //if selected is not a king, return 0
        //we just moved a king, so we can no longer castle
        castlingRights[0] = false;
        castlingRights[1] = false;
        int x = 4;
        int y = (selectedPiece.isWhite() ? 7:0);
        Coordinates currentPos = selectedPiece.getPos();
        if (currentPos.equals(x+2,y)){
            return 1;
        } else if (currentPos.equals(x-2,y)){
            return 2;
        }
        return 0;
    }
    
    public void doCastling(int castle) {
        if (castle == 0) return;
        Coordinates kingPos = match.getSelectedPiece().getPos();
        Coordinates from;
        Coordinates to;
        if (castle == 1){
            from = new Coordinates(7, kingPos.y);
            to = new Coordinates(kingPos.x-1, kingPos.y);
        } else {
            from = new Coordinates(0, kingPos.y);
            to = new Coordinates(kingPos.x+1, kingPos.y);
        }
        ChessPiece pieceToMove = gameBoard.at(from);
        gameBoard.move(from, to);
        pieceToMove.move(to);
    }

    //Good
    public void takenEnPassant(Coordinates cell, ChessMove currentMove) {
        int x = match.getEnPassant().x;
        int y = match.getEnPassant().y + (match.getWhiteToPlay() ? 1:-1);
        ChessPiece takenPiece = gameBoard.at(x,y);
        match.pieceTaken(takenPiece);
        currentMove.setTakenPiece(takenPiece);
        match.setEnPassant(null);
        gameBoard.place(null, new Coordinates(x,y));
    }

    //Good, but can be improved/needs in depth analysis
    public ChessPiece promotePawn(ChessPiece pawn) {
        String option = AppContainer.showPromotionOptions(pawn, ChessGame.possiblePromotions);
        ChessPiece promotion = null;
        WorB color = (pawn.isWhite() ? WorB.WHITE:WorB.BLACK);
        if (option==null) option = "Queen";
        switch (option) {
            case "Queen" -> promotion = new Queen(color);
            case "Knight" -> promotion = new Knight(color);
            case "Rook" -> promotion = new Rook(color);
            case "Bishop" -> promotion = new Bishop(color);
            //default is ugly. If you exit the window, you are given a queen? 1 of 2 solutions. 
            //1. create own JDialog. this might help https://stackoverflow.com/questions/45372376/joptionpane-showinputdialog-without-cancel-button-and-exit-handle
            //2. make it so that if the user exits the window, undo is called.
        }
        promotion.move(pawn.getPrevPos());
        promotion.move(pawn.getPos());
        gameBoard.place(promotion, pawn.getPos());
        gameBoard.addPiece(promotion);
        gameBoard.removePiece(pawn);
        return promotion;
    }

}
