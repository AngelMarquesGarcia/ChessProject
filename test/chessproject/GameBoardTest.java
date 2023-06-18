/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package chessproject;

import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pieces.ChessPiece;
import utilities.Coordinates;
import utilities.WorB;

/**
 *
 * @author burak
 */
public class GameBoardTest {
    
    public GameBoardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testConstructors(){
        ChessBoard gameBoard = new ChessBoard("rnbqkbnr/ppp1pppp/3p4/4P3/8/8/PPPP1PPP/RNBQKBNR");
        ChessPiece piece = gameBoard.at(new Coordinates(4, 3));
        assertNotNull(piece);
        assertEquals(piece.getName(), "P");
        
    }
    /**
     * Test of getClosestPiece method, of class GameBoard.
     */
    @Test
    public void testGetClosestPiece() {
        System.out.println("getClosestPiece");
        Coordinates pos = null;
        List<Coordinates> line = null;
        ChessBoard gameBoard = null;
        ChessPiece expResult = null;
        ChessPiece result = ChessBoard.getClosestPiece(pos, line, gameBoard);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isLegal method, of class GameBoard.
     */
    @Test
    public void testIsLegal() {
        System.out.println("isLegal");
        Coordinates c = null;
        boolean expResult = false;
        boolean result = ChessBoard.isLegal(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBoard method, of class GameBoard.
     */
    @Test
    public void testGetBoard() {
        System.out.println("getBoard");
        ChessBoard instance = null;
        ChessPiece[][] expResult = null;
        ChessPiece[][] result = instance.getBoard();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPieces method, of class GameBoard.
     */
    @Test
    public void testGetPieces() {
        System.out.println("getPieces");
        WorB color = null;
        ChessBoard instance = null;
        List<ChessPiece> expResult = null;
        List<ChessPiece> result = instance.getPieces(color);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTaken method, of class GameBoard.
     */
    @Test
    public void testGetTaken() {
        System.out.println("getTaken");
        WorB color = null;
        ChessBoard instance = null;
        List<ChessPiece> expResult = null;
        List<ChessPiece> result = instance.getTaken(color);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getKing method, of class GameBoard.
     */
    @Test
    public void testGetKing() {
        System.out.println("getKing");
        WorB color = null;
        ChessBoard instance = null;
        ChessPiece expResult = null;
        ChessPiece result = instance.getKing(color);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllMoves method, of class GameBoard.
     */
    @Test
    public void testGetAllMoves() {
        System.out.println("getAllMoves");
        WorB color = null;
        ChessBoard instance = null;
        Set<Coordinates> expResult = null;
        Set<Coordinates> result = instance.getAllMoves(color);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllAttackedCells method, of class GameBoard.
     */
    @Test
    public void testGetAllAttackedCells() {
        System.out.println("getAllAttackedCells");
        WorB color = null;
        ChessBoard instance = null;
        Set<Coordinates> expResult = null;
        Set<Coordinates> result = instance.getAllAttackedCells(color);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of move method, of class GameBoard.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        Coordinates pos1 = null;
        Coordinates pos2 = null;
        ChessBoard instance = null;
        instance.move(pos1, pos2);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of at method, of class GameBoard.
     */
    @Test
    public void testAt() {
        System.out.println("at");
        Coordinates c = null;
        ChessBoard instance = null;
        ChessPiece expResult = null;
        ChessPiece result = instance.at(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addTakenPiece method, of class GameBoard.
     */
    @Test
    public void testAddTakenPiece() {
        System.out.println("addTakenPiece");
        ChessPiece piece = null;
        ChessBoard instance = null;
        instance.addTakenPiece(piece);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
