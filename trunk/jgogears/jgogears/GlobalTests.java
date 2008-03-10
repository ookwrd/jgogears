package jgogears;

import jgogears.engine.*;
import junit.framework.*;

// TODO: Auto-generated Javadoc
/**
 * Manually created test suite of all useful tests in the project, across all packages. The default test suite for the
 * project.
 * 
 * @author Stuart
 */

public class GlobalTests {

	/**
	 * test collection.
	 * 
	 * @return the test
	 * @author syeates
	 */

	public static Test suite() {
		TestSuite suite = new TestSuite("all tests in project");
		suite.addTestSuite(NodeIteratorTest.class);
		suite.addTestSuite(TreeIteratorTest.class);
		suite.addTestSuite(NodeTest.class);
		suite.addTestSuite(ModelTest.class);
		suite.addTestSuite(VertexLineariserTest.class);
		suite.addTestSuite(MoveTest.class);
		suite.addTestSuite(SGFPropertyTest.class);
		suite.addTestSuite(SGFNodeTest.class);
		suite.addTestSuite(SGFParserTest.class);
		suite.addTestSuite(RankTest.class);
		suite.addTestSuite(GTPParserUtilsTest.class);
		suite.addTestSuite(GnuGoEngineTest.class);
		suite.addTestSuite(BoardTest.class);
		suite.addTestSuite(SGFGameTest.class);
		suite.addTestSuite(GTPScoreTest.class);
		suite.addTestSuite(GnuGoEngineTest2.class);
		suite.addTestSuite(FastBoardTest.class);
		suite.addTestSuite(FasterBoardTest.class);
		suite.addTestSuite(NoKoRuleSetTest.class);
		suite.addTestSuite(ZobristTest.class);
		suite.addTestSuite(CheckAllSGFFilesTest.class);
		return suite;
	}

}
