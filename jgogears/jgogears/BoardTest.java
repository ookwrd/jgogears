package jgogears;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

import jgogears.graph.Graph;

import junit.framework.TestCase;

public class BoardTest extends TestCase {
	public void testAllSizes() {
		testAllVertexesN(3);
		testAllVertexesN(6);
		testAllVertexesN(7);
		testAllVertexesN(8);
		testAllVertexesN(9);
		testAllVertexesN(10);
		testAllVertexesN(11);
		testAllVertexesN(12);
		testAllVertexesN(13);
		testAllVertexesN(14);
		testAllVertexesN(15);
		testAllVertexesN(16);
		testAllVertexesN(17);
		testAllVertexesN(18);
		testAllVertexesN(19);
		testAllVertexesN(20);
		testAllVertexesN(21);
		testAllVertexesN(22);
	}

	public void testAllVertexesN(int size) {
		BoardI board = new Board(size);

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Move move = new Move(i, j, Board.VERTEX_BLACK);
				board = board.newBoard(move);
				assertTrue(board.getColour(i, j) == Board.VERTEX_BLACK);

				for (int l = 0; l < size; l++) {
					for (int m = 0; m < size; m++) {
						if (l != i && m != j)
							assertTrue(board.getColour(l, m) == Board.VERTEX_EMPTY);
					}
				}
				move = new Move(i, j, Board.VERTEX_EMPTY);
				board = board.newBoard(move);
				assertTrue(board.getColour(i, j) == Board.VERTEX_EMPTY);

				for (int l = 0; l < size; l++) {
					for (int m = 0; m < size; m++) {
						if (l != i && m != j)
							assertTrue(board.getColour(i, j) == Board.VERTEX_EMPTY);
					}
				}
				move = new Move(i, j, Board.VERTEX_WHITE);
				board = board.newBoard(move);
				assertTrue(board.getColour(i, j) == Board.VERTEX_WHITE);

				for (int l = 0; l < size; l++) {
					for (int m = 0; m < size; m++) {
						if (l != i && m != j)
							assertTrue(board.getColour(l, m) == Board.VERTEX_EMPTY);
					}
				}
				move = new Move(i, j, Board.VERTEX_KO);
				board = board.newBoard(move);
				assertTrue(board.getColour(i, j) == Board.VERTEX_KO);

				for (int l = 0; l < size; l++) {
					for (int m = 0; m < size; m++) {
						if (l != i && m != j)
							assertTrue(board.getColour(l, m) == Board.VERTEX_EMPTY);
					}
				}
				move = new Move(i, j, Board.VERTEX_EMPTY);
				board = board.newBoard(move);
				assertTrue(board.getColour(i, j) == Board.VERTEX_EMPTY);

				for (int l = 0; l < size; l++) {
					for (int m = 0; m < size; m++) {
						if (l != i && m != j)
							assertTrue(board.getColour(l, m) == Board.VERTEX_EMPTY);
					}
				}
			}
		}
	}

	public void testToString() {
		BoardI working = new Board((short) 19);
		assertNotNull(working);
		working = working.newBoard(new Move("B q10"));
		// System.out.println(working);
	}

	public void testLoadSimpleGnugo() throws IOException {

		Game game = Game.loadFromFile(new File("sgf/testing/simpleGnuGo.sgf"));
		Iterator<Move> i = game.getMoves();
		Move move = null;
		BoardI board = new Board(game.getSize());
		while (i.hasNext()) {
			move = i.next();
			assertNotNull(move);
			board = board.newBoard(move);
		}
		// System.err.println(g);
	}

	public void testLoadAllSGFfiles() throws IOException {
		Stack<String> files = new Stack<String>();
		files.push("sgf/2004-12");

		while (files.size() > 0) {
			String filename = files.pop();
			File file = new File(filename);
			System.err.println("examining \"" + filename + "\"");
			if (file.exists()) {
				if (!file.isDirectory()) {
					// System.err.println("\"" + filename + "\" is not a
					// directory, parsing as an SGF file");

					Game game = Game.loadFromFile(file);
					Iterator<Move> i = game.getMoves();
					Move move = null;
					BoardI board = new Board(game.getSize());
					// System.err.println("board size is: \"" + goGame.getSize()
					// + "\"");
					while (i.hasNext()) {
						move = i.next();
						assertNotNull(move);
						// System.err.print("move: \"" + move + "\"");
						// assertTrue("" + board + "\n" +
						// move.toString(),board.isLegalMove(move));
						board = board.newBoard(move);
						// System.err.println(" board size is: \"" +
						// board.getSize() + "\"");
					}
					// System.err.println();

					// TODO allow us to actually read all the files
					return;

				} else {
					System.err.println("\"" + filename + "\" is a directory");
					String[] children = file.list();
					for (int i = 0; i < children.length; i++) {
						// System.err.println("pushing \"" + children[i] +
						// "\"");
						files.push(filename + "/" + children[i]);
					}
				}
			}
		}

	}

}
