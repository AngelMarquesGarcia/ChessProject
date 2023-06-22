package chessproject;

import java.awt.Dimension;
import views.containers.AppContainer;
import javax.swing.*;

/**
 * Contains the JFrame, initializes the GUI, and creates a Game.
 * @author Ángel Marqués García
 */
public class UILauncher {

    private static JFrame frame;

    public static JFrame getFrame() {
        return frame;
    }

    public void launch() {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("Chess App");
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
