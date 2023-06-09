package pieces;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import utilities.Coordinates;
import utilities.WorB;

/**
 *
 * @author Ángel Marqués García
 */
public class Queen extends ChessPiece {

    public static List<Coordinates> updateAvailableMoves(Coordinates p, WorB c) {
        List<Coordinates> coords = new ArrayList<>();
        coords.addAll(Rook.updateAvailableMoves(p, c));
        coords.addAll(Bishop.updateAvailableMoves(p, c));
        return coords;
    }

private static final String CLASS_NAME = "Queen";
    private static final String CLASS_REP = "Q";
    private static final String whiteSprite = "./files/WhiteQueen.png";
    private static final String blackSprite = "./files/BlackQueen.png";
    private static final int V = 9;

    public Queen(WorB color) {
        super(CLASS_NAME, CLASS_REP, (color==WorB.WHITE? whiteSprite:blackSprite), color);
        value = V;
    }

    @Override
    public List<Coordinates> updateAvailableMoves() {
        return Queen.updateAvailableMoves(pos, color);
        /*List<Coordinates> moves = Queen.updateAvailableMoves(pos, color, pinned);
        availableMoves.clear();
        availableMoves.addAll(moves);*/
    }

}
