package jgogears;

import junit.framework.TestCase;
import java.io.*;

import jgogears.SGF.ParseException;


public class SGFParserTest extends TestCase {

    public static String BADEXAMPLES[] = { "(;", "();", ";)", "()", "$$", "" };

    /*
     * Test method
     */
    public void testA() throws ParseException {
        String example = SGFParser.EXAMPLEA;
        StringReader reader = new StringReader(example);
        jgogears.SGF.SGF parser = new jgogears.SGF.SGF(reader);
        parser.gameTree();
    }

    /*
     * Test method
     */
    public void testOne() throws ParseException {
        String example = SGFParser.EXAMPLEONE;
        StringReader reader = new StringReader(example);
        jgogears.SGF.SGF parser = new jgogears.SGF.SGF(reader);
        SGFGameTree tree = parser.gameTree();

        assertTrue(parser != null);
        assertTrue(parser.toString() != null);
        assertTrue(parser.toString().length() > 0);

        assertTrue(tree != null);

    }

    /*
     * Test method
     */
    public void testTwo() throws ParseException {
        String example = SGFParser.EXAMPLETWO;
        StringReader reader = new StringReader(example);
        jgogears.SGF.SGF parser = new jgogears.SGF.SGF(reader);
        parser.gameTree();

        assertTrue(parser != null);
        assertTrue(parser.toString() != null);
        assertTrue(parser.toString().length() > 0);
    }

    /*
     * Test method
     */
    public void testThree() throws ParseException {
        String example = SGFParser.EXAMPLETHREE;
        StringReader reader = new StringReader(example);
        jgogears.SGF.SGF parser = new jgogears.SGF.SGF(reader);
        parser.gameTree();

        assertTrue(parser != null);
        assertTrue(parser.toString() != null);
        assertTrue(parser.toString().length() > 0);
    }

    /*
     * Test method
     */
    public void testFour() throws ParseException {
        String example = SGFParser.EXAMPLEFOUR;
        StringReader reader = new StringReader(example);
        jgogears.SGF.SGF parser = new jgogears.SGF.SGF(reader);
        parser.gameTree();

        assertTrue(parser != null);
        assertTrue(parser.toString() != null);
        assertTrue(parser.toString().length() > 0);
    }

    /*
     * Test method
     */
    public void testFive() throws ParseException {
        String example = SGFParser.EXAMPLEFIVE;
        StringReader reader = new StringReader(example);
        jgogears.SGF.SGF parser = new jgogears.SGF.SGF(reader);
        parser.gameTree();

        assertTrue(parser != null);
        assertTrue(parser.toString() != null);
        assertTrue(parser.toString().length() > 0);
    }

    public void testRepeat() throws ParseException {
        String examples[] = { SGFParser.EXAMPLEA, SGFParser.EXAMPLEONE,
                SGFParser.EXAMPLETWO, SGFParser.EXAMPLETHREE,
                SGFParser.EXAMPLEFOUR, SGFParser.EXAMPLEFIVE };
        for (int i = 0; i < examples.length; i++) {
            String example = examples[i];
            StringReader reader = new StringReader(example);
            jgogears.SGF.SGF parser = new jgogears.SGF.SGF(reader);

            assertTrue(parser != null);
            assertTrue(parser.toString() != null);
            assertTrue(parser.toString().length() > 0);
            assertTrue(parser.toString().length() > 0);
            assertTrue(parser.toString().compareTo(parser.toString()) == 0);

            String result = parser.gameTree().toString();
            reader = new StringReader(result);
            parser = new jgogears.SGF.SGF(reader);
            String result2 = parser.gameTree().toString();

            assertTrue(parser != null);
            assertTrue(parser.toString() != null);
            assertTrue(parser.toString().length() > 0);

            assertTrue(result.compareTo(result2) == 0);

        }
    }

    public void testBADRepeat() throws ParseException {
        String examples[] = BADEXAMPLES;
        for (int i = 0; i < examples.length; i++) {
            try {
                String example = examples[i];
                StringReader reader = new StringReader(example);
                jgogears.SGF.SGF parser = new jgogears.SGF.SGF(reader);

                assertTrue(parser != null);
                assertTrue(parser.toString() != null);
                assertTrue(parser.toString().length() > 0);
                assertTrue(parser.toString().length() > 0);
                assertTrue(parser.toString().compareTo(parser.toString()) == 0);

                String result = parser.gameTree().toString();
                reader = new StringReader(result);
                parser = new jgogears.SGF.SGF(reader);
                String result2 = parser.gameTree().toString();

                assertTrue(parser != null);
                assertTrue(parser.toString() != null);
                assertTrue(parser.toString().length() > 0);

                assertTrue(result.compareTo(result2) == 0);
            } catch (ParseException p) {
                assertTrue(true);
                continue;
            } catch (jgogears.SGF.TokenMgrError e) {
                assertTrue(true);
                continue;
            }
            // we chould never reach here
            assertTrue("Bad SGF file accepted \"" + BADEXAMPLES[i] + "\"",
                    false);
        }
    }

}
