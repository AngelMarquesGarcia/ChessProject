package chess.pieces;

import utilities.WorB;

/**
 *
 * @author Ángel Marqués García
 */
public class Knight extends ChessPiece {
    
    private static final String CLASS_NAME = "Knight";
    private static final String CLASS_REP = "N";
    private static final String sprStart = "./files/";
    private static final String sprEnd = ".png";
    private static final int V = 3;

    public Knight(WorB color) {
        super(CLASS_NAME, CLASS_REP, sprStart+color+CLASS_NAME+sprEnd, color);
        value = V;
    }
}
