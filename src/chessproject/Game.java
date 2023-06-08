package chessproject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import pieces.Bishop;
import pieces.ChessPiece;
import pieces.Knight;
import pieces.Queen;
import pieces.Rook;
import utilities.Coordinates;
import utilities.WorB;
import views.AppContainer;
import views.BoardView;
import views.GameMenu;

/**
 *
 * @author Ángel Marqués García 
 */
public class Game {
    private static List<ChessTurn> history = new ArrayList<>();
    private static GameBoard gameBoard;
    private static boolean[] whiteCastle = new boolean[]{true,true};
    private static boolean[] blackCastle = new boolean[]{true,true};
    private static int currentMoveNum = 0;
    private static int movesWithNoPawnOrCapture = 0;
    private static Coordinates enPassant = null; //could potentially be Coordinates
    private static boolean whiteToPlay = true;
    private static Set<Coordinates> goodMoves = null;
    private static WorB checkedKing = null;
    
    private static List<ChessPiece> checkers = new ArrayList<>();
    
    private static ChessPiece selectedPiece = null;
    private static List<Coordinates> availableMoves;
    private static boolean completed;
    
    private static ChessTurn currentTurn;
    private static int startingMoveNum;
    
    private static final String[] possiblePromotions =  new String[]{"Rook","Bishop","Knight", "Queen"};

    public static boolean[] getCastle(WorB color){
        return (color==WorB.WHITE ? whiteCastle:blackCastle);
    }    
    public static void createGame() { 
        String configuration = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        createGame(configuration);
    }
    
    /**
     * Returns true if the king of color is under check, 
     * while adding the pieces checking it to a field.
     * 
     * <p>The only two pieces which can check are the piece that was just moved and
     * the first piece behind it. Thus, we check if the piece that was just moved
     * is delivering a check. After that, we obtain the positions behind it.
     * Out of those, we take the one which is closest to the king AND has a piece,
     * and we check if that piece is delivering a check.
     * @param color
     * @return 
     */
    private static boolean isInCheck(WorB color){
        checkers.clear();
        ChessPiece king = gameBoard.getKing(color);
        boolean isCheck = false;
        
        if (selectedPiece.updateAvailableMoves().contains(king.getPos())){
            isCheck = true;
            checkers.add(selectedPiece);
        }
        List<Coordinates> line = Coordinates.getLine(selectedPiece.getPrevPos(), king.getPos(), true);
        ChessPiece piece = GameBoard.getClosestPiece(king.getPos(), line, gameBoard);
        
        if (piece != null && ! piece.isColor(color) && piece.updateAvailableMoves().contains(king.getPos())){
            isCheck = true;
            checkers.add(piece);
        }
        return isCheck;
    }

    public static Coordinates getEnPassant() {
        return enPassant;
    }
    
    public static boolean isCheckMate(WorB color){
        ChessPiece king = gameBoard.getKing(color);
        if (!king.updateAvailableMoves().isEmpty())
            return false; //false not checkmate, King moves available
        if (checkers.size()==2){
            return true; //true, checkmate. King is checked by two pieces and can't move
        }
        ChessPiece attacker = checkers.get(0);
        goodMoves = new HashSet<>();
        goodMoves.addAll(Coordinates.getLine(attacker.getPos(), king.getPos(), false));
        Set<Coordinates> allMoves = gameBoard.getAllMoves(color);
        goodMoves = allMoves; //MIGHT improve performance on succesive updateAvailableMoves(). Maybe not since it's a set.
        return allMoves.isEmpty();
    }
    private static void kingChecked(WorB color) {
        checkedKing = color;
        if (isCheckMate(color)){
            checkMate(color);
        } else if (goodMoves == null){
            ChessPiece attacker = checkers.get(0);
            goodMoves = new HashSet<>();
            goodMoves.addAll(Coordinates.getLine(attacker.getPos(), gameBoard.getKing(color).getPos(), false));
        }
        /**Tenemos un set en goodMoves con los movimientos que nos salvarían
        *Es jaque mate si 
        *   1. El rey no se puede mover
        *   2. Ninguna de las otras piezas puede moverse a algún sitio de goodMoves
        */ 
    }
    
