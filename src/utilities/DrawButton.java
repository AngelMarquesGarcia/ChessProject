/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package utilities;

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
public class DrawButton extends JButton implements ActionListener {

    private static final String RESIGN_PATH = "./files/draw.png";

    public DrawButton (){
        ImageIcon imageIcon = new ImageIcon(RESIGN_PATH); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(20, 20,  Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        setIcon(imageIcon);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Game.removeFocus();
        System.out.println("Restart Button Clicked");
        boolean whiteResigns = Game.getWhiteToPlay();
        String player = (whiteResigns ? "White":"Black");
        String opponent = (!whiteResigns ? "White ":"Black");
        int option = JOptionPane.showConfirmDialog(this, 
                player + " offers a draw. " + opponent +  ", do you accept it?", 
                "Draw Offer", 
                0, 
                1); //0 yes, 1 no
        if (option == 0){
            JOptionPane.showMessageDialog(this, "Draw Accepted", "Draw Offer", 1);
            Game.lock();
        } else {
            JOptionPane.showMessageDialog(this, "Draw Declined", "Draw Offer", 1);
        }
        System.out.println(option);
        
    }
}
