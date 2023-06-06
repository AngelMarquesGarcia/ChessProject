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
public class RestartButton extends JButton implements ActionListener {

    private static final String RESIGN_PATH = "./files/restart.png";
    private ImageIcon imageIcon;

    public RestartButton (){
        imageIcon = new ImageIcon(RESIGN_PATH); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(20, 20,  Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        setIcon(imageIcon);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Game.removeFocus();
        System.out.println("Restart Button Clicked");
        String fenString = (String)JOptionPane.showInputDialog(
                    App.getFrame(),
                    "Are you sure you want to restart?\nThe following board will be loaded",
                    "Confirm Restart",
                    JOptionPane.PLAIN_MESSAGE,
                    imageIcon,
                    null,
                    "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        System.out.println(fenString);
        if (fenString != null)
            Game.restart(fenString);        
    }
}
