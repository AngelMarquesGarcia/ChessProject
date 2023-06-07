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
public class ChessMove {
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

    public boolean isIsShortCastle() {
        return isShortCastle;
    }

    public void setIsShortCastle(boolean isShortCastle) {
        this.isShortCastle = isShortCastle;
    }

    public boolean isIsLongCastle() {
        return isLongCastle;
    }

    public void setIsLongCastle(boolean isLongCastle) {
        this.isLongCastle = isLongCastle;
    }

    public boolean isIsCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public boolean isIsCheckMate() {
        return isCheckMate;
    }

    public void setIsCheckMate(boolean isCheckMate) {
        this.isCheckMate = isCheckMate;
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

    public void setCastle(int castle) {
        if (castle==1){
            isShortCastle = true;
        } else if (castle==2){
            this.isLongCastle = true;
        }
    }
    public int getCastle(){
        if (isShortCastle) return 1;
        if (isLongCastle) return 2;
        return 0;
    }
}
