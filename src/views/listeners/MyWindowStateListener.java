package views.listeners;

import java.awt.Component;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import views.containers.AppContainer;

/**
 *
 * @author Ángel Marqués García 
 */
public class MyWindowStateListener implements WindowStateListener {

    @Override
    public void windowStateChanged(WindowEvent e) {
        System.out.println("Window state changed");
        //Ideal would be to only do this when maximizing or returning to normal
        //That would be done with e.getNewState(). However, getNewState returns
        //an int that is a "bitwise mask" of the state. I don't undestand that,
        //so instead of trying to use it and failing, I'll abstain.
        Component frame = e.getComponent();
        AppContainer appContainer = AppContainer.getAppContainer();
        appContainer.scaleTo(frame.getSize());
        //repaint the frame, which will repaint everything contained in it
        frame.repaint();
    }

}
