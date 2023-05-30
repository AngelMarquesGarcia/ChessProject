/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package views;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Ángel Marqués García 
 */
public class AppContainer extends JPanel {
    private JPanel board;
    private JPanel infoMenu;
    private static AppContainer appContainer;
    
    public static AppContainer getAppContainer(){
        return appContainer;
    }

    public AppContainer() {
        super();
        board = new BoardView();
        add(board);
        
        infoMenu = new InfoMenu();
        add(infoMenu);
        
        setPreferredSize(new Dimension(450, 450));
        appContainer = this;
        
        
    }

}
