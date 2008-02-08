package jgogears.engine;

import java.util.*;

import jgogears.*;

/**
 * A probability model of Go
 */
public final class Model {

	/** A small value used as a small increment to probabilities */
	public static float DELTA = (float) 0.0001;

	/** A value used for == analysis of doubles */
	public static float TINY = (float) 0.0000001;

	/** are we being verbose? */
	public static boolean DEBUG = false;

	/** The random number generator */
	private static Random random = new Random();

	/**
	 * Gets the random.
	 * 
	 * @return the random
	 */
	static public double getRandom() {
		double r = random.nextDouble();
		return r;
	}

	/**
	 * Gets the random delta.
	 * 
	 * @return the random delta
	 */
	static public double getRandomDelta() {
		double r = random.nextDouble();
		return r * DELTA;
	}

	/**
	 * Max of two floats
	 * 
	 * @param a
	 *            the first double
	 * @param b
	 *            the second double
	 * @return the float
	 */
	public final static float max(float a, float b) {
		if (a >= b)
			return a;
		else
			return b;
	}

	/**
	 * Max of two doubles, biased towards the first in teh case of equality
	 * 
	 * @param a
	 *            the first double
	 * @param b
	 *            the second double
	 * @return the float
	 */
	public final static double max(double a, double b) {
		if (a >= b)
			return a;
		else
			return b;
	}

	/** The root of the model tree */
	private final Node root = new Node();

	/**
	 * Instantiates a new model.
	 */
	public Model() {
	}

	/**
	 * Recursively count the nodes in a model
	 * 
	 * @param node
	 *            the node
	 * @return the long
	 */
	public long countNodes(Node node) {
		long count = 1;
		if (node.black != null)
			count = count + this.countNodes(node.black);
		if (node.white != null)
			count = count + this.countNodes(node.white);
		if (node.empty != null)
			count = count + this.countNodes(node.empty);
		return count;
	}

	/**
	 * Gets the root.
	 * 
	 * @return the root
	 */
	public Node getRoot() {
		return this.root;
	}

	/**
	 * Gets the score for a situation
	 *  
	 * @param board
	 *            the board
	 * @param colour
	 *            the colour
	 * @param row
	 *            the row
	 * @param column
	 *            the column
	 * @param sym
	 *            the sym
	 * @return the score
	 */
	 float getScore(BoardI board, short colour, short row, short column, short sym) {
		VertexLineariser linear = new VertexLineariser(board, row, column, sym);
		if (!linear.hasNext())
			throw new Error();
		Node current = this.root;
		int counter = 1;
		float result = DELTA;
		while (linear.hasNext()) {
			if (DEBUG)
				System.out.println("result = " + result + " current.score = " + current.score);
			result = (float) (java.lang.Math.sqrt(result) + current.score);
			counter++;
			Short c = linear.next();
			switch (c) {
			case BoardI.VERTEX_BLACK:
				if (current.black == null)
					return result;
				else
					current = current.black;
				break;
			case BoardI.VERTEX_WHITE:
				if (current.white == null)
					return result;
				else
					current = current.white;
				break;
			case BoardI.VERTEX_OFF_BOARD:
				if (current.off == null)
					return result;
				else
					current = current.off;
				break;
			case BoardI.VERTEX_KO:
			case BoardI.VERTEX_EMPTY:
				if (current.empty == null)
					return result;
				else
					current = current.empty;
				break;
			default:
				throw new Error();
			}
		}
		return result;
	}

	/**
	 * Gets the score for every vertex on the board.
	 * 
	 * @param board
	 *            the board
	 * @param colour
	 *            the colour
	 * @return the scores
	 */
	public float[][] getScores(BoardI board, short colour) {
		float[][] result = new float[board.getSize()][board.getSize()];
		for (short i = 0; i < board.getSize(); i++)
			for (short j = 0; j < board.getSize(); j++) {
				result[i][j] = -DELTA;
				for (short sym = 0; sym < 8; sym++) {
					float tmp = this.getScore(board, colour, i, j, sym);
					result[i][j] = max(result[i][j], tmp);
				}
			}
		return result;
	}

