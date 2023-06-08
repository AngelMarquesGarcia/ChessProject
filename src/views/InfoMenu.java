package views;

import javax.swing.*;
import utilities.WorB;

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
        takenByBlack = new TakenPieces(WorB.BLACK);
        takenByWhite = new TakenPieces(WorB.WHITE);
        JPanel menu = new GameMenu();

        add(takenByBlack);
        add(menu);
        add(takenByWhite);
    }
    
    public static TakenPieces getTakenBy(WorB color) {
        return (TakenPieces) (color == WorB.WHITE ? takenByWhite : takenByBlack);
    }

    //useless
    public static TakenPieces getTakenByBlack() {
        return (TakenPieces) takenByBlack;
    }

    //useless
    public static TakenPieces getTakenByWhite() {
        return (TakenPieces) takenByWhite;
    }

}
