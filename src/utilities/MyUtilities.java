package utilities;

/**
 *
 * @author Ángel Marqués García 
 */
public class MyUtilities {

    public static String removeNumbers(String board) {
        String[] parts = board.split(" ");
        String newBoard = "";
        for (int i = 0; i < parts.length - 3; i++) {
            newBoard += parts[i];
        }
        return newBoard;
    }
    // </editor-fold>

}
