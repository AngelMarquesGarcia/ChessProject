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
    private static JPanel takenByBlack;
    private static JPanel takenByWhite;

    public InfoMenu() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        takenByBlack = new TakenPieces();
        takenByWhite = new TakenPieces();
        JPanel menu = new GameMenu();
        
        add(takenByBlack);
        add(menu);
        add(takenByWhite);
    }
    
    public static TakenPieces getTakenByBlack(){
        return (TakenPieces) takenByBlack;
    }
    
    public static TakenPieces getTakenByWhite(){
        return (TakenPieces) takenByWhite;
    }

}
