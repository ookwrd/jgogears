package jgogears;

import jgogears.GoMove;

class GTPHandicaps {

    final static public GoMove[] ALL   = { new GoMove("B d4"),
            new GoMove("B q16"), new GoMove("B d16"), new GoMove("B q4"),
            new GoMove("B d10"), new GoMove("B q10"), new GoMove("B k4"),
            new GoMove("B k10"),

            new GoMove("B g13"), new GoMove("B n7"), new GoMove("B g7"),
            new GoMove("B n13"),

            new GoMove("B c17"), new GoMove("B r3"), new GoMove("B c3"),
            new GoMove("B r17"),

            new GoMove("B g16"), new GoMove("B n4"), new GoMove("B q14"),
            new GoMove("B d7"),

                                       };

    final static public GoMove[] NINE  = { new GoMove("B d4"),
            new GoMove("B q16"), new GoMove("B d16"), new GoMove("B q4"),
            new GoMove("B d10"), new GoMove("B q10"), new GoMove("B k4"),
            new GoMove("B k10"),      };

    final static public GoMove[] EIGHT = { new GoMove("B d4"),
            new GoMove("B q16"), new GoMove("B d16"), new GoMove("B q4"),
            new GoMove("B d10"), new GoMove("B q10"), new GoMove("B k4"),
            new GoMove("B k16"),      };

    final static public GoMove[] SEVEN = { new GoMove("B d4"),
            new GoMove("B q16"), new GoMove("B d16"), new GoMove("B q4"),
            new GoMove("B d10"), new GoMove("B q10"), new GoMove("B k10"), };

    final static public GoMove[] SIX   = { new GoMove("B d4"),
            new GoMove("B q16"), new GoMove("B d16"), new GoMove("B q4"),
            new GoMove("B d10"), new GoMove("B q10"), };

    final static public GoMove[] FIVE  = { new GoMove("B d4"),
            new GoMove("B q16"), new GoMove("B d16"), new GoMove("B q4"),
            new GoMove("B k10"),      };

    final static public GoMove[] FOUR  = { new GoMove("B d4"),
            new GoMove("B q16"), new GoMove("B d16"), new GoMove("B q4"), };

    final static public GoMove[] THREE = { new GoMove("B d4"),
            new GoMove("B q16"), new GoMove("B d16"), };
    final static public GoMove[] TWO   = { new GoMove("B D4"),
            new GoMove("B Q16"),      };

    public static final GoMove[] fixedHandicap(int handicap) {
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

    public static final GoMove[] freeHandicap(int handicap) {
        GoMove[] result = new GoMove[handicap];
        for (int i = 0; i < handicap && i < ALL.length; i++) {
            result[i] = ALL[i];
        }
        return result;
    }

}
