/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package chessproject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import pieces.ChessPiece;
import utilities.Coordinates;
import utilities.WorB;
import views.AppContainer;
import views.BoardView;
import views.GameMenu;

/**
 *
 * @author Ángel Marqués García 
 */
public class Game {
    private static List<ChessTurn> history = new ArrayList<>();
    private static GameBoard gameBoard;
    private static boolean[] whiteCastle = new boolean[]{true,true};
    private static boolean[] blackCastle = new boolean[]{true,true};
    private static int currentMoveNum = 0;
    private static int movesWithNoPawnOrCapture = 0;
    private static String enPassant = "-"; //could potentially be Coordinates
    private static boolean whiteToPlay = true;
    private static Set<Coordinates> goodMoves = null;
    private static WorB checkedKing = null;
    
    private static List<ChessPiece> checkers = new ArrayList<>();
    
    private static ChessPiece selectedPiece = null;
    private static List<Coordinates> availableMoves;
    private static boolean completed;
    
    private static ChessTurn currentTurn;

    public static void createGame() { 
        String configuration = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        createGame(configuration);
    }
    
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
     * @return 
     */
    private static boolean isInCheck(WorB color){
        checkers.clear();
        ChessPiece king = gameBoard.getKing(color);
        boolean isCheck = false;
        
        if (selectedPiece.updateAvailableMoves().contains(king.getPos())){
            isCheck = true;
            checkers.add(selectedPiece);
        }
        List<Coordinates> line = Coordinates.getLine(selectedPiece.getPrevPos(), king.getPos(), true);
        ChessPiece piece = GameBoard.getClosestPiece(king.getPos(), line, gameBoard);
        
        if (piece != null && ! piece.isColor(color) && piece.updateAvailableMoves().contains(king.getPos())){
            isCheck = true;
            checkers.add(piece);
        }
        return isCheck;
    }
    
    public static boolean isCheckMate(WorB color){
        ChessPiece king = gameBoard.getKing(color);
        if (!king.updateAvailableMoves().isEmpty())
            return false; //false not checkmate, King moves available
        if (checkers.size()==2){
            return true; //true, checkmate. King is checked by two pieces and can't move
        }
        ChessPiece attacker = checkers.get(0);
        goodMoves = new HashSet<>();
        goodMoves.addAll(Coordinates.getLine(attacker.getPos(), king.getPos(), false));
        Set<Coordinates> allMoves = gameBoard.getAllMoves(color);
        goodMoves = allMoves; //MIGHT improve performance on succesive updateAvailableMoves(). Maybe not since it's a set.
        return allMoves.isEmpty();
    }
    private static void kingChecked(WorB color) {
        checkedKing = color;
        if (isCheckMate(color)){
            checkMate(color);
        } else if (goodMoves == null){
            ChessPiece attacker = checkers.get(0);
            goodMoves = new HashSet<>();
            goodMoves.addAll(Coordinates.getLine(attacker.getPos(), gameBoard.getKing(color).getPos(), false));
        }
        /**Tenemos un set en goodMoves con los movimientos que nos salvarían
        *Es jaque mate si 
        *   1. El rey no se puede mover
        *   2. Ninguna de las otras piezas puede moverse a algún sitio de goodMoves
        */ 
    }
    
    public static void createGame(String configuration) {
        GameMenu.reset();
        completed = false;
        String[] parts = configuration.split(" ");
        gameBoard = new GameBoard(parts[0]);
        setUpVariables(parts);
    }
    
    public static GameBoard getGameBoard(){
        return gameBoard;
    }
    
    public static void restart(String fen){
        if (fen == null || "".equals(fen)){
            createGame();
        } else{
            createGame(fen);
        }
        AppContainer.getAppContainer().repaint();
    }
    
