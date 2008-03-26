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
		suite.addTestSuite(GameTest.class);
		suite.addTestSuite(GTPParserUtilsTest.class);
		suite.addTestSuite(GTPScoreTest.class);
		suite.addTestSuite(GnuGoEngineTest.class);
		suite.addTestSuite(SGFGameTest.class);
		suite.addTestSuite(TwoGTPTest.class);
		suite.addTestSuite(GnuGoEngineTest2.class);
		suite.addTestSuite(NoKoRuleSetTest.class);
		suite.addTestSuite(CheckAllSGFFilesTest.class);
		suite.addTestSuite(SmallBoardTest.class);
		suite.addTestSuite(RankTest.class);
		suite.addTestSuite(ZobristTest.class);
		suite.addTestSuite(SGFNodeTest.class);
		suite.addTestSuite(SGFPropertyTest.class);
		suite.addTestSuite(BoardTest.class);
		suite.addTestSuite(BoardToASCIITest.class);
		suite.addTestSuite(RandomTest.class);
		suite.addTestSuite(SGFParserTest.class);
		suite.addTestSuite(MoveTest.class);
		suite.addTestSuite(SmallerBoardTest.class);

		suite.addTestSuite(TrainerTest.class);
		suite.addTestSuite(VertexLineariserTest.class);
		suite.addTestSuite(NodeTest.class);
		suite.addTestSuite(NodeIteratorTest.class);
		suite.addTestSuite(SufogoEngineTest.class);
		suite.addTestSuite(TreeIteratorTest.class);
		suite.addTestSuite(ModelTest.class);
		suite.addTestSuite(StraightVertexLineariserTest.class);

		return suite;
	}

}
