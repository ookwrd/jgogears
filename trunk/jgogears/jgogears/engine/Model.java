package jgogears.engine;

import java.util.*;

import jgogears.*;

/**
 * A probability model of Go
 */
public final class Model {

	/** A small value used as a small increment to probabilities */
	public static float DELTA = (float) 0.0001;

	/** are we being verbose? */
	public static boolean DEBUG = false;

	/** */
	public static int MAX_LENGTH = 21 * 21;

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
	 * Max of two doubles, biased towards the first in the case of equality
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

	private final int boardsTrained = 0;

	private final int gamesTrained = 0;

	/** The root of the model tree */
	private final Node root = new Node();

	/**
	 * Instantiates a new model.
	 */
	public Model() {
	}

	/**
	 * Which vertex is the best to play?
	 * 
	 * @param board
	 *            the board we're playing on
	 * @param white
	 *            is white to play?
	 * @return the vertex to play
	 */
	Vertex getBestScore(BoardI board, boolean white) {
		double[][] result = this.getScores(board, white);
		double best = Double.MAX_VALUE;
		int I = 0, J = 0;
		int i, j;
		for (i = 0; i < board.getSize(); i++)
			for (j = 0; j < board.getSize(); j++) {
				if (result[i][j] < best) {
					if (RuleSet.DEFAULT.moveIsLegal(null, board, new Move(i, j, white ? BoardI.VERTEX_WHITE
							: BoardI.VERTEX_BLACK))) {
						best = result[i][j];
						I = i;
						J = j;
					}
				}
			}
		return new Vertex(I, J);
	}

	/**
	 * Gets the root of the model
	 * 
	 * @return the root
	 */
	public Node getRoot() {
		return this.root;
	}

	/**
	 * TODO
	 * 
	 * @param board
	 * @param white
	 * @return
	 */
	public double[][] getScores(BoardI board, boolean white) {
		short size = board.getSize();
		double[][] result = new double[size][size];
		for (short row = 0; row < size; row++) {
			for (short column = 0; column < size; column++) {
				for (short sym = 0; sym < 8; sym++) {
					result[row][column] = 0.0;
					Node node = this.getRoot();
					Node previous = this.getRoot();
					int maxdepth = 0;
					VertexLineariser linear = new VertexLineariser(board, row, column, sym, white);
					double estimate = 1.0;
					int depth = 0;
					while (linear.hasNext() && node != null) {
						depth++;
						if (depth > maxdepth)
							maxdepth = depth;
						short colour = linear.next();
						Node child = null;

						switch (colour) {
						case BoardI.VERTEX_BLACK:
							child = node.getBlack();
							break;
						case BoardI.VERTEX_WHITE:
							child = node.getWhite();
							break;
						case BoardI.VERTEX_OFF_BOARD:
							child = node.getOff();
							break;
						case BoardI.VERTEX_KO:
						case BoardI.VERTEX_EMPTY:
							child = node.getEmpty();
							break;
						default:
							throw new Error("Unknown vertex colour: " + colour);
						}
						double childP = 1;
						double childNP = 1;
						if (child != null) {
							childP = child.getPlayed();
							childNP = child.getNotPlayed();
							previous = node;
							node = child;
							estimate = estimate * 0.5 + childP / (childP + childNP) * 0.5;
						}

						if (DEBUG)
							System.err.println("Model::getScores following a " + BoardI.colourString(colour)
									+ " branch, estimate = " + estimate + ", childP = " + childP + ", childNP = "
									+ childNP + ", combination = " + childP / (childP + childNP) * 0.5);
					}

					// estimate = (1 + previous.getPlayed()) / (previous.getPlayed() + previous.getNotPlayed()) * (1 -
					// (1 /depth));
					if (result[row][column] < estimate) {
						result[row][column] = estimate;
						if (DEBUG)
							System.err.println("Model::getScores  " + estimate);
					}
					if (DEBUG)
						System.err.println("Model::getScores " + maxdepth);
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
				System.err.println("Model::train next board is: \n" + board);
			if (DEBUG)
				System.err.println("Model::train about to train on: " + move);
			int colour = move.getColour();
			boolean isBlack = colour == BoardI.VERTEX_BLACK;
			// float str = (float) (isBlack ? strengthB : strengthW);

			if (game != null || move != null || move.getPass()) {
				movecounter++;
				for (short i = 0; i < size; i++)
					for (short j = 0; j < size; j++)
						for (short sym = 0; sym < 8; sym++) {
							// TODO this needs a lot of work, i think

							VertexLineariser linear = new VertexLineariser(board, i, j, sym, !isBlack);
							if (!move.getPass() && move.getRow() != i && move.getColumn() != j)
								this.root.train(linear, true, true, 100);
							else
								this.root.train(linear, false, true, 30);
						}
			}
		}
	}
}
