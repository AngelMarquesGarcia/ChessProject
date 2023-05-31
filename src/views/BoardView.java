
package views;

/*
 * BlankArea.java is used by:
 *    MouseEventDemo.java.
 *    MouseMotionEventDemo.java
 */

import chessproject.Game;
import chessproject.GameBoard;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
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
    public static final Dimension BOARDSIZE = new Dimension(500, 500);
    private int sizeX;
    private int sizeY;
    private final Color COLOR1 = Color.MAGENTA;
    private final Color COLOR2 = Color.CYAN;
    private final Color HIGHLIGHT_COLOR = Color.ORANGE;
    private Map<String, BufferedImage> sprites = new HashMap<>();
    private List<Coordinates> highlights = new ArrayList<>();

    private static BoardView boardView;
    
    public static BoardView getBoardView(){
        return boardView;
    }
    public static Dimension getBoardSize() {
        return new Dimension(BOARDSIZE);
    }

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
    
    @Override
    public void paint(Graphics g){
        drawBoard(g);
        drawPosition(g);
        drawHighlights(g);
    }
    
    private void setSizes() {
        Dimension size = getSize();
        size.setSize(new Dimension(size.width-10, size.height-10));
        sizeX = size.width/8;
        sizeY = size.height/8;    
    }

    private void drawBoard(Graphics g) {
        for(int i = 0; i <= 8;i++){
            for (int j = 0; j <= 8;j++){
                if (i == 8 || j == 8) continue;
                if ((i+j) % 2 == 0){ g.setColor(COLOR1);
                } else {g.setColor(COLOR2);}
                g.fillRect(i*sizeX, j*sizeY, sizeX,sizeY);
            }
        }    
    }

    private void drawPosition(Graphics g) {
        ChessPiece[][] gameBoard = Game.getGameBoard().getBoard();
        for(int i = 0; i < 8;i++){
            for (int j = 0; j < 8;j++){
                //if (i == 8 || j == 8) continue;
                ChessPiece piece = gameBoard[i][j];
                if (piece != null){ 
                    String name = piece.getName();
                    g.drawImage(sprites.get(name), j*sizeX, i*sizeY,sizeX,sizeY, this); 
                }
            }
        }    
        //g.drawImage(sprites.get("k"), startX * sizeX, startY*sizeY,sizeX,sizeY, this);    
    }

    public void highlight(List<Coordinates> availableMoves) {
        highlights.clear();
        highlights.addAll(availableMoves);
    }
    
    
    public final void loadSprites(){
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

    public Dimension getMinimumSize() {
        return BOARDSIZE;
    }

    public Dimension getPreferredSize() {
        return BOARDSIZE;
    }

    private void drawHighlights(Graphics g) {
        if (highlights.isEmpty()) return;
        g.setColor(HIGHLIGHT_COLOR);
        for (Coordinates cell: highlights){
            int x = cell.x;
            int y = cell.y;
            int startOfCellX = x * sizeX;
            int startOFCellY = y * sizeY;
            int middlePointX = sizeX/2;
            int middlePointY = sizeY/2;
            int circleWidth = sizeX/4;
            int circleHeight = sizeY/4;
            int centerX = startOfCellX + middlePointX - circleWidth/2;
            int centerY = startOFCellY + middlePointY - circleHeight/2;
            g.fillOval(centerX, centerY, circleWidth, circleHeight);
        }
        highlights.clear();
    }
}

