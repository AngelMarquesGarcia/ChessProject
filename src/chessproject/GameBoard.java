/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chessproject;

import java.util.ArrayList;
import pieces.ChessPiece;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;
import utilities.Coordinates;
import utilities.WorB;

/**
 *If it was found to be neccesary to have multiple boards/games at once, it could be done as follows.
 * Turn all fields non-static. Create a new static field Map< String, ChessPiece[][]>.
 * Whenever a new board is created, insert it into the map with an id.
 * Whenever a board is needed, retrieve it from the map with its id.
 * 
 * @author Ángel Marqués García
 */
public class GameBoard {

    public static ChessPiece getClosestPiece(Coordinates pos, List<Coordinates> line, GameBoard gameBoard) {
        int bestDistance = 18;
        ChessPiece bestPiece = null;
        for (Coordinates c: line){
            Coordinates d = c.clone().sub(pos);
            int dist = d.x+d.y;
            if (dist < bestDistance){
                bestDistance = dist;
                bestPiece = gameBoard.at(c);
            }
        }
        return bestPiece;
    }
  
    private ChessPiece[][] gameBoard = new ChessPiece[8][8];
    private List<ChessPiece> whitePieces = new ArrayList<>(); 
    private List<ChessPiece> blackPieces = new ArrayList<>();
    private List<ChessPiece> whiteTaken = new ArrayList<>(); 
    private List<ChessPiece> blackTaken = new ArrayList<>();
    private ChessPiece whiteKing;
    private ChessPiece blackKing;
    
    public ChessPiece[][] getBoard(){ return gameBoard;}
    public List<ChessPiece> getPieces(WorB color){
        return (color==WorB.WHITE ? whitePieces:blackPieces);}
    public List<ChessPiece> getTaken(WorB color){ 
        return (color==WorB.WHITE ? whiteTaken:blackTaken);}
    public ChessPiece getKing(WorB color){ 
        return (color==WorB.WHITE ? whiteKing:blackKing);}
    
    public Set<Coordinates> getAllMoves(WorB color){
        List<ChessPiece> pieces = (color==WorB.WHITE ? whitePieces:blackPieces);
        Set<Coordinates> coords = new HashSet<>();
        for (ChessPiece piece: pieces){
            coords.addAll(piece.updateAvailableMoves());
        }
        return coords;
    }
    
    public GameBoard(String board){
        setUpBoard(board);
    }
    
    private void setUpBoard(String board) {
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
                if (p != null){
                    p.move(new Coordinates(j,i,false));
                    if (p.isWhite()) 
                        whitePieces.add(p);
                    else blackPieces.add(p);
                }
                gameBoard[i][j] = p;
            }
    }
    
    private ChessPiece identifyPiece(String id){
        ChessPiece value = null;
        switch (id){
            case "Q" -> value = new Queen(WorB.WHITE);
            case "R" -> value = new Rook(WorB.WHITE);
            case "B" -> value = new Bishop(WorB.WHITE);
            case "N" -> value = new Knight(WorB.WHITE);
            case "P" -> value = new Pawn(WorB.WHITE);
            
            case "q" -> value = new Queen(WorB.BLACK);
            case "r" -> value = new Rook(WorB.BLACK);
            case "b" -> value = new Bishop(WorB.BLACK);
            case "n" -> value = new Knight(WorB.BLACK);
            case "p" -> value = new Pawn(WorB.BLACK);
            
            case "K" -> {
                value = new King(WorB.WHITE);
                whiteKing = value;
            }
            case "k" -> {
                value = new King(WorB.BLACK);
                blackKing = value;
            }
        }
        return value;
    }
    
    public void move(Coordinates pos1, Coordinates pos2){
        ChessPiece piece = gameBoard[pos1.y][pos1.x];
        gameBoard[pos1.y][pos1.x] = null;
        gameBoard[pos2.y][pos2.x] = piece;
    }
    
    public ChessPiece at(Coordinates c){
        return gameBoard[c.y][c.x];
    }
    
        public void addTakenPiece(ChessPiece piece) {
        if (piece.isWhite()){
            whiteTaken.add(piece);
        } else
            blackTaken.add(piece);
    }
    
    /*
    public  void createBoard() {
        String configuration = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        createBoard(configuration);
    }
    
    public void createBoard(String board) {
        setUpBoard(board);
    }
    */  
}
