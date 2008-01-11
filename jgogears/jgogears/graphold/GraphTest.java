package jgogears.graphold;

import java.util.*;
import java.io.*;

import jgogears.*;

import junit.framework.TestCase;

public class GraphTest extends TestCase {

    public void testSevenBySeven() {
        GoBoard board = new GoBoard(7);
        assertNotNull(board);
        Graph g = new Graph(board);
        assertNotNull(g);
    }

    public void testSevenBySevenMerge() {
        GoBoard board = new GoBoard(7);
        assertNotNull(board);
        Graph g = new Graph(board);
        assertNotNull(g);
        g.merge();
    }

    public void testLoadSeki() {

        GoGame goGame = GoGame.loadFromFile(new File("sgf/testing/seki.sgf"));
        Iterator<GoBoard> i = goGame.getBoards();
        GoBoard board = null;
        Graph g = null;
        while (i.hasNext()) {
            board = i.next();
            assertNotNull(board);
            g = new Graph(board);
            assertNotNull(g);
            g.merge();
            checkAllVertexesPresentOnlyOnce(g);
            Graph h = new Graph(g);
            checkAllVertexesPresentOnlyOnce(g);
            checkAllVertexesPresentOnlyOnce(h);
        }
        // System.err.println(g);
    }

    public void testLoadSimpleGnugoLegality() {

        GoGame goGame = GoGame.loadFromFile(new File(
                "sgf/testing/simpleGnuGo.sgf"));
        Iterator<GoMove> i = goGame.getMoves();
        GoMove move = null;
        BoardInterface board = new GoBoard(goGame.getSize());
        while (i.hasNext()) {
            move = i.next();
            assertNotNull(move);
            //TODO
//            assertTrue(GraphUtilities.isMoveLegal(board, move));
//            if (!GraphUtilities.isMoveLegal(board, move)) {
//                System.err.println(board.toString(move));
//                System.err.println(move);
//            }
            // assertTrue("" + board + "\n" +
            // move.toString(),board.isLegalMove(move));
            board = board.newBoard(move);
        }
        // System.err.println(g);
    }
    public void testLoadSimpleGnugoLong() {

        //fail();
        GoGame goGame = GoGame.loadFromFile(new File(
                "sgf/testing/simpleGnuGo.sgf"));
        Iterator<GoBoard> i = goGame.getBoards();
        GoBoard board = null;
        Graph g = null;
        while (i.hasNext()) {
            board = i.next();
            assertNotNull(board);
            g = new Graph(board);
            assertNotNull(g);
            g.merge();
            checkAllVertexesPresentOnlyOnce(g);
            Graph h = new Graph(g);
            checkAllVertexesPresentOnlyOnce(g);
            checkAllVertexesPresentOnlyOnce(h);
        }
        // System.err.println(g);
    }

    public void checkAllVertexesPresentOnlyOnce(Graph g) {
        BoardInterface board = g.board;
        short size = board.getSize();
        for (short i = 0; i < size; i++) {
            for (short j = 0; j < size; j++) {
                int count = 0;
                Iterator<Node> it = g.nodes.iterator();
                while (it.hasNext()) {
                    Node node = it.next();
                    Iterator<Vector<Short>> current = node.getVertexes()
                            .iterator();
                    while (current.hasNext()) {
                        Vector<Short> vertex = current.next();
                        assertTrue(vertex.size() == 2);
                        if (vertex.elementAt(0) == i
                                && vertex.elementAt(1) == j) count++;
                    }
                }
                if (count != 1) {
                    System.err.println("bad count of " + count);
                }
                // assertTrue(count == 1);
            }
        }
    }
    public void testLoadAllSGFfiles() {
        Stack<String> files = new Stack<String>();
        files.push("sgf/2004-12");
        int left = 10;

        while (files.size() > 0 && left > 0) {
            left --;
            String filename = files.pop();
            File file = new File(filename);
            System.err.println("\nexamining \"" + filename + "\"");
            if (file.exists()) {
                if (!file.isDirectory()) {
                    // System.err.println("\"" + filename + "\" is not a
                    // directory, parsing as an SGF file");

                    GoGame goGame = GoGame.loadFromFile(file);
                    Iterator<GoMove> i = goGame.getMoves();
                    GoMove move = null;
                    BoardInterface board = new GoBoard(goGame.getSize());
                    int m = 0;
                    // System.err.println("board size is: \"" + goGame.getSize()
                    // + "\"");
                    while (i.hasNext()) {
                        move = i.next();
                        assertNotNull(move);
                        Collection<Node> captures = null; 
                        //GraphUtilities.getCaptures(board, move); TODO
                        if (captures  != null && captures.size() > 0) {
                            System.err.println(" captured: " + captures.size() + " " );                            
                        }
                        
                        //assertTrue("file\"" + filename + "\" \"" + move + "\" \"" + m + "\" \n" + board + "", GraphUtilities.isMoveLegal(board, move));
                       // System.err.print("move: \"" + move + "\"");
                        // assertTrue("" + board + "\n" +
                        // move.toString(),board.isLegalMove(move));
                        board = board.newBoard(move);
                        m++;
                        // System.err.println(" board size is: \"" +
                        // board.getSize() + "\"");
                    }
                    // System.err.println();
                    
                    //TODO allow us to actually read all the files

                } else {
                    System.err.println("\"" + filename + "\" is a directory");
                    String[] children = file.list();
                    for (int i = 0; i < children.length; i++) {
                        // System.err.println("pushing \"" + children[i] +
                        // "\"");
                        files.push(filename + "/" + children[i]);
                    }
                }
            }
        }
    }
}
