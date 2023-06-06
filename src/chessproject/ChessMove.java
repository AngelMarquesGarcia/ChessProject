/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package chessproject;

import pieces.ChessPiece;
import utilities.Coordinates;

/**
 *
 * @author Ángel Marqués García 
 */
class ChessMove {
    private Coordinates iniPos;
    private Coordinates finPos;
    private ChessPiece movedPiece;
    private ChessPiece takenPiece;
    private String fenBoardAfter;
    private boolean isShortCastle;
    private boolean isLongCastle;
    private boolean isCheck;
    private boolean isCheckMate;

    public String getFenBoardAfter() {
        return fenBoardAfter;
    }
    
    @Override
    public String toString(){
        if (isShortCastle){return "O-O";}
        if (isLongCastle){return "O-O-O";}
        String pieceName = movedPiece.getName().toUpperCase();
        String from = iniPos.toString();
        String takes = (takenPiece==null? "":"x");
        String takenName = finPos.toString();
        String checkSign = (isCheck?"+":"");
        checkSign = (isCheckMate?"#":checkSign);
        return pieceName + from  + takes + takenName + checkSign;
        
    }

    public void setFenBoardAfter(String fenBoardAfter) {
        this.fenBoardAfter = fenBoardAfter;
    }

    public ChessMove(Coordinates iniPos, Coordinates finPos, ChessPiece movedPiece) {
        this.iniPos = iniPos;
        this.finPos = finPos;
        this.movedPiece = movedPiece;
    }

    public void setTakenPiece(ChessPiece takenPiece) {
        this.takenPiece = takenPiece;
    }

    public ChessPiece getTakenPiece() {
        return takenPiece;
    }

    public Coordinates getIniPos() {
        return iniPos;
    }

    public void setIniPos(Coordinates iniPos) {
        this.iniPos = iniPos;
    }

    public Coordinates getFinPos() {
        return finPos;
    }

    public void setFinPos(Coordinates finPos) {
        this.finPos = finPos;
    }

    public ChessPiece getMovedPiece() {
        return movedPiece;
    }

    public void setMovedPiece(ChessPiece movedPiece) {
        this.movedPiece = movedPiece;
    }
    
    
}
