package jgogears;

import java.io.*;
import junit.framework.TestCase;

public class GTPParserUtilsTest extends TestCase {

	boolean DEBUG = false;

	GnuGoEngine engine = null;

	protected void setUp() throws Exception {
		super.setUp();
		engine = new GnuGoEngine();
		engine.initialise();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		if (engine != null)
			engine.quit();
	}

	public final void testShowBoard() {
		try {
			engine.initialise();
			BoardI board = engine.showBoard();
			// TODO
			engine.quit();
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

	public final void testLoadsgf() {
		try {
			engine.initialise();
			Boolean b = engine.loadsgf("sgf/testing/seki.sgf", 20);
			assertTrue(b);
			Move move = engine.genMove(BoardI.VERTEX_BLACK);
			assertNotNull(move);
			BoardI board = engine.showBoard();
			System.err
					.println("testLoadsgf:: the following board should have moves on it:");
			System.err.println(board);
			engine.quit();
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

	public void testParseGnuGoBoard() {
		testParseGnuGoBoard((short) 3);
		testParseGnuGoBoard((short) 5);
		testParseGnuGoBoard((short) 7);
		testParseGnuGoBoard((short) 9);
		testParseGnuGoBoard((short) 11);
		testParseGnuGoBoard((short) 13);
		testParseGnuGoBoard((short) 15);
		testParseGnuGoBoard((short) 17);
		testParseGnuGoBoard((short) 19);
		// testParseGnuGoBoard((short)21);
		// testParseGnuGoBoard((short)23);
		// testParseGnuGoBoard((short)25);
	}

	public void testParseGnuGoBoard(short i) {
		try {
			engine = new GnuGoEngine();
			boolean result = engine.initialise();
			assertTrue(result);
			result = engine.setBoardSize(i);
			assertTrue(result);
			engine.quit();
		} catch (IOException e) {

		}
	}

}
