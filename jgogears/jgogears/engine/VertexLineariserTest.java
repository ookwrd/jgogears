package jgogears.engine;

import java.io.StringReader;
import java.util.Iterator;

import jgogears.*;
import junit.framework.TestCase;

// TODO: Auto-generated Javadoc
/**
 * The Class VertexLineariserTest.
 */
public class VertexLineariserTest extends TestCase {

	public static final boolean DEBUG = false;

	/**
	 * test what happens when linearising boards of different sizes
	 * 
	 * @throws Exception
	 */
	public void testDifferenetSizes5() throws Exception {
		try {
			BoardI board = new Board(19);
			VertexLineariser lineariser = new VertexLineariser(board, (short) 2, (short) 2, (short) 0, false);
			assertNotNull(lineariser);
			board = new Board(9);
			lineariser = new VertexLineariser(board, (short) 2, (short) 2, (short) 0, false);
			fail("shouldn't be able to linearise different sizes of board");
		} catch (IllegalArgumentException e) {
			assertNotNull(e);
		}
	}

	/**
	 * test what happens when linearising boards of different sizes
	 * 
	 * @throws Exception
	 */
	public void testDifferenetSizes9() throws Exception {
		try {
			BoardI board = new Board(19);
			VertexLineariser lineariser = new VertexLineariser(board, (short) 2, (short) 2, (short) 0, false);
			assertNotNull(lineariser);
			board = new Board(9);
			lineariser = new VertexLineariser(board, (short) 2, (short) 2, (short) 0, false);
			fail("shouldn't be able to linearise different sizes of board");
		} catch (IllegalArgumentException e) {
			assertNotNull(e);
		}
	}

	/**
	 * test what happens when linearising boards of different sizes
	 * 
	 * @throws Exception
	 */
	public void testDifferenetSizes13() throws Exception {
		try {
			BoardI board = new Board(19);
			VertexLineariser lineariser = new VertexLineariser(board, (short) 2, (short) 2, (short) 0, false);
			assertNotNull(lineariser);
			board = new Board(13);
			lineariser = new VertexLineariser(board, (short) 2, (short) 2, (short) 0, false);
			fail("shouldn't be able to linearise different sizes of board");
		} catch (IllegalArgumentException e) {
			assertNotNull(e);
		}
	}

	/**
	 * test what happens when linearising boards of different sizes
	 * 
	 * @throws Exception
	 */
	public void testDifferenetSizes25() throws Exception {
		try {
			BoardI board = new Board(19);
			VertexLineariser lineariser = new VertexLineariser(board, (short) 2, (short) 2, (short) 0, false);
			assertNotNull(lineariser);
			board = new Board(25);
			lineariser = new VertexLineariser(board, (short) 2, (short) 2, (short) 0, false);
			fail("shouldn't be able to linearise different sizes of board");
		} catch (IllegalArgumentException e) {
			assertNotNull(e);
		}
	}

	/**
	 * Test everything.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void testEverything() throws Exception {

		String examples[] = {
		// SGFParser.EXAMPLEONE,
				SGFParser.EXAMPLETWO, SGFParser.EXAMPLETHREE, SGFParser.EXAMPLEFOUR, SGFParser.EXAMPLEFIVE };

		for (int i = 0; i < examples.length; i++) {
			String example = examples[i];

			StringReader reader = new StringReader(example);
			jgogears.SGF.SGF parser = new jgogears.SGF.SGF(reader);
			SGFGameTree tree = parser.gameTree();

			assertTrue(parser != null);
			assertTrue(parser.toString() != null);
			assertTrue(parser.toString().length() > 0);

			assertTrue(tree != null);
			assertTrue(tree.toString() != null);
			assertTrue(tree.toString().length() > 0);

			Game game = new Game(tree);
			assertTrue(game != null);

			Iterator<BoardI> iterator = game.getBoards();
			assertTrue(iterator != null);

			BoardI board = null;
			while (iterator.hasNext()) {
				board = iterator.next();
				assertTrue(board != null);
				for (short j = 0; j < 8; j++) {
					Iterator<Short> linear = new VertexLineariser(board, (short) 2, (short) 2, j, false);
					assertTrue(linear != null);
					while (linear.hasNext()) {
						Short s = linear.next();
						assertTrue(s != null);
						// System.out.print(" " + s + ", ");
					}
				}
				// System.out.println();
			}

		}

	}

	/**
	 * Test trained model.
	 */
	public void testVerbose() {
		BoardI board = new Board();
		assertNotNull(board);
		board = board.newBoard(new Move("white b2"));
		board = board.newBoard(new Move("black k4"));
		board = board.newBoard(new Move("white c3"));
		board = board.newBoard(new Move("black g4"));
		board = board.newBoard(new Move("white d4"));
		board = board.newBoard(new Move("black h4"));
		board = board.newBoard(new Move("white n4"));

		assertNotNull(board);
		if (DEBUG)
			System.err.print("VertexLineariserTest::testVertexLineariser() Black = " + Board.parseColour("black"));
		if (DEBUG)
			System.err.print(" White = " + Board.parseColour("white"));
		if (DEBUG)
			System.err.print(" Empty = " + Board.VERTEX_EMPTY);
		if (DEBUG)
			System.err.print(" Off = " + Board.VERTEX_OFF_BOARD);
		if (DEBUG)
			System.err.println("");
		for (short j = 0; j < 8; j++) {
			Iterator<Short> linear = new VertexLineariser(board, (short) 2, (short) 2, j, false);
			assertTrue(linear != null);
			while (linear.hasNext()) {
				Short s = linear.next();
				assertTrue(s != null);
				if (DEBUG)
					System.err.print(" " + s + ", ");
			}
			if (DEBUG)
				System.err.println();
		}

	}

	/**
	 * make sure that all the linearisations are different
	 */
	public void testAllDifferent() {
		BoardI board = new Board();
		assertNotNull(board);
		board = board.newBoard(new Move("white b2"));
		board = board.newBoard(new Move("black k4"));
		board = board.newBoard(new Move("white c3"));
		board = board.newBoard(new Move("black g4"));
		board = board.newBoard(new Move("white d4"));
		board = board.newBoard(new Move("black h4"));
		board = board.newBoard(new Move("white n4"));

		assertNotNull(board);
		for (short j = 0; j < 8; j++) 
			for (short i = 0; i < 8; i++) {
				if (i!= j){
				Iterator<Short> lineara = new VertexLineariser(board, (short) 2, (short) 2, j, false);
				Iterator<Short> linearb = new VertexLineariser(board, (short) 2, (short) 2, i, false);
				assertTrue(lineara != null);
				assertTrue(linearb != null);
				boolean differenceFound = false;
				while (lineara.hasNext() && linearb.hasNext()) {
					Short a = lineara.next();
					Short b = linearb.next();
					assertTrue(a != null);
					assertTrue(b != null);
					if (!a.equals(b))
						differenceFound = true;
				}
				assertTrue(i + " "+  j, differenceFound);
				}
		}

	}

	/**
	 * Test trained model.
	 */
	public void testSize() {
		BoardI board = new Board();
		assertNotNull(board);
		int count = 0;
		Iterator<Short> linear = new VertexLineariser(board, (short) 2, (short) 2, (short) 0, false);
		while (linear.hasNext()) {
			Short s = linear.next();
			assertNotNull(s);
			count++;
		}
		int plussize = (board.getSize() + 2) * (board.getSize() + 2);
		
		assertTrue(count + "/" + plussize, count == plussize);
	}

}
