/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package views;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Ángel Marqués García 
 */
public class GameMenu extends JPanel {
    
    private final Dimension BOXSIZE = new Dimension(200, 350);

    public GameMenu() {
        super();
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(BOXSIZE);
        setSize(BOXSIZE);
        setBackground(Color.ORANGE);
        
        //JPanel top = new JPanel();
        
        
        //JPanel mid = new JPanel();
        //JPanel bot = new JPanel();

        
        
        setVisible(true);
    }

}