    public static void createGame(String configuration) {
        try{
            GameMenu.reset();
            history.clear();
            completed = false;
            String[] parts = configuration.split(" ");
            gameBoard = new GameBoard(parts[0]);
        
            setUpVariables(parts);
        } catch (RuntimeException e){
            System.out.println("----------------------------------La string FEN proporcionada no tiene el formato correcto----------------------------------");
            System.out.println(e.getClass().getName());
            createGame();
        }
    }
    
    public static GameBoard getGameBoard(){
        return gameBoard;
    }
    
    public static void restart(String fen){
        if (fen == null || "".equals(fen)){
            createGame();
        } else{
            createGame(fen);
        }
        AppContainer.getAppContainer().repaint();
    }
    
    private static void setUpVariables(String[] parts) {
        whiteToPlay = "w".equals(parts[1].strip());
        for (int i=0;i<parts[2].length();i++){
            switch (parts[2].charAt(i)) {
                case 'K' -> whiteCastle[0] = true;
                case 'Q' -> whiteCastle[1] = true;
                case 'k' -> blackCastle[0] = true;
                case 'q' -> blackCastle[1] = true;
        }   }
        enPassant = interpretEnPassant(parts[3].strip());
        movesWithNoPawnOrCapture = Integer.parseInt(parts[4].strip());
        startingMoveNum = Integer.parseInt(parts[5].strip());
        currentMoveNum = 1;
    }
    
    public static String toStringFEN(){
        String conf = toStringBoard();
        conf += toStringFlags();
        return conf;
    }
    private static String toStringFlags(){
        String conf = "";
        conf += " " + (whiteToPlay ? "w":"b");
        conf += " ";
        if (whiteCastle[0]) conf += "K";
        if (whiteCastle[1]) conf += "Q";
        if (blackCastle[0]) conf += "k";
        if (blackCastle[1]) conf += "q";
        if (conf.endsWith(" ")) conf += "-";
        conf += " " + (enPassant==null ? "-":enPassant.toString()); //enPassant;
        conf += " " + Integer.toString(movesWithNoPawnOrCapture);
        conf += " " + Integer.toString(currentMoveNum+startingMoveNum);
        return conf;
    }
    
    private static String toStringBoard() {
        ChessPiece[][] board = gameBoard.getBoard();
        String conf = "";
        int empties = 0;
        for (ChessPiece[] row: board){
            if (empties != 0){
                    conf += empties;
                    empties = 0;
                }
                conf += "/";
            for (ChessPiece piece: row){
                if (piece==null){
                    empties++;
                    if (empties == 8){ 
                        conf += Integer.toString(empties);
                        empties = 0;
                    }
                } else {
                    if (empties != 0){
                        conf += Integer.toString(empties);
                        empties = 0;
                    }
                    conf += piece.getName();
        }   }   }
        return conf.substring(1);
    }
    
