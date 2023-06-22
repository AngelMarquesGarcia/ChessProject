package views.containers;

import chessproject.ChessApp;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import pieces.ChessPiece;
import utilities.WorB;

/**
 *
 * @author Ángel Marqués García
 */
public class TakenPieces extends JPanel {

    private final Dimension BOXSIZE = new Dimension(200, 50);
    private Map<String, BufferedImage> sprites = new HashMap<>();
    private int PIECEWIDTH = 25;
    private final WorB color;

    public TakenPieces(WorB c) {
        color = c;
        setPreferredSize(BOXSIZE);
        setSize(BOXSIZE);
        setVisible(true);
        loadSprites();

        this.setBackground(Color.red);
    }

    @Override
    public void paint(Graphics g) {
        List<ChessPiece> takenPieces = ChessApp.getChessApp().getCurrentMatch().getChessBoard().getTaken(color);
        Collections.sort(takenPieces, Collections.reverseOrder());
        int y = 0;
        g.setColor(Color.red);
        g.fillRect(0, 0, BOXSIZE.width, BOXSIZE.height);
        int i = 0;
        for (ChessPiece piece : takenPieces) {
            //BufferedImage bi = sprites.get(piece.getName());
            g.drawImage(piece.getSprite(), i * PIECEWIDTH, y, PIECEWIDTH, PIECEWIDTH, this);
            i++;
            if ((i + 1) * PIECEWIDTH > BOXSIZE.width) {
                y = BOXSIZE.height/2;
                i = 0;
            }
        }
        //g.setColor(Color.BLUE);
        //g.drawString("+JUAN", i * PIECEWIDTH, 45);
    }

    public final void loadSprites() {
        //same as with BoardView, might not be neccesary if we use GameBoard.whiteTaken
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

    @Override
    public final void setPreferredSize(Dimension dim){
        BOXSIZE.setSize(dim);
        PIECEWIDTH = dim.height/2;
        super.setPreferredSize(BOXSIZE);
        setSize(BOXSIZE);
    }
}
