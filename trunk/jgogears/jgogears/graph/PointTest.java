package jgogears.graph;

import junit.framework.TestCase;

public class PointTest extends TestCase {

	public void testPoint() {
		for (short i = 0; i < 100; i++)
			for (short j = 0; j < 100; j++) {
				Point point = new Point(i, j);
				assertNotNull(point);
				assertTrue(point.getRow() == i);
				assertTrue(point.getColumn() == j);
			}
	}

	public void testNegativePoint() {
		for (short i = -100; i < 0; i++)
			for (short j = -100; j < 0; j++) {
				try {
					Point point = new Point(i, j);
					fail("created point with negative " + i + " " + j);
				} catch (Error e) {

				}
			}
		for (short i = -100; i < 0; i++)
			for (short j = 100; j < 0; j++) {
				try {
					Point point = new Point(i, j);
					fail("created point with negative " + i + " " + j);
				} catch (Error e) {

				}
			}
		for (short i = 100; i < 0; i++)
			for (short j = -100; j < 0; j++) {
				try {
					Point point = new Point(i, j);
					fail("created point with negative " + i + " " + j);
				} catch (Error e) {

				}
			}
	}

}
