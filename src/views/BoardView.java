
package views;

/*
 * BlankArea.java is used by:
 *    MouseEventDemo.java.
 *    MouseMotionEventDemo.java
 */

import chessproject.GameBoard;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import pieces.ChessPiece;
import utilities.MyMouseListener;

public class BoardView extends JPanel {
    public static final Dimension BOARDSIZE = new Dimension(500, 500);
    private int sizeX;
    private int sizeY;
    private int startX = 0; //creo que sobrarán
    private int startY = 0;
    private final Color COLOR1 = Color.MAGENTA;
    private final Color COLOR2 = Color.CYAN;
    private Map<String, BufferedImage> sprites = new HashMap<>();

    private static BoardView boardView;
    
    public static BoardView getBoardView(){
        return boardView;
    }
    public static Dimension getBoardSize() {
        return new Dimension(BOARDSIZE);
    }

    public BoardView() {
        GameBoard.createBoard();
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
    
    //creo que sobrará
    public void moveImage(String sq){
        startX = Integer.parseInt(sq.substring(0,1));
        startY = Integer.parseInt(sq.substring(2));
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
        ChessPiece[][] gameBoard = GameBoard.getBoard();
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
    
}
