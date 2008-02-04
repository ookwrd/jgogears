package jgogears;

import junit.framework.TestCase;

public class ZobristTest extends TestCase {
	public void testInstantiation() {
		Zobrist z = new Zobrist();
	}

	public void testEachNotEqual() {
		for (int i = 0; i < Zobrist.MAX_BOARD_SIZE; i++)
			for (int j = 0; j < Zobrist.MAX_BOARD_SIZE; j++)
				for (int k = 0; k < Zobrist.MAX_COLOUR; k++)
					for (int l = 0; l < Zobrist.MAX_BOARD_SIZE; l++)
						for (int m = 0; m < Zobrist.MAX_BOARD_SIZE; m++)
							for (int n = 0; n < Zobrist.MAX_COLOUR; n++)
								if (i != l || j != m || k != n)
									assertFalse(
											"" + i + " " + j + " " + k + " "
													+ l + " " + m + " " + n,
											Zobrist.grid[i][j][k] == Zobrist.grid[l][m][n]);
	}

	public void testTrivial() {
		Zobrist z = new Zobrist();
		Zobrist z2 = new Zobrist(z, 1, 1, 1);
		assertNotNull(z2);
		assertFalse(z2 == z);

		z = z2;
		z2 = new Zobrist(z, 1, 1, 1);
		assertNotNull(z2);
		assertFalse(z2 == z);
		assertFalse(z2 == new Zobrist());
	}

	public void testEachUndoable() {
		for (int i = 0; i < Zobrist.MAX_BOARD_SIZE; i++)
			for (int j = 0; j < Zobrist.MAX_BOARD_SIZE; j++)
				for (int k = 0; k < Zobrist.MAX_COLOUR; k++) {
					Zobrist z = new Zobrist();
					Zobrist z2 = new Zobrist(z, i, j, k);
					Zobrist z3 = new Zobrist(z2, i, j, k);
					Zobrist z4 = new Zobrist(z3, i, j, k);
					Zobrist z5 = new Zobrist(z4, i, j, k);
					Zobrist zz = new Zobrist(z, i, j, k);

					assertNotNull(z);
					assertNotNull(z2);
					assertNotNull(z3);
					assertNotNull(z4);
					assertNotNull(z5);
					System.err.println(z);
					System.err.println(z3);
					System.err.println(z2);
					System.err.println(zz);
					assertTrue(z2.equals(zz));
					assertFalse(z2.equals(z));
					assertFalse(z4.equals(z));
					assertTrue(z.equals(z));
					assertTrue(z.compareTo(z) == 0);
					assertTrue(z2.compareTo(z2) == 0);
					assertTrue(z3.compareTo(z3) == 0);
					assertTrue(z4.compareTo(z4) == 0);
					assertTrue(z5.compareTo(z5) == 0);
					assertTrue(z.compareTo(z3) == 0);
					assertTrue(z.compareTo(z5) == 0);
					assertTrue(z3.compareTo(z5) == 0);

				}
	}

	// why isn;t this working??
	public void testEachUndoableII() {
		for (int i = 0; i < Zobrist.MAX_BOARD_SIZE; i++)
			for (int j = 0; j < Zobrist.MAX_BOARD_SIZE; j++)
				for (int k = 0; k < Zobrist.MAX_COLOUR; k++) {
					Zobrist z = new Zobrist();
					Zobrist z2 = new Zobrist(z, i, j, k);
					Zobrist zz = new Zobrist(z, i, j, k);

					assertNotNull(z);
					assertNotNull(z2);
					System.err.println(z);
					System.err.println(z2);
					System.err.println(zz);
					assertFalse(z2.equals(z));
					assertTrue(z2 == zz);

				}
	}
}
