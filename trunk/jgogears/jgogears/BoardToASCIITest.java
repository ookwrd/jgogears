/**
 * 
 */
package jgogears;

import junit.framework.TestCase;

// TODO: Auto-generated Javadoc
/**
 * The Class BoardToASCIITest.
 */
public class BoardToASCIITest extends TestCase {

	/** The Constant DEBUG. */
	static final public boolean DEBUG = true;

	/**
	 * Test empty.
	 */
	public void testEmpty() {
		BoardI board = BoardI.newBoard();
		String string = BoardToASCII.Transform(board);
		assertNotNull(board);
		assertNotNull(string);
		board = new FastBoard();
		string = BoardToASCII.Transform(board);
		assertNotNull(board);
		assertNotNull(string);
		board = new FasterBoard();
		string = BoardToASCII.Transform(board);
		assertNotNull(board);
		assertNotNull(string);
	}

	/**
	 * Test one.
	 */
	public void testOne() {
		BoardI board = BoardI.newBoard();
		board = board.newBoard(new Move("w b1"));
		String string = BoardToASCII.Transform(board);
		assertNotNull(board);
		assertNotNull(string);
		if (DEBUG)
			System.err.println(string);
	}

}
