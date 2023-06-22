/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package views.listeners;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import views.containers.AppContainer;

/**
 *
 * @author Ángel Marqués García 
 */
public class MyComponentListener extends ComponentAdapter {
    
    @Override
    public void componentResized(ComponentEvent e){
        System.out.println("Component Resized");
        System.out.println(e.getComponent().getSize());
        Component frame = e.getComponent();
        AppContainer appContainer = AppContainer.getAppContainer();
        appContainer.scaleTo(frame.getSize());
        //repaint the frame, which will repaint everything contained in it
        frame.repaint();
        
    }
}
