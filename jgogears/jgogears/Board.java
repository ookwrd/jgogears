package jgogears;

import java.util.*;

/**
 * GoBoard represents the state of a Go board at a particular point in time. It does NOT represent the number of
 * prisoners, the number (or order) of previous moves or whose turn it is too play.
 * 
 * @author stuart
 */
public class Board extends BoardI {

	static boolean DEBUG = false;

	static boolean CHECK = true;

	/**
	 * The size of this board (defaults to DEFAULT_BOARD_SIZE or 19
	 * 
	 * @see #DEFAULT_BOARD_SIZE
	 */
	private short size = DEFAULT_BOARD_SIZE;
	/**
	 * The actual board, of size size
	 * 
	 * @see #size
	 */
	private short[][] board = null;
	private RuleSet rule = new NoKoRuleSet();

	/**
	 * Default constructor
	 */
	public Board() {
		this.init();
	}

	/**
	 * Default constructor
	 */
	public Board(boolean zobrist) {
		super(zobrist);
		this.init();
	}

	/**
	 * constructor of specially sized boards
	 */
	public Board(int size) {
		this.size = (short) size;
		this.init();
	}

	/**
	 * constructor of specially sized boards
	 */
	public Board(int size, RuleSet rule) {
		this.size = (short) size;
		this.rule = rule;
		this.init();
	}

	/**
	 * Default constructor
	 */
	public Board(RuleSet rule) {
		this.rule = rule;
		this.init();
	}

	public Board(short size) {
		this.size = size;
		this.init();
	}

	/**
	 * constructor of specially sized boards
	 */
	public Board(short size, RuleSet rule) {
		this.size = size;
		this.rule = rule;
		this.init();
	}

	/*
	 * @see jgogears.BoardInterface#getColour
	 */
	@Override
	public int getColour(int row, int column) {
		// System.err.println("getColour() " + " " + row + " " + column + " " +
		// size);
		if ((row < 0) || (column < 0) || (row >= this.size) || (column >= this.size))
			return VERTEX_OFF_BOARD;
		return this.board[row][column];
	}

	/**
	 * @return
	 */
	public RuleSet getKoRule() {
		return this.rule;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.BoardInterface#getSize()
	 */
	@Override
	public short getSize() {
		return this.size;
	}

	/**
	 * initialise the board, creating it and setting it empty.
	 */
	protected void init() {
		this.board = new short[this.size][this.size];
		for (int i = 0; i < this.size; i++)
			for (int j = 0; j < this.size; j++)
				this.board[i][j] = VERTEX_EMPTY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.BoardInterface#newBoard(jgogears.GoMove)
	 */
	@Override
	public BoardI newBoard(Move move) {
		if (DEBUG)
			System.out.println("creating a new board using:" + move);
		Board result = new Board(this.getSize(), this.getKoRule());
		for (int i = 0; i < this.size; i++)
			for (int j = 0; j < this.size; j++)
				if (result.board[i][j] != VERTEX_KO)
					result.board[i][j] = this.board[i][j];
				else
					result.board[i][j] = VERTEX_EMPTY;
		if (move == null)
			return result;
		if (move.getResign()) {
			// do nothing
		} else if (move.getPass()) {
			// do nothing, since GoBoard doesn't know whose turn it is
		} else {
			// place the stone
			result.setColour(move.getRow(), move.getColumn(), move.getColour());
			if (this.zobrist != null)
				result.setZobrist(new Zobrist(this.zobrist, move.getRow(), move.getColumn(), BoardI.VERTEX_EMPTY));

			// take the captures
			TreeSet<Vertex> captures = this.rule.captures(null, this, move);
			Iterator<Vertex> i = captures.iterator();
			while (i.hasNext()) {
				Vertex v = i.next();
				result.setColour(v.getRow(), v.getColumn(), BoardI.VERTEX_EMPTY);
				if (this.zobrist != null)
					result.setZobrist(new Zobrist(result.getZobrist(), v.getRow(), v.getColumn(), BoardI.VERTEX_EMPTY));
			}
		}

		return result;
	}

	/*
	 * @see jgogears.BoardInterface#
	 */
	public int setColour(int row, int column, short colour) {

		int result = this.getColour(row, column);
		if (CHECK)
			if ((row >= this.getSize()) || (row < 0))
				throw new Error("Bad board size " + row + "/" + this.getSize() + " ");
		if (CHECK)
			if ((column >= this.getSize()) || (column < 0))
				throw new Error("Bad board size or play off the edge of the board (remember we're zero indexed) "
						+ column + "/" + this.getSize() + " ");

		this.board[row][column] = colour;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.BoardInterface#toString()
	 */
	@Override
	public String toString() {
		return this.toString(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.BoardInterface#toString(jgogears.GoMove)
	 */
	public String toString(Move move) {
		StringBuffer buf = new StringBuffer();
		int rowoff = -1, coloff = -1;
		if (move != null) {
			rowoff = move.getRow();
			coloff = move.getColumn();
		}
		// do the header
		buf.append("   ");
		for (int i = 0; i < this.size; i++) {
			buf.append(i);
		}
		buf.append("\n");
		for (int i = 0; i < this.size; i++) {
			buf.append(" ").append(i).append(" ");
			for (int j = 0; j < this.size; j++) {
				if ((i == rowoff) && (j == coloff)) {
					buf.append("&");
				}
				switch (this.board[i][j]) {
				case VERTEX_KO:
					buf.append("!");
					break;
				case VERTEX_BLACK:
					buf.append("X");
					break;
				case VERTEX_WHITE:
					buf.append("O");
					break;
				case VERTEX_EMPTY:
					buf.append(".");
					break;
				default:
					throw new Error();
				}
			}
			buf.append("\n");
		}
		buf.append("\n");
		return buf.toString();
	}

}
