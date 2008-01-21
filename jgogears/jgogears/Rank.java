package jgogears;

/**
 * Class representing dan / kyu ranks. 30kyu to 1kyu and 1dan to 20dan.
 * 
 * Classes are represented internally as a double. Rank parsing and formatting
 * are only supported for whole numbers.
 * 
 * @author syeates
 * 
 */

public final class Rank {

	public final static Rank SHODAN = new Rank("1d");
	public final static Rank BEGINNER = new Rank("25k");
	public final static Rank ME = new Rank("12k");

	final static boolean TESTING = false;
	final static double MAX_RANK = 80;
	final static double MIN_RANK = 130;

	final static double MAX_RATING = 1;
	final static double MIN_RATING = 0;

	private double rank = 25;

	static boolean checkRating(double rating) {
		if (TESTING)
			if (rating > MAX_RATING || rating < MIN_RATING)
				throw new Error("bad rating " + rating);
		return true;
	}

	static boolean checkRank(double ranking) {
		if (TESTING)
			if (ranking > MAX_RATING || ranking < MIN_RATING)
				throw new Error("bad ranking " + ranking);
		return true;
	}

	public double getRating() {
		if (TESTING)
			checkRank(rank);
		System.err.println(MAX_RANK + " " + MIN_RANK + " " + rank);
		double result = (MIN_RANK - rank) / (MIN_RANK - MAX_RANK);
		if (TESTING)
			checkRating(result);
		return result;
	}

	public double getRank() {
		if (TESTING)
			checkRank(rank);
		return rank;
	}

	public void setRank(double rank) {
		this.rank = rank;
	}

	public Rank(String r) {
		this.rank = convert(r);
		if (TESTING)
			checkRank(rank);
	}

	public Rank(double r) {
		if (TESTING)
			checkRank(rank);
		this.rank = r;
		if (TESTING)
			checkRank(rank);
	}

	public Rank() {
		if (TESTING)
			checkRank(rank);
	}

	public static int convert(String r) {
		if (r == null || r.length() < 2)
			return (int) BEGINNER.getRank();
		r = r.toLowerCase();
		int offset = 0;
		while (r.charAt(offset) == ' ')
			offset++;
		StringBuffer number = new StringBuffer();
		while (Character.isDigit(r.charAt(offset))) {
			number.append(r.charAt(offset));
			offset++;
		}
		while (r.charAt(offset) == ' ')
			offset++;
		if (number.length() == 0)
			throw new Error();
		int num = Integer.parseInt(number.toString());
		if (r.charAt(offset) == 'k')
			return num + 100;
		else if (r.charAt(offset) == 'd')
			return 100 + 1 - num;
		else if (r.charAt(offset) == 'p')
			return 120 + 1 - num;
		else
			throw new Error(r);
	}

	public String toString() {
		if (TESTING)
			checkRank(rank);
		StringBuffer result = new StringBuffer(10);
		if (rank <= 100)
			result.append((int) (-rank + 100 + 1)).append("dan");
		else
			result.append((int) (rank - 100)).append("kyu");
		return result.toString();
	}

}
