/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package views;

import javax.swing.*;

/**
 *
 * @author Ángel Marqués García 
 */
public class InfoMenu extends JPanel {

    public InfoMenu() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel takenByBlack = new TakenPieces();
        JPanel takenByWhite = new TakenPieces();
        JPanel menu = new GameMenu();
        
        add(takenByBlack);
        add(menu);
        add(takenByWhite);
    }

}
