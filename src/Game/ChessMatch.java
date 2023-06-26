package Game;

import chessproject.ChessBoard;
import chessproject.ChessMove;
import chessproject.ChessTurn;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import pieces.ChessPiece;
import pieces.MoveUpdater;
import utilities.Coordinates;
import utilities.WorB;
import views.containers.AppContainer;
import views.containers.BoardView;
import views.containers.GameMenu;

/**
 *
 * @author Ángel Marqués García 
 */
public class ChessMatch {
    private String whitePlayer;
    private String blackPlayer;
    private Flags flags = new Flags();
    private List<ChessTurn> history;
    private ChessTurn currentTurn;

    //timer
    private ChessBoard board;

    private boolean completed;
    private MatchState matchState;
    
    private MovementManager mover;
    
    
    
    // <editor-fold desc="CONSTRUCTORS">
    public ChessMatch() {
        this(ChessGame.defaultConfig);
    }
    
    public ChessMatch(String configuration) {
        flags = new Flags();
        matchState = new MatchState();
        history = new ArrayList<>();
        completed = false;
        String[] parts = configuration.split(" ");
        try{
            board = new ChessBoard(parts[0]);
        } catch (RuntimeException e){
            System.out.println("----------------------------------La string FEN proporcionada no tiene el formato correcto----------------------------------");
            System.out.println(e.getClass().getName());
            board = new ChessBoard(ChessGame.defaultConfig);
        }
        mover = new MovementManager(this);
        setUpVariables(parts);
    }
    
    private void setUpVariables(String[] parts) {
        flags.whiteToPlay = "w".equals(parts[1].strip());
        for (int i=0;i<parts[2].length();i++){
            switch (parts[2].charAt(i)) {
                case 'K' -> flags.whiteCastle[0] = true;
                case 'Q' -> flags.whiteCastle[1] = true;
                case 'k' -> flags.blackCastle[0] = true;
                case 'q' -> flags.blackCastle[1] = true;
        }   }
        flags.enPassant = Flags.interpretEnPassant(parts[3].strip());
        flags.movesWithNoPawnOrCapture = Integer.parseInt(parts[4].strip());
        flags.startingMoveNum = Integer.parseInt(parts[5].strip());
        flags.currentMoveNum = 1;
    }
    
    // </editor-fold>
    
    // <editor-fold desc="GETTERS & SETTERS">
    //properties
    public ChessBoard getChessBoard(){
        return board;
    }
    //Flags
    public boolean[] getCastle(WorB color){
        return (color==WorB.WHITE ? flags.whiteCastle:flags.blackCastle);
    }
    public Coordinates getEnPassant() {
        return flags.enPassant;
    }
    public boolean getWhiteToPlay() {
        return flags.whiteToPlay;
    }
    //MatchState
    public void setSelectedPiece(ChessPiece p){
        if (!completed && p != null && p.isWhite() == flags.whiteToPlay){ //the turn system is disabled if we remove the second condition
            matchState.selectedPiece = p;
            matchState.availableMoves = MoveUpdater.getPossibleMoves(p, board);
            MoveUpdater.cullAvailableMoves(matchState.availableMoves, matchState.selectedPiece);
            highlightAvailableMoves();
            AppContainer.getAppContainer().repaint();
        }
    }
    public ChessPiece getSelectedPiece(){
        return matchState.selectedPiece;
    }
    public Set<Coordinates> getGoodMoves(){
        return matchState.goodMoves;
    }
    public List<ChessPiece> getCheckers() {
        return matchState.checkers;
    }
    public WorB getCheckedKing() {
        return matchState.checkedKing;
    }
    public ChessTurn getCurrentTurn() {
        return currentTurn;
    }
    public void setCurrentTurn(ChessTurn currentTurn) {
        this.currentTurn = currentTurn;
    }
    public List<ChessTurn> getHistory() {
        return history;
    }
    public Flags getFlags() {
        return flags;
    }
    public MatchState getState() {
        return matchState;
    }

    // </editor-fold>

