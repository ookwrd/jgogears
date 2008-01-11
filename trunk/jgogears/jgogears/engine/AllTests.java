package jgogears.engine;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for jgogears graph");
        //$JUnit-BEGIN$
        suite.addTestSuite(ModelTest.class);
        suite.addTestSuite(VertexLineariserTest.class);
        //$JUnit-END$
        return suite;
    }
}
