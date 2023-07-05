package chess;

/**
 *
 * @author Ángel Marqués García 
 */
public class ChessGame {
    public static final int boardWidth = 8;
    public static final int boardHeight = 8;
    public static final String defaultConfig = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    public static final String[] possiblePromotions =  new String[]{"Rook","Bishop","Knight", "Queen"};
    
    //se podrían añadir cosos para las reglas de empate
    public final String[] defaultPieces = new String[]{"8p","8P","2r","2R","2n","2N","2b","2B","1q","1Q","1k","1K"};
    
    
    public ChessGame(){
    }
    
}
