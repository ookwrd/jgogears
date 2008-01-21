package jgogears.tsume;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for jgogears.tsume");
		// $JUnit-BEGIN$
		suite.addTestSuite(FastBoardTest.class);
		suite.addTestSuite(FasterBoardTest.class);
		// $JUnit-END$
		return suite;
	}

}