    public static void move(Coordinates cell) {
        if (!availableMoves.contains(cell)){ //does this ever happen? I don't think it should. It does happen, and I think it's wrong
            selectedPiece = null;
            AppContainer.getAppContainer().repaint();
            return; 
        } 
        
        movesWithNoPawnOrCapture++;
        ChessMove currentMove = new ChessMove(selectedPiece.getPos(), cell, selectedPiece);
        updateEnPassant(cell, currentMove);
        
        ChessPiece pieceToMove = selectedPiece;
        if (selectedPiece.getName().toLowerCase().equals("p")){
            movesWithNoPawnOrCapture = 0;
        }

        ChessPiece pieceAtCell = gameBoard.at(cell);
        if (pieceAtCell != null){
            movesWithNoPawnOrCapture = 0;
            pieceTaken(pieceAtCell);
            currentMove.setTakenPiece(pieceAtCell);
        }
        
        gameBoard.move(pieceToMove.getPos(), cell);
        AppContainer.getAppContainer().repaint();
        pieceToMove.move(cell);
        int isCastle = checkCastle(selectedPiece);
        doCastling(isCastle);
        currentMove.setCastle(isCastle);
        whiteToPlay = !whiteToPlay;
        currentMove.setFenBoardAfter(toStringFEN());
        whiteToPlay = !whiteToPlay;

        
        ChessPiece promoted = null;
        if (((whiteToPlay && cell.y == 0) || (!whiteToPlay && cell.y == 7)) && selectedPiece.getName().toUpperCase().equals("P")){
            promoted = promotePawn(selectedPiece);
            selectedPiece = promoted;
        }
        currentMove.setPromotion(promoted);
        
        if (whiteToPlay){
            currentMoveNum++;
            currentTurn = new ChessTurn(currentMoveNum);
            history.add(currentTurn);
            currentTurn.setWhiteMove(currentMove);
        } else {
            currentTurn.setBlackMove(currentMove);
        }
        
        checkedKing = null;
        goodMoves = null;
        
        WorB color = (pieceToMove.isWhite() ? WorB.BLACK:WorB.WHITE);
        if (gameBoard.getAllMoves(color).isEmpty()){
            staleMate(color);
        } 
        if (whiteToPlay && isInCheck(WorB.BLACK)){
            currentMove.setIsCheck(true);
            kingChecked(WorB.BLACK);
        } else if (!whiteToPlay && isInCheck(WorB.WHITE)){
            currentMove.setIsCheck(true);
            kingChecked(WorB.WHITE);
        }
        updateCastlingRightsAfterMove(cell);
        
        selectedPiece = null; //goodMoves = null;
        availableMoves.clear();
        whiteToPlay = ! whiteToPlay;
        
        GameMenu.addHalfMove(currentMove);
               
        check50MoveRule();
        check3FoldRepetition();
        
        //System.out.println("Just did a move. Current move: " + Integer.toString(currentMoveNum));
        System.out.println(toStringFEN());
    }

    private static void pieceTaken(ChessPiece piece) {
        gameBoard.addTakenPiece(piece);
    }

    /**
     * Orders BoardView to highlight the availableMoves
     */
    private static void highlightAvailableMoves() {
        BoardView boardView = BoardView.getBoardView();
        boardView.highlight(availableMoves);
    }

    /**
     * Iterates through availableMoves, and removes ilegal moves
     */
    private static void cullAvailableMoves() {
        //System.out.println("Game.cullAvailableMoves not supported yet. And most likely it will never be.");
        GameBoard gameBoard = Game.getGameBoard();
        ArrayList<Coordinates>  copy = (ArrayList<Coordinates>) availableMoves;
        copy = (ArrayList<Coordinates>) copy.clone();
        for (Coordinates move:copy){
            if (gameBoard.at(move) != null && gameBoard.at(move).isWhite() == selectedPiece.isWhite()){
                availableMoves.remove(move);
        }
        }
    }
    
    public static void setSelectedPiece(ChessPiece p){
        if (!completed && p != null && p.isWhite() == whiteToPlay){ //the turn system is disabled if we remove the second condition
            selectedPiece = p;
            availableMoves = p.updateAvailableMoves();
            cullAvailableMoves();
            highlightAvailableMoves();
            AppContainer.getAppContainer().repaint();
        }
    }
    
    public static ChessPiece getSelectedPiece(){
        return selectedPiece;
    }
    
    public static Set<Coordinates> getGoodMoves(){
        return goodMoves;
    }

    public static List<ChessPiece> getCheckers() {
        return checkers;
    }

    private static void checkMate(WorB color) {
        System.out.println("-------------------------------Game Over-------------------------------");
        System.out.println("-------------------------------Game Over-------------------------------");
        AppContainer.checkMate(color);
    }