    private static void setUpVariables(String[] parts) {
        whiteToPlay = "w".equals(parts[1].strip());
        for (int i=0;i<parts[2].length();i++){
            switch (parts[2].charAt(i)) {
                case 'K' -> whiteCastle[0] = true;
                case 'Q' -> whiteCastle[1] = true;
                case 'k' -> blackCastle[0] = true;
                case 'q' -> blackCastle[1] = true;
        }   }
        enPassant = parts[3].strip();
        movesWithNoPawnOrCapture = Integer.parseInt(parts[4].strip());
        currentMoveNum = Integer.parseInt(parts[5].strip());
    }
    
    public static String toStringFEN(){
        String conf = toStringBoard();
        conf += toStringFlags();
        return conf;
    }
    private static String toStringFlags(){
        String conf = "";
        conf += " " + (whiteToPlay ? "w":"b");
        conf += " ";
        if (whiteCastle[0]) conf += "K";
        if (whiteCastle[1]) conf += "Q";
        if (blackCastle[0]) conf += "k";
        if (blackCastle[1]) conf += "q";
        if (conf.endsWith(" ")) conf += "-";
        conf += " " + enPassant; //(enPassant==null ? "-":enPassant.toString());
        conf += " " + Integer.toString(movesWithNoPawnOrCapture);
        conf += " " + Integer.toString(currentMoveNum);
        return conf;
    }
    
    private static String toStringBoard() {
        ChessPiece[][] board = gameBoard.getBoard();
        String conf = "";
        int empties = 0;
        for (ChessPiece[] row: board){
            if (empties != 0){
                    conf += empties;
                    empties = 0;
                }
                conf += "/";
            for (ChessPiece piece: row){
                if (piece==null){
                    empties++;
                    if (empties == 8){ 
                        conf += Integer.toString(empties);
                        empties = 0;
                    }
                } else {
                    if (empties != 0){
                        conf += Integer.toString(empties);
                        empties = 0;
                    }
                    conf += piece.getName();
        }   }   }
        return conf.substring(1);
    }
    
    public static void move(Coordinates cell) {
        movesWithNoPawnOrCapture++;
        ChessMove currentMove = new ChessMove(selectedPiece.getPos(), cell, selectedPiece);
        ChessPiece pieceToMove = selectedPiece;
        if (selectedPiece.getName().toLowerCase().equals("p")){
            movesWithNoPawnOrCapture = 0;
        }
        if (!availableMoves.contains(cell)){
            selectedPiece = null;
            AppContainer.getAppContainer().repaint();
            return; 
        } 
        ChessPiece pieceAtCell = gameBoard.at(cell);
        if (pieceAtCell != null){
            movesWithNoPawnOrCapture = 0;
            pieceTaken(pieceAtCell);
            currentMove.setTakenPiece(pieceAtCell);
        }
        
        gameBoard.move(pieceToMove.getPos(), cell);
        pieceToMove.move(cell);
        currentMove.setFenBoardAfter(toStringFEN());
        
        if (whiteToPlay){
            currentMoveNum++;
            currentTurn = new ChessTurn(currentMoveNum);
            history.add(currentTurn);
            currentTurn.setWhiteMove(currentMove);
        } else {
            currentTurn.setBlackMove(currentMove);
        }
        
        checkedKing = null;
        goodMoves = null;
        
        if (whiteToPlay && isInCheck(WorB.BLACK)){
            currentMove.setIsCheck(true);
            kingChecked(WorB.BLACK);
        } else if (!whiteToPlay && isInCheck(WorB.WHITE)){
            currentMove.setIsCheck(true);
            kingChecked(WorB.WHITE);
        }
        
        selectedPiece = null; //goodMoves = null;
        availableMoves.clear();
        whiteToPlay = ! whiteToPlay;
        
        GameMenu.addHalfMove(currentMove);
        
        AppContainer.getAppContainer().repaint();
        
        System.out.println(toStringFEN());
    }

    private static void pieceTaken(ChessPiece piece) {
        gameBoard.addTakenPiece(piece);
    }

