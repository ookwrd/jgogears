package jgogears;

// TODO: Auto-generated Javadoc
/**
 * The Class CopyOfRankToRating.
 */
final public class CopyOfRankToRating {

	/**
	 * Rank to rating.
	 * 
	 * @param S the s
	 * 
	 * @return the double
	 */
	public static final double rankToRating(String S) {
		String s = S.toUpperCase();
		s = s.replaceAll("kyu", "k");
		s = s.replaceAll("dan", "d");
		s = s.replaceAll("pro", "p");
		s = s.replaceAll(" ", "");
		if (s.compareToIgnoreCase("14d") == 0)
			return 1500;
		if (s.compareToIgnoreCase("13d") == 0)
			return 1400;
		if (s.compareToIgnoreCase("12d") == 0)
			return 1300;
		if (s.compareToIgnoreCase("11d") == 0)
			return 1200;
		if (s.compareToIgnoreCase("10d") == 0)
			return 1100;
		if (s.compareToIgnoreCase("9d") == 0)
			return 1000;
		if (s.compareToIgnoreCase("8d") == 0)
			return 800;
		if (s.compareToIgnoreCase("7d") == 0)
			return 700;
		if (s.compareToIgnoreCase("6d") == 0)
			return 600;
		if (s.compareToIgnoreCase("5d") == 0)
			return 500;
		if (s.compareToIgnoreCase("4d") == 0)
			return 400;
		if (s.compareToIgnoreCase("3d") == 0)
			return 300;
		if (s.compareToIgnoreCase("2d") == 0)
			return 200;
		if (s.compareToIgnoreCase("1d") == 0)
			return 100;
		if (s.compareToIgnoreCase("1k") == 0)
			return 95;
		if (s.compareToIgnoreCase("2k") == 0)
			return 90;
		if (s.compareToIgnoreCase("3k") == 0)
			return 85;
		if (s.compareToIgnoreCase("4k") == 0)
			return 80;
		if (s.compareToIgnoreCase("5k") == 0)
			return 75;
		if (s.compareToIgnoreCase("6k") == 0)
			return 70;
		if (s.compareToIgnoreCase("7k") == 0)
			return 65;
		if (s.compareToIgnoreCase("8k") == 0)
			return 60;
		if (s.compareToIgnoreCase("9k") == 0)
			return 55;
		if (s.compareToIgnoreCase("10k") == 0)
			return 50;
		if (s.compareToIgnoreCase("11k") == 0)
			return 45;
		if (s.compareToIgnoreCase("12k") == 0)
			return 40;
		if (s.compareToIgnoreCase("13k") == 0)
			return 35;
		if (s.compareToIgnoreCase("14k") == 0)
			return 30;
		if (s.compareToIgnoreCase("15k") == 0)
			return 29;
		if (s.compareToIgnoreCase("16k") == 0)
			return 28;
		if (s.compareToIgnoreCase("17k") == 0)
			return 27;
		if (s.compareToIgnoreCase("18k") == 0)
			return 26;
		if (s.compareToIgnoreCase("19k") == 0)
			return 25;
		if (s.compareToIgnoreCase("20k") == 0)
			return 24;
		if (s.compareToIgnoreCase("21k") == 0)
			return 23;
		if (s.compareToIgnoreCase("22k") == 0)
			return 22;
		if (s.compareToIgnoreCase("23k") == 0)
			return 21;
		if (s.compareToIgnoreCase("24k") == 0)
			return 20;
		if (s.compareToIgnoreCase("25k") == 0)
			return 19;
		if (s.compareToIgnoreCase("26k") == 0)
			return 18;
		if (s.compareToIgnoreCase("27k") == 0)
			return 17;
		if (s.compareToIgnoreCase("28k") == 0)
			return 16;
		if (s.compareToIgnoreCase("29k") == 0)
			return 15;
		if (s.compareToIgnoreCase("30k") == 0)
			return 14;
		if (s.compareToIgnoreCase("31k") == 0)
			return 13;

		return 10;
	}

}
