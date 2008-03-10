/**
 * 
 */
package jgogears.engine;

import jgogears.*;

/**
 * Class to calculate scores using a model and a board.
 * 
 * @author syeates
 */
public class Scorer {
	/** are we being verbose? */
	public static boolean DEBUG = false;

	/**
	 * Which vertex is the best to play?
	 * 
	 * @param board
	 *            the board we're playing on
	 * @param white
	 *            is white to play?
	 * @param model
	 * @return the vertex to play
	 */
	Vertex getBestScore(Model model, BoardI board, boolean white) {
		double[][] result = this.getScores(model, board, white);
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
	 * TODO
	 * 
	 * @param board
	 * @param white
	 * @param model
	 * @return the array of scores
	 */
	public double[][] getScores(Model model, BoardI board, boolean white) {
		short size = board.getSize();
		double[][] result = new double[size][size];
		for (short row = 0; row < size; row++) {
			for (short column = 0; column < size; column++) {
				for (short sym = 0; sym < 8; sym++) {
					result[row][column] = 0.0;
					Node node = model.getRoot();
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

}
