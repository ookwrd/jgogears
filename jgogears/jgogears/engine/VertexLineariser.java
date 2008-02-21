package jgogears.engine;

import java.util.*;

import jgogears.BoardI;

// TODO: Auto-generated Javadoc
/**
 * The Class VertexLineariser.
 */
public class VertexLineariser implements Iterator<Short> {
	// Table of sequences
	/** The cache. */
	static private short[][][][][] cache = null;

	// the SIZE of the board we've cached
	/** The Constant BOARD_SIZE. */
	static private final short BOARD_SIZE = 19;

	// the SIZE of the board we've cached
	/** The Constant SIZE. */
	static private final short SIZE = 21;

	// arbitrary offset to break
	/** The RO w_ offset. */
	static double ROW_OFFSET = -0.0100;
	// arbitrary offset to break
	/** The COLUM n_ offset. */
	static double COLUMN_OFFSET = -0.0230;

	/** The offset. */
	protected int offset = 0;

	/** The board. */
	protected BoardI board = null;

	/** The row. */
	short row = -2;

	/** The column. */
	short column = -2;

	/** The sym. */
	short sym = -2;
	
	/** Have the colours been inverted? */
	boolean invert = false;

	/**
	 * Instantiates a new vertex lineariser.
	 * 
	 * @param board
	 *            the board
	 * @param row
	 *            the row
	 * @param column
	 *            the column
	 * @param sym
	 *            the sym
	 */
	public VertexLineariser(BoardI board, short row, short column, short sym,boolean invert) {
		this.board = board;
		this.row = row;
		this.column = column;
		this.sym = sym;
		this.invert = invert;

		if (this.board.getSize() != BOARD_SIZE)
			throw new IllegalArgumentException("only boards of BOARD_SIZE " + BOARD_SIZE + " please");
		if (cache == null)
			this.init();
		this.check();
	}

	/**
	 * Check.
	 * 
	 * @return true, if successful
	 */
	private boolean check() {
		if (this.board == null)
			throw new Error();
		if (this.row == -2)
			throw new Error();
		if (this.column == -2)
			throw new Error();
		if (this.offset == -2)
			throw new Error();

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return this.offset * this.offset < SIZE * SIZE;
	}

	/**
	 * Inits the.
	 * 
	 * @return true, if successful
	 */
	boolean init() {
		if (cache == null) {
			cache = new short[2][8][SIZE][SIZE][SIZE * SIZE];
			for (int n = 0; n < 2; n++)
				for (int m = 0; m < 8; m++)
					for (int i = 0; i < SIZE; i++)
						for (int j = 0; j < SIZE; j++)
							for (int l = 0; l < SIZE * SIZE; l++)
								cache[n][m][i][j][l] = -5;
		} else if (cache.length != SIZE) {
			throw new Error("VertexLineariser Error! only one boardsize allowed");
		}

		for (int row = 0; row < SIZE; row++)
			for (int column = 0; column < SIZE; column++) {
				for (int sym = 0; sym < 8; sym++) {
					TreeMap<Double, ArrayList<Short>> values = new TreeMap<Double, ArrayList<Short>>();
					for (short i = 0; i < SIZE; i++)
						for (short j = 0; j < SIZE; j++) {
							short a, b; // i and j map to a and b after
							// transform
							switch (sym) {
							case 0:
								a = i;
								b = j;
								break;
							case 1:
								a = j;
								b = i;
								break;
							case 2:
								a = (short) (SIZE - 1 - i);
								b = j;
								break;
							case 3:
								a = i;
								b = (short) (SIZE - 1 - j);
								break;
							case 4:
								a = (short) (SIZE - 1 - i);
								b = (short) (SIZE - 1 - j);
								break;
							case 5:
								a = (short) (SIZE - 1 - j);
								b = i;
								break;
							case 6:
								a = j;
								b = (short) (SIZE - 1 - i);
								break;
							case 7:
								a = (short) (SIZE - 1 - j);
								b = (short) (SIZE - 1 - i);
								break;
							default:
								throw new Error();
							}
							double d = Math.sqrt(Math.pow(row - i + ROW_OFFSET, 2)
									+ Math.pow(column - j + COLUMN_OFFSET, 2));

							ArrayList<Short> array = new ArrayList<Short>();
							array.add(new Short(a));
							array.add(new Short(b));

							// System.out.println(" (" + i + "," + j + "," + a +
							// "," + b + "), ");
							values.put(new Double(d), array);
						}
					// System.out.println();
					Short[] array = new Short[] { 1, 2 };
					// System.out.println(values.size() + ", " +SIZE*SIZE+ ", "
					// +19*19);
					for (int i = 0; i < (SIZE * SIZE); i++) {
						if (values.isEmpty())
							throw new Error();
						Short[] thisone = values.get(values.firstKey()).toArray(array);
						values.remove(values.firstKey());
						// rowsequence[i] = thisone[0];
						// columnsequence[i] = thisone[1];
						cache[0][sym][row][column][i] = thisone[0];
						cache[1][sym][row][column][i] = thisone[1];
						// System.out.println(" [" +
						// cache[0][sym][row][column][i] + "," +
						// cache[1][sym][row][column][i] + "], ");
					}
					if (values.size() > 0)
						throw new Error("values should be empty but was " + values.size());
				}
			}
		// System.out.println();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#next()
	 */
	public Short next() {
		if (!this.hasNext())
			throw new NoSuchElementException();
		// System.err.println("next() " + sym + " " + row + " " + column + " "
		// + offset);
		short c = this.board.getColour(cache[0][this.sym][this.row][this.column][this.offset],
				cache[1][this.sym][this.row][this.column][this.offset]);
		this.offset++;
		if (this.invert)
			return c;
		else
			return invert(c);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		throw new java.lang.UnsupportedOperationException();
	}
	/**
	 * Invert a colour. Used when white is to play
	 * @param colour
	 * @return the inverted colour
	 */
	public Short invert(Short colour){
		switch (colour.shortValue()){
		case BoardI.VERTEX_BLACK:
			return BoardI.VERTEX_WHITE;
		case BoardI.VERTEX_WHITE:
			return BoardI.VERTEX_BLACK;
		default:
			return colour;

		}
	}


}
