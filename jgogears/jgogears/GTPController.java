package jgogears;

import java.io.*;

public class GTPController {

    java.io.BufferedReader reader = null;

    java.io.Writer         writer = null;

    void consumeResult() throws java.io.IOException {
        while (reader.ready()) {
            String s = reader.readLine();
            System.err.println("GTPController: \"" + s + "\"");
        }
    }

    boolean setup() throws java.io.IOException {
        writer.write("boardsize 19\n\n");
        writer.flush();
        consumeResult();
        writer.write("clear_board\n\n");
        writer.flush();
        consumeResult();
        return true;
    }

    boolean run() throws java.io.IOException {
        PipedWriter farwriter = new PipedWriter();
        reader = new BufferedReader(new PipedReader(farwriter));
        PipedReader farreader = new PipedReader();
        writer = new PipedWriter(farreader);

        GTPEngine engine = new GTPEngine();
        engine.engine = new RandomEngine();
        engine.reader = new BufferedReader(farreader);
        engine.writer = farwriter;

        Thread thread = new Thread(engine);
        thread.start();

        this.setup();
        while (true) {
            consumeResult();
            Thread.yield();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
        new GTPController().run();

    }
}
