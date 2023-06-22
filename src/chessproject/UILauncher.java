package chessproject;

import java.awt.Dimension;
import views.containers.AppContainer;
import javax.swing.*;
import views.listeners.MyComponentListener;
import views.listeners.MyWindowStateListener;

/**
 * Contains the JFrame, initializes the GUI, and creates a Game.
 * @author Ángel Marqués García
 */
public class UILauncher {

    private static JFrame frame;
    private static final Dimension MINIMUM_SIZE = new Dimension(663,516);
    private static final Dimension PREFERRED_SIZE = new Dimension(800,600);
    private static final Dimension SIZE = new Dimension(800,600);

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
        frame.setSize(SIZE);
        frame.setMinimumSize(MINIMUM_SIZE);
        frame.setPreferredSize(PREFERRED_SIZE);
        frame.addComponentListener(new MyComponentListener());
        frame.addWindowStateListener(new MyWindowStateListener());
        
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
