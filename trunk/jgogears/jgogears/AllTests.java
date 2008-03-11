package jgogears;

import junit.framework.*;

/**
 * Package-wide test collection.
 * 
 * @author syeates
 */
public class AllTests {

	/**
	 * Package-wide test collection.
	 * 
	 * @return the test
	 * @author syeates
	 */

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for jgogears");
		// $JUnit-BEGIN$
		suite.addTestSuite(GTPParserUtilsTest.class);
		suite.addTestSuite(GTPScoreTest.class);
		suite.addTestSuite(SGFGameTest.class);
		suite.addTestSuite(GnuGoEngineTest.class);
		suite.addTestSuite(TwoGTPTest.class);
		suite.addTestSuite(GnuGoEngineTest2.class);
		suite.addTestSuite(NoKoRuleSetTest.class);
		suite.addTest(AllTests.suite());
		suite.addTestSuite(RankTest.class);
		suite.addTestSuite(ZobristTest.class);
		suite.addTestSuite(SGFNodeTest.class);
		suite.addTestSuite(SmallerBoardTest.class);
		suite.addTestSuite(SGFPropertyTest.class);
		suite.addTestSuite(BoardTest.class);
		suite.addTestSuite(BoardToASCIITest.class);
		suite.addTestSuite(SGFParserTest.class);
		suite.addTest(GlobalTests.suite());
		suite.addTestSuite(MoveTest.class);
		suite.addTestSuite(SmallBoardTest.class);
		// $JUnit-END$
		return suite;
	}

}
