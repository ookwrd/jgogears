package jgogears;

/**
 * A class representing a go score, as represented by SGF and GTP (GTP defines
 * scores in terms of the SGF standard).
 * 
 * A score is either (a) a win to {black|white} with a margin, by time, by
 * resignation or by forfiet (b) a win to niether (a draw or void) or (c)
 * unknown.
 * 
 * Unknown scores are also used returned by go engines and systems which cannot
 * score games, even if the score is obvious.
 * 
 * The margin of a sore should not be taken to represent the gap in skill
 * between the two players, since a player who is loosing even slightly may make
 * increasingly risky moves in an attempt to force an error.
 * 
 * @author syeates
 */

public final class GTPScore {

	private boolean whiteWin;
	private boolean neitherWin = false;
	private boolean scored = false;
	private double margin = Double.MIN_VALUE;
	private boolean resign = false;
	private boolean time = false;
	private boolean forfeit = false;
	private boolean _void = false;
	private boolean unknown = false;
	private String value;

	public boolean DEBUG = false;

	public boolean getWhiteWin() {
		return !this.neitherWin && this.whiteWin;
	};

	public boolean getBlackWin() {
		return !this.neitherWin && !this.whiteWin;
	};

	public boolean getResign() {
		return this.resign;
	};

	public boolean getTime() {
		return this.time;
	};

	public boolean getForfeit() {
		return this.forfeit;
	};

	public boolean getDraw() {
		return this.margin == 0.0;
	};

	public boolean getVoid() {
		return _void;
	};

	public boolean getUnknown() {
		return this.unknown;
	};

	public boolean getScored() {
		return this.scored;
	};

	public double getMargin() {
		return this.margin;
	};

	public String toString() {
		if (this.neitherWin)
			if (this.margin == 0.0)
				return "0";
			else if (this._void)
				return "void";
			else
				return "?";
		else // there's a winner
		if (this.whiteWin) { // white wins
			if (resign)
				return "w+r";
			else if (time)
				return "w+t";
			else if (forfeit)
				return "w+f";
			else if (margin == Double.MAX_VALUE)
				return "w+";
			else
				return "w+" + margin;
		} else { // black wins
			if (resign)
				return "b+r";
			else if (time)
				return "b+t";
			else if (forfeit)
				return "b+f";
			else if (margin == Double.MAX_VALUE)
				return "b+";
			else
				return "b+" + margin;
		}
	}

	public GTPScore(String s) {
		init(s);
	}

	public boolean init(String s) {
		String original = s;
		if (s.length() == 0) {
			System.err
					.println("cannot initialise a score from a zero length string");
			return false;
		}
		try {
			this.value = s;
			s = s.toLowerCase();
			if (s.contains("w+r")) {
				this.whiteWin = true;
				this.resign = true;
			} else if (s.contains("b+r")) {
				this.whiteWin = false;
				this.resign = true;
			} else if (s.compareTo("0") == 0 || s.compareTo("draw") == 0) {
				this.whiteWin = false;
				this.neitherWin = true;
				this.margin = 0.0;
			} else if (s.contains("w+t")) {
				this.whiteWin = true;
				this.time = true;
			} else if (s.contains("b+t")) {
				this.whiteWin = false;
				this.time = true;
			} else if (s.contains("void")) {
				this.neitherWin = true;
				this._void = true;
			} else if (s.contains("w+f")) {
				this.whiteWin = true;
				this.forfeit = true;
			} else if (s.contains("b+f")) {
				this.whiteWin = false;
				this.forfeit = true;
			} else if (s.contains("w+?")) {
				this.whiteWin = true;
				this.margin = Double.MAX_VALUE;
			} else if (s.contains("b+?")) {
				this.whiteWin = false;
				this.margin = Double.MAX_VALUE;
			} else if (s.contains("?")) {
				this.unknown = true;
				this.neitherWin = true;
			} else {
				switch (s.charAt(0)) {
				case 'w':
					this.whiteWin = true;
					break;
				case 'b':
					this.whiteWin = false;
					break;
				default:
					throw new IllegalArgumentException(
							"Badly formed GTP Score \"" + this.value + "\"");
				}
				if (s.length() == 2)
					this.margin = Double.MAX_VALUE;
				else {
					String number = s.substring(2);
					this.scored = true;
					this.margin = Double.parseDouble(number);
				}
			}
			if (DEBUG)
				System.err.println("Generated a GTPScore of \"" + this
						+ "\" from  \"" + this.value + "\"");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			if (DEBUG)
				System.err.println("Problem with \"" + original + "\"");
			if (original.length() > 2) {
				try {
					init(s.substring(2, original.length()));
				} catch (IllegalArgumentException e2) {
					e2.printStackTrace();
					throw e;
				}
			}
			return true;
		}
		return true;
	}

	protected Object clone() throws CloneNotSupportedException {
		return new GTPScore(this.toString());
	}

	public boolean equals(Object arg0) {
		return this.toString().equalsIgnoreCase(arg0.toString());
	}

}
