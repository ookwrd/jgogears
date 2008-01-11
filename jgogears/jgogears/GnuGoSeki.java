package jgogears;

import java.io.*;
import java.util.*;

public class GnuGoSeki {

    static public boolean testForSeki(String filename) {
        return testForSeki(new File(filename));
    }
    static public boolean testForSeki(File file) {

        GoGame goGame = GoGame.loadFromFile(file);
        if (goGame == null) {
            System.err.println("failed to load \"" + file + "\"");
            return false;
        }
        if (!goGame.getScore().getScored()|| goGame.getSize() != 19)
            return false;
        if (goGame.isBranched())
            return false;
        Iterator moves = goGame.getMoves();

        GnuGoEngine engine = new GnuGoEngine();
        while (moves.hasNext()) {
            GoMove move = (GoMove) moves.next();
            // System.err.println(move);
            engine.play(move);
        }
        //engine.showBoard();
        //engine.getFinalScore();
        Date before = new Date();
        GoMove[] stonesInSeki = engine.finalStatusList("seki");
        Date after = new Date();
        System.err.println("\""+ file + "\" time = \"" + (after.getTime() - before.getTime()) + "\" result = \"" + goGame.getScore() + "\"");      
        if (stonesInSeki != null && stonesInSeki.length != 0) {
            System.err.println("\""+ file + "\" stonesInSeki = \"" + stonesInSeki + "\"");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Stack<String> files = new Stack<String>();
        files.push("sgf/");

        while (files.size() > 0) {
            String filename = files.pop();
            File file = new File(filename);
            // System.err.println("examining \"" + filename + "\"");
            if (file.exists()) {
                if (!file.isDirectory()) {
                    // System.err.println("\"" + filename + "\" is not a
                    // directory");
                    if (testForSeki(file)) {
                        System.err.println("\"" + filename
                                + "\" contains a seki");
                    }
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

        // testForSeki("sgf/testing/seki.sgf");
    }

}
