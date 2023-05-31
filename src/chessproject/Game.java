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
    public Game(){
        
    }
    
    void doStuff() {

    }
    
    private static ChessPiece selectedPiece = null;
    private static List<Coordinates> availableMoves;

    public static void move(Coordinates cell) {
        ChessPiece pieceToMove = selectedPiece;
        //List<Coordinates> availableMoves = selectedPiece.updateAvailableMoves();
        if (!availableMoves.contains(cell)){
            return;
        }
        
        ChessPiece pieceAtCell = GameBoard.at(cell);
        if (pieceAtCell != null){
            pieceTaken(pieceAtCell);
        }
        
        GameBoard.move(pieceToMove.getPos(), cell);
        pieceToMove.move(cell);
        BoardView boardView = BoardView.getBoardView();
        AppContainer.getAppContainer().repaint();
    }

    private static void pieceTaken(ChessPiece piece) {
        TakenPieces taken;
        if (piece.isWhite()){
            taken = InfoMenu.getTakenByBlack();
        } else {
            taken = InfoMenu.getTakenByWhite();
        }
        taken.addTakenPiece(piece);
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
