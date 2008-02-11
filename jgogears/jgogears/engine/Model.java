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
	 * Gets the root.
	 * 
	 * @return the root
	 */
	public Node getRoot() {
		return this.root;
	}

	public double[][] getScores(BoardI board, boolean white) {
		short size = board.getSize();
		double[][] result = new double[size][size];
		for (short row = 0; row < size; row++) {
			for (short column = 0; column < size; column++) {
				result[row][column] = TINY;
				for (short sym = 0; sym < 8; sym++) {
					Node node = this.getRoot();
					VertexLineariser linear = new VertexLineariser(board, row, column, sym, white);
					double estimate = 0.6;
					int depth = 1;
					while (linear.hasNext() && node != null) {
						short colour = linear.next();
						switch (colour) {
						case BoardI.VERTEX_BLACK:
							if (node.getBlack() == null)
								estimate = 1 - estimate * (1 / node.size()) * (depth / size*size);
							else
								estimate = 1 - estimate * (node.getBlack().size() / node.size()) * (depth / size*size);
							node = node.getBlack();
							break;
						case BoardI.VERTEX_WHITE:
							if (node.getWhite() == null)
								estimate = 1 - estimate * (1 / node.size()) * (depth / size*size);
							else
								estimate = 1 - estimate * (node.getWhite().size() / node.size()) * (depth / size*size);
							node = node.getWhite();
							break;
						case BoardI.VERTEX_OFF_BOARD:
							if (node.getOff() == null)
								estimate = 1 - estimate * (1 / node.size()) * (depth / size*size);
							else
								estimate = 1 - estimate * (node.getOff().size() / node.size()) * (depth / size*size);
							node = node.getOff();
							break;
						case BoardI.VERTEX_KO:
						case BoardI.VERTEX_EMPTY:
							if (node.getEmpty() == null)
								estimate = 1 - estimate * (1 / node.size()) * (depth / size*size);
							else
								estimate = 1 - estimate * (node.getEmpty().size() / node.size()) * (depth / size*size);
							node = node.getEmpty();
							break;
						default:
							throw new Error("Unknown vertex colour: " + colour);
						}

					}
				
				if (result[row][column] < estimate){
					result[row][column] = estimate;
					//System.err.println(estimate);
				}
				}
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
							VertexLineariser linear = new VertexLineariser(board, i, j, sym, !isBlack);

							boolean played = false;
							if ((!move.getPass()) && (move.getRow() == i) && (move.getColumn() == j))
								played = true;
							// TODO
							root.train(linear, played);
						}
			}
		}
	}
}
