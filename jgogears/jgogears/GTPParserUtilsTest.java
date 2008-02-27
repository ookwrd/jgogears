package jgogears;

import java.io.IOException;

import junit.framework.TestCase;


/**
 * The Class GTPParserUtilsTest.
 */
public class GTPParserUtilsTest extends TestCase {

	/** Are we being verbose? */
	boolean DEBUG = false;

	/** The engine. */
	GnuGoEngine engine = null;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.engine = new GnuGoEngine();
		this.engine.initialise();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		if (this.engine != null)
			this.engine.quit();
	}

	/**
	 * Test loadsgf.
	 */
	public final void testLoadsgf() {
		try {
			this.engine.initialise();
			Boolean b = this.engine.loadsgf("sgf/testing/seki.sgf", 20);
			assertTrue(b);
			Move move = this.engine.genMove(BoardI.VERTEX_BLACK);
			assertNotNull(move);
			BoardI board = this.engine.showBoard();
			System.err.println("testLoadsgf:: the following board should have moves on it:");
			assertNotNull(board);
			System.err.println(board);
			this.engine.quit();
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

	/**
	 * Test parse gnu go board.
	 * @throws IOException if the underlying engine can't be created or communicated with
	 */
	public void testParseGnuGoBoard() throws IOException {
		this.testParseGnuGoBoard((short) 7);
		this.testParseGnuGoBoard((short) 9);
		this.testParseGnuGoBoard((short) 11);
		this.testParseGnuGoBoard((short) 13);
		this.testParseGnuGoBoard((short) 15);
		this.testParseGnuGoBoard((short) 17);
		this.testParseGnuGoBoard((short) 19);
		// testParseGnuGoBoard((short)21);
		// testParseGnuGoBoard((short)23);
		// testParseGnuGoBoard((short)25);
	}

	/**
	 * Test parse gnu go board.
	 * 
	 * @param i the i
	 */
	public void testParseGnuGoBoard(short i) throws IOException {
		try {
			this.engine = new GnuGoEngine();
			boolean result = this.engine.initialise();
			assertTrue(result);
			result = this.engine.setBoardSize(i);
			assertTrue(result);
			this.engine.quit();
		} catch (GTPError e) {
			System.err.println("testParseGnuGoBoard(" + i + ") failed");
			//System.err.println(e);
			//e.printStackTrace();
			fail();
		}
	}

	/**
	 * 
	 * This is currently failing because I don't have good code to parse the ASCII board back into a BoardI
	 */
	public final void testShowBoard() {
		try {
			this.engine.initialise();
			BoardI board = this.engine.showBoard();
			assertNotNull(board);
			// TODO actually test the generated board here...
			this.engine.quit();
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

}