    // <editor-fold desc="CHECK & CHECKMATE">
    public void kingChecked(WorB color) {
        matchState.checkedKing = color;
        if (CheckDrawDetector.isCheckMate(color, matchState, board)){
            checkMate(color);
        } else if (matchState.goodMoves.isEmpty()){ //what is this if for. Are we considering double checks?
            if (matchState.checkers.size() > 1) return; 
            ChessPiece attacker = matchState.checkers.get(0);
            matchState.goodMoves = new HashSet<>();
            matchState.goodMoves.addAll(Coordinates.getLine(attacker.getPos(), board.getKing(color).getPos(), false));
        }
        /**Tenemos un set en goodMoves con los movimientos que nos salvarían
        *Es jaque mate si 
        *   1. El rey no se puede mover
        *   2. Ninguna de las otras piezas puede moverse a algún sitio de goodMoves
        */ 
    }
    
    private void checkMate(WorB color) {
        System.out.println("-------------------------------Game Over-------------------------------");
        System.out.println("-------------------------------Game Over-------------------------------");
        ChessMove lastMove = currentTurn.getBlackMove();
        if (lastMove == null){
            lastMove = currentTurn.getWhiteMove();
        }
        lastMove.setIsCheckMate(true);
        AppContainer.showCheckMate(color);
    }
    // </editor-fold>
    
    // <editor-fold desc="UNDO">
    ////////////////////////////////UNDO////////////////////////////////
    ////////////////////////////////UNDO////////////////////////////////
    public void undoLastMove() {
        GameMenu.removeHalfMove();
        if (flags.currentMoveNum > 1 && !flags.whiteToPlay)
            flags.currentMoveNum--;
        
        //System.out.println("Undone a move. Current move: " + Integer.toString(currentMoveNum));
       
        ChessMove moveToUndo;
        if (flags.whiteToPlay){ //deshacemos un movimiento negro
            moveToUndo = currentTurn.pop(WorB.BLACK);
        } else { //deshacemos un movimiento blanco
            moveToUndo = currentTurn.pop(WorB.WHITE);
            if (flags.currentMoveNum > 1)
                history.remove(history.size()-1);
            currentTurn = history.get(history.size()-1);
        }
        if (flags.movesWithNoPawnOrCapture > 0)
            flags.movesWithNoPawnOrCapture--;
        
        matchState.selectedPiece = moveToUndo.getMovedPiece();
        if (moveToUndo.isPromotion()){
            board.replace(matchState.selectedPiece.getPos(), matchState.selectedPiece);
        }
        board.move(moveToUndo.getFinPos(), moveToUndo.getIniPos());
        matchState.selectedPiece.move(moveToUndo.getIniPos());
        undoCastle(moveToUndo.getCastle());
        matchState.selectedPiece = null;
        
        ChessPiece takenPiece = moveToUndo.getTakenPiece();
        board.place(takenPiece, moveToUndo.getFinPos());
        if (takenPiece != null) {
            board.removeTakenPiece(takenPiece);
            board.place(null, moveToUndo.getFinPos());
            board.place(takenPiece, takenPiece.getPos());
        }
        
        flags.whiteToPlay = !flags.whiteToPlay;
        AppContainer.getAppContainer().repaint();
        
        //reset castling rights and enPassant
        if (flags.currentMoveNum > 1){
            ChessMove move = (!flags.whiteToPlay ? currentTurn.getWhiteMove():currentTurn.getBlackMove());
            String fen = move.getFenBoardAfter();
            updateCastlingRights(fen);
            undoEnPassant(fen);
        }

    }

    public boolean canUndo() {
        return flags.currentMoveNum > 1 || !flags.whiteToPlay;
    }

    private void undoCastle(int castle) {
        if (castle==0) return;
        Coordinates kingPos = matchState.selectedPiece.getPrevPos();
        ChessPiece rook;
        Coordinates newPos;
        if (castle == 1){
            newPos = new Coordinates(7, kingPos.y);
            rook = board.at(kingPos.x-1, kingPos.y);
            board.move(rook.getPos(), newPos);
            rook.move(newPos);
        } else if (castle == 2){
            newPos = new Coordinates(0, kingPos.y);
            rook = board.at(kingPos.x+1, kingPos.y);
            board.move(rook.getPos(), newPos);
            rook.move(newPos);
        }
    }

