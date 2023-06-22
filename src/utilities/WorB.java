package utilities;

/**
 *
 * @author Ángel Marqués García
 */
public enum WorB {
    WHITE, BLACK;

    public static WorB not(WorB c) {
        if (c == WHITE) {
            return BLACK;
        }
        return WHITE;
    }
}