	/**
	 * Train.
	 * 
	 * @param game
	 *            the game
	 */
	public void train(Game game) {
		short size = game.getSize();
		Iterator<BoardI> boards = game.getBoards();
		if (boards == null)
			throw new Error();
		Iterator<Move> moves = game.getMoves();
		if (moves == null)
			throw new Error();
		int movecounter = 1;

		double strengthB = game.getBlackRank().getRating();
		double strengthW = game.getWhiteRank().getRating();

		while (boards.hasNext() && moves.hasNext()) {
			movecounter++;
			BoardI board = boards.next();
			if (board == null)
				throw new Error();
			Move move = moves.next();
			if (move == null)
				throw new Error();
			if (DEBUG)
				System.out.println("next board is: \n" + board);
			if (DEBUG)
				System.out.println("about to train on: " + move);
			int colour = move.getColour();
			boolean isBlack = colour == BoardI.VERTEX_BLACK;
			float str = (float) (isBlack ? strengthB : strengthW);

			// mark the remaining points as not worth playing
			if ((game != null) || (game.getScore() != null) || game.getScore().getScored() || (move != null)
					|| move.getPass()) {
				movecounter++;
				for (short i = 0; i < size; i++)
					for (short j = 0; j < size; j++)
						for (short sym = 0; sym < 8; sym++) {
							VertexLineariser linear = new VertexLineariser(board, i, j, sym);

							boolean played = false;
							if ((!move.getPass()) && (move.getRow() == i) && (move.getColumn() == j))
								played = true;
							// TODO
							this.train(linear, str, played);
						}
			}
		}
	}

	/**
	 * Train.
	 * 
	 * @param linear
	 *            the linear
	 * @param strength
	 *            the strength
	 * @param played
	 *            the played
	 */
	public void train(VertexLineariser linear, float strength, boolean played) {
		if (strength == 0.0)
			strength = DELTA;
		Node current = this.root;
		while (linear.hasNext()) {
			Short colour = linear.next();
			switch (colour) {
			case BoardI.VERTEX_BLACK:
				if (current.black == null)
					if (played)
						current.black = new Node(strength);
					else
						return;
				current = current.black;
				break;
			case BoardI.VERTEX_WHITE:
				if (current.white == null)
					if (played)
						current.white = new Node(strength);
					else
						return;
				current = current.white;
				break;
			case BoardI.VERTEX_OFF_BOARD:
				if (current.off == null)
					if (played)
						current.off = new Node(strength);
					else
						return;
				current = current.off;
				break;
			case BoardI.VERTEX_EMPTY:
			case BoardI.VERTEX_KO:
				if (current.empty == null)
					if (played)
						current.empty = new Node(strength);
					else
						return;
				current = current.empty;
				break;

			default:
				throw new Error();
			}
			if (played) {
				if (strength > current.score) {
					current.score = strength;
				} else {
					current.score = current.score + DELTA;
				}
			} else {
				if (strength > current.score) {
					current.score = current.score - DELTA;
				} else {
					current.score = current.score - DELTA;
				}
			}
			current.count++;
		}
	}

	/**
	 * Class to hold a single node in the tree.
	 * 
	 * @author stuart
	 */
	private final class Node {

		/** The score. */
		double score = 1;;

		/** The count. */
		long count = 0;;

		/** The white. */
		Node white = null;;

		/** The off. */
		Node off = null;

		/** The black. */
		Node black = null;

		/** The empty. */
		Node empty = null;

		/**
		 * Instantiates a new node.
		 */
		Node() {
		}

		/**
		 * Instantiates a new node.
		 * 
		 * @param score
		 *            the score
		 */
		Node(double score) {
			this.score = (float) score;
		}

		/**
		 * Instantiates a new node.
		 * 
		 * @param score
		 *            the score
		 */
		Node(float score) {
			this.score = score;
		}
	}

}
