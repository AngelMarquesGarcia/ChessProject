package chess.pieces;

import utilities.WorB;

/**
 *
 * @author Ángel Marqués García
 */
public class Queen extends ChessPiece {

private static final String CLASS_NAME = "Queen";
    private static final String CLASS_REP = "Q";
    private static final String whiteSprite = "./files/WhiteQueen.png";
    private static final String blackSprite = "./files/BlackQueen.png";
    private static final int V = 9;

    public Queen(WorB color) {
        super(CLASS_NAME, CLASS_REP, (color==WorB.WHITE? whiteSprite:blackSprite), color);
        value = V;
    }
}
