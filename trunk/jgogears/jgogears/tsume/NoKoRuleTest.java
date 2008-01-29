package jgogears.tsume;

import java.util.TreeSet;

import junit.framework.TestCase;
import jgogears.*;

public class NoKoRuleTest extends TestCase {

	public void testinherits() {
		NoKoRule rule = new NoKoRule();
		assertNotNull(rule);
		KoRule rule2 = rule;
		assertNotNull(rule2);
	}

	public void testStringEmptyBoard() {
		NoKoRule rule = new NoKoRule();
		short size = 7;
		GoBoard board = new GoBoard(size);

		for (short i = 0; i < size; i++)
			for (short j = 0; j < size; j++) {
				TreeSet<Vertex> string = rule.getString(i, j, board);
				assertNotNull(string);
				assertTrue("" + i + " / " + j + " / " + string.size() + " / "
						+ size * size + " (" + string + ")",
						string.size() == size * size);
			}
	}

	public void testStringOffBoard() {
		NoKoRule rule = new NoKoRule();
		short size = 7;
		GoBoard board = new GoBoard(size);

		TreeSet<Vertex> string = rule.getString((short) -1, (short) -1, board);
		assertNotNull(string);
		assertTrue(string.size() == 0);

		string = rule.getString((short) 0, (short) -1, board);
		assertNotNull(string);
		assertTrue(string.size() == 0);
		string = rule.getString((short) 15, (short) -1, board);
		assertNotNull(string);
		assertTrue(string.size() == 0);
		string = rule.getString((short) -15, (short) -15, board);
		assertNotNull(string);
		assertTrue(string.size() == 0);
	}

	public void testStringAlmostEmptyBoard() {
		NoKoRule rule = new NoKoRule();
		short size = 5;

		for (short i = 0; i < size; i++)
			for (short j = 0; j < size; j++)
				for (short k = 0; k < size; k++)
					for (short l = 0; l < size; l++) {
						GoBoard board = new GoBoard(size);
						assertNotNull(board);
						GoMove move = new GoMove(k, l, GoBoard.VERTEX_BLACK);
						assertNotNull(move);
						assertTrue(move.getPlay());
						assertTrue(move.getRow() == k);
						assertTrue(move.getColour() == GoBoard.VERTEX_BLACK);
						assertTrue(move.getColumn() == l);

						board = board.newBoard(move);
						assertNotNull(board);

						TreeSet<Vertex> string = rule.getString(i, j, board);
						assertNotNull(string);
						if (i != k || j != l)
							assertTrue("" + i + " / " + j + " / " + k + " / "
									+ l + " / " + string.size() + " / "
									+ (size * size - 1) + " (" + string + ")",
									string.size() == size * size - 1);
						else
							assertTrue("" + i + " / " + j + " / " + k + " / "
									+ l + " / " + string.size() + " / " + size
									* size + " (" + string + ")",
									string.size() == 1);

					}
	}

