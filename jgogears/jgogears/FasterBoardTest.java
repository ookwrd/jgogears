package jgogears;

import java.io.*;
import java.util.*;

import junit.framework.TestCase;

public class FasterBoardTest extends TestCase {

	public void testAllSizes() {
		this.testAllVertexesN(3);
		this.testAllVertexesN(6);
		this.testAllVertexesN(7);
		this.testAllVertexesN(8);
		this.testAllVertexesN(9);
		this.testAllVertexesN(10);
		this.testAllVertexesN(11);
		this.testAllVertexesN(12);
		this.testAllVertexesN(13);
		this.testAllVertexesN(14);
		this.testAllVertexesN(15);
		this.testAllVertexesN(16);
		this.testAllVertexesN(17);
		this.testAllVertexesN(18);
		this.testAllVertexesN(19);
		this.testAllVertexesN(20);
		this.testAllVertexesN(21);
		this.testAllVertexesN(22);
	}

	public void testAllVertexesN(int size) {
		BoardI board = new FasterBoard(size);

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Move move = new Move(i, j, BoardI.VERTEX_BLACK);
				board = board.newBoard(move);
				assertTrue("" + size + "," + i + "," + j + "," + board.getColour(i, j) + "," + BoardI.VERTEX_BLACK,
						board.getColour(i, j) == BoardI.VERTEX_BLACK);

				for (int l = 0; l < size; l++) {
					for (int m = 0; m < size; m++) {
						if ((l != i) && (m != j)) {

							assertTrue("" + size + "," + i + "," + j + "," + l + "," + m + "," + board.getColour(l, m)
									+ "," + BoardI.VERTEX_EMPTY, board.getColour(l, m) == BoardI.VERTEX_EMPTY);
							assertTrue(board.getColour(l, m) == BoardI.VERTEX_EMPTY);
							assertTrue(BoardI.VERTEX_EMPTY == BoardI.VERTEX_EMPTY);
							assertTrue(board.getColour(l, m) == board.getColour(l, m));
							assertTrue(board.getColour(l, m) == BoardI.VERTEX_EMPTY);
						}
					}
				}
				move = new Move(i, j, BoardI.VERTEX_EMPTY);
				board = board.newBoard(move);
				assertTrue(board.getColour(i, j) == BoardI.VERTEX_EMPTY);

				for (int l = 0; l < size; l++) {
					for (int m = 0; m < size; m++) {
						if ((l != i) && (m != j)) {
							assertTrue("" + size + "," + i + "," + j + "," + l + "," + m + "," + board.getColour(l, m)
									+ ",", board.getColour(i, j) == BoardI.VERTEX_EMPTY);
						}
					}
				}
				move = new Move(i, j, BoardI.VERTEX_WHITE);
				board = board.newBoard(move);
				assertTrue(board.getColour(i, j) == BoardI.VERTEX_WHITE);

				for (int l = 0; l < size; l++) {
					for (int m = 0; m < size; m++) {
						if ((l != i) && (m != j)) {
							assertTrue("" + size + "," + i + "," + j + "," + l + "," + m + "," + board.getColour(l, m)
									+ ",", board.getColour(l, m) == BoardI.VERTEX_EMPTY);
						}
					}
				}
				move = new Move(i, j, BoardI.VERTEX_KO);
				board = board.newBoard(move);
				assertTrue(board.getColour(i, j) == BoardI.VERTEX_KO);

				for (int l = 0; l < size; l++) {
					for (int m = 0; m < size; m++) {
						if ((l != i) && (m != j)) {
							assertTrue("" + size + "," + i + "," + j + "," + l + "," + m + "," + board.getColour(l, m)
									+ ",", board.getColour(l, m) == BoardI.VERTEX_EMPTY);
						}
					}
				}
				move = new Move(i, j, BoardI.VERTEX_EMPTY);
				board = board.newBoard(move);
				assertTrue(board.getColour(i, j) == BoardI.VERTEX_EMPTY);

				for (int l = 0; l < size; l++) {
					for (int m = 0; m < size; m++) {
						if ((l != i) && (m != j)) {
							assertTrue("" + size + "," + i + "," + j + "," + l + "," + m + "," + board.getColour(l, m)
									+ ",", board.getColour(l, m) == BoardI.VERTEX_EMPTY);
						}
					}
				}
			}
		}
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
					BoardI board = new FasterBoard(game.getSize());
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

				} else {
					System.err.println("\"" + filename + "\" is a directory");
					if (!file.getName().contains(".svn")) {
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

	public void testLoadSimpleGnugo() throws IOException {

		Game game = Game.loadFromFile(new File("sgf/testing/simpleGnuGo.sgf"));
		Iterator<Move> i = game.getMoves();
		Move move = null;
		BoardI board = new FasterBoard(game.getSize());
		while (i.hasNext()) {
			move = i.next();
			assertNotNull(move);
			board = board.newBoard(move);
		}
		// System.err.println(g);
	}

	public void testSize10() {
		this.testAllVertexesN(10);
	}

	public void testSize11() {
		this.testAllVertexesN(11);
	}

	public void testSize12() {
		this.testAllVertexesN(12);
	}

	public void testSize13() {
		this.testAllVertexesN(13);
	}

	public void testSize14() {
		this.testAllVertexesN(14);
	}

	public void testSize16() {
		this.testAllVertexesN(16);
	}

	public void testSize17() {
		this.testAllVertexesN(17);
	}

	public void testSize18() {
		this.testAllVertexesN(18);
	}

	public void testSize19() {
		this.testAllVertexesN(19);
	}

	public void testSize20() {
		this.testAllVertexesN(20);
	}

	public void testSize21() {
		this.testAllVertexesN(21);
	}

	public void testSize22() {
		this.testAllVertexesN(22);
	}

	public void testSize23() {
		this.testAllVertexesN(23);
	}

	public void testSize24() {
		this.testAllVertexesN(24);
	}

	public void testSize25() {
		this.testAllVertexesN(25);
	}

	public void testSize3() {
		this.testAllVertexesN(3);
	}

	public void testSize4() {
		this.testAllVertexesN(4);
	}

	public void testSize5() {
		this.testAllVertexesN(5);
	}

	public void testSize6() {
		this.testAllVertexesN(6);
	}

	public void testSize7() {
		this.testAllVertexesN(7);
	}

	public void testSize8() {
		this.testAllVertexesN(8);
	}

	public void testSize9() {
		this.testAllVertexesN(9);
	}

	public void testToString() {
		BoardI working = new FasterBoard((short) 19);
		assertNotNull(working);
		working = working.newBoard(new Move("B q10"));
		System.out.println(working);
	}

}
