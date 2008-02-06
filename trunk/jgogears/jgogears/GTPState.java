package jgogears;

/**
 * Class representing the state of a GTP game
 * 
 * @author syeates
 */
public class GTPState {

	protected short boardsize = -1;
	protected BoardI board = null;
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
		if ((this.komi < 10000) && (this.komi > 10000))
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
		this.playedMoves = new java.util.Vector<Move>();
	}

	public int getBlackCapturedCount() {
		return this.blackCapturedCount;
	}

	public BoardI getBoard() {
		return this.board;
	}

	public int getBoardsize() {
		return this.boardsize;
	}

	public double getByoYomiStones() {
		return this.byoYomiStones;
	}

	public double getByoYomiTime() {
		return this.byoYomiTime;
	}

	public double getKomi() {
		return this.komi;
	}

	public double getMainTime() {
		return this.mainTime;
	}

	public java.util.Vector<Move> getMoves() {
		return new java.util.Vector<Move>(this.playedMoves);
	}

	public int getWhiteCapturedCount() {
		return this.whiteCapturedCount;
	}

	public void playMove(Move move) {
		this.playedMoves.add(move);
		if (move.getPass())
			return;
		if (move.getResign())
			return;
		this.board = this.board.newBoard(move);
	}

	public void setBlackCapturedCount(int blackCapturedCount) {
		this.blackCapturedCount = blackCapturedCount;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setBoardsize(short boardsize) {
		this.boardsize = boardsize;
	}

	public void setByoYomiStones(double byoTomiStones) {
		this.byoYomiStones = byoTomiStones;
	}

	public void setByoYomiTime(double byoYomiTime) {
		this.byoYomiTime = byoYomiTime;
	}

	public void setKomi(double komi) {
		this.komi = komi;
	}

	public void setMainTime(double mainTime) {
		this.mainTime = mainTime;
	}

	public void setWhiteCapturedCount(int whiteCapturedCount) {
		this.whiteCapturedCount = whiteCapturedCount;
	}
}
