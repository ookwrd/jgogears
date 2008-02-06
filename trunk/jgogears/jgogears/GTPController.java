package jgogears;

import java.io.*;

public class GTPController {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		new GTPController().run();

	}

	java.io.BufferedReader reader = null;

	java.io.Writer writer = null;

	void consumeResult() throws java.io.IOException {
		while (this.reader.ready()) {
			String s = this.reader.readLine();
			System.err.println("GTPController: \"" + s + "\"");
		}
	}

	boolean run() throws java.io.IOException {
		PipedWriter farwriter = new PipedWriter();
		this.reader = new BufferedReader(new PipedReader(farwriter));
		PipedReader farreader = new PipedReader();
		this.writer = new PipedWriter(farreader);

		GTPEngine engine = new GTPEngine();
		engine.engine = new RandomEngine();
		engine.reader = new BufferedReader(farreader);
		engine.writer = farwriter;

		Thread thread = new Thread(engine);
		thread.start();

		this.setup();
		while (true) {
			this.consumeResult();
			Thread.yield();
		}
	}

	boolean setup() throws java.io.IOException {
		this.writer.write("boardsize 19\n\n");
		this.writer.flush();
		this.consumeResult();
		this.writer.write("clear_board\n\n");
		this.writer.flush();
		this.consumeResult();
		return true;
	}
}
