/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chessproject;

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
    public static ChessPiece[][] getBoard(){ return gameBoard;}

    private static ChessPiece[][] gameBoard = new ChessPiece[8][8];

    public static void createBoard() {
        String configuration = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
        createBoard(configuration);
    }
    
    public static void createBoard(String configuration) {
        List<String> numbers = Arrays.asList( "1","2","3","4","5","6","7","8" );
        String[] rows = configuration.split("/");
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
    
    public static ChessPiece identifyPiece(String id){
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
    }
}
