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
	public static float TINY = (float) 0.000000000001;

	/** are we being verbose? */
	public static boolean DEBUG = false;

	/** The random number generator */
	private static Random random = new Random();
	
	private int boardsTrained = 0;
	private int gamesTrained = 0;
	

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
	 * Gets the random tiny.
	 * 
	 * @return the random tiny
	 */
	static public double getRandomTiny() {
		double r = random.nextDouble();
		return r * TINY;
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

	/** The root of the model tree */
	private final Node root = new Node();

	/**
	 * Instantiates a new model.
	 */
	public Model() {
	}

	/**
	 * Gets the root of the model
	 * 
	 * @return the root
	 */
	public Node getRoot() {
		return this.root;
	}
	
	Vertex getBestScore(BoardI board, boolean white){
		double[][] result = getScores(board, white);
		double best = Double.MIN_VALUE;
		int I = 0,J = 0;
		int i,j;
		for (i=0;i<board.getSize();i++)
			for (j=0;j<board.getSize();j++) {
				if (result[i][j] > best){
					if (RuleSet.DEFAULT.moveIsLegal(null, board, new Move(i,j,white?BoardI.VERTEX_WHITE:BoardI.VERTEX_BLACK))){
					best = result[i][j] + getRandomTiny();
					I =i;
					J = j;
					}
				}
			}
		return new Vertex(I,J);
	}
	
/**
 * 
 * TODO
 * @param board
 * @param white
 * @return
 */
	public double[][] getScores(BoardI board, boolean white) {
		short size = board.getSize();
		double[][] result = new double[size][size];
		for (short row = 0; row < size; row++) {
			for (short column = 0; column < size; column++) 
				for (short sym = 0; sym < 8; sym++) {
				result[row][column] = TINY;
				Node node = this.getRoot();
					VertexLineariser linear = new VertexLineariser(board, row, column, sym, white);
					double estimate = 1;
					int depth = 0;
					while (linear.hasNext() && node != null) {
						depth++;
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
						long childCount = 1;
						if (child != null)
							childCount = child.getCount();
						estimate = estimate * childCount / node.getCount() ;

					}
				
				if (result[row][column] < estimate){
					result[row][column] = estimate;
					//System.err.println(estimate);
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
			//float str = (float) (isBlack ? strengthB : strengthW);

			if ((game != null) || (move != null)|| move.getPass()) {
				movecounter++;
				for (short i = 0; i < size; i++)
					for (short j = 0; j < size; j++)
						for (short sym = 0; sym < 8; sym++) {
							VertexLineariser linear = new VertexLineariser(board, i, j, sym, !isBlack);

							boolean played = false;
							if ((!move.getPass()) && (move.getRow() == i) && (move.getColumn() == j))
								played = true;
							// TODO
							root.train(linear, played, played, Integer.MAX_VALUE);
						}
			}
		}
	}
}
