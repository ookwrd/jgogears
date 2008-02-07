package jgogears;

import java.util.Iterator;

import jgogears.engine.*;

/**
 * TODO.
 * 
 * @author syeates
 */
public class SufgoEngine extends SkeletonEngine {

	/** The model. */
	Model model = null;

	/**
	 * TODO.
	 */
	public SufgoEngine() {

	}

/**
 * TODO.
 * 
 * @param status the status
 * @param state the state
 * 
 * @return the move[]
 */
	public Move[] finalStatusList(String status, GTPState state) {
		// TODO Auto-generated method stub
		throw new Error("not implemented");
	}

/**
 * TODO.
 * 
 * @return the engine name
 */
	public String getEngineName() {
		return "SufgoEngine";
	}

/**
 * TODO.
 * 
 * @return the engine version
 */
	public String getEngineVersion() {
		return "0.0.0.1";
	}

/**
 * TODO.
 * 
 * @param state the state
 * 
 * @return the final score
 */
	public GTPScore getFinalScore(GTPState state) {
		// TODO Auto-generated method stub
		return null;
	}

/**
 * TODO.
 * 
 * @param colour the colour
 * @param state the state
 * 
 * @return the move
 */
	public Move regGenMove(int colour, GTPState state) {
		BoardI board = state.getBoard();
		short size = board.getSize();

		Short[][] values = new Short[size][size];

		for (short r = 0; r < size; r++) {
			for (short c = 0; c < size; c++) {

				for (short j = 0; j < 8; j++) {
					Iterator<Short> linear = new VertexLineariser(board, r, c, j);
					while (linear.hasNext()) {
						Short s = linear.next();
						// System.out.print(" " + s + ", ");
					}
				}
			}
		}

		// TODO Auto-generated method stub
		return null;
	}

}
