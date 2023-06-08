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

/**
 *
 * @author Ángel Marqués García 
 */
public class Game {
    
    private static GameBoard gameBoard;
    private static boolean[] whiteCastle = new boolean[]{true,true};
    private static boolean[] blackCastle = new boolean[]{true,true};
    private static int currentMove = 0;
    private static int movesWithNoPawnOrCapture = 0;
    private static String enPassant = "-"; //could potentially be Coordinates
    private static boolean whiteToPlay = true;
    private static Set<Coordinates> goodMoves = null;
    
    private static List<ChessPiece> checkers = new ArrayList<>();
    
    private static ChessPiece selectedPiece = null;
    private static List<Coordinates> availableMoves;

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
        System.out.println("I mean, I better hope so");
        System.out.println("Que tal, mundo");
        
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
        String[] parts = configuration.split(" ");
        gameBoard = new GameBoard(parts[0]);
        setUpVariables(parts);
    }
    
    public static GameBoard getGameBoard(){
        return gameBoard;
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
        currentMove = Integer.parseInt(parts[5].strip());
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
        conf += " " + Integer.toString(currentMove);
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
        ChessPiece pieceToMove = selectedPiece;
        if (!availableMoves.contains(cell)){
            selectedPiece = null;
            AppContainer.getAppContainer().repaint();
            return; 
        } 
        ChessPiece pieceAtCell = gameBoard.at(cell);
        if (pieceAtCell != null){
            pieceTaken(pieceAtCell);
        }
        gameBoard.move(pieceToMove.getPos(), cell);
        pieceToMove.move(cell);
        goodMoves = null;
        
        if (whiteToPlay && isInCheck(WorB.BLACK)){
            kingChecked(WorB.BLACK);
        } else if (!whiteToPlay && isInCheck(WorB.WHITE)){
            kingChecked(WorB.WHITE);
        }

        selectedPiece = null; //goodMoves = null;
        availableMoves.clear();
        whiteToPlay = ! whiteToPlay;
        
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
    }
    
    public static void setSelectedPiece(ChessPiece p){
        if (p != null && p.isWhite() == whiteToPlay){ //the turn system is disabled if we remove the second condition
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
}