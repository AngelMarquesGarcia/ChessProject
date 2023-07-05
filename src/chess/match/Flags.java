/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package chess.match;

import utilities.Coordinates;

/**
 * This is basically a strut.
 * @author Ángel Marqués García 
 */
public class Flags {
    public boolean whiteToPlay;
    public final boolean[] whiteCastle = new boolean[]{true,true};
    public final boolean[] blackCastle = new boolean[]{true,true};
    public Coordinates enPassant;
    public int movesWithNoPawnOrCapture;
    public int startingMoveNum;
    public int currentMoveNum;
    
    public Flags(){
        whiteToPlay = true;
        enPassant = null;
        movesWithNoPawnOrCapture = 0;
        startingMoveNum = 0;
        currentMoveNum = 1;
    }
    
    public static Coordinates interpretEnPassant(String strip) {
        if ("-".equals(strip)) return null;
        String col = strip.substring(0,1);
        String row = strip.substring(1,2);
        int x = 0;
        int y = 8 - Integer.parseInt(row);
        switch (col) {
            case "a" -> x = 0;
            case "b" -> x = 1;
            case "c" -> x = 2;
            case "d" -> x = 3;
            case "e" -> x = 4;
            case "f" -> x = 5;
            case "g" -> x = 6;
            case "h" -> x = 7;
        }
        return new Coordinates(x,y);
    }

    @Override
    public String toString(){
        String conf = "";
        conf += " " + (whiteToPlay ? "w":"b");
        conf += " ";
        if (whiteCastle[0]) conf += "K";
        if (whiteCastle[1]) conf += "Q";
        if (blackCastle[0]) conf += "k";
        if (blackCastle[1]) conf += "q";
        if (conf.endsWith(" ")) conf += "-";
        conf += " " + (enPassant==null ? "-":enPassant.toString()); //enPassant;
        conf += " " + Integer.toString(movesWithNoPawnOrCapture);
        conf += " " + Integer.toString(currentMoveNum+startingMoveNum);
        return conf;
    }
}
