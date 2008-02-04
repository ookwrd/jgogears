package jgogears;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

import jgogears.*;
import jgogears.graph.Graph;

import junit.framework.TestCase;

public class FastBoardTest extends TestCase {

	public void testToString() {
		BoardI working = new FastBoard((short) 19);
		assertNotNull(working);
		working = working.newBoard(new Move("B q10"));
		System.out.println(working);
	}

	public void testSize3() {
		testAllVertexesN(3);
	}

	public void testSize4() {
		testAllVertexesN(4);
	}

	public void testSize5() {
		testAllVertexesN(5);
	}

	public void testSize6() {
		testAllVertexesN(6);
	}

	public void testSize7() {
		testAllVertexesN(7);
	}

	public void testSize8() {
		testAllVertexesN(8);
	}

	public void testSize9() {
		testAllVertexesN(9);
	}

	public void testSize10() {
		testAllVertexesN(10);
	}

	public void testSize11() {
		testAllVertexesN(11);
	}

	public void testSize12() {
		testAllVertexesN(12);
	}

	public void testSize13() {
		testAllVertexesN(13);
	}

	public void testSize14() {
		testAllVertexesN(14);
	}

	public void testSize16() {
		testAllVertexesN(16);
	}

	public void testSize17() {
		testAllVertexesN(17);
	}

	public void testSize18() {
		testAllVertexesN(18);
	}

	public void testSize19() {
		testAllVertexesN(19);
	}

	public void testSize20() {
		testAllVertexesN(20);
	}

	public void testSize21() {
		testAllVertexesN(21);
	}

	public void testSize22() {
		testAllVertexesN(22);
	}

	public void testSize23() {
		testAllVertexesN(23);
	}

	public void testSize24() {
		testAllVertexesN(24);
	}

	public void testSize25() {
		testAllVertexesN(25);
	}

	public void testAllVertexesN(int size) {
		BoardI board = new FastBoard(size);

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Move move = new Move(i, j, FastBoard.VERTEX_BLACK);
				board = board.newBoard(move);
				assertTrue(board.getColour(i, j) == FastBoard.VERTEX_BLACK);

				for (int l = 0; l < size; l++) {
					for (int m = 0; m < size; m++) {
						if (l != i && m != j) {
							assertTrue(board.getColour(l, m) == FastBoard.VERTEX_EMPTY);
							assertTrue(FastBoard.VERTEX_EMPTY == FastBoard.VERTEX_EMPTY);
							assertTrue(board.getColour(l, m) == board
									.getColour(l, m));
							assertTrue(board.getColour(l, m) == FastBoard.VERTEX_EMPTY);
							// assertTrue(board.getColour(l, m) ==
							// FastBoard.VERTEX_EMPTY);
							assertTrue(
									"" + size + "," + i + "," + j + "," + l
											+ "," + m + ","
											+ board.getColour(l, m) + ","
											+ FasterBoard.VERTEX_EMPTY,
									board.getColour(l, m) == FasterBoard.VERTEX_EMPTY);
						}
					}
				}
				move = new Move(i, j, FastBoard.VERTEX_EMPTY);
				board = board.newBoard(move);
				assertTrue(board.getColour(i, j) == FastBoard.VERTEX_EMPTY);

				for (int l = 0; l < size; l++) {
					for (int m = 0; m < size; m++) {
						if (l != i && m != j) {
							assertTrue(board.getColour(i, j) == FastBoard.VERTEX_EMPTY);
						}
					}
				}
				move = new Move(i, j, FastBoard.VERTEX_WHITE);
				board = board.newBoard(move);
				assertTrue(board.getColour(i, j) == FastBoard.VERTEX_WHITE);

				for (int l = 0; l < size; l++) {
					for (int m = 0; m < size; m++) {
						if (l != i && m != j) {
							assertTrue(board.getColour(l, m) == FastBoard.VERTEX_EMPTY);
						}
					}
				}
				move = new Move(i, j, FastBoard.VERTEX_KO);
				board = board.newBoard(move);
				assertTrue(board.getColour(i, j) == FastBoard.VERTEX_KO);

				for (int l = 0; l < size; l++) {
					for (int m = 0; m < size; m++) {
						if (l != i && m != j) {
							assertTrue(board.getColour(l, m) == FastBoard.VERTEX_EMPTY);
						}
					}
				}
				move = new Move(i, j, FastBoard.VERTEX_EMPTY);
				board = board.newBoard(move);
				assertTrue(board.getColour(i, j) == FastBoard.VERTEX_EMPTY);

				for (int l = 0; l < size; l++) {
					for (int m = 0; m < size; m++) {
						if (l != i && m != j) {
							assertTrue(board.getColour(l, m) == FastBoard.VERTEX_EMPTY);
						}
					}
				}
			}
		}
	}

	public void testLoadSimpleGnugo() throws IOException {

		Game game = Game.loadFromFile(new File("sgf/testing/simpleGnuGo.sgf"));
		Iterator<Move> i = game.getMoves();
		Move move = null;
		BoardI board = new FastBoard(game.getSize());
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
					BoardI board = new FastBoard(game.getSize());
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
