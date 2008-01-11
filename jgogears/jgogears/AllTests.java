package jgogears;

import jgogears.engine.VertexLineariserTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for jgogears");
        //$JUnit-BEGIN$
        suite.addTestSuite(GoMoveTest.class);
        suite.addTestSuite(SGFPropertyTest.class);
        suite.addTestSuite(SGFNodeTest.class);
        suite.addTestSuite(SGFParserTest.class);
        suite.addTestSuite(RankTest.class);
        suite.addTestSuite(GTPParserUtilsTest.class);
        suite.addTestSuite(GnuGoEngineTest.class);
        suite.addTestSuite(GoBoardTest.class);
        suite.addTestSuite(SGFGameTest.class);
        suite.addTestSuite(CopyOfRankTest.class);
        suite.addTestSuite(GTPScoreTest.class);
        suite.addTestSuite(GnuGoEngineTest2.class);
        //$JUnit-END$

        return suite;
    }

}
