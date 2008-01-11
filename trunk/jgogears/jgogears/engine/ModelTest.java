package jgogears.engine;

import junit.framework.TestCase;
import java.io.*;
import java.util.*;

import jgogears.*;

public class ModelTest extends TestCase {
    
    public static final boolean DEBUG = true;
    
    
    public void testtrainModel(){
     Model model = new Model();
     assertNotNull(model);
        model.train();
    }

    public void testRandomDelta(){
        System.err.println(Model.getRandomDelta());
    }
    public void testRandom(){
        System.err.println(Model.getRandom());
     }

    public GoGame loadTestGame() {
        try {
            FileReader reader = new FileReader(
                    "sgf/testing/2007-01-12- BamboField- tokabe.sgf");
            jgogears.SGF.SGF parser = new jgogears.SGF.SGF(reader);
            SGFGameTree tree = parser.gameTree();
            GoGame goGame = new GoGame(tree);
            return goGame;
        } catch (Exception e) {
            System.err.println(e);;
            return null;
        }

    }

    public void testTrainedModel(){
        Model model = new Model();
        assertNotNull(model);
        System.out.println(" model size =  " + model.countNodes(model.getRoot()));
        model.train(this.loadTestGame());
        System.out.println(" model size =  " + model.countNodes(model.getRoot()));
        GoBoard board = new GoBoard();
        board = board.newBoard(new GoMove("white b2")); 
        board = board.newBoard(new GoMove("black k4")); 
        board = board.newBoard(new GoMove("white c3")); 
        board = board.newBoard(new GoMove("black g4")); 
        board = board.newBoard(new GoMove("white d4")); 
        board = board.newBoard(new GoMove("black h4")); 
        board = board.newBoard(new GoMove("white n4")); 
        float[][] r = model.getScores(board, BoardInterface.VERTEX_BLACK);
        assertNotNull(r);
        if (DEBUG){
        for (int i = 0; i< r.length;i++){
            for (int j = 0; j< r[i].length;j++)
                System.out.print(" " + r[i][j]);
            System.out.println();
        }
        }
    }
    public void testLoadAllSGFfiles() {
        Stack<String> files = new Stack<String>();
        files.push("sgf/2004-12");
        assertNotNull(files);
        Date before = new Date();
        assertNotNull(before);
        int filecount = 0;
        int movecount = 0;
        Model model = new Model();
        assertNotNull(model);
 
        
        while (files.size() > 0 && filecount < 100) {
            String filename = files.pop();
            File file = new File(filename);
            if (file.exists()) {
                if (!file.isDirectory()) {
                    // System.err.println("\"" + filename + "\" is not a
                    // directory, parsing as an SGF file");

                    GoGame goGame = GoGame.loadFromFile(file);
                    if (goGame.getSize() == 19){
                    filecount++;
                    model.train(goGame);

                    Date after = new Date();
                    System.err.println("loaded "+ filecount+ " files with "+ movecount+  " moves in " + (after.getTime()-before.getTime()) + " milliseconds,  model size =  " + model.countNodes(model.getRoot()));
                    }

                } else {
                    if (DEBUG)  System.err.println("\"" + filename + "\" is a directory");
                    String[] children = file.list();
                    for (int i = 0; i < children.length; i++) {
                        files.push(filename + "/" + children[i]);
                    }
                }
            }
 
        }
        Date after = new Date();
        System.err.println("loaded "+ filecount+ " files with "+ movecount+  " moves in " + (after.getTime()-before.getTime()) + " milliseconds");
        GoBoard board = new GoBoard();
        board = board.newBoard(new GoMove("white b2")); 
        board = board.newBoard(new GoMove("black k4")); 
        board = board.newBoard(new GoMove("white c3")); 
        board = board.newBoard(new GoMove("black g4")); 
        board = board.newBoard(new GoMove("white d4")); 
        board = board.newBoard(new GoMove("black h4")); 
        board = board.newBoard(new GoMove("white n4")); 
        float[][] r = model.getScores(board, BoardInterface.VERTEX_BLACK);
        assertNotNull(r);
        if (DEBUG){
            for (int i = 0; i< r.length;i++){
                for (int j = 0; j< r[i].length;j++)
                    System.out.print(" " + r[i][j]);
                System.out.println();
            }
            }

    }
    
    

}
