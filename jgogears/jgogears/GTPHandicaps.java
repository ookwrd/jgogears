package jgogears;

import jgogears.Move;

class GTPHandicaps {

	final static public Move[] ALL = { new Move("B d4"),
			new Move("B q16"), new Move("B d16"), new Move("B q4"),
			new Move("B d10"), new Move("B q10"), new Move("B k4"),
			new Move("B k10"),

			new Move("B g13"), new Move("B n7"), new Move("B g7"),
			new Move("B n13"),

			new Move("B c17"), new Move("B r3"), new Move("B c3"),
			new Move("B r17"),

			new Move("B g16"), new Move("B n4"), new Move("B q14"),
			new Move("B d7"),

	};

	final static public Move[] NINE = { new Move("B d4"),
			new Move("B q16"), new Move("B d16"), new Move("B q4"),
			new Move("B d10"), new Move("B q10"), new Move("B k4"),
			new Move("B k10"), };

	final static public Move[] EIGHT = { new Move("B d4"),
			new Move("B q16"), new Move("B d16"), new Move("B q4"),
			new Move("B d10"), new Move("B q10"), new Move("B k4"),
			new Move("B k16"), };

	final static public Move[] SEVEN = { new Move("B d4"),
			new Move("B q16"), new Move("B d16"), new Move("B q4"),
			new Move("B d10"), new Move("B q10"), new Move("B k10"), };

	final static public Move[] SIX = { new Move("B d4"),
			new Move("B q16"), new Move("B d16"), new Move("B q4"),
			new Move("B d10"), new Move("B q10"), };

	final static public Move[] FIVE = { new Move("B d4"),
			new Move("B q16"), new Move("B d16"), new Move("B q4"),
			new Move("B k10"), };

	final static public Move[] FOUR = { new Move("B d4"),
			new Move("B q16"), new Move("B d16"), new Move("B q4"), };

	final static public Move[] THREE = { new Move("B d4"),
			new Move("B q16"), new Move("B d16"), };
	final static public Move[] TWO = { new Move("B D4"),
			new Move("B Q16"), };

	public static final Move[] fixedHandicap(int handicap) {
		switch (handicap) {
		case 2:
			return TWO;
		case 3:
			return THREE;
		case 4:
			return FOUR;
		case 5:
			return FIVE;
		case 6:
			return SIX;
		case 7:
			return SEVEN;
		case 8:
			return EIGHT;
		case 9:
			return NINE;
		default:
			throw new Error();
		}

	}

	public static final Move[] freeHandicap(int handicap) {
		Move[] result = new Move[handicap];
		for (int i = 0; i < handicap && i < ALL.length; i++) {
			result[i] = ALL[i];
		}
		return result;
	}

}
