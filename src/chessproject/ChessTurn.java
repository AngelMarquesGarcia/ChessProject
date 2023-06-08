package chessproject;

import utilities.WorB;

/**
 *
 * @author Ángel Marqués García 
 */
public class ChessTurn {


    private int moveNum;
    private ChessMove whiteMove;
    private ChessMove blackMove;

    public ChessTurn(int moveNum) {
        this.moveNum = moveNum;
    }
    
    
    public int getMoveNum() {
        return moveNum;
    }

    public void setMoveNum(int moveNum) {
        this.moveNum = moveNum;
    }

    public ChessMove getWhiteMove() {
        return whiteMove;
    }

    public void setWhiteMove(ChessMove whiteMove) {
        this.whiteMove = whiteMove;
    }

    public ChessMove getBlackMove() {
        return blackMove;
    }

    public void setBlackMove(ChessMove blackMove) {
        this.blackMove = blackMove;
    }

    public ChessMove pop(WorB color) {
        ChessMove move;
        if (color == WorB.WHITE ){
            move = whiteMove;
            whiteMove = null;
        } else {
            move = blackMove;
            blackMove = null;
        }
        return move;
    }
}
