package pieces;

import utilities.WorB;

/**
 *
 * @author Ángel Marqués García 
 */
public class King extends ChessPiece {
    
    //////////////////////////////INSTANCE METHODS//////////////////////////////
    //////////////////////////////INSTANCE METHODS//////////////////////////////
    //////////////////////////////INSTANCE METHODS//////////////////////////////
    private static final String CLASS_NAME = "King";
    private static final String CLASS_REP = "K";
    private static final String whiteSprite = "./files/WhiteKing.png";
    private static final String blackSprite = "./files/BlackKing.png";
    private static final int V = 4;

    public King(WorB color) {
        super(CLASS_NAME, CLASS_REP, (color==WorB.WHITE? whiteSprite:blackSprite), color);
        value = V;
    }
}
