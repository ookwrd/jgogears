package jgogears;

/**
 * Class representing dan / kyu ranks. 20000kyu to 1kyu and 1dan to 99dan.
 * 
 * Classes are represented internally as a double. Rank parsing and formatting
 * are only supported for whole numbers.
 * 
 * @author syeates
 * 
 */

public final class CopyOfRank {

	public final static CopyOfRank SHODAN = new CopyOfRank("1d");
	public final static CopyOfRank BEGINNER = new CopyOfRank("25k");
	public final static CopyOfRank ME = new CopyOfRank("12k");

	private double rank = 0;

	public double getRank() {
		return rank;
	}

	public void setRank(double rank) {
		this.rank = rank;
	}

	public CopyOfRank(String r) {
		this.rank = convert(r);
	}

	public CopyOfRank(double r) {
		this.rank = r;
	}

	public static int convert(String r) {
		if (r == null || r.length() < 2)
			throw new Error();
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
		else
			throw new Error();
	}

	public String toString() {
		StringBuffer result = new StringBuffer(10);
		if (rank <= 100)
			result.append((int) (-rank + 100 + 1)).append("dan");
		else
			result.append((int) (rank - 100)).append("kyu");
		return result.toString();
	}

}
