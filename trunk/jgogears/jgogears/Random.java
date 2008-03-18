/**
 * 
 */
package jgogears;


/**
 * A random number generator
 * @author syeates
 *
 */
public class Random {
	/** The random number generator */
	private static final java.util.Random random = new java.util.Random();
	/** A small value used as a small increment to probabilities */
	public static final double DELTA = (double) 0.01;

	/**
	 * Gets the random.
	 * 
	 * @return the random
	 */
	static public final double nextDouble() {
		double r = random.nextDouble();
		return r;
	}
	static public final boolean nextBoolean() {
		boolean r =  random.nextBoolean();
		return r;
	}
	static public final int nextInt(int max) {
		int r =  random.nextInt(max);
		return r;
	}

	/**
	 * Gets the random delta.
	 * 
	 * @return the random delta
	 */
	static public final double getRandomDelta() {
		double r = random.nextDouble();
		return r * DELTA;
	}
	
	static public final double getRandomBest(double first,double second){
		if (first + getRandomDelta() >= second + getRandomDelta())
			return first;
		else
			return second;
	}

	

}
