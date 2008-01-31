package jgogears;

import jgogears.graph.Graph;

public interface BoardI {

	/**
	 * The default board size
	 */
	public static final short DEFAULT_BOARD_SIZE = 19;
	/**
	 * The MAXIMUM value for a vertex colour
	 */
	public static final short VERTEX_MAX = 6;
	/**
	 * A vertex in ko
	 */
	public static final short VERTEX_OFF_BOARD = 6;
	/**
	 * A vertex in ko
	 */
	public static final short VERTEX_KO = 5;
	/**
	 * A vertex with a black stone
	 */
	public static final short VERTEX_BLACK = 4;
	/**
	 * A vretex with a white stone
	 */
	public static final short VERTEX_WHITE = 3;
	/**
	 * A vertex without a stone (and not in KO
	 */
	public static final short VERTEX_EMPTY = 2;
	/**
	 * The MINIMUM value for a vertex colour
	 */
	public static final short VERTEX_MIN = 2;

	/**
	 * get the size of this board
	 */
	public abstract short getSize();

	/**
	 * create a new board based on the current board plus a move
	 */
	public abstract BoardI newBoard(GoMove move);

	/**
	 * 
	 */
	public abstract String toString();

	/**
	 * get the colour of a vertex
	 */
	public abstract int getColour(int row, int column);

	// /**
	// * get the colour of a vertex
	// */
	// public abstract int setColour(int row, int column, short colour) ;

}
