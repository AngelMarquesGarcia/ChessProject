/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package chessproject;

import java.awt.Dimension;
import views.AppContainer;
import javax.swing.*;
import pieces.ChessPiece;

/**
 *
 * @author Ángel Marqués García 
 */
public class App {
    
    public App(){
        
        System.out.println("Hola, mundo");
        
        
        System.out.println("Adios");
        
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
        Game.createGame();
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Chess App");
        frame.setSize(800, 800);
        frame.setPreferredSize(new Dimension(800, 600));
        //frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create and set up the content pane.
        JComponent content = new AppContainer();
        content.setOpaque(true); //content panes must be opaque
        frame.setContentPane(content);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