    public static Coordinates checkForPin(Coordinates p, WorB c) {
        GameBoard gameBoard = Game.getGameBoard();
        
        Coordinates enemyKingPos = gameBoard.getKing(c).getPos();
        List<Coordinates> line = Coordinates.getLine(p, enemyKingPos, false);
        if (!line.isEmpty()) line.remove(0);
        for (Coordinates pos : line) {
            ChessPiece piece = gameBoard.at(pos);
            if (piece != null) { //look between me and my king. When you find a piece, check if it is my king. If it's not, I can't be pinned
                if (pos.equals(enemyKingPos)) {
                    break;
                } else return null;
            }
        }
        
        line = Coordinates.getLine(p, gameBoard.getKing(c).getPos(), true);
        if (!line.isEmpty()) line.remove(0);
        Coordinates dir = Coordinates.getDir(p, gameBoard.getKing(c).getPos());
        for (Coordinates pos : line) {
            ChessPiece piece = gameBoard.at(pos);
            if (piece != null) {
                if (piece.isColor(c)) {
                    break;
                } else {
                    String pieceName = piece.getName().toUpperCase();
                    if ((dir.x == 0 || dir.y == 0) && (pieceName.equals("R") || "Q".equals(pieceName))) {
                        return dir;
                    } else if ((Math.abs(dir.x) == 1 && Math.abs(dir.y) == 1) && (pieceName.equals("B") || "Q".equals(pieceName))) {
                        return dir;
                    }
                }
            }
        }
        return null;
    }

    public static WorB getCheckedKing() {
        return checkedKing;
    }

    public static void lock() {
        completed = true;
    }

    public static boolean getWhiteToPlay() {
        return whiteToPlay;
    }

    public static void removeFocus() {
        selectedPiece = null; //goodMoves = null;
        if (availableMoves != null)
            availableMoves.clear();    
        AppContainer.getAppContainer().repaint();

    }

    public static void undoLastMove() {
        GameMenu.removeHalfMove();
        if (currentMoveNum > 1 && !whiteToPlay)
            currentMoveNum--;
        
        //System.out.println("Undone a move. Current move: " + Integer.toString(currentMoveNum));
       
        ChessMove moveToUndo;
        if (whiteToPlay){ //deshacemos un movimiento negro
            moveToUndo = currentTurn.pop(WorB.BLACK);
        } else { //deshacemos un movimiento blanco
            moveToUndo = currentTurn.pop(WorB.WHITE);
            if (currentMoveNum > 1)
                history.remove(history.size()-1);
            currentTurn = history.get(history.size()-1);
        }
        if (movesWithNoPawnOrCapture > 0)
            movesWithNoPawnOrCapture--;
        
        selectedPiece = moveToUndo.getMovedPiece();
        if (moveToUndo.isPromotion()){
            gameBoard.replace(selectedPiece.getPos(), selectedPiece);
        }
        gameBoard.move(moveToUndo.getFinPos(), moveToUndo.getIniPos());
        selectedPiece.move(moveToUndo.getIniPos());
        undoCastle(moveToUndo.getCastle());
        selectedPiece = null;
        
        ChessPiece takenPiece = moveToUndo.getTakenPiece();
        gameBoard.place(takenPiece, moveToUndo.getFinPos());
        if (takenPiece != null) {
            gameBoard.removeTakenPiece(takenPiece);
            gameBoard.place(null, moveToUndo.getFinPos());
            gameBoard.place(takenPiece, takenPiece.getPos());
        }
        
        whiteToPlay = !whiteToPlay;
        AppContainer.getAppContainer().repaint();
        
        //reset castling rights and enPassant
        if (currentMoveNum > 1){
            ChessMove move = (!whiteToPlay ? currentTurn.getWhiteMove():currentTurn.getBlackMove());
            String fen = move.getFenBoardAfter();
            updateCastlingRights(fen);
            undoEnPassant(fen);
        }

    }

