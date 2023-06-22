package pieces;

import utilities.WorB;

/**
 *
 * @author Ángel Marqués García
 */
public class Bishop extends ChessPiece {

    private static final String CLASS_NAME = "Bishop";
    private static final String CLASS_REP = "B";
    private static final String sprStart = "./files/";
    private static final String sprEnd = ".png";
    private static final int V = 3;

    public Bishop(WorB color) {
        super(CLASS_NAME, CLASS_REP, sprStart+color+CLASS_NAME+sprEnd, color);
        value = V;
    }
}
