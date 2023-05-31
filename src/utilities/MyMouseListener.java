/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import chessproject.Game;
import chessproject.GameBoard;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import pieces.ChessPiece;
import views.BoardView;

/**
 *
 * @author Ángel Marqués García
 */
public class MyMouseListener implements MouseListener {

    private Coordinates mousePressedOn;

    public MyMouseListener() {

    }

    public void mousePressed(MouseEvent e) {
        mousePressedOn = getSquare(e.getX(), e.getY());
    }

    public void mouseReleased(MouseEvent e) {
        Coordinates mouseReleasedOn = getSquare(e.getX(), e.getY());
        if (mousePressedOn.equals(mouseReleasedOn)) {
            cellClicked(mouseReleasedOn);
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        //celClicked(new Coordinates(e.getX(), e.getY()));
    }

    private Coordinates getSquare(int x, int y) {
        Dimension size = BoardView.getBoardSize();
        size.setSize(new Dimension(size.width - 10, size.height - 10));
        int sizeX = size.width / 8;
        int sizeY = size.height / 8;

        int xCoord = (int) Math.floor(x / sizeX);
        int yCoord = (int) Math.floor(y / sizeY);

        return new Coordinates(xCoord, yCoord, false); //row + yCoord;
    }

    private void cellClicked(Coordinates cell) {
        ChessPiece[][] gameBoard = GameBoard.getBoard();
        ChessPiece piece = gameBoard[cell.y][cell.x];
        if (Game.getSelectedPiece() == null) {
            Game.setSelectedPiece(piece);
        } else { 
            //if we selected another of our pieces, change the selected piece to that
            //else, try to move there.
            if (piece != null && piece.isWhite() == Game.getSelectedPiece().isWhite()) {
                Game.setSelectedPiece(piece);
            } else {
                Game.move(cell);
                Game.setSelectedPiece(null);
            }
        }

        mousePressedOn = null;
    }

}
