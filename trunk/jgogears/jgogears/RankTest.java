package jgogears;

import junit.framework.TestCase;

public class RankTest extends TestCase {

	public void test30k() {
		assertTrue(Rank.convert("30k") == 130);
	};

	public void test29k() {
		assertTrue(Rank.convert("29k") == 129);
	};

	public void test28k() {
		assertTrue(Rank.convert("28k") == 128);
	};

	public void test27k() {
		assertTrue(Rank.convert("27k") == 127);
	};

	public void test26k() {
		assertTrue(Rank.convert("26k") == 126);
	};

	public void test25k() {
		assertTrue(Rank.convert("25k") == 125);
	};

	public void test24k() {
		assertTrue(Rank.convert("24k") == 124);
	};

	public void test23k() {
		assertTrue(Rank.convert("23k") == 123);
	};

	public void test22k() {
		assertTrue(Rank.convert("22k") == 122);
	};

	public void test21k() {
		assertTrue(Rank.convert("21k") == 121);
	};

	public void test20k() {
		assertTrue(Rank.convert("20k") == 120);
	};

	public void test19k() {
		assertTrue(Rank.convert("19k") == 119);
	};

	public void test18k() {
		assertTrue(Rank.convert("18k") == 118);
	};

	public void test17k() {
		assertTrue(Rank.convert("17k") == 117);
	};

	public void test16k() {
		assertTrue(Rank.convert("16k") == 116);
	};

	public void test15k() {
		assertTrue(Rank.convert("15k") == 115);
	};

	public void test14k() {
		assertTrue(Rank.convert("14k") == 114);
	};

	public void test13k() {
		assertTrue(Rank.convert("13k") == 113);
	};

	public void test12k() {
		assertTrue(Rank.convert("12k") == 112);
	};

	public void test11k() {
		assertTrue(Rank.convert("11k") == 111);
	};

	public void test10k() {
		assertTrue(Rank.convert("10k") == 110);
	};

	public void test9k() {
		assertTrue(Rank.convert("9k") == 109);
	};

	public void test8k() {
		assertTrue(Rank.convert("8k") == 108);
	};

	public void test7k() {
		assertTrue(Rank.convert("7k") == 107);
	};

	public void test6k() {
		assertTrue(Rank.convert("6k") == 106);
	};

	public void test5k() {
		assertTrue(Rank.convert("5k") == 105);
	};

	public void test4k() {
		assertTrue(Rank.convert("4k") == 104);
	};

	public void test3k() {
		assertTrue(Rank.convert("3k") == 103);
	};

	public void test2k() {
		assertTrue(Rank.convert("2k") == 102);
	};

	public void test1k() {
		assertTrue(Rank.convert("1k") == 101);
	};

	public void test20d() {
		assertTrue(Rank.convert("20d") == 81);
	};

	public void test19d() {
		assertTrue(Rank.convert("19d") == 82);
	};

	public void test18d() {
		assertTrue(Rank.convert("18d") == 83);
	};

	public void test17d() {
		assertTrue(Rank.convert("17d") == 84);
	};

	public void test16d() {
		assertTrue(Rank.convert("16d") == 85);
	};

	public void test15d() {
		assertTrue(Rank.convert("15d") == 86);
	};

	public void test14d() {
		assertTrue(Rank.convert("14d") == 87);
	};

	public void test13d() {
		assertTrue(Rank.convert("13d") == 88);
	};

	public void test12d() {
		assertTrue(Rank.convert("12d") == 89);
	};

	public void test11d() {
		assertTrue(Rank.convert("11d") == 90);
	};

	public void test10d() {
		assertTrue(Rank.convert("10d") == 91);
	};

	public void test9d() {
		assertTrue(Rank.convert("9d") == 92);
	};

	public void test8d() {
		assertTrue(Rank.convert("8d") == 93);
	};

	public void test7d() {
		assertTrue(Rank.convert("7d") == 94);
	};

	public void test6d() {
		assertTrue(Rank.convert("6d") == 95);
	};

	public void test5d() {
		assertTrue(Rank.convert("5d") == 96);
	};

	public void test4d() {
		assertTrue(Rank.convert("4d") == 97);
	};

	public void test3d() {
		assertTrue(Rank.convert("3d") == 98);
	};

	public void test2d() {
		assertTrue(Rank.convert("2d") == 99);
	};

	public void test1d() {
		assertTrue(Rank.convert("1d") == 100);
	};

	public void test1ka() {
		assertTrue(Rank.convert(" 1 k ") == 101);
	};

	public void test1kb() {
		assertTrue(Rank.convert("1 k ") == 101);
	};

	public void test1kc() {
		assertTrue(Rank.convert("1kyu") == 101);
	};

	public void test1kd() {
		assertTrue(Rank.convert(" 1 kyu ") == 101);
	};

	public void test1ke() {
		assertTrue(Rank.convert("1K") == 101);
	};

	public void test1da() {
		assertTrue(Rank.convert(" 1 d ") == 100);
	};

	public void test1db() {
		assertTrue(Rank.convert("1 d ") == 100);
	};

	public void test1dc() {
		assertTrue(Rank.convert("1dan") == 100);
	};

	public void test1dd() {
		assertTrue(Rank.convert(" 1 dyu ") == 100);
	};

	public void test1de() {
		assertTrue(Rank.convert("1D") == 100);
	};

	public void testRange() {
		for (int i = 130; i >= 80; i--) {
			Rank rank = new Rank(i);
			// System.err.println(rank);
			assertTrue("" + rank + "/" + i + "/" + rank.getRank(), Rank
					.convert(rank.toString()) == i);
		}
	}

	public void testRatings() {
		for (int i = 130; i >= 80; i--) {
			Rank rank = new Rank(i);
			double d = rank.getRating();
			assertTrue(d >= 0.0);
			assertTrue(d <= 1000);
			// System.err.println(rank);
			assertTrue("" + rank + "/" + i + "/" + rank.getRank(), Rank
					.convert(rank.toString()) == i);
		}
	}

}
