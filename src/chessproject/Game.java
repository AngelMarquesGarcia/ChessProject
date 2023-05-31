/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package chessproject;

import java.util.ArrayList;
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
        //se puede optimizar.
        //Only way 2 pieces check is discovered attack
        //Si hacemos una línea entre la posición de la pieza
        //que se acaba de meter y el rey, podemos comprobar los espacios
        //que queden detrás de la pieza. Si la línea es diagonal y 
        //hay un alfil, hay dos atacantes. Si es recta y hay una torre,
        //dos atacantes.
    }
    
    public static boolean isCheckMate(WorB color){
        ChessPiece king = gameBoard.getKing(color);
        if (!king.updateAvailableMoves().isEmpty())
            return false;
        if (checkers.size() == 1){ //
            ChessPiece attacker = checkers.get(0);
            Set<Coordinates> allMoves = gameBoard.getAllMoves(color);
            //si se puede comer la pieza return false
                //if (allMoves.contains(attacker.getPos()))
                //    return true;
            //si se puede bloquear return false
            List<Coordinates> line = Coordinates.getLine(attacker.getPos(), king.getPos(), false);
            for (Coordinates c: line){
                if(allMoves.contains(c))
                    return false;
            }
        }
        return true;
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
            return; //esto no se debería dar nunca
        } 
        ChessPiece pieceAtCell = gameBoard.at(cell);
        if (pieceAtCell != null){
            pieceTaken(pieceAtCell);
        }
        gameBoard.move(pieceToMove.getPos(), cell);
        pieceToMove.move(cell);
        
        if (whiteToPlay && isInCheck(WorB.BLACK)){
            kingChecked(WorB.BLACK);
        } else if (!whiteToPlay && isInCheck(WorB.WHITE)){
            kingChecked(WorB.WHITE);
        }
        
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

    private static void kingChecked(WorB color) {
        System.out.println("Game.kingChecked not supported yet");
    }
}


/*//se puede optimizar
    public static boolean isInCheck(WorB color){
        checkers.clear();
        List<ChessPiece> pieces = gameBoard.getPieces(WorB.opposite(color));
        ChessPiece king = gameBoard.getKing(color);
        boolean isCheck = false;
        for (ChessPiece piece: pieces){
            if (piece.updateAvailableMoves().contains(king.getPos())){
                isCheck = true;
                checkers.add(piece);
                //se puede optimizar.
                //Only way 2 pieces check is discovered attack
                //Si hacemos una línea entre la posición de la pieza
                //que se acaba de meter y el rey, podemos comprobar los espacios
                //que queden detrás de la pieza. Si la línea es diagonal y 
                //hay un alfil, hay dos atacantes. Si es recta y hay una torre,
                //dos atacantes.
                
            }
        }
        return isCheck;
    }*/