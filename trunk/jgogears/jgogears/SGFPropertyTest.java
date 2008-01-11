package jgogears;

//import java.io.StringReader;
//import java.util.Iterator;

import junit.framework.TestCase;

public class SGFPropertyTest extends TestCase {

    /*
     * Test method for 'jgogears.SGFNode.columnFromMoveString(String)'
     */
    public void testStrip() throws Exception {
        String a1 = "[19]";
        String a2 = "19";
        assertTrue(SGFProperty.stripSquareBrackets(a1).compareTo(a2) == 0);
        assertFalse(SGFProperty.stripSquareBrackets(a2).compareTo(a1) == 0);
        assertTrue(SGFProperty.stripSquareBrackets(a2).compareTo(a2) == 0);
        assertFalse(SGFProperty.stripSquareBrackets(a1).compareTo(a1) == 0);

    }

}
