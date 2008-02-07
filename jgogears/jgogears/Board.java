package jgogears;

import java.util.*;


/**
 * GoBoard represents the state of a Go board at a particular point in time. It does NOT represent the number of
 * prisoners, the number (or order) of previous moves or whose turn it is too play.
 * 
 * @author stuart
 */
public class Board extends BoardI {

	/** verbose debugging */
	static boolean DEBUG = false;

	/** bounds checking */
	static boolean CHECK = true;

	/** The actual board, of size size. */
	private short[][] board = null;
	

	/**
	 * Default constructor.
	 */
	public Board() {
		this.init();
	}

	/**
	 * Default constructor.
	 * 
	 * @param zobrist the zobrist
	 */
	public Board(boolean zobrist) {
		super(zobrist);
		this.init();
	}

	/**
	 * constructor of specially sized boards.
	 * 
	 * @param size the size
	 */
	public Board(int size) {
		this.size = (short) size;
		this.init();
	}

	/**
	 * constructor of specially sized boards.
	 * 
	 * @param size the size
	 * @param rule the rule
	 */
	public Board(int size, RuleSet rule) {
		this.size = (short) size;
		this.ruleSet = rule;
		this.init();
	}
	/**
	 * create a new board based on the current board plus a move.
	 * 
	 * @param move the move
	 * 
	 * @return the new board 
	 */
	public final Board newBoard( Move move) {
		return new Board(this,move);
	}
	
	/**
	 * create a new board based on the current board plus a move.
	 * 
	 * @param board the move
	 * @param move the move
	 * 
	 * @return the new board 
	 */
	public Board(Board board , Move move) {
		this.size = board.getSize();
		init();
		copydata(board,move);
	}

	/**
	 * Default constructor.
	 * 
	 * @param rule the rule
	 */
	public Board(RuleSet rule) {
		this.ruleSet = rule;
		this.init();
	}

	/**
	 * Instantiates a new board.
	 * 
	 * @param size the size
	 */
	public Board(short size) {
		this.size = size;
		this.init();
	}

	/**
	 * constructor of specially sized boards.
	 * 
	 * @param size the size
	 * @param rule the rule
	 */
	public Board(short size, RuleSet rule) {
		this.size = size;
		this.ruleSet = rule;
		this.init();
	}


	@Override
	public short getColour(int row, int column) {
		// System.err.println("getColour() " + " " + row + " " + column + " " +
		// size);
		if ((row < 0) || (column < 0) || (row >= this.size) || (column >= this.size))
			return VERTEX_OFF_BOARD;
		return this.board[row][column];
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


	/**
	 * Sets the colour of a vertex
	 * 
	 * @param row the row
	 * @param column the column
	 * @param colour the colour
	 * 
	 * @return the previous colour
	 */
	public void setColour(int row, int column, short colour) {

//		if (CHECK)
//			if ((row >= this.getSize()) || (row < 0))
//				throw new Error("Bad board size " + row + "/" + this.getSize() + " ");
//		if (CHECK)
//			if ((column >= this.getSize()) || (column < 0))
//				throw new Error("Bad board size or play off the edge of the board (remember we're zero indexed) "
//						+ column + "/" + this.getSize() + " ");

		this.board[row][column] = colour;
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
