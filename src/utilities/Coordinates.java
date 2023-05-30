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

    public Coordinates clone(Coordinates c) {
        return new Coordinates(c.x, c.y, false);
    }

    public void copy(Coordinates c) {
        x = c.x;
        y = c.y;
    }

    public boolean equals(Coordinates c) {
        return (x == c.x && y == c.y);
    }

    public String toString() {
        String row;
        switch (x) {
            case 0:
                row = "a";
                break;
            case 1:
                row = "b";
                break;
            case 2:
                row = "c";
                break;
            case 3:
                row = "d";
                break;
            case 4:
                row = "e";
                break;
            case 5:
                row = "f";
                break;
            case 6:
                row = "g";
                break;
            case 7:
                row = "h";
                break;
        }
        return x + Integer.toString(y);
    }
}
