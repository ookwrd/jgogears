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
		suite.addTestSuite(GnuGoEngineTest2.class);
		suite.addTestSuite(CopyOfRankTest.class);
		suite.addTestSuite(NoKoRuleSetTest.class);
		suite.addTestSuite(RankTest.class);
		suite.addTestSuite(SGFNodeTest.class);
		suite.addTestSuite(FasterBoardTest.class);
		suite.addTestSuite(SGFPropertyTest.class);
		suite.addTestSuite(BoardTest.class);
		suite.addTestSuite(SGFParserTest.class);
		suite.addTestSuite(MoveTest.class);
		suite.addTestSuite(FastBoardTest.class);
		// $JUnit-END$
		return suite;
	}

}
