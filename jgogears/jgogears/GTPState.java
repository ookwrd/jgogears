package jgogears;

import jgogears.*;

/**
 * Class representing the state of a GTP game
 * 
 * @author syeates
 * 
 */
public class GTPState {

	protected short boardsize = -1;
	protected Board board = null;
	protected int whiteCapturedCount = Integer.MIN_VALUE;
	protected int blackCapturedCount = Integer.MIN_VALUE;
	protected double komi = Double.MIN_VALUE;
	protected double mainTime = Double.MIN_VALUE;
	protected double byoYomiTime = Double.MIN_VALUE;
	protected double byoYomiStones = Double.MIN_VALUE;
	protected java.util.Vector<Move> playedMoves = new java.util.Vector<Move>();

	/**
	 * returns true if we're in a playable state or throws an error.
	 * 
	 * @return true if everything is hunky-dory
	 */
	public boolean check() {
		if (this.board == null)
			throw new Error("Illegal GTPState state");
		if (this.whiteCapturedCount < 0)
			throw new Error("Illegal GTPState state");
		if (this.blackCapturedCount < 0)
			throw new Error("Illegal GTPState state");
		if (this.komi < 10000 && this.komi > 10000)
			throw new Error("Illegal GTPState state");
		if (this.mainTime < 0)
			throw new Error("Illegal GTPState state");
		if (this.byoYomiTime < 0)
			throw new Error("Illegal GTPState state");
		if (this.byoYomiStones < 0)
			throw new Error("Illegal GTPState state");

		return true;
	}

	public void clearBoard() {
		// TODO
		this.whiteCapturedCount = 0;
		this.blackCapturedCount = 0;
		this.board = new Board((short) this.getBoardsize());
		playedMoves = new java.util.Vector<Move>();
	}

	public void playMove(Move move) {
		this.playedMoves.add(move);
		if (move.getPass())
			return;
		if (move.getResign())
			return;
		this.board = this.board.newBoard(move);
	}

	public java.util.Vector<Move> getMoves() {
		return new java.util.Vector<Move>(this.playedMoves);
	}

	public int getBlackCapturedCount() {
		return blackCapturedCount;
	}

	public void setBlackCapturedCount(int blackCapturedCount) {
		this.blackCapturedCount = blackCapturedCount;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getBoardsize() {
		return boardsize;
	}

	public void setBoardsize(short boardsize) {
		this.boardsize = boardsize;
	}

	public double getByoYomiStones() {
		return byoYomiStones;
	}

	public void setByoYomiStones(double byoTomiStones) {
		this.byoYomiStones = byoTomiStones;
	}

	public double getByoYomiTime() {
		return byoYomiTime;
	}

	public void setByoYomiTime(double byoYomiTime) {
		this.byoYomiTime = byoYomiTime;
	}

	public double getKomi() {
		return komi;
	}

	public void setKomi(double komi) {
		this.komi = komi;
	}

	public double getMainTime() {
		return mainTime;
	}

	public void setMainTime(double mainTime) {
		this.mainTime = mainTime;
	}

	public int getWhiteCapturedCount() {
		return whiteCapturedCount;
	}

	public void setWhiteCapturedCount(int whiteCapturedCount) {
		this.whiteCapturedCount = whiteCapturedCount;
	}
}
