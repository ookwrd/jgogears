package jgogears;

/**
 * A move. A colour and either a vertex, a pass or a resign.
 * 
 * Also used for handicap stones.
 * 
 * @author syeates
 */

public final class GoMove {

	private short row = Short.MIN_VALUE;

	private short column = Short.MIN_VALUE;

	private boolean pass = false;

	private boolean resign = false;

	private short colour = BoardI.VERTEX_KO;

	/**
	 * create a GoMove
	 * 
	 * @param row
	 *            the row of the move
	 * @param column
	 *            the column of the move
	 * @param colour
	 *            the colour of the move
	 */
	public GoMove(short row, short column, short colour) {
		this.row = row;
		this.column = column;
		this.colour = colour;
	}

	/**
	 * create a GoMove
	 * 
	 * @param row
	 *            the row of the move
	 * @param column
	 *            the column of the move
	 * @param colour
	 *            the colour of the move
	 */
	public GoMove(int row, int column, int colour) {
		this.row = (short) row;
		this.column = (short) column;
		this.colour = (short) colour;
	}

	/**
	 * create a GoMove
	 * 
	 * @param row
	 *            the row of the move
	 * @param column
	 *            the column of the move
	 * @param colour
	 *            the colour of the move
	 */
	public GoMove(short row, short column, int colour) {
		this.row = row;
		this.column = column;
		this.colour = (short) colour;
	}

	/**
	 * create an empty GoMove
	 */
	public GoMove() {
	}

	/**
	 * create a GoMove from a GTP move string
	 * 
	 * @param move
	 *            the string to parse
	 */
	public GoMove(String move) throws IllegalArgumentException {
		// System.err.println("GoMove::GoMove(\"" + move + "\"");

		this.colour = BoardI.VERTEX_EMPTY;
		move = move.toLowerCase();

		int space = move.indexOf(" ");
		if (space < 0)
			throw new IllegalArgumentException("Bad argument to GoMove(" + move
					+ ")");
		String colourString = move.substring(0, space);
		this.colour = BoardI.parseColour(colourString);
		String vertexString = move.substring(space + 1, move.length());
		Vertex v = new Vertex(vertexString);
		setRow(v.getRow());
		setColumn(v.getColumn());
	}

	/**
	 * Create a handicap stone from a string
	 * 
	 * @param v
	 * @return the stone as a move
	 */
	public static GoMove createHandicapStone(String s) {
		GoMove result = new GoMove();
		result.setColour(BoardI.VERTEX_BLACK);
		Vertex v = new Vertex(s);
		result.setRow(v.getRow());
		result.setColumn(v.getColumn());
		return result;
	}


	/**
	 * return the colour of this move
	 * 
	 * @return the colour
	 */
	public short getColour() {
		return colour;
	}

	/**
	 * get the row of this column.
	 * 
	 * @throws Error
	 *             if this is a resignation move
	 * @throws Error
	 *             if this is a pass move
	 * @return the column
	 */
	public short getColumn() {
		if (resign)
			throw new Error();
		if (pass)
			throw new Error();
		return column;
	}

	/**
	 * get the row of this column.
	 * 
	 * @throws Error
	 *             if this is a resignation move
	 * @throws Error
	 *             if this is a pass move
	 * @return the column
	 */
	public short getRow() {
		if (resign)
			throw new Error();
		if (pass)
			throw new Error();
		return row;
	}

	/**
	 * @return true if this move is a pass
	 */
	public boolean getPass() {
		return pass;
	}

	/**
	 * @return true if this is a resignation
	 */
	public boolean getResign() {
		return resign;
	}

	/**
	 * return true if this is a plying move (not a pass or resign)
	 * 
	 * @return whether this is a play
	 */
	public boolean getPlay() {
		if (pass)
			return false;
		if (resign)
			return false;
		return true;
	}

	/**
	 * @param colour
	 * @return the colour as a string
	 */
	public static String colourString(int colour) {
		// find the colour of the move
		String colourS = "";
		switch (colour) {
		case BoardI.VERTEX_WHITE:
			colourS = "white";
			break;
		case BoardI.VERTEX_BLACK:
			colourS = "black";
			break;
		case BoardI.VERTEX_KO:
			colourS = "k";
			break;
		case BoardI.VERTEX_EMPTY:
			colourS = "";
			break;
		default:
			throw new java.lang.InternalError();
		}
		return colourS;
	}

	/**
	 * @return this vertex as a string
	 */
	public String toVertexString() {
		// catch the booleans
		if (pass) {
			return "pass";
		}
		if (resign) {
			return "resign";
		}
		return numberToCharacter(row) + ("" + (column + 1));
	}

	public String toStringGTP() {

		// find the colour of the move
		String colourS = colourString(this.colour);

		// calculate the position
		String vertexS = toVertexString();

		return colourS + " " + vertexS;
	}

	public String toString() {
		return toStringGTP();
	}

	/**
	 * 
	 * @param s
	 *            an alphabetic label from a traditional goban
	 * @return the corresponding (zero-indexed) integer
	 */
	protected String numberToCharacter(short s) {
		String rowS = null;
		switch (this.row) {
		case 0:
			rowS = "a";
			break;
		case 1:
			rowS = "b";
			break;
		case 2:
			rowS = "c";
			break;
		case 3:
			rowS = "d";
			break;
		case 4:
			rowS = "e";
			break;
		case 5:
			rowS = "f";
			break;
		case 6:
			rowS = "g";
			break;
		case 7:
			rowS = "h";
			break;// no i
		case 8:
			rowS = "j";
			break;
		case 9:
			rowS = "k";
			break;
		case 10:
			rowS = "l";
			break;
		case 11:
			rowS = "m";
			break;
		case 12:
			rowS = "n";
			break;
		case 13:
			rowS = "o";
			break;
		case 14:
			rowS = "p";
			break;
		case 15:
			rowS = "q";
			break;
		case 16:
			rowS = "r";
			break;
		case 17:
			rowS = "s";
			break;
		case 18:
			rowS = "t";
			break;
		case 19:
			rowS = "u";
			break;
		case 20:
			rowS = "v";
			break;
		case 21:
			rowS = "w";
			break;
		case 22:
			rowS = "x";
			break;
		case 23:
			rowS = "y";
			break;
		case 24:
			rowS = "z";
			break;
		default:
			throw new java.lang.InternalError();
		}
		return rowS;
	}

	/**
	 * @param colour
	 *            the colour to set
	 */
	public GoMove setColour(short colour) {
		this.colour = colour;
		return this;
	}

	/**
	 * @param column
	 *            the column to set
	 */
	public GoMove setColumn(short column) {
		this.column = column;
		return this;
	}

	/**
	 * @param pass
	 *            the pass to set
	 */
	public GoMove setPass(boolean pass) {
		this.pass = pass;
		return this;
	}

	/**
	 * @param resign
	 *            the resign to set
	 */
	public GoMove setResign(boolean resign) {
		this.resign = resign;
		return this;
	}

	/**
	 * @param row
	 *            the row to set
	 */
	public GoMove setRow(short row) {
		this.row = row;
		return this;
	}

}