    public static boolean canUndo() {
        return currentMoveNum > 1 || !whiteToPlay;
    }

    /**
     * Checks if the move that was just done is castling. if it is not, returns 0.
     * If it is a short castle, returns 1. If it is a long castle, returns 2.
     * @param selectedPiece
     * @return 
     */
    private static int checkCastle(ChessPiece selectedPiece) {
        boolean[] castlingRights = (whiteToPlay ? whiteCastle:blackCastle);
        if (!castlingRights[0] && !castlingRights[1]) return 0; //if can't castle return 0
        if (!selectedPiece.getName().toUpperCase().equals("K")) return 0; //if selected is not a king, return 0
        //we just moved a king, so we can no longer castle
        castlingRights[0] = false;
        castlingRights[1] = false;
        int x = 4;
        int y = (selectedPiece.isWhite() ? 7:0);
        Coordinates currentPos = selectedPiece.getPos();
        if (currentPos.equals(x+2,y)){
            return 1;
        } else if (currentPos.equals(x-2,y)){
            return 2;
        }
        return 0;
    }

    private static void doCastling(int castle) {
        if (castle == 0) return;
        Coordinates kingPos = selectedPiece.getPos();
        Coordinates from;
        Coordinates to;
        if (castle == 1){
            from = new Coordinates(7, kingPos.y);
            to = new Coordinates(kingPos.x-1, kingPos.y);
        } else {
            from = new Coordinates(0, kingPos.y);
            to = new Coordinates(kingPos.x+1, kingPos.y);
        }
        ChessPiece pieceToMove = gameBoard.at(from);
        gameBoard.move(from, to);
        pieceToMove.move(to);
    }

    private static void undoCastle(int castle) {
        if (castle==0) return;
        Coordinates kingPos = selectedPiece.getPrevPos();
        ChessPiece rook;
        Coordinates newPos;
        if (castle == 1){
            newPos = new Coordinates(7, kingPos.y);
            rook = gameBoard.at(kingPos.x-1, kingPos.y);
            gameBoard.move(rook.getPos(), newPos);
            rook.move(newPos);
        } else if (castle == 2){
            newPos = new Coordinates(0, kingPos.y);
            rook = gameBoard.at(kingPos.x+1, kingPos.y);
            gameBoard.move(rook.getPos(), newPos);
            rook.move(newPos);
        }
    }

    private static void updateCastlingRights(String fen) {
        String[] parts = fen.split(" ");
        String castle = parts[2];
        for (int i=0;i<castle.length();i++){
            switch (castle.charAt(i)) {
                case 'K' -> whiteCastle[0] = true;
                case 'Q' -> whiteCastle[1] = true;
                case 'k' -> blackCastle[0] = true;
                case 'q' -> blackCastle[1] = true;
        }   }
    }

    private static void updateCastlingRightsAfterMove(Coordinates finPos) {
        Coordinates iniPos = selectedPiece.getPos();
        if (iniPos.equals(0,0) || finPos.equals(0,0)) 
            blackCastle[1] = false;
        if (iniPos.equals(7,0) || finPos.equals(7,0)) 
            blackCastle[0] = false;
        if (iniPos.equals(7,7) || finPos.equals(7,7)) 
            whiteCastle[0] = false;
        if (iniPos.equals(0,7) || finPos.equals(0,7)) 
            whiteCastle[1] = false;
        if (iniPos.equals(4,0)){
            blackCastle[0] = false;
            blackCastle[1] = false;
        } else if (iniPos.equals(4,0)){
            whiteCastle[0] = false;
            whiteCastle[1] = false;
        }
    }

