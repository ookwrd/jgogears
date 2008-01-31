package jgogears;

import jgogears.SGF.ParseException;
import junit.framework.TestCase;

public class GoMoveTest extends TestCase {

	public void testGTPConstructor() throws ParseException {
		assertTrue(new GoMove("w ReSiGn").getResign() == true);
		assertTrue(new GoMove("w resign").getColour() == BoardI.VERTEX_WHITE);
		assertNotNull(new GoMove("w PASS"));
		assertNotNull(new GoMove("w pass"));
		assertTrue(new GoMove("w b2").getColumn() == 1);
		assertTrue(new GoMove("white b2").getRow() == 1);

		assertTrue(new GoMove("white t19").getRow() == 18);
		assertTrue(new GoMove("white t19").getColumn() == 18);
		assertTrue(new GoMove("white t19").getColour() == BoardI.VERTEX_WHITE);
		assertTrue(new GoMove("white t19").getResign() == false);
		assertTrue(new GoMove("white t19").getPass() == false);

		assertTrue(new GoMove("black t19").getRow() == 18);
		assertTrue(new GoMove("black t19").getColumn() == 18);
		assertTrue(new GoMove("black t19").getColour() == BoardI.VERTEX_BLACK);
		assertTrue(new GoMove("black t19").getResign() == false);
		assertTrue(new GoMove("black t19").getPass() == false);

		assertTrue(("" + new GoMove("black q10").getRow()), new GoMove(
				"black q10").getRow() == 15);
		assertTrue(("" + new GoMove("black q10").getColumn()), new GoMove(
				"black q10").getColumn() == 9);
		assertTrue(new GoMove("black q10").getColour() == BoardI.VERTEX_BLACK);
		assertTrue(new GoMove("black q10").getResign() == false);
		assertTrue(new GoMove("black q10").getPass() == false);

		assertTrue(new GoMove("white pass").getResign() == false);
		assertTrue(new GoMove("white pass").getPass() == true);

		assertTrue(new GoMove("white resign").getResign() == true);
		assertTrue(new GoMove("white resign").getPass() == false);
	}

	/*
	 * Test method
	 */
	public void testStringConversions() throws ParseException {
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				GoMove move = new GoMove(i, j, BoardI.VERTEX_WHITE);
				assertNotNull(move);
				assertTrue("" + i + " " + j, move.getRow() == i);
				assertTrue("" + i + " " + j, move.getColumn() == j);
				assertTrue(move.getColour() == BoardI.VERTEX_WHITE);
				GoMove move2 = new GoMove(move.toString());
				assertNotNull(move2);
				assertTrue("" + i + " " + j + " " + move + " " + move2, move2
						.getRow() == i);
				assertTrue("" + i + " " + j + " " + move + " " + move2, move2
						.getColumn() == j);
				assertTrue(move2.getColour() == BoardI.VERTEX_WHITE);
			}
		}
	}

	/*
	 * Test method
	 */
	public void testToString() throws ParseException {
		System.err.println(new GoMove(0, 0, BoardI.VERTEX_WHITE));
		assertTrue(new GoMove(0, 0, BoardI.VERTEX_WHITE).toString()
				.compareTo("white a1") == 0);
		assertTrue(new GoMove(18, 18, BoardI.VERTEX_WHITE).toString()
				.compareTo("white t19") == 0);
	}

	/*
	 * Test method
	 */
	public void testBad() throws ParseException {
		try {
			new GoMove("green b1");
			fail();
		} catch (Throwable t) {
		}
		try {
			new GoMove("white !3");
			fail();
		} catch (Throwable t) {
		}
		try {
			new GoMove("white");
			fail();
		} catch (Throwable t) {
		}
		try {
			new GoMove("b2");
			fail();
		} catch (Throwable t) {
		}
		try {
			new GoMove("");
			fail();
		} catch (Throwable t) {
		}
		try {
			new GoMove("ZERBA 56");
			fail();
		} catch (Throwable t) {
		}
		try {
			new GoMove("help");
			fail();
		} catch (Throwable t) {
		}
	}

}
