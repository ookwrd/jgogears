package jgogears;

import java.util.*;

/**
 * Class representing a Zobrist hash, a binary hash of the current board state. TODO add proper references
 * 
 * @author Stuart
 */

public class Zobrist extends BitSet {
	/**
	 * TODO
	 */
	private static final long serialVersionUID = 1L;
	public static final short MAX_BOARD_SIZE = 25;
	public static final short MAX_COLOUR = 4;
	public static final short ZOBRIST_SIZE = 96;

	// initialisers ...
	static BitSet[][][] grid = null;
	static private boolean initialised = false;

	static private void init() {
		if (!initialised) {
			initialised = true;
			grid = new BitSet[MAX_BOARD_SIZE][MAX_BOARD_SIZE][MAX_COLOUR];
			Random random = new Random(new Date().getTime());
			for (int i = 0; i < MAX_BOARD_SIZE; i++)
				for (int j = 0; j < MAX_BOARD_SIZE; j++)
					for (int k = 0; k < MAX_COLOUR; k++) {
						grid[i][j][k] = new BitSet();
						// System.err.println("" + i + " " + j + " " + k);
						for (int l = 0; l < ZOBRIST_SIZE; l++) {
							grid[i][j][k].set(l, random.nextBoolean());
						}
					}
		}
	}

	/**
	 * Default constructor, represents an empty board
	 */
	public Zobrist() {
		init();
	}

	/**
	 * Constructor building a Zobrist from an existing Zobrist and a move
	 * 
	 * @param old
	 * @param row
	 * @param column
	 * @param colour
	 */
	public Zobrist(Zobrist old, int row, int column, int colour) {
		init();
		if ((row >= MAX_BOARD_SIZE) || (row < 0))
			throw new Error("" + row);
		if ((column >= MAX_BOARD_SIZE) || (column < 0))
			throw new Error("" + column);
		if ((colour >= 4) || (colour < 0))
			throw new Error("" + colour);
		this.clear();
		this.xor(old);
		this.xor(grid[row][column][colour]);
	}

	public int compareTo(Object o) {
		if (o.getClass() != this.getClass())
			return this.hashCode() > o.hashCode() ? 1 : -1;
		Zobrist other = (Zobrist) o;
		if (other == null)
			throw new Error();
		for (int i = 0; i < ZOBRIST_SIZE; i++)
			if (this.get(i) != other.get(i))
				return this.get(i) == true ? 1 : -1;
		return 0;
	}

	// TODO why doesn't this work with == ?
	@Override
	public boolean equals(Object o) {
		if (this == null)
			throw new Error();
		if (o == null)
			return false;
		if (o.getClass() != this.getClass())
			return super.equals(o);
		Zobrist other = (Zobrist) o;
		if (other == null)
			throw new Error();
		for (int i = 0; i < ZOBRIST_SIZE; i++)
			if (this.get(i) != other.get(i))
				return false;
		System.err.println(",");
		return true;
	}

}