	public void testStringpair() {
		NoKoRule rule = new NoKoRule();
		short size = 7;

		GoBoard board = new GoBoard(size);
		assertNotNull(board);
		GoMove move = new GoMove(1, 1, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(1, 2, GoBoard.VERTEX_WHITE);
		board = board.newBoard(move);

		TreeSet<Vertex> string = rule.getString(1, 1, board);
		assertTrue(string.size() == 1);
		string = rule.getString(1, 2, board);
		assertTrue(string.size() == 1);
		string = rule.getString(0, 0, board);
		assertTrue(string.size() == size * size - 2);

	}

	public void testStringpairsame() {
		NoKoRule rule = new NoKoRule();
		short size = 7;

		GoBoard board = new GoBoard(size);
		assertNotNull(board);
		GoMove move = new GoMove(1, 1, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(2, 2, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);

		TreeSet<Vertex> string = rule.getString(1, 1, board);
		assertTrue(string.size() == 1);
		string = rule.getString(2, 2, board);
		assertTrue(string.size() == 1);
		string = rule.getString(0, 0, board);
		assertTrue(string.size() == size * size - 2);

	}

	public void testStringpairnext() {
		NoKoRule rule = new NoKoRule();
		short size = 7;

		GoBoard board = new GoBoard(size);
		assertNotNull(board);
		GoMove move = new GoMove(1, 1, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(1, 2, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);

		TreeSet<Vertex> string = rule.getString(1, 1, board);
		assertTrue(string.size() == 2);
		string = rule.getString(1, 2, board);
		assertTrue(string.size() == 2);
		string = rule.getString(0, 0, board);
		assertTrue(string.size() == size * size - 2);

	}

	public void testStringrow() {
		NoKoRule rule = new NoKoRule();
		short size = 7;

		GoBoard board = new GoBoard(size);
		assertNotNull(board);
		GoMove move = new GoMove(1, 0, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(1, 1, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(1, 2, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(1, 3, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(1, 4, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(1, 5, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(1, 6, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);

		TreeSet<Vertex> string = rule.getString(1, 1, board);
		assertTrue(string.size() == 7);
		string = rule.getString(1, 0, board);
		assertTrue(string.size() == 7);
		string = rule.getString(0, 0, board);
		assertTrue(string.size() == 7);
		string = rule.getString(6, 6, board);
		assertTrue(string.size() == size * (size - 2));

	}

	public void testLibertiesAlmostEmptyBoard() {
		NoKoRule rule = new NoKoRule();
		short size = 13;

		for (short k = 0; k < size; k++)
			for (short l = 0; l < size; l++) {
				GoBoard board = new GoBoard(size);
				assertNotNull(board);
				GoMove move = new GoMove(k, l, GoBoard.VERTEX_BLACK);
				assertNotNull(move);
				assertTrue(move.getPlay());
				assertTrue(move.getRow() == k);
				assertTrue(move.getColour() == GoBoard.VERTEX_BLACK);
				assertTrue(move.getColumn() == l);

				board = board.newBoard(move);
				assertNotNull(board);

				TreeSet<Vertex> string = rule.getString(k, l, board);
				assertNotNull(string);
				assertTrue(string.size() == 1);
				TreeSet<Vertex> liberties = rule.getLiberties(k, l, board);
				assertNotNull(liberties);
				short count = rule.countLiberties(k, l, board);
				assertNotNull(count == liberties.size());
				if (k == 0 || k == size - 1)
					if (l == 0 || l == size - 1) {
						assertTrue(liberties.size() == 2);
					} else {
						assertTrue(liberties.size() == 3);
					}
				else if (l == 0 || l == size - 1) {
					assertTrue(liberties.size() == 3);
				} else {
					assertTrue(liberties.size() == 4);
				}

			}
	}

	public void testLibertiesrow() {
		NoKoRule rule = new NoKoRule();
		short size = 7;

		GoBoard board = new GoBoard(size);
		assertNotNull(board);
		GoMove move = new GoMove(1, 0, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(1, 1, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(1, 2, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(1, 3, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(1, 4, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(1, 5, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(1, 6, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);

		TreeSet<Vertex> libs = rule.getLiberties(1, 1, board);
		assertTrue(libs.size() == 14);
		libs = rule.getLiberties(1, 0, board);
		assertTrue(libs.size() == 14);
	}

	public void testLibertiesCross() {
		NoKoRule rule = new NoKoRule();
		short size = 7;

		GoBoard board = new GoBoard(size);
		assertNotNull(board);
		GoMove move = new GoMove(3, 1, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(3, 2, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(3, 4, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(3, 5, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(1, 3, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(2, 3, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(4, 3, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);
		move = new GoMove(5, 3, GoBoard.VERTEX_BLACK);
		board = board.newBoard(move);

		TreeSet<Vertex> libs = rule.getString(3, 3, board);
		assertTrue(libs.size() == 1);
		libs = rule.getLiberties(4, 3, board);
		assertTrue(libs.size() == 6);
		libs = rule.getLiberties(3, 4, board);
		assertTrue(libs.size() == 6);
	}

}
