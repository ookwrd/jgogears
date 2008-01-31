package jgogears;

import java.util.*;
import java.io.*;

import junit.framework.TestCase;

public class GnuGoEngineTest2 extends TestCase {

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

	public final void testClearBoard() throws IOException {
		engine.initialise();
		boolean r = engine.clearBoard();
		engine.quit();
		assertTrue(r);
	}

	public final void testFinalStatusList() {
		try {

			Game game = Game
					.loadFromFile(new File("sgf/testing/seki.sgf"));
			assertNotNull(game);

			Iterator<GoMove> moves = game.getMoves();
			assertNotNull(moves);
			GnuGoEngine engine = new GnuGoEngine();
			while (moves.hasNext()) {
				GoMove move =  moves.next();
				// System.err.println(move);
				engine.play(move);
			}
			engine.initialise();
			TreeSet<Vertex> alive = engine.finalStatusList("alive");
			assertNotNull(alive);
			TreeSet<Vertex> dead = engine.finalStatusList("dead");
			assertNotNull(dead);
			TreeSet<Vertex> seki = engine.finalStatusList("seki");
			assertNotNull(seki);
			engine.quit();
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

	public final void testFixedHandicap() {
		try {
			engine.initialise();
			for (short i = 2; i < 10; i++) {
				engine.setBoardSize((short) 19);
				TreeSet<Vertex> moves = engine.fixedHandicap(i);
				assertNotNull(moves);
				assertTrue(moves.size() == i);
			}
			engine.quit();
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

	public final void testGenMove() {
		try {
			engine.initialise();
			engine.setBoardSize((short) 19);
			engine.clearBoard();
			GoMove move = engine.genMove(BoardI.VERTEX_BLACK);
			if (DEBUG)
				System.err.println(move);
			engine.quit();
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}

	}

	public final void testGetEngineName() {
		try {
			engine.initialise();
			String s = engine.getEngineName();
			if (DEBUG)
				System.err.println(s);
			engine.quit();
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

	public final void testGetEngineVersion() {
		try {
			engine.initialise();
			String s = engine.getEngineVersion();
			System.err.println(s);
			engine.quit();
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

	public final void testGetFinalScore() {
		try {
			engine.initialise();
			Boolean b = engine.loadsgf("sgf/testing/seki.sgf", 0);
			assertTrue(b);

			GTPScore score = engine.getFinalScore();
			assertNotNull(score);
			// TODO
			engine.quit();
			// TODO
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

	public final void testGetKnownCommand() {
		try {
			engine.initialise();
			engine.clearBoard();
			engine.quit();
			// TODO
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

	public final void testGetListCommands() {
		try {
			engine.initialise();
			engine.clearBoard();
			engine.quit();
			// TODO
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}

	}

	public final void testGetProtocolVersion() {
		try {
			engine.initialise();
			int v = engine.getProtocolVersion();
			assertTrue(v > 0);
			assertTrue(v < 3);
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
			GoMove move = engine.genMove(BoardI.VERTEX_BLACK);
			assertNotNull(move);
			BoardI board = engine.showBoard();
			System.err
					.println("testLoadsgf:: the following board should have moves on it:");
			System.err.println(board);
			engine.quit();
			// TODO
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

	public final void testPlaceFreeHandicapGoMoveArray() {
		try {
			engine.initialise();
			engine.clearBoard();
			engine.quit();
			// TODO
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

	public final void testPlaceFreeHandicapInt() {

		try {
			engine.initialise();
			engine.setBoardSize((short) 19);
			engine.clearBoard();
			TreeSet<Vertex> moves = engine.placeFreeHandicap((short) 9);
			assertTrue(moves.size() == 9);
			engine.quit();
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

	public final void testPlaceFreeHandicapIntMulti() {

		try {
			for (short i = 2; i < 50; i++) {
				engine.initialise();
				engine.setBoardSize((short) 19);
				engine.clearBoard();
				TreeSet<Vertex> moves = engine.placeFreeHandicap(i);
				assertTrue(moves.size() == i);
				engine.quit();
			}
			for (short i = 2; i < 20; i++) {
				engine.initialise();
				engine.setBoardSize((short) 13);
				engine.clearBoard();
				TreeSet<Vertex> moves = engine.placeFreeHandicap(i);
				assertTrue(moves.size() == i);
				engine.quit();
			}
			for (short i = 2; i < 10; i++) {
				engine.initialise();
				engine.setBoardSize((short) 9);
				engine.clearBoard();
				TreeSet<Vertex> moves = engine.placeFreeHandicap(i);
				assertTrue(moves.size() == i);
				engine.quit();
			}
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

	public final void testPlay() {
		try {
			engine.initialise();
			engine.setBoardSize((short) 19);
			engine.clearBoard();
			engine.play(new GoMove("white c3"));
			engine.quit();
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

	public final void testQuit() {
		try {
			engine.initialise();
			engine.quit();
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

	public final void testRegGenMove() {
		try {
			engine.initialise();
			GoMove move = engine.regGenMove(BoardI.VERTEX_BLACK);
			System.err.println(move);
			engine.quit();
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

	public final void testSetBoardSize() {
		try {
			engine.initialise();
			boolean result = engine.setBoardSize((short) 19);
			assertTrue(result);
			result = engine.setBoardSize((short) 9);
			assertTrue(result);
			engine.quit();
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

	public final void testSetKomi() {
		try {
			engine.initialise();
			engine.setKomi(7.0);
			engine.quit();
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

	public final void testSetTimeLeft() {
		try {
			engine.initialise();
			engine.setTimeLeft(BoardI.VERTEX_BLACK, 1.0, 1.0);
			engine.quit();
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
	}

	public final void testSetTimeSettings() {
		try {
			engine.initialise();
			engine.setTimeSettings(1.0, 1.0, 1.0);
			engine.quit();
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			fail();
		}
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

	public final void testUndo() {
		try {
			engine.initialise();
			boolean result = engine.undo();
			assertFalse(result);
			engine.quit();
		} catch (Throwable t) {
			System.err.println(t);
			t.printStackTrace();
			// TODO
			// fail(t.toString());
		}

	}

}
