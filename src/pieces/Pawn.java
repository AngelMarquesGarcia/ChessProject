package pieces;

import utilities.WorB;

/**
 *
 * @author Ángel Marqués García
 */
public class Pawn extends ChessPiece {

    private static final String CLASS_NAME = "Pawn";
    private static final String CLASS_REP = "P";
    private static final String whiteSprite = "./files/WhitePawn.png";
    private static final String blackSprite = "./files/BlackPawn.png";
    private static final int V = 1;

    public Pawn(WorB color) {
        super(CLASS_NAME, CLASS_REP, (color==WorB.WHITE? whiteSprite:blackSprite), color);
        value = V;
        takesSameAsMoves = false;
    }
}
