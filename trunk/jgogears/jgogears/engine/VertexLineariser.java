package jgogears.engine;

import java.util.*;

import jgogears.BoardI;

public class VertexLineariser implements Iterator<Short> {
	// table of sequences
	static private short[][][][][] cache = null;

	// the SIZE of the board we've cached
	static private final short BOARD_SIZE = 19;

	// the SIZE of the board we've cached
	static private final short SIZE = 21;

	// arbitary offsets to break
	static double ROW_OFFSET = -0.0100;

	static double COLUMN_OFFSET = -0.0230;

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
			throw new Error(
					"VertexLineariser Error! only one boardsize allowed");
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
							double d = Math.sqrt(Math.pow(row - i + ROW_OFFSET,
									2)
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
						Short[] thisone = values.get(values.firstKey())
								.toArray(array);
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
						throw new Error("values should be empty but was "
								+ values.size());
				}
			}
		// System.out.println();
		return true;
	}

	protected int offset = 0;

	protected BoardI board = null;

	short row = -2;

	short column = -2;

	short sym = -2;

	public VertexLineariser(BoardI board, short row, short column, short sym) {
		this.board = board;
		this.row = row;
		this.column = column;
		this.sym = sym;

		if (this.board.getSize() != BOARD_SIZE)
			throw new IllegalArgumentException("only boards of SIZE " + SIZE
					+ " please");
		if (cache == null)
			init();
		check();
	}

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

	public boolean hasNext() {
		return offset * offset < SIZE * SIZE;
	}

	public Short next() {
		if (!hasNext())
			throw new NoSuchElementException();
		// System.err.println("next() " + sym + " " + row + " " + column + " "
		// + offset);
		int c = board.getColour(cache[0][sym][row][column][offset],
				cache[1][sym][row][column][offset]);
		offset++;
		return new Short((short) c);
	}

	public void remove() {
		throw new java.lang.UnsupportedOperationException();
	}

}
