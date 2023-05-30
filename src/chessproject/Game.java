/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package chessproject;

import pieces.ChessPiece;
import utilities.Coordinates;
import views.AppContainer;
import views.BoardView;

/**
 *
 * @author Ángel Marqués García 
 */
public class Game {
    private static ChessPiece selectedPiece = null;

    public static void move(Coordinates cell) {
        ChessPiece piece = selectedPiece;
        GameBoard.move(piece.getPos(), cell);
        piece.move(cell);
        BoardView boardView = BoardView.getBoardView();
        AppContainer.getAppContainer().repaint();
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
