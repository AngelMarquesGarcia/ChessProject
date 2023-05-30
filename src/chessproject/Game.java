/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package chessproject;

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
    private static ChessPiece selectedPiece = null;

    public static void move(Coordinates cell) {
        ChessPiece pieceAtCell = GameBoard.at(cell);
        if (pieceAtCell != null){
            pieceTaken(pieceAtCell);
        }
        
        ChessPiece pieceToMove = selectedPiece;
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

    public Game(){
        
    }
    
    void doStuff() {

    }
    
    public static void setSelectedPiece(ChessPiece p){
        selectedPiece = p;
    }
    
    public static ChessPiece getSelectedPiece(){
        return selectedPiece;
    }
    
    

}
