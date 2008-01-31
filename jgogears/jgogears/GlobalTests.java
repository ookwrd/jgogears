package jgogears;

import jgogears.engine.ModelTest;
import jgogears.engine.VertexLineariserTest;
import jgogears.graph.GraphTest;
import jgogears.graph.NodeTest;
import jgogears.graph.PointTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class GlobalTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("all tests in jgogears");
		suite.addTestSuite(ModelTest.class);
		suite.addTestSuite(VertexLineariserTest.class);
		suite.addTestSuite(GoMoveTest.class);
		suite.addTestSuite(SGFPropertyTest.class);
		suite.addTestSuite(SGFNodeTest.class);
		suite.addTestSuite(SGFParserTest.class);
		suite.addTestSuite(RankTest.class);
		suite.addTestSuite(GTPParserUtilsTest.class);
		suite.addTestSuite(GnuGoEngineTest.class);
		suite.addTestSuite(BoardTest.class);
		suite.addTestSuite(SGFGameTest.class);
		suite.addTestSuite(CopyOfRankTest.class);
		suite.addTestSuite(GTPScoreTest.class);
		suite.addTestSuite(GnuGoEngineTest2.class);

		suite.addTestSuite(FastBoardTest.class);
		suite.addTestSuite(FasterBoardTest.class);
		suite.addTestSuite(NoKoRuleTest.class);

		return suite;
	}

}
