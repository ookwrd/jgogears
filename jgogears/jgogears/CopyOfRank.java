package jgogears;

/**
 * Class representing dan / kyu ranks. 20000kyu to 1kyu and 1dan to 99dan. Classes are represented internally as a
 * double. Rank parsing and formatting are only supported for whole numbers.
 * 
 * @author syeates
 */

public final class CopyOfRank {

	/** The Constant SHODAN. */
	public final static CopyOfRank SHODAN = new CopyOfRank("1d");

	/** The Constant BEGINNER. */
	public final static CopyOfRank BEGINNER = new CopyOfRank("25k");

	/** The Constant ME. */
	public final static CopyOfRank ME = new CopyOfRank("12k");

	/**
	 * Convert.
	 * 
	 * @param r
	 *            the r
	 * @return the int
	 */
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

	/** The rank. */
	private double rank = 0;

	/**
	 * Instantiates a new copy of rank.
	 * 
	 * @param r
	 *            the r
	 */
	public CopyOfRank(double r) {
		this.rank = r;
	}

	/**
	 * Instantiates a new copy of rank.
	 * 
	 * @param r
	 *            the r
	 */
	public CopyOfRank(String r) {
		this.rank = convert(r);
	}

	/**
	 * Gets the rank.
	 * 
	 * @return the rank
	 */
	public double getRank() {
		return this.rank;
	}

	/**
	 * Sets the rank.
	 * 
	 * @param rank
	 *            the new rank
	 */
	public void setRank(double rank) {
		this.rank = rank;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(10);
		if (this.rank <= 100)
			result.append((int) (-this.rank + 100 + 1)).append("dan");
		else
			result.append((int) (this.rank - 100)).append("kyu");
		return result.toString();
	}

}
