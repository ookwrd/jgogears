package jgogears.graph;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for jgogears graph");
		// $JUnit-BEGIN$
		suite.addTestSuite(GraphTest.class);
		suite.addTestSuite(PointTest.class);
		suite.addTestSuite(NodeTest.class);
		// $JUnit-END$
		return suite;
	}
}
