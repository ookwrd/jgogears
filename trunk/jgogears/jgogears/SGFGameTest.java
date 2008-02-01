package jgogears;

import java.io.StringReader;

import junit.framework.TestCase;
import java.util.*;
import java.io.*;

import jgogears.SGF.ParseException;

public class SGFGameTest extends TestCase {

	public void testLoad() throws IOException {
		try {
			FileReader reader = new FileReader(
					"sgf/testing/2007-01-12- BamboField- tokabe.sgf");
			jgogears.SGF.SGF parser = new jgogears.SGF.SGF(reader);
			assertNotNull(parser);
			SGFGameTree tree = parser.gameTree();
			assertNotNull(tree);
			Game game = new Game(tree);
			assertNotNull(game);
			Iterator<Move> moves = game.getMoves();
			assertNotNull(moves);
			assertTrue(moves.hasNext());
			while (moves.hasNext()) {
				Move move = moves.next();
				assertNotNull(move);
				System.err.print(move);

			}
			System.err.println();

		} catch (IOException e) {
			System.err.println(e);
			e.printStackTrace();
			fail("throw an error");
		} catch (ParseException e) {
			System.err.println(e);
			e.printStackTrace();
			fail("throw an error");
		}

	}

	/*
	 * Test method for 'jgogears.Sufgo.isTrue(boolean)'
	 */
	public void testIsVerbose() throws ParseException {

		String example = SGFParser.EXAMPLEB;
		StringReader reader = new StringReader(example);
		jgogears.SGF.SGF parser = new jgogears.SGF.SGF(reader);

		Game game = new Game(parser.gameTree());

		Iterator<Move> iterator = game.getMoves();
		while (iterator.hasNext()) {
			iterator.next();
			// System.out.println(iterator.next());
		}
		Iterator<Board> iterator2 = game.getBoards();
		while (iterator.hasNext()) {
			iterator2.next();
			// System.out.println(iterator.next());
		}

	}
}
