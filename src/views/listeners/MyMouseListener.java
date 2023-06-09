package views.listeners;

import chess.match.ChessMatch;
import chess.utilities.MovementManager;
import chessproject.ChessApp;
import chess.match.ChessBoard;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import chess.pieces.ChessPiece;
import utilities.Coordinates;
import views.information.BoardView;

/**
 *
 * @author Ángel Marqués García
 */
public class MyMouseListener implements MouseListener {

    private Coordinates mousePressedOn;

    public MyMouseListener() { //probably can be removed
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressedOn = getSquare(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Coordinates mouseReleasedOn = getSquare(e.getX(), e.getY());
        if (mousePressedOn.equals(mouseReleasedOn)) {
            cellClicked(mouseReleasedOn);
        }
    }
    
    private void cellClicked(Coordinates cell) {
        ChessMatch match = ChessApp.getChessApp().getCurrentMatch();
        ChessBoard gameBoard = match.getChessBoard();
        ChessPiece piece = gameBoard.at(cell);
        if (match.getSelectedPiece() == null) {
            match.setSelectedPiece(piece);
        } else {
            //if we selected another of our pieces, change the selected piece to that
            //else, try to move there.
            if (piece != null && piece.isWhite() == match.getSelectedPiece().isWhite()) {
                match.setSelectedPiece(piece);
            } else {
                new MovementManager(match).move(cell);
            }
        }

        mousePressedOn = null;
    }

    private Coordinates getSquare(int x, int y) {
        Dimension size = BoardView.getBoardSize();
        size.setSize(new Dimension(size.width - 10, size.height - 10));
        int sizeX = size.width / 8;
        int sizeY = size.height / 8;

        int xCoord = (int) Math.floor(x / sizeX);
        int yCoord = (int) Math.floor(y / sizeY);

        return new Coordinates(xCoord, yCoord); //row + yCoord;
    }
    
    
    //////////////////////////////NOT USED//////////////////////////////
    //////////////////////////////NOT USED//////////////////////////////
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //celClicked(new Coordinates(e.getX(), e.getY()));
    }

}
