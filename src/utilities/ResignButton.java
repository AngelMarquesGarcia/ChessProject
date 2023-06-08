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
        Game.removeFocus();
        System.out.println("Restart Button Clicked");
        boolean whiteResigns = Game.getWhiteToPlay();
        String player = (whiteResigns ? "White" : "Black");
        String opponent = (!whiteResigns ? "White " : "Black");
        int option = JOptionPane.showConfirmDialog(App.getFrame(),
                player + ", are you sure you want to resign?",
                "Resignation Letter",
                0,
                3); //0 yes, 1 no
        if (option == 0) {

            String msg = player + " has resigned, " + opponent + " wins"; //String msg = (whiteResigns ? "White has resigned, Black wins":"Black has resigned, White wins");
            JOptionPane.showMessageDialog(App.getFrame(), msg, "Resignation", 1);
            Game.lock();
        } else {
            //JOptionPane.showMessageDialog(this, "Draw Declined", "Resignation", 1);
        }
        System.out.println(option);

    }
}
