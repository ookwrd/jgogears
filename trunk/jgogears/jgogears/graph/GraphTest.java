package jgogears.graph;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.util.*;

import jgogears.*;

public class GraphTest extends TestCase {

	static final boolean DEBUG = false;

	public void testGrapha7() {
		Board board = new Board(7);
		assertNotNull(board);
		assertTrue(board.getSize() == 7);
		Graph graph = new Graph(board);
		assertNotNull(graph);
	}

	public void testGrapha9() {
		Board board = new Board(9);
		assertNotNull(board);
		assertTrue(board.getSize() == 9);
		Graph graph = new Graph(board);
		assertNotNull(graph);
	}

	public void testGrapha13() {
		Board board = new Board(13);
		assertNotNull(board);
		assertTrue(board.getSize() == 13);
		Graph graph = new Graph(board);
		assertNotNull(graph);
	}

	public void testGrapha19() {
		Board board = new Board(19);
		assertNotNull(board);
		assertTrue(board.getSize() == 19);
		Graph graph = new Graph(board);
		assertNotNull(graph);
	}

	public void testGrapha23() {
		Board board = new Board(23);
		assertNotNull(board);
		assertTrue(board.getSize() == 23);
		Graph graph = new Graph(board);
		assertNotNull(graph);
	}

	public void testGraphloop() {
		for (int i = 1; i < 101; i++) {
			Date before = new Date();
			Board board = new Board(i);
			Graph graph = new Graph(board);
			Date after = new Date();

			long milli = after.getTime() - before.getTime();
			if (DEBUG)
				System.err.println("" + i + "/" + milli);

			assertNotNull(board);
			assertTrue(board.getSize() == i);
			assertNotNull(graph);
			assertTrue("" + i * i + "!="
					+ graph.getNodeset().first().getPoints().size(), graph
					.getNodeset().first().getPoints().size() == i * i);

			// make local copies
			Node[][] nodegrid = new Node[i][i];
			Point[][] pointgrid = new Point[i][i];
			for (int r = 0; r < i; r++) {
				for (int c = 0; c < i; c++) {
					nodegrid[r][c] = graph.getNodegrid()[r][c];
					pointgrid[r][c] = graph.getPointgrid()[r][c];
				}
			}

			// destruction test local copies
			for (int r = 0; r < i; r++) {
				for (int c = 0; c < i; c++) {
					assertNotNull(pointgrid[r][c]);
					assertTrue(pointgrid[r][c].getRow() == r);
					assertTrue(pointgrid[r][c].getColumn() == c);
					assertNotNull(nodegrid[r][c]);
					assertTrue(nodegrid[r][c].getPoints().contains(
							pointgrid[r][c]));
				}
			}
			Iterator<Node> nodes = graph.getNodeset().iterator();
			while (nodes.hasNext()) {
				Node node = nodes.next();
				Iterator<Point> points = node.getPoints().iterator();
				while (points.hasNext()) {
					Point point = points.next();
					pointgrid[point.getRow()][point.getColumn()] = null;
				}
			}
			for (int r = 0; r < i; r++) {
				for (int c = 0; c < i; c++) {
					assertNull(pointgrid[r][c]);
				}
			}
		}
	}

	public void testGraphc() throws IOException {

		Game game = Game.loadFromFile(new File("sgf/testing/seki.sgf"));
		Iterator<BoardI> i = game.getBoards();
		BoardI board = null;
		Graph g = null;
		while (i.hasNext()) {
			board = i.next();
			assertNotNull(board);
			g = new Graph(board);
			assertNotNull(g);
		}
	}

}
