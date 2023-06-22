/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package views.listeners;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 *
 * @author Ángel Marqués García 
 */
public class MyComponentListener extends ComponentAdapter {
    
    @Override
    public void componentResized(ComponentEvent e){
        e.getComponent();
        
    }
}
