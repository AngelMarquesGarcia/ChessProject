package views;

import chessproject.Game;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import pieces.ChessPiece;
import utilities.Coordinates;
import utilities.MyMouseListener;

public class BoardView extends JPanel {
    private static final Dimension BOARDSIZE = new Dimension(500, 500);
    private static BoardView boardView;

    public static BoardView getBoardView() {
        return boardView;
    }

    public static Dimension getBoardSize() { 
        return new Dimension(BOARDSIZE);
    }
    
    //////////////////PERSONALIZATION//////////////////
    private final Color COLOR1 = Color.MAGENTA;
    private final Color COLOR2 = Color.CYAN;
    private final Color HIGHLIGHT_COLOR = Color.ORANGE;
    ///////////////////////FONT///////////////////////
    private final int MARGIN = 3;
    private final int FONT_SIZE = 13;
    /////////////////OTHER PROPERTIES/////////////////
    private int sizeX;
    private int sizeY;
    private Map<String, BufferedImage> sprites = new HashMap<>();
    private List<Coordinates> highlights = new ArrayList<>();

    //////////////////////////////CONSTRUCTOR//////////////////////////////
    public BoardView() {
        setPreferredSize(BOARDSIZE);
        setSize(BOARDSIZE);
        setVisible(true);
        //setOpaque(true);
        //setBorder(BorderFactory.createLineBorder(Color.black));
        loadSprites();
        setSizes();

        addMouseListener(new MyMouseListener());
        boardView = this;
    }

    private void setSizes() {
        Dimension size = getSize();
        size.setSize(new Dimension(size.width - 10, size.height - 10));
        sizeX = size.width / 8;
        sizeY = size.height / 8;
    }
   
    public final void loadSprites() {
        //might not be needed. The idea would be, each piece has their sprite, and to show them
        //BoardView is given the GameBoard with the actual ChessPiece objects, so it can access their sprites
        //Or even better, it could use what's currently GameBoard.whitePieces and GameBoard.blackPieces
        //and show all pieces on those lists/sets.
        BufferedImage bi;
        try {
            bi = ImageIO.read(new File("./files/BlackKing.png"));
            this.sprites.put("k", bi);
            bi = ImageIO.read(new File("./files/BlackQueen.png"));
            this.sprites.put("q", bi);
            bi = ImageIO.read(new File("./files/BlackBishop.png"));
            this.sprites.put("b", bi);
            bi = ImageIO.read(new File("./files/BlackKnight.png"));
            this.sprites.put("n", bi);
            bi = ImageIO.read(new File("./files/BlackRook.png"));
            this.sprites.put("r", bi);
            bi = ImageIO.read(new File("./files/BlackPawn.png"));
            this.sprites.put("p", bi);

            bi = ImageIO.read(new File("./files/WhiteKing.png"));
            this.sprites.put("K", bi);
            bi = ImageIO.read(new File("./files/WhiteQueen.png"));
            this.sprites.put("Q", bi);
            bi = ImageIO.read(new File("./files/WhiteBishop.png"));
            this.sprites.put("B", bi);
            bi = ImageIO.read(new File("./files/WhiteKnight.png"));
            this.sprites.put("N", bi);
            bi = ImageIO.read(new File("./files/WhiteRook.png"));
            this.sprites.put("R", bi);
            bi = ImageIO.read(new File("./files/WhitePawn.png"));
            this.sprites.put("P", bi);

        } catch (IOException e) {
            System.out.println("Image could not be read");
            e.printStackTrace();
        }
    }
    
    //////////////////////////////PAINTING//////////////////////////////
    @Override
    public void paint(Graphics g) {
        drawBoard(g);
        drawPosition(g);
        drawHighlights(g);
        drawCoordinates(g);
    }

    private void drawBoard(Graphics g) {
        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j <= 8; j++) {
                if (i == 8 || j == 8) {
                    continue;
                }
                if ((i + j) % 2 == 0) {
                    g.setColor(COLOR1);
                } else {
                    g.setColor(COLOR2);
                }
                g.fillRect(i * sizeX, j * sizeY, sizeX, sizeY);
            }
        }
    }

    private void drawPosition(Graphics g) {
        ChessPiece[][] gameBoard = Game.getGameBoard().getBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //if (i == 8 || j == 8) continue;
                ChessPiece piece = gameBoard[i][j];
                if (piece != null) {
                    String name = piece.getName();
                    g.drawImage(sprites.get(name), j * sizeX, i * sizeY, sizeX, sizeY, this);
                }
            }
        }
        //g.drawImage(sprites.get("k"), startX * sizeX, startY*sizeY,sizeX,sizeY, this);    
    }

    private void drawHighlights(Graphics g) {
        if (highlights.isEmpty()) {
            return;
        }
        g.setColor(HIGHLIGHT_COLOR);
        for (Coordinates cell : highlights) {
            int x = cell.x;
            int y = cell.y;
            int startOfCellX = x * sizeX;
            int startOFCellY = y * sizeY;
            int middlePointX = sizeX / 2;
            int middlePointY = sizeY / 2;
            int circleWidth = sizeX / 4;
            int circleHeight = sizeY / 4;
            int centerX = startOfCellX + middlePointX - circleWidth / 2;
            int centerY = startOFCellY + middlePointY - circleHeight / 2;
            g.fillOval(centerX, centerY, circleWidth, circleHeight);
        }
        highlights.clear();
    }

    private void drawCoordinates(Graphics g) {
        int y = sizeY * 8 - MARGIN;
        int x = MARGIN;

        String[] letters = new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};
        g.setColor(COLOR2);
        g.setFont(new Font("TimesRoman", Font.BOLD, FONT_SIZE));

        for (int i = 0; i < 8; i++) {
            if (g.getColor() == COLOR1) {
                g.setColor(COLOR2);
            } else {
                g.setColor(COLOR1);
            }
            g.drawString(letters[i], x + i * sizeX, y);
        }
        y = 5 * MARGIN;
        x = sizeX * 8 - 3 * MARGIN;
        for (int i = 0; i < 9; i++) {
            g.drawString(Integer.toString(9 - i), x, y + (i - 1) * sizeY);
            if (g.getColor() == COLOR1) {
                g.setColor(COLOR2);
            } else {
                g.setColor(COLOR1);
            }
        }
    }

    //////////////////////////////GETTERS & SETTERS//////////////////////////////
    public void highlight(List<Coordinates> availableMoves) {
        highlights.clear();
        highlights.addAll(availableMoves);
    }
       
    @Override
    public Dimension getMinimumSize() {
        return BOARDSIZE;
    }

    @Override
    public Dimension getPreferredSize() {
        return BOARDSIZE;
    }

    
}
