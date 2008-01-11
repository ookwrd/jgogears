package jgogears.engine;

import java.io.*;
import java.util.*;

import jgogears.GoBoard;
import jgogears.GoGame;
import jgogears.SGFGameTree;
import jgogears.SGFParser;


import junit.framework.TestCase;

public class VertexLineariserTest extends TestCase {

	/*
	 * Test method for 'jgogears.SGFNode.columnFromMoveString(String)'
	 */
	public void testEverything() throws Exception {

		String examples[] = {
				// SGFParser.EXAMPLEONE,
				SGFParser.EXAMPLETWO, SGFParser.EXAMPLETHREE,
				SGFParser.EXAMPLEFOUR, SGFParser.EXAMPLEFIVE };

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

			GoGame goGame = new GoGame(tree);
			assertTrue(goGame != null);

			Iterator<GoBoard> iterator = goGame.getBoards();
			assertTrue(iterator != null);

			GoBoard board = null;
			while (iterator.hasNext()) {
				board = iterator.next();
				assertTrue(board != null);
				for (short j = 0; j < 8; j++) {
					Iterator<Short> linear = new VertexLineariser(board,
							(short) 2, (short) 2, j);
					assertTrue(linear != null);
					while (linear.hasNext()) {
						Short s = linear.next();
						assertTrue(s != null);
						//System.out.print(" " + s + ", ");
					}
				}
				 //System.out.println();
			}

		}

	}

}
