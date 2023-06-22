package views.containers;

import java.awt.*;
import javax.swing.*;
import pieces.ChessPiece;
import utilities.WorB;

/**
 *
 * @author Ángel Marqués García
 */
public class AppContainer extends JPanel {

    //possibly could be moved to App. Evaluate wether App is only gonna be for chess.
    public static String showPromotionOptions(ChessPiece pawn, String[] possiblePromotions) {
        ImageIcon imageIcon = new ImageIcon("./files/promotion5.png"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        return (String) JOptionPane.showInputDialog(appContainer,
                "What do you want your pawn to promote to?",
                "Pawn Promotion",
                0,
                imageIcon,
                possiblePromotions, "Queen");
    }

    public static void showCheckMate(WorB color) {
        JOptionPane.showMessageDialog(appContainer, color + " has been checkmated. " + WorB.not(color) + " wins.", "Game Over", 1);
    }

    public static void showStaleMate(WorB color) {
        JOptionPane.showMessageDialog(appContainer, "The game results in a draw, as " + color + " cannot move.", "Stalemate", 1);
    }

    public static void show50MoveDraw() {
        JOptionPane.showMessageDialog(appContainer, "The game results in a draw by the 50 move rule.", "Draw", 1);
    }

    public static void show3FoldDraw() {
        JOptionPane.showMessageDialog(appContainer, "The game results in a draw by the threefold repetition rule.", "Draw", 1);
    }
    
    private JPanel board;
    private JPanel infoMenu;
    private static AppContainer appContainer;

    public static AppContainer getAppContainer() {
        return appContainer;
    }

    public AppContainer() {
        super();
        board = new BoardView();
        add(board);

        infoMenu = new InfoMenu();
        add(infoMenu);

        setPreferredSize(new Dimension(800, 600));
        appContainer = this;
    }

    public void scaleTo(Dimension size) {
        int min = (size.height-100 < size.width*5/8 ? size.height-100:size.width*5/8);
        board.setPreferredSize(new Dimension(min, min));
        
        int infoWidth = min*2/5;
        int infoHeight = min*9/10;
        infoMenu.setPreferredSize(new Dimension(infoWidth, infoHeight));
        
    }

}
