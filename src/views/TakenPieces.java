/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Ángel Marqués García 
 */
public class TakenPieces extends JPanel {

    private final Dimension BOXSIZE = new Dimension(200, 50);
    private Map<String, BufferedImage> sprites = new HashMap<>();
    private ArrayList<String> takenPieces = new ArrayList<>();
    private final int PIECEWIDTH = 25; 
    
    public TakenPieces() {
        takenPieces.add("q");
        takenPieces.add("r");
        takenPieces.add("N");
        takenPieces.add("P");
        takenPieces.add("P");
        takenPieces.add("p");
        takenPieces.add("b");
        takenPieces.add("K");
        takenPieces.add("n");
        takenPieces.add("q");
        
        setPreferredSize(BOXSIZE);
        setSize(BOXSIZE);
        setVisible(true);
        loadSprites();
        
        this.setBackground(Color.red);
    }
    
    @Override
    public void paint(Graphics g){
        int y = 0;
        g.setColor(Color.red);
        g.fillRect(0, 0, 200, 50);
        int i = 0;
        for (String piece: takenPieces){
            BufferedImage bi = sprites.get(piece);
            g.drawImage(bi, i * PIECEWIDTH, y,PIECEWIDTH,PIECEWIDTH, this);   
            i++;
            if ((i+1) * PIECEWIDTH > BOXSIZE.width){
                y = 25;
                i = 0;
            }
        }
        //g.setColor(Color.BLUE);
        //g.drawString("+JUAN", i * PIECEWIDTH, 45);
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

}