    private void undoEnPassant(String fen) {
        String[] parts = fen.split(" ");
        String enPassantString = parts[3];
        flags.enPassant = interpretEnPassant(enPassantString);
    }
    // </editor-fold>
    
    
    // <editor-fold desc="UTILITY">
    /**Orders BoardView to highlight the availableMoves */
    private void highlightAvailableMoves() {
        BoardView boardView = BoardView.getBoardView();
        boardView.highlight(matchState.availableMoves);
    }
    
    public void pieceTaken(ChessPiece piece) {
        board.addTakenPiece(piece);
    }
    
    /**
     * De-selects any piece, and clears availableMoves. This method is to be used whenever a pop-up appears.
     * Though it could also be used to de-select pieces when clicking elsewhere.
     */
    public void removeFocus() {
        matchState.selectedPiece = null; //goodMoves = null;
        if (matchState.availableMoves != null)
            matchState.availableMoves.clear();
    }
 
    // <editor-fold desc="TO-STRING">
    @Override
    public String toString(){ //previously toStringFEN()
        String conf = board.toString();
        conf += flags.toString();
        return conf;
    }
    // </editor-fold>
    
    // <editor-fold desc="CASTLING & ENPASSANT">
    public void updateCastlingRights(String fen) {
        String[] parts = fen.split(" ");
        String castle = parts[2];
        for (int i=0;i<castle.length();i++){
            switch (castle.charAt(i)) {
                case 'K' -> flags.whiteCastle[0] = true;
                case 'Q' -> flags.whiteCastle[1] = true;
                case 'k' -> flags.blackCastle[0] = true;
                case 'q' -> flags.blackCastle[1] = true;
        }   }
    }
    
    /**
     * Evaluates wether a piece is moving from or to any of the corners of the board.
     * If that is true, then it's not possible to castle on that side.
     * It might be better to do this by checking name instead of position
     * @param finPos 
     */
    public void updateCastlingRightsAfterMove(Coordinates finPos) {
        Coordinates iniPos = matchState.selectedPiece.getPrevPos();
        if (iniPos.equals(0,0) || finPos.equals(0,0))
            flags.blackCastle[1] = false;
        if (iniPos.equals(7,0) || finPos.equals(7,0)) 
            flags.blackCastle[0] = false;
        if (iniPos.equals(7,7) || finPos.equals(7,7)) 
            flags.whiteCastle[0] = false;
        if (iniPos.equals(0,7) || finPos.equals(0,7)) 
            flags.whiteCastle[1] = false;
        if (iniPos.equals(4,0)){
            flags.blackCastle[0] = false;
            flags.blackCastle[1] = false;
        } else if (iniPos.equals(4,0)){
            flags.whiteCastle[0] = false;
            flags.whiteCastle[1] = false;
        }
    }

    public void updateEnPassant(Coordinates cell, ChessMove currentMove) {
        if (cell.equals(flags.enPassant)){
            mover.takenEnPassant(cell, currentMove);
            return;
        }
        flags.enPassant = null;
        if (!matchState.selectedPiece.getName().equals("Pawn")) return;
        Coordinates pos = matchState.selectedPiece.getPos();
        if (Math.abs(pos.y-cell.y) > 1){
            int y = (flags.whiteToPlay ? cell.y+1:cell.y-1);
            flags.enPassant = new Coordinates(cell.x, y);
        }
    }

    public Coordinates interpretEnPassant(String strip) {
        if ("-".equals(strip)) return null;
        String col = strip.substring(0,1);
        String row = strip.substring(1,2);
        int x = 0;
        int y = 8 - Integer.parseInt(row);
        switch (col) {
            case "a" -> x = 0;
            case "b" -> x = 1;
            case "c" -> x = 2;
            case "d" -> x = 3;
            case "e" -> x = 4;
            case "f" -> x = 5;
            case "g" -> x = 6;
            case "h" -> x = 7;
        }
        return new Coordinates(x,y);
    }
    // </editor-fold>
    
    public void lock() {
        completed = true;
    }
    // </editor-fold>
}