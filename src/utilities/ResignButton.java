package utilities;

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
public class ResignButton extends JButton implements ActionListener {

    private static final String RESIGN_PATH = "./files/resign.png";

    public ResignButton() {
        ImageIcon imageIcon = new ImageIcon(RESIGN_PATH); // load the image to a imageIcon
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
                player + ", are you sure you want to resign?",
                "Resignation Letter",
                0,
                3); //0 yes, 1 no
        if (option == 0) {
            String msg = player + " has resigned, " + opponent + " wins"; //String msg = (whiteResigns ? "White has resigned, Black wins":"Black has resigned, White wins");
            JOptionPane.showMessageDialog(UILauncher.getFrame(), msg, "Resignation", 1);
            app.getCurrentMatch().lock();
        } else {
            //JOptionPane.showMessageDialog(this, "Draw Declined", "Resignation", 1);
        }
        System.out.println(option);

    }
}
