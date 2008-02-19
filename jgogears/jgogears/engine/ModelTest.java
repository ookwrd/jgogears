package jgogears.engine;

import java.io.*;
import java.util.*;

import jgogears.*;
import junit.framework.TestCase;

// TODO: Auto-generated Javadoc
/**
 * The Class ModelTest.
 */
public class ModelTest extends TestCase {

	/** are we boing verbose debugging */
	public static final boolean DEBUG = true;

	/**
	 * Load test game.
	 * 
	 * @return the game
	 */
	public Game loadTestGame() {
		try {
			FileReader reader = new FileReader("sgf/testing/2007-01-12- BamboField- tokabe.sgf");
			jgogears.SGF.SGF parser = new jgogears.SGF.SGF(reader);
			SGFGameTree tree = parser.gameTree();
			Game game = new Game(tree);
			return game;
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}

	}

	/**
	 * Test load all sgf files.
	 * TODO make this quciker. it takes far too long
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void testLoadAllSGFFiles() throws IOException {
		Stack<String> files = new Stack<String>();
		files.push("sgf/2004-12");
		assertNotNull(files);
		
		int filecount = 0;
		int movecount = 0;
		Model model = new Model();
		assertNotNull(model);
		Date start = new Date();
		assertNotNull(start);

		while ((files.size() > 0) && (filecount < 10)) {
			String filename = files.pop();
			File file = new File(filename);
			if (file.exists()) {
				if (!file.isDirectory()) {

					Date before = new Date();
					assertNotNull(before);

					Game game = Game.loadFromFile(file);
					if (game.getSize() == 19) {
						filecount++;
						model.train(game);

						Date after = new Date();
						if (DEBUG)
							System.err.println("loaded " + filecount + " files with " + movecount + " moves in "
									+ (after.getTime() - before.getTime()) + " milliseconds,  model size =  "
									+ model.getRoot().size());
					}

				} else {
					String[] children = file.list();
					for (int i = 0; i < children.length; i++) {
						files.push(filename + "/" + children[i]);
					}
				}
			}

		}
		Date after = new Date();
		if (DEBUG)
			System.err.println("loaded " + filecount + " files with " + movecount + " moves in "
					+ (after.getTime() - start.getTime()) + " milliseconds");
		BoardI board = new Board(19);
		board = board.newBoard(new Move("white b2"));
		board = board.newBoard(new Move("black b4"));
		board = board.newBoard(new Move("white c3"));
		board = board.newBoard(new Move("black c4"));
		board = board.newBoard(new Move("white d4"));
		board = board.newBoard(new Move("black a4"));
		board = board.newBoard(new Move("white d2"));
		double[][] r =  model.getScores(board, false);
		assertNotNull(r);
		if (DEBUG) {
			for (int i = 0; i < r.length; i++) {
				for (int j = 0; j < r[i].length; j++)
					System.out.print(" " + r[i][j]);
				System.out.println();
			}
		}

	}

	/**
	 * Test random.
	 */
	public void testRandom() {
		double a = Model.getRandom();
		if (DEBUG)System.err.println("Random data = " + a);
	}

	/**
	 * Test random delta.
	 */
	public void testRandomDelta() {
		double a = Model.getRandomDelta();
		System.err.println("Random delta = " + a);
	}

	/**
	 * Test trained model.
	 */
	public void testTrainedModel() {
		Model model = new Model();
		assertNotNull(model);
		System.out.println(" model size =  " + model.getRoot().size());
		model.train(this.loadTestGame());
		System.out.println(" model size =  " + model.getRoot().size());
		BoardI board = new Board();
		board = board.newBoard(new Move("white b2"));
		board = board.newBoard(new Move("black k4"));
		board = board.newBoard(new Move("white c3"));
		board = board.newBoard(new Move("black g4"));
		board = board.newBoard(new Move("white d4"));
		board = board.newBoard(new Move("black h4"));
		board = board.newBoard(new Move("white n4"));
		double[][] r = model.getScores(board, false);
		assertNotNull(r);
		if (DEBUG) {
			for (int i = 0; i < r.length; i++) {
				for (int j = 0; j < r[i].length; j++)
					System.out.print(" " + r[i][j]);
				System.out.println();
			}
		}
	}

	/**
	 * Test trivial.
	 */
	public void testTrivial() {
		Model model = new Model();
		assertNotNull(model);
	}

}
