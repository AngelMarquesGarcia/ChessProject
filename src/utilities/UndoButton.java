/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import chessproject.App;
import chessproject.Game;
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
public class UndoButton extends JButton implements ActionListener {

    private static final String RESIGN_PATH = "./files/undo.png";

    public UndoButton() {
        ImageIcon imageIcon = new ImageIcon(RESIGN_PATH); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        setIcon(imageIcon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Game.removeFocus();
        if (!Game.canUndo()) {
            JOptionPane.showMessageDialog(App.getFrame(), "It is not possible to undo, as there are no moves to undo", "Cannot Undo", 0);
            return;
        }
        System.out.println("Undo Button Clicked");
        boolean whiteResigns = Game.getWhiteToPlay();
        String player = (!whiteResigns ? "White" : "Black");
        String opponent = (whiteResigns ? "White" : "Black");
        String msg = player + " wants to undo their last move. " + opponent + ", do you allow it?";
        int option = JOptionPane.showConfirmDialog(App.getFrame(), msg, "titulo", 0, 2); //0 yes, 1 no
        System.out.println(option);

        if (option == 0) {
            Game.undoLastMove();
        }

    }
}