    /**
     * Orders BoardView to highlight the availableMoves
     */
    private static void highlightAvailableMoves() {
        BoardView boardView = BoardView.getBoardView();
        boardView.highlight(availableMoves);
    }

    /**
     * Iterates through availableMoves, and removes ilegal moves
     */
    private static void cullAvailableMoves() {
        System.out.println("Game.cullAvailableMoves not supported yet. And most likely it will never be.");
        GameBoard gameBoard = Game.getGameBoard();
        ArrayList<Coordinates>  copy = (ArrayList<Coordinates>) availableMoves;
        copy = (ArrayList<Coordinates>) copy.clone();
        for (Coordinates move:copy){
            if (gameBoard.at(move) != null && gameBoard.at(move).isWhite() == selectedPiece.isWhite()){
                availableMoves.remove(move);
        }
        }
    }
    
    public static void setSelectedPiece(ChessPiece p){
        if (!completed && p != null && p.isWhite() == whiteToPlay){ //the turn system is disabled if we remove the second condition
            selectedPiece = p;
            availableMoves = p.updateAvailableMoves();
            cullAvailableMoves();
            highlightAvailableMoves();
            AppContainer.getAppContainer().repaint();
        }
    }
    
    public static ChessPiece getSelectedPiece(){
        return selectedPiece;
    }
    
    public static Set<Coordinates> getGoodMoves(){
        return goodMoves;
    }

    public static List<ChessPiece> getCheckers() {
        return checkers;
    }

    private static void checkMate(WorB color) {
        System.out.println("-------------------------------Game Over-------------------------------");
        System.out.println("-------------------------------Game Over-------------------------------");
    }

    public static Coordinates checkForPin(Coordinates p, WorB c) {
        GameBoard gameBoard = Game.getGameBoard();
        
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
                    String pieceName = piece.getName().toUpperCase();
                    if ((dir.x == 0 || dir.y == 0) && (pieceName.equals("R") || "Q".equals(pieceName))) {
                        return dir;
                    } else if ((Math.abs(dir.x) == 1 && Math.abs(dir.y) == 1) && (pieceName.equals("B") || "Q".equals(pieceName))) {
                        return dir;
                    }
                }
            }
        }
        return null;
    }

    public static WorB getCheckedKing() {
        return checkedKing;
    }

    public static void lock() {
        completed = true;
    }

    public static boolean getWhiteToPlay() {
        return whiteToPlay;
    }

    public static void removeFocus() {
        selectedPiece = null; //goodMoves = null;
        if (availableMoves != null)
            availableMoves.clear();    
        AppContainer.getAppContainer().repaint();

    }

    public static void undoLastMove() {
        GameMenu.removeHalfMove();
        if (currentMoveNum > 1 && !whiteToPlay)
            currentMoveNum--;
       
        ChessMove moveToUndo;
        if (whiteToPlay){ //deshacemos un movimiento negro
            moveToUndo = currentTurn.pop(WorB.BLACK);
        } else { //deshacemos un movimiento blanco
            moveToUndo = currentTurn.pop(WorB.WHITE);
            if (currentMoveNum > 1)
                history.remove(history.size()-1);
            currentTurn = history.get(history.size()-1);
        }
        if (movesWithNoPawnOrCapture > 0)
            movesWithNoPawnOrCapture--;
        
        selectedPiece = moveToUndo.getMovedPiece();
        gameBoard.move(moveToUndo.getFinPos(), moveToUndo.getIniPos());
        selectedPiece.move(moveToUndo.getIniPos());
        selectedPiece = null;
        
        ChessPiece takenPiece = moveToUndo.getTakenPiece();
        gameBoard.place(takenPiece, moveToUndo.getFinPos());
        if (takenPiece != null)
            gameBoard.removeTakenPiece(takenPiece);
        whiteToPlay = !whiteToPlay;
        
        AppContainer.getAppContainer().repaint();
        
    }

    public static boolean canUndo() {
        return currentMoveNum > 1 || !whiteToPlay;
    }
}