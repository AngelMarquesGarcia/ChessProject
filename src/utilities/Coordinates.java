package utilities;

import chessproject.GameBoard;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import views.BoardView;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Ángel Marqués García
 */
public class Coordinates {

    /**
     * returns a list of coordinates that span from pos1 to pos2.
     * pos1 is included, pos2 is excluded.
     * <p>If inverted is true, it gives a list of all coordinates along the line between pos1 and pos2
     * that are behind pos2.
     * <p> ORDER is ALWAYS away from pos1. It starts at pos1 and creeps toward pos2.
     * @param pos1
     * @param pos2
     * @param inverted
     * @return 
     */
    public static List<Coordinates> getLine(Coordinates pos1, Coordinates pos2, boolean inverted) {
        int mod = (inverted ? -1:1);
        List<Coordinates> coords = new ArrayList<>();
        checkHorizontalLine(coords, pos1, pos2,mod);
        checkDiagonalLine(coords,pos1,pos2,mod);
        //if (!inverted)
        //    coords.add(pos1.clone());
        return coords;
    }

    private static void addLine(List<Coordinates> coords, Coordinates pos1, Coordinates pos2, int xStep, int yStep) {
        Coordinates p1 = pos1.clone();
        while (!p1.equals(pos2) && GameBoard.isLegal(p1)){
            coords.add(p1.clone());
            p1.sum(xStep,yStep);
        }
    }

    private static void checkHorizontalLine(List<Coordinates> coords, Coordinates pos1, Coordinates pos2, int mod) {
        Coordinates c = pos2.clone().sub(pos1);
        if (c.x == 0 || c.y == 0){ 
            int xStep = 0;
            int yStep = 0;
            if (pos1.x == pos2.x)
                yStep = (pos1.y > pos2.y ? -1:1);
            else if (pos1.y == pos2.y)
                xStep = (pos1.x > pos2.x ? -1:1);
            addLine(coords, pos1, pos2, mod*xStep, mod*yStep);
        }
    }

    private static void checkDiagonalLine(List<Coordinates> coords, Coordinates pos1, Coordinates pos2, int mod) {
        Coordinates c = pos2.clone().sub(pos1);
        if (Math.abs(c.x) == Math.abs(c.y)){ //they are in the same diagonal
            int xStep = (c.x < 0 ? -1:1);
            int yStep = (c.y < 0 ? -1:1);
            addLine(coords, pos1, pos2, mod*xStep, mod*yStep);
        }
    }
    
    public static Coordinates getDir(Coordinates pos1, Coordinates pos2) {
        Coordinates dir = pos2.sub(pos1);
        int min = Math.min(dir.x, dir.y);
        if (min == 0)
            min = Math.max(dir.x,dir.y);
        dir.x = dir.x/min;
        dir.y = dir.y/min;
        return dir;
    }


    public int x;
    public int y;

    public Coordinates(int xCoord, int yCoord, boolean calc) {
        if (!calc) {
            x = xCoord;
            y = yCoord;
        } else {
            Dimension size = BoardView.BOARDSIZE;
            size.setSize(new Dimension(size.width - 10, size.height - 10));
            int sizeX = size.width / 8;
            int sizeY = size.height / 8;

            x = (int) Math.floor(xCoord / sizeX);
            y = (int) Math.floor(yCoord / sizeY);
        }
    }

    public Coordinates sum(Coordinates c) {
        x += c.x;
        y += c.y;
        return this;
    }
    
    public void sum(int xCoord, int yCoord) {
        x += xCoord;
        y += yCoord;
    }

    @Override
    public Coordinates clone() {
        return new Coordinates(x, y, false);
    }

    public void copy(Coordinates c) {
        x = c.x;
        y = c.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Coordinates other = (Coordinates)obj;
        if (x != other.x) return false;
        return y == other.y;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.x;
        hash = 67 * hash + this.y;
        return hash;
    }

    @Override
    public String toString() {
        String row;
        switch (x) {
            case 0 -> row = "a";
            case 1 -> row = "b";
            case 2 -> row = "c";
            case 3 -> row = "d";
            case 4 -> row = "e";
            case 5 -> row = "f";
            case 6 -> row = "g";
            case 7 -> row = "h";
            default -> row = "";
        }
        return row + Integer.toString(y);
    }

    public boolean equals(int xCoord, int yCoord) {
        return (x==xCoord && y==yCoord);
    }

    public Coordinates sub(Coordinates c) {
        x -= c.x;
        y -= c.y;
        return this;
    }

    public Coordinates mult(int i) {
        x *= i;
        y *= i;
        return this;
    }
}
