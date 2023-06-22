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
import pieces.MoveUpdater;
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
public class ChessBoard {
    // <editor-fold desc="STATIC">
    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_HEIGHT = 8;

    public static ChessPiece getClosestPiece(Coordinates pos, List<Coordinates> line, ChessBoard gameBoard) {
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

    public static boolean isLegal(Coordinates c) {
        return (c.y >= 0 && c.y < BOARD_HEIGHT) && (c.x >= 0 && c.x < BOARD_WIDTH);
    }
  
    // </editor-fold>
    
    private ChessPiece[][] chessBoard = new ChessPiece[BOARD_HEIGHT][BOARD_WIDTH];
    private List<ChessPiece> whitePieces = new ArrayList<>(); 
    private List<ChessPiece> blackPieces = new ArrayList<>();
    private List<ChessPiece> whiteTaken = new ArrayList<>(); 
    private List<ChessPiece> blackTaken = new ArrayList<>();
    private ChessPiece whiteKing;
    private ChessPiece blackKing;
    
    
    //////////////////////////////GETTERS & SETTERS//////////////////////////////
    //////////////////////////////GETTERS & SETTERS//////////////////////////////
    public ChessPiece[][] getBoard(){
        return chessBoard;
    }
    public List<ChessPiece> getPieces(WorB color){
        return (color==WorB.WHITE ? whitePieces:blackPieces);
    }
    public List<ChessPiece> getTaken(WorB color){ 
        return (color==WorB.WHITE ? whiteTaken:blackTaken);
    }
    public ChessPiece getKing(WorB color){ 
        return (color==WorB.WHITE ? whiteKing:blackKing);
    }
    
    public Set<Coordinates> getAllMoves(WorB color){
        List<ChessPiece> pieces = (color==WorB.WHITE ? whitePieces:blackPieces);
        Set<Coordinates> coords = new HashSet<>();
        for (ChessPiece piece: pieces){
            coords.addAll(MoveUpdater.getPossibleMoves(piece, this));
        }
        return coords;
    }
    
    /**
     * returns a set of all Coordinates that are being attacked by a piece or pawn.
     * It does not take into consideration Coordinates being attacked by the enemy king so as to not create a loop.
     * @param color
     * @return 
     */
    public Set<Coordinates> getAllAttackedCells(WorB color){
        List<ChessPiece> pieces = (color==WorB.WHITE ? whitePieces:blackPieces);
        ChessPiece king = (color==WorB.WHITE ? whiteKing:blackKing);
        Set<Coordinates> coords = new HashSet<>();
        for (ChessPiece piece: pieces){
            if (piece != king) //this is so that it doesn't infinitely recurse
               coords.addAll(MoveUpdater.getAttackedCells(piece, this));
        }
        return coords;
    }
    
    public ChessPiece at(Coordinates c){
        return chessBoard[c.y][c.x];
    }
    public ChessPiece at(int x, int y) {
        return chessBoard[y][x];
    }

    //////////////////////////////CONSTRUCTORS//////////////////////////////
    //////////////////////////////CONSTRUCTORS//////////////////////////////
    
    public ChessBoard(String board){
        setUpBoard(board);
    }
    
    private void setUpBoard(String board) {
        List<String> numbers = Arrays.asList( "1","2","3","4","5","6","7","8" );
        String[] rows = board.split("/");
        int x;
        for (int i=0;i<rows.length;i++){
            x = 0;
            for (int j=0;j<rows[i].length();j++){
                String piece = rows[i].substring(j, j+1);
                if (numbers.contains(piece)){
                    x += Integer.parseInt(piece); //-1
                    continue;
                } 
                ChessPiece p = identifyPiece(piece);
                if (p != null){
                    p.move(new Coordinates(x,i));
                    if (p.isWhite()) 
                        whitePieces.add(p);
                    else blackPieces.add(p);
                }
                chessBoard[i][x] = p;
                x++;
            }
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
    
    //////////////////////////////BOARD//////////////////////////////
    //////////////////////////////BOARD//////////////////////////////
    public void move(Coordinates pos1, Coordinates pos2){
        ChessPiece piece = chessBoard[pos1.y][pos1.x];
        chessBoard[pos1.y][pos1.x] = null;
        chessBoard[pos2.y][pos2.x] = piece;
    }
    public void place(ChessPiece takenPiece, Coordinates finPos) {
        //maybe this should be private, and only replace be public
        chessBoard[finPos.y][finPos.x] = takenPiece;
    }
    public void replace(Coordinates pos, ChessPiece piece) {
        ChessPiece pieceToReplace = at(pos);
        place(piece, pos);
        addPiece(piece);
        removePiece(pieceToReplace);
    }

    //////////////////////////////PIECES//////////////////////////////
    //////////////////////////////PIECES//////////////////////////////
    public void addTakenPiece(ChessPiece piece) {
        if (piece.isWhite()){
            whiteTaken.add(piece);
            whitePieces.remove(piece);
        } else {
            blackTaken.add(piece);
            blackPieces.remove(piece);
        }
    }
    
    public void removeTakenPiece(ChessPiece piece) {
        if (piece.isWhite()){
            whiteTaken.remove(piece);
        } else
            blackTaken.remove(piece);
       
    }
    
    public void addPiece(ChessPiece piece) {
        if (piece == null){return;}
        if (piece.isWhite()){
            whitePieces.add(piece);
        } else {
            blackPieces.add(piece);
        }
    }

    public void removePiece(ChessPiece piece) {
        if (piece == null){return;}
        if (piece.isWhite()){
            whitePieces.remove(piece);
        } else {
            blackPieces.remove(piece);
        }
    }

    
    
    ///UTILITY
    @Override
    public String toString(){
        String conf = "";
        int empties = 0;
        for (ChessPiece[] row: chessBoard){
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
                    conf += piece.getRepresentation();
        }   }   }
        return conf.substring(1);
    }
}
