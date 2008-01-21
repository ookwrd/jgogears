package jgogears;

import jgogears.*;
import java.io.*;

public class TwoGTP {

	private GTPStatelessInterface black = null;
	private GTPStatelessInterface white = null;

	public boolean playOutGame() {
		int passes = 0;
		boolean blackNext = true;

		while (passes < 4) {
			if (blackNext) {
				GoMove move = black.genMove(BoardInterface.VERTEX_BLACK);
				assert (move != null);
				white.play(move);
				if (move.getPass())
					passes++;
				else
					passes = 0;
				blackNext = false;
			} else {
				GoMove move = white.genMove(BoardInterface.VERTEX_WHITE);
				assert (move != null);
				black.play(move);
				if (move.getPass())
					passes++;
				else
					passes = 0;
				blackNext = true;
			}

		}
		System.err.println(black.getFinalScore());
		System.err.println(white.getFinalScore());
		System.err.println(black.showBoard());
		System.err.println(white.showBoard());

		return true;
	}

	public static void main(String[] args) throws IOException {
		TwoGTP twoGTP = new TwoGTP();
		twoGTP.black = new GnuGoEngine();
		twoGTP.white = new GnuGoEngine();
		twoGTP.playOutGame();

	}

	public GTPStatelessInterface getBlack() {
		return black;
	}

	public void setBlack(GTPStatelessInterface black) {
		this.black = black;
	}

	public GTPStatelessInterface getWhite() {
		return white;
	}

	public void setWhite(GTPStatelessInterface white) {
		this.white = white;
	}

}
