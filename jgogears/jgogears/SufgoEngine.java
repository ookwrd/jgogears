package jgogears;

import java.util.Iterator;

import jgogears.engine.*;

public class SufgoEngine extends SkeletonEngine {

	Model model = null;

	// default constructor
	public SufgoEngine() {

	}

	public Move[] finalStatusList(String status, GTPState state) {
		// TODO Auto-generated method stub
		throw new Error("not implemented");
	}

	public String getEngineName() {
		return "SufgoEngine";
	}

	public String getEngineVersion() {
		return "0.0.0.1";
	}

	public GTPScore getFinalScore(GTPState state) {
		// TODO Auto-generated method stub
		return null;
	}

	public Move regGenMove(int colour, GTPState state) {
		BoardI board = state.getBoard();
		short size = board.getSize();

		Short[][] values = new Short[size][size];

		for (short r = 0; r < size; r++) {
			for (short c = 0; c < size; c++) {

				for (short j = 0; j < 8; j++) {
					Iterator<Short> linear = new VertexLineariser(board,
							(short) r, (short) c, j);
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
