package jgogears;

import jgogears.graph.Graph;

/**
 * Abstract interface to a goban. Knows about the size of the 
 * board, which stones are where, and about colours. Knows nothing 
 * of the history of the board or whose turn it is to play.
 * 
 * @author Stuart
 *
 */
public abstract class BoardI {

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
	 * get the colour of a vertex
	 */
	public abstract int getColour(int row, int column);

	// /**
	// * get the colour of a vertex
	// */
	// public abstract int setColour(int row, int column, short colour) ;
	

	/**
	 * parse the colour of a move
	 * 
	 * @param colourString
	 * @return the colour as a short
	 */
	public short parseColour(String colourString) {

		if (colourString.compareTo("w") == 0) {
			return BoardI.VERTEX_WHITE;
		} else if (colourString.compareTo("white") == 0) {
			return BoardI.VERTEX_WHITE;
		} else if (colourString.compareTo("b") == 0) {
			return BoardI.VERTEX_BLACK;
		} else if (colourString.compareTo("black") == 0) {
			return BoardI.VERTEX_BLACK;
		} else {
			throw new IllegalArgumentException("trying to parse (1) \""
					+ colourString + "\" as a colour");
		}
	}


}
