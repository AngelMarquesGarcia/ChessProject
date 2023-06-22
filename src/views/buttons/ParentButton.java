/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package views.buttons;

import javax.swing.JButton;

/**
 * This class is here only so that the icons for the buttons can be scaled without
 * losing definition
 * @author Ángel Marqués García 
 */
public class ParentButton extends JButton{
    
    public final String ICON_PATH;
    
    protected ParentButton(String path){
        ICON_PATH = path;
    }

}
