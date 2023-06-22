package pieces;

import utilities.WorB;

/**
 *
 * @author Ángel Marqués García
 */
public class Rook extends ChessPiece {
    private static final String CLASS_NAME = "Rook";
    private static final String CLASS_REP = "R";
    private static final String sprStart = "./files/";
    private static final String sprEnd = ".png";
    private static final int V = 5;

    public Rook(WorB color) {
        super(CLASS_NAME, CLASS_REP, sprStart+color+CLASS_NAME+sprEnd, color);
        value = V;
    }
}
