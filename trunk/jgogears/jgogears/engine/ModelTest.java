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
	public Model trainNFiles(int count)throws IOException {
		Stack<String> files = new Stack<String>();
		files.push("sgf/2004-12");
		assertNotNull(files);

		int filecount = 0;
		int movecount = 0;
		Model model = new Model();
		assertNotNull(model);
		Date start = new Date();
		assertNotNull(start);

		while ((files.size() > 0) && (filecount < count)) {
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
				}
				} else {
					String[] children = file.list();
					for (int i = 0; i < children.length; i++) {
						files.push(filename + "/" + children[i]);
					}
				}
			}
		}
		if (DEBUG)
			System.err.println("loaded " + filecount + " files ");
		return model;
	}
	
	/**
	 * Test load all sgf files. TODO make this quciker. it takes far too long
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void testLoadAllSGFFiles() throws IOException {
		Model model = this.trainNFiles(10);

		BoardI board = new Board(19);
//		board = board.newBoard(new Move("white b2"));
//		board = board.newBoard(new Move("black b4"));
//		board = board.newBoard(new Move("white c3"));
//		board = board.newBoard(new Move("black c4"));
//		board = board.newBoard(new Move("white d4"));
//		board = board.newBoard(new Move("black a4"));
//		board = board.newBoard(new Move("white d2"));
		double[][] r = model.getScores(board, false);
		assertNotNull(r);
		if (DEBUG) {
			for (int i = 0; i < r.length; i++) {
				for (int j = 0; j < r[i].length; j++)
					System.err.print(" " + r[i][j]);
				System.err.println();
			}
		}
		double maxScore = Double.MIN_NORMAL;
		Move maxMove = null;
		for (int i = 0; i < r.length; i++) 
			for (int j = 0; j < r[i].length; j++)
				if (maxScore <= r[i][j]){
					maxScore = r[i][j];
					maxMove = new Move(i,j, Board.VERTEX_BLACK) ;
			}
				
			System.out.println("MaxScore = " + maxScore + ", " + maxMove);

	}

	/**
	 * check some stats on some files
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void testTreeWellFormedness() throws IOException {
		Model model = this.trainNFiles(10);
		// generate
		int nodeCount = 0;
		int leafCount = 0;
		int fullCount = 0;
		int countSum = 0;
		Stack<Node> nodes = new Stack<Node>();
		nodes.push(model.getRoot());
		while (nodes.size() > 0) {
			Node node = nodes.pop();
			if (node != null) {
				nodeCount++;
				assertTrue(node.getBlack() != node);
				assertTrue(node.getWhite() != node);
				assertTrue(node.getEmpty() != node);
				assertTrue(node.getOff() != node);
				assertTrue(node.getCount() >= 0);

				if (node.getBlack() != null) {
					assertTrue(node.getCount() >= 1);
					nodes.push(node.getBlack());
				}
				if (node.getWhite() != null) {
					assertTrue(node.getCount() >= 1);
					nodes.push(node.getWhite());
				}
				if (node.getEmpty() != null) {
					assertTrue(node.getCount() >= 1);
					nodes.push(node.getEmpty());
				}
				if (node.getOff() != null) {
					assertTrue(node.getCount() >= 1);
					nodes.push(node.getOff());
				}
				if (node.getBlack() != null && node.getWhite() != null && node.getEmpty() != null
						&& node.getOff() != null)
					fullCount++;
				if (node.getBlack() == null && node.getWhite() == null && node.getEmpty() == null
						&& node.getOff() == null)
					leafCount++;
				countSum += node.getCount();
			}
		}
		if (DEBUG)
			System.err.println("nodes=" + nodeCount + " leaves=" + leafCount + " full=" + fullCount + " countSum="
					+ countSum + " countAvg =" + countSum / nodeCount + " ");

	}

	/**
	 * check that there are no loops in the tree
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void testTreeWellFormednessII() throws IOException {
		Model model = this.trainNFiles(10);
		Stack<Node> nodes = new Stack<Node>();
		Set<Node> nodeSet = new TreeSet<Node>();
		nodes.push(model.getRoot());
		while (nodes.size() > 0) {
			Node node = nodes.pop();
			if (node != null) {
				assertFalse(nodeSet.contains(node));
				nodeSet.add(node);
			}
			if (node.getBlack() != null) {
				nodes.push(node.getBlack());
			}
			if (node.getWhite() != null) {
				nodes.push(node.getWhite());
			}
			if (node.getEmpty() != null) {
				nodes.push(node.getEmpty());
			}
			if (node.getOff() != null) {
				nodes.push(node.getOff());
			}

		}
	}

	/**
	 * Test random.
	 */
	public void testRandom() {
		double a = Model.getRandom();
		if (DEBUG)
			System.err.println("Random data = " + a);
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
