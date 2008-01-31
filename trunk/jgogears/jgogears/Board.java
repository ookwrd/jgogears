package jgogears;

import java.util.*;

/**
 * GoBoard represents the state of a Go board at a particular point in time.
 * 
 * It does NOT represent the number of prisoners, the number (or order) of
 * previous moves or whose turn it is too play.
 * 
 * @author stuart
 */
public class Board extends BoardI {

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

	static boolean DEBUG = false;
	static boolean CHECK = true;
	private KoRule rule = new NoKoRule();

	/**
	 * initialise the board, creating it and setting it empty.
	 */
	protected void init() {
		board = new short[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				board[i][j] = VERTEX_EMPTY;
	}

	/**
	 * Default constructor
	 */
	public Board() {
		init();
	}

	/**
	 * constructor of specially sized boards
	 */
	public Board(short size) {
		this.size = size;
		init();
	}

	/**
	 * constructor of specially sized boards
	 */
	public Board(int size) {
		this.size = (short) size;
		init();
	}
	/**
	 * Default constructor
	 */
	public Board(KoRule rule) {
		this.rule = rule;
		init();
	}

	/**
	 * constructor of specially sized boards
	 */
	public Board(short size,KoRule rule) {
		this.size = size;
		this.rule = rule;
		init();
	}

	/**
	 * constructor of specially sized boards
	 */
	public Board(int size, KoRule rule) {
		this.size = (short) size;
		this.rule = rule;
		init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.BoardInterface#getSize()
	 */
	public short getSize() {
		return size;
	}
/**
 * 
 * @return
 */
	public KoRule getKoRule(){
		return rule;
	}
	
	/*
	 * @see jgogears.BoardInterface#getColour
	 */
	public int getColour(int row, int column) {
		// System.err.println("getColour() " + " " + row + " " + column + " " +
		// size);
		if (row < 0 || column < 0 || row >= size || column >= size)
			return VERTEX_OFF_BOARD;
		return board[row][column];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.BoardInterface#newBoard(jgogears.GoMove)
	 */
	public Board newBoard(GoMove move) {
		if (DEBUG)
			System.out.println("creating a new board using:" + move);
		Board result = new Board(this.size);
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
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
			
			// take the captures
			TreeSet<Vertex> captures = rule.captures(null, this, move);
			Iterator<Vertex> i = captures.iterator();
			while (i.hasNext()){
				Vertex v = i.next();
				result.setColour(v.getRow(), v.getColumn(), Board.VERTEX_EMPTY);
			}
		}

		return result;
	}

	/*
	 * @see jgogears.BoardInterface#
	 */
	public int setColour(int row, int column, short colour) {

		int result = getColour(row, column);
		if (CHECK)
			if (row >= this.getSize())
				throw new Error("Bad board size " + row + "/" + this.getSize()
						+ " ");
		if (CHECK)
			if (column >= this.getSize())
				throw new Error("Bad board size or play off the edge of the board (remember we're zero indexed) " + column + "/"
						+ this.getSize() + " ");

		board[row][column] = colour;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.BoardInterface#toString()
	 */
	public String toString() {
		return toString(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.BoardInterface#toString(jgogears.GoMove)
	 */
	public String toString(GoMove move) {
		StringBuffer buf = new StringBuffer();
		int rowoff = -1, coloff = -1;
		if (move != null) {
			rowoff = move.getRow();
			coloff = move.getColumn();
		}
		// do the header
		buf.append("   ");
		for (int i = 0; i < size; i++) {
			buf.append(i);
		}
		buf.append("\n");
		for (int i = 0; i < size; i++) {
			buf.append(" ").append(i).append(" ");
			for (int j = 0; j < size; j++) {
				if (i == rowoff && j == coloff) {
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
