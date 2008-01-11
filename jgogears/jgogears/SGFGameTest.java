package jgogears;

import java.io.StringReader;

import junit.framework.TestCase;
import java.util.*;
import java.io.*;

import jgogears.SGF.ParseException;

public class SGFGameTest extends TestCase {

    public void testLoad() throws IOException {
        try {
            FileReader reader = new FileReader(
                    "sgf/testing/2007-01-12- BamboField- tokabe.sgf");
            jgogears.SGF.SGF parser = new jgogears.SGF.SGF(reader);
            assertNotNull(parser);
            SGFGameTree tree = parser.gameTree();
            assertNotNull(tree);
            GoGame goGame = new GoGame(tree);
            assertNotNull(goGame);
            Iterator<GoMove> moves = goGame.getMoves();
            assertNotNull(moves);
            assertTrue(moves.hasNext());
            while (moves.hasNext()) {
                GoMove move = moves.next();
                assertNotNull(move);
                System.err.print(move);

            }
            System.err.println();

        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
            fail("throw an error");
        } catch (ParseException e) {
            System.err.println(e);
            e.printStackTrace();
            fail("throw an error");
        }

    }

    /*
     * Test method for 'jgogears.Sufgo.isTrue(boolean)'
     */
    public void testIsVerbose() throws ParseException {

        String example = SGFParser.EXAMPLEB;
        StringReader reader = new StringReader(example);
        jgogears.SGF.SGF parser = new jgogears.SGF.SGF(reader);

        GoGame goGame = new GoGame(parser.gameTree());

        Iterator iterator = goGame.getMoves();
        while (iterator.hasNext()) {
            iterator.next();
            // System.out.println(iterator.next());
        }
        iterator = goGame.getBoards();
        while (iterator.hasNext()) {
            iterator.next();
            // System.out.println(iterator.next());
        }

    }
}
