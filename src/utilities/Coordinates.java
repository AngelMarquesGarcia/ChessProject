package utilities;

import java.awt.Dimension;
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

    public void sum(Coordinates c) {
        x += c.x;
        y += c.y;
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
}
