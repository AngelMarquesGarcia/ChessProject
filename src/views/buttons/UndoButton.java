package views.buttons;

import Game.ChessMatch;
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
public class UndoButton extends ParentButton implements ActionListener {

    private static final String UNDO_PATH = "./files/undo.png";

    public UndoButton() {
        super(UNDO_PATH);
        ImageIcon imageIcon = new ImageIcon(UNDO_PATH); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        setIcon(imageIcon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ChessApp app = ChessApp.getChessApp();
        app.removeFocusFromMatch();
        ChessMatch match = app.getCurrentMatch();
        if (!match.canUndo()) {
            JOptionPane.showMessageDialog(UILauncher.getFrame(), "It is not possible to undo, as there are no moves to undo", "Cannot Undo", 0);
            return;
        }
        System.out.println("Undo Button Clicked");
        boolean whiteResigns = match.getWhiteToPlay();
        String player = (!whiteResigns ? "White" : "Black");
        String opponent = (whiteResigns ? "White" : "Black");
        String msg = player + " wants to undo their last move. " + opponent + ", do you allow it?";
        int option = JOptionPane.showConfirmDialog(UILauncher.getFrame(), msg, "titulo", 0, 2); //0 yes, 1 no
        System.out.println(option);

        if (option == 0) {
            match.undoLastMove();
        }

    }
}
