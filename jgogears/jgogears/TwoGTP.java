package jgogears;

import java.io.IOException;
/**
 * An incomplete clone of the TwoGTP program included in the GnuGo distribution. It runs a go game between a pair of GTP-capiable players
 * 
 * TODO finish this implementation
 * @author syeates
 *
 */

public class TwoGTP {
	/**
	 * Play two GTP-compatible players against each other
	 * @param args
	 * @throws IOException
	 */

	public static void main(String[] args) throws IOException {
		TwoGTP twoGTP = new TwoGTP();
		twoGTP.black = new GnuGoEngine();
		twoGTP.white = new GnuGoEngine();
		twoGTP.playOutGame();

	}
/**
 * The black player
 */
	private GTPStatelessInterface black = null;
/**
 * The white player
 */
	private GTPStatelessInterface white = null;
	/**
	 * Run the game. Assumes that the black and white players have already been set up.
	 * @return
	 */

	public boolean playOutGame() {
		int passes = 0;
		boolean blackNext = true;
		//TODO check the black and white players have been set up

		while (passes < 4) {
			if (blackNext) {
				Move move = this.black.genMove(BoardI.VERTEX_BLACK);
				assert (move != null);
				this.white.play(move);
				if (move.getPass())
					passes++;
				else
					passes = 0;
				blackNext = false;
			} else {
				Move move = this.white.genMove(BoardI.VERTEX_WHITE);
				assert (move != null);
				this.black.play(move);
				if (move.getPass())
					passes++;
				else
					passes = 0;
				blackNext = true;
			}

		}
		System.err.println(this.black.getFinalScore());
		System.err.println(this.white.getFinalScore());
		System.err.println(this.black.showBoard());
		System.err.println(this.white.showBoard());

		return true;
	}

}
