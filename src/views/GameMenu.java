/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
 import javax.swing.JPanel;
import utilities.DrawButton;
import utilities.ResignButton;
import utilities.RestartButton;
import utilities.UndoButton;

/**
 *
 * @author Ángel Marqués García 
 */
public class GameMenu extends JPanel {
    
    private final Dimension BOXSIZE = new Dimension(200, 350);

    public GameMenu() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(BOXSIZE);
        setSize(BOXSIZE);
        setBackground(Color.ORANGE);
        
        JPanel top = new JPanel();
        
        JButton resignButton = new ResignButton();
        JButton drawButton = new DrawButton();
        JButton undoButton = new UndoButton();
        
        resignButton.addActionListener((ActionListener) resignButton);
        drawButton.addActionListener((ActionListener) drawButton);
        undoButton.addActionListener((ActionListener) undoButton);
        
        top.add(resignButton);
        top.add(drawButton);
        top.add(undoButton);
        
        add(top);
        
        
        
        JPanel mid = new JPanel();
        mid.setPreferredSize(new Dimension(200, 300));
        mid.setBackground(Color.orange);
        add(mid);
        
        JPanel bot = new JPanel();
        JButton restartButton = new RestartButton(); //"Resign", 
        restartButton.addActionListener((ActionListener) restartButton);
        bot.add(restartButton);
        JCheckBox checkBox = new JCheckBox("Introducir FEN");
        bot.add(checkBox);
        add(bot);

        
        
        setVisible(true);
    }

}
