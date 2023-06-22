package views.buttons;

import chessproject.ChessApp;
import chessproject.UILauncher;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Ángel Marqués García
 */
public class DrawButton extends ParentButton implements ActionListener {

    private static final String DRAW_PATH = "./files/draw.png";

    public DrawButton() {
        super(DRAW_PATH);
        ImageIcon imageIcon = new ImageIcon(DRAW_PATH); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        setIcon(imageIcon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ChessApp app = ChessApp.getChessApp();
        app.removeFocusFromMatch();
        System.out.println("Restart Button Clicked");
        boolean whiteResigns = app.getCurrentMatch().getWhiteToPlay();
        String player = (whiteResigns ? "White" : "Black");
        String opponent = (!whiteResigns ? "White " : "Black");
        int option = JOptionPane.showConfirmDialog(UILauncher.getFrame(),
                player + " offers a draw. " + opponent + ", do you accept it?",
                "Draw Offer",
                0,
                1); //0 yes, 1 no
        if (option == 0) {
            JOptionPane.showMessageDialog(UILauncher.getFrame(), "Draw Accepted", "Draw Offer", 1);
            app.getCurrentMatch().lock();
        } else {
            JOptionPane.showMessageDialog(UILauncher.getFrame(), "Draw Declined", "Draw Offer", 1);
        }
        System.out.println(option);

    }
}
