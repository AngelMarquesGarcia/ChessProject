/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package chessproject;

import java.util.List;
import pieces.ChessPiece;
import utilities.Coordinates;
import views.AppContainer;
import views.BoardView;
import views.InfoMenu;
import views.TakenPieces;

/**
 *
 * @author Ángel Marqués García 
 */
public class Game {
    
    //these would presumably be the ones to keep track of turns and stuff
    public Game(){}
    void doStuff() {}
    
    private static GameBoard gameBoard;
    private static boolean[] whiteCastle = new boolean[]{true,true};
    private static boolean[] blackCastle = new boolean[]{true,true};
    private static int currentMove = 0;
    private static int movesWithNoPawnOrCapture = 0;
    private static String enPassant = "-"; //could potentially be Coordinates
    private static boolean whiteToPlay = true;
    
    
    private static ChessPiece selectedPiece = null;
    private static List<Coordinates> availableMoves;

    public static void createGame() {
        String configuration = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        createGame(configuration);
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
            return;
        } 
        ChessPiece pieceAtCell = gameBoard.at(cell);
        if (pieceAtCell != null){
            pieceTaken(pieceAtCell);
        }
        gameBoard.move(pieceToMove.getPos(), cell);
        pieceToMove.move(cell);
        
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
        System.out.println("Game.cullAvailableMoves not supported yet.");
    }
    
    public static void setSelectedPiece(ChessPiece p){
        selectedPiece = p;
        if (p != null){
            availableMoves = p.updateAvailableMoves();
            cullAvailableMoves();
            highlightAvailableMoves();
        }
        AppContainer.getAppContainer().repaint();
    }
    
    public static ChessPiece getSelectedPiece(){
        return selectedPiece;
    }
}