    private static void updateEnPassant(Coordinates cell, ChessMove currentMove) {
        if (cell.equals(enPassant)){
            takenEnPassant(cell, currentMove);
            return;
        }
        enPassant = null;
        if (!selectedPiece.getName().toUpperCase().equals("P")) return;
        Coordinates pos = selectedPiece.getPos();
        if (Math.abs(pos.y-cell.y) > 1){
            int y = (whiteToPlay ? cell.y+1:cell.y-1);
            enPassant = new Coordinates(cell.x, y);
        }
    }

    private static Coordinates interpretEnPassant(String strip) {
        if ("-".equals(strip)) return null;
        String col = strip.substring(0,1);
        String row = strip.substring(1,2);
        int x = 0;
        int y = 8 - Integer.parseInt(row);
        switch (col) {
            case "a" -> x = 0;
            case "b" -> x = 1;
            case "c" -> x = 2;
            case "d" -> x = 3;
            case "e" -> x = 4;
            case "f" -> x = 5;
            case "g" -> x = 6;
            case "h" -> x = 7;
        }
        return new Coordinates(x,y);
    }

    private static void takenEnPassant(Coordinates cell, ChessMove currentMove) {
        int x = enPassant.x;
        int y = enPassant.y + (whiteToPlay ? 1:-1);
        ChessPiece takenPiece = gameBoard.at(x,y);
        pieceTaken(takenPiece);
        currentMove.setTakenPiece(takenPiece);
        enPassant = null;
        gameBoard.place(null, new Coordinates(x,y));
    }

    private static void undoEnPassant(String fen) {
        String[] parts = fen.split(" ");
        String enPassantString = parts[3];
        enPassant = interpretEnPassant(enPassantString);
    }

    private static ChessPiece promotePawn(ChessPiece pawn) {
        String option = AppContainer.showPromotionOptions(pawn, possiblePromotions);
        ChessPiece promotion = null;
        WorB color = (pawn.isWhite() ? WorB.WHITE:WorB.BLACK);
        if (option==null) option = "Queen";
        switch (option) {
            case "Queen" -> promotion = new Queen(color);
            case "Knight" -> promotion = new Knight(color);
            case "Rook" -> promotion = new Rook(color);
            case "Bishop" -> promotion = new Bishop(color);
            //default is ugly. If you exit the window, you are given a queen? 1 of 2 solutions. 
            //1. create own JDialog. this might help https://stackoverflow.com/questions/45372376/joptionpane-showinputdialog-without-cancel-button-and-exit-handle
            //2. make it so that if the user exits the window, undo is called.
        }
        promotion.move(pawn.getPrevPos());
        promotion.move(pawn.getPos());
        gameBoard.place(promotion, pawn.getPos());
        gameBoard.addPiece(promotion);
        gameBoard.removePiece(pawn);
        return promotion;
    }

    private static void staleMate(WorB color) {
        AppContainer.showStaleMate(color);
    }

    private static void check50MoveRule() {
        if (movesWithNoPawnOrCapture >= 50){
            lock();
            AppContainer.show50MoveDraw();
        }
    }

    private static void check3FoldRepetition() {
        String currentPos = Game.toStringFEN();
        currentPos = removeNumbers(currentPos);
        int counter = 0;
        for (ChessTurn turn: history){
            if (counter >= 3) break;
            ChessMove move = turn.getBlackMove();
            if (checkEqualBoards(move, currentPos)) counter++;
            move = turn.getWhiteMove();
            if (checkEqualBoards(move, currentPos)) counter++;
        }
        if (counter >= 3){
            AppContainer.show3FoldDraw();
            lock();
        }
    }
    private static boolean checkEqualBoards(ChessMove move, String currentPos){
        if (move != null){
            String board = move.getFenBoardAfter();
            board = removeNumbers(board);
            if (board.equals(currentPos))
                return true;
        }
        return false;
    }

    private static String removeNumbers(String board) {
        String[] parts = board.split(" ");
        String newBoard = "";
        for (int i=0; i<parts.length-3;i++){
            newBoard += parts[i];
        }
        return newBoard;
    }
        
    
}