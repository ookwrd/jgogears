package jgogears.engine;

import junit.framework.*;

/**
 * The Class EngineTests.
 */
public class EngineTests {

	/**
	 * Suite.
	 * 
	 * @return the test
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for jgogears graph");
		// $JUnit-BEGIN$
		suite.addTestSuite(ModelTest.class);
		suite.addTestSuite(VertexLineariserTest.class);
		// $JUnit-END$
		return suite;
	}
}
