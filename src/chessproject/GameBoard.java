/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chessproject;

import java.util.ArrayList;
import pieces.ChessPiece;
import java.util.Arrays;
import java.util.List;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;
import utilities.Coordinates;
import utilities.WorB;

/**
 *
 * @author Ángel Marqués García
 */
public class GameBoard {
  
    private static ChessPiece[][] gameBoard = new ChessPiece[8][8];
    private static List<ChessPiece> whitePieces = new ArrayList<>(); 
    private static List<ChessPiece> blackPieces = new ArrayList<>(); 
    private static boolean[] whiteCastle = new boolean[]{true,true};
    private static boolean[] blackCastle = new boolean[]{true,true};
    private static int currentMove = 0;
    private static int movesWithNoPawnOrCapture = 0;
    private static String enPassant = "-"; //could potentially be Coordinates
    private static boolean whiteToPlay = true;
    
    public static ChessPiece[][] getBoard(){ return gameBoard;}
    
    public static void createBoard() {
        String configuration = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        createBoard(configuration);
    }
    
    public static String toStringFEN(){
        String conf = toStringBoard();
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
        String conf = "";
        int empties = 0;
        for (ChessPiece[] row: gameBoard){
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
    
    public static void createBoard(String configuration) {
        String[] parts = configuration.split(" ");
        setUpVariables(parts);
        setUpBoard(parts[0]);
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
    
    private static void setUpBoard(String board) {
        List<String> numbers = Arrays.asList( "1","2","3","4","5","6","7","8" );
        String[] rows = board.split("/");
        for (int i=0;i<rows.length;i++)
            for (int j=0;j<rows[i].length();j++){
                String piece = rows[i].substring(j, j+1);
                if (numbers.contains(piece)){
                    j += Integer.parseInt(piece) -1;
                    continue;
                } 
                ChessPiece p = identifyPiece(piece);
                if (p != null) 
                    p.move(new Coordinates(j,i,false));
                gameBoard[i][j] = p;
            }
    }
    
    private static ChessPiece identifyPiece(String id){
        ChessPiece value = null;
        switch (id){         
            case "K" -> value = new King(WorB.WHITE);
            case "Q" -> value = new Queen(WorB.WHITE);
            case "R" -> value = new Rook(WorB.WHITE);
            case "B" -> value = new Bishop(WorB.WHITE);
            case "N" -> value = new Knight(WorB.WHITE);
            case "P" -> value = new Pawn(WorB.WHITE);
            
            case "k" -> value = new King(WorB.BLACK);
            case "q" -> value = new Queen(WorB.BLACK);
            case "r" -> value = new Rook(WorB.BLACK);
            case "b" -> value = new Bishop(WorB.BLACK);
            case "n" -> value = new Knight(WorB.BLACK);
            case "p" -> value = new Pawn(WorB.BLACK);
        }
        return value;
    }
    
    public static void move(Coordinates pos1, Coordinates pos2){
        ChessPiece piece = GameBoard.gameBoard[pos1.y][pos1.x];
        ChessPiece asd = GameBoard.gameBoard[5][0];
        GameBoard.gameBoard[pos1.y][pos1.x] = null;
        GameBoard.gameBoard[pos2.y][pos2.x] = piece;
        System.out.println(toStringFEN());
    }
    
    public static ChessPiece at(Coordinates c){
        return gameBoard[c.y][c.x];
    }
}
