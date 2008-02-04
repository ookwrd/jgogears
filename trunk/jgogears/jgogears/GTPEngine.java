package jgogears;

import java.io.*;

import jgogears.*;

/**
 * The engine side of a GTP connection. Uses a GTPInterface to talk to the
 * engine that actually does the work.
 * 
 * @author syeates
 * 
 */
public class GTPEngine implements Runnable {

	BufferedReader reader = null;
	Writer writer = null;
	GTPState state = new GTPState();
	GTPInterface engine = null;

	/**
	 * compares two strings and returns true is the shorter is the first
	 * substring of the second
	 * 
	 * @param a
	 *            the first string
	 * @param b
	 *            the second string
	 * @return true if they match
	 */
	boolean compare(String a, String b) {
		// ensure b is longer that a
		if (a.length() > b.length()) {
			String c = a;
			a = b;
			b = c;
		}
		b = b.substring(0, a.length());
		return b.compareTo(a) == 0;

	}

	/**
	 * Processes single command
	 * 
	 * @param command
	 *            the command to process
	 * @return true on success
	 * @throws Exception
	 */
	boolean processCommand(String command) throws Exception {
		command = command.toLowerCase();
		if (compare(command, GTPConstants.BOARDSIZE)) {
			short size = Short.parseShort(command
					.substring(GTPConstants.BOARDSIZE.length() + 1));
			engine.setBoardSize(size, state);
			engine.clearBoard(state);
			return true;
		} else if (compare(command, GTPConstants.CLEARBAORD)) {
			engine.clearBoard(state);
			return true;
		} else if (compare(command, GTPConstants.QUIT)) {
			engine.quit();
			writer.flush();
			writer.close();
			writer = null;
			reader.close();
			reader = null;
			state = null;
			System.err.println("quiting");
			return true;
		} else if (compare(command, GTPConstants.KNOWNCOMMAND)) {
			String commandS = command.substring(GTPConstants.KNOWNCOMMAND
					.length() + 1);
			boolean result = engine.getKnownCommand(commandS);
			writer.write("" + result);
			return true;
		} else if (compare(command, GTPConstants.LISTCOMMANDS)) {
			System.err.println("don't know how to handle the command:"
					+ command);
			return false;
		} else if (compare(command, GTPConstants.NAME)) {
			writer.write(engine.getEngineName());
			return true;
		} else if (compare(command, GTPConstants.VERSION)) {
			writer.write(engine.getEngineVersion());
			return true;
		} else if (compare(command, GTPConstants.PROTOCOLVERSION)) {
			int version = engine.getProtocolVersion();
			if (version != 2)
				throw new Error();
			writer.write("" + version);
			return true;
		} else if (compare(command, GTPConstants.PLAY)) {
			Move move = new Move(command
					.substring(GTPConstants.PLAY.length() + 1));
			engine.play(move, state);
			return true;
		} else if (compare(command, GTPConstants.GENMOVE)) {
			short colour = BoardI.parseColour(command
					.substring(GTPConstants.PLAY.length() + 1));
			Move move = engine.genMove(colour, state);
			writer.write(move.toVertexString());
			return true;
		} else if (compare(command, GTPConstants.UNDO)) {
			if (!engine.undo(state)) {
				writer.write(GTPConstants.CANNOTUNDO);
				return false;
			}
			return true;
		} else if (compare(command, GTPConstants.FINALSCORE)) {
			writer.write(engine.getFinalScore(state).toString());
			return true;
		} else if (compare(command, GTPConstants.LOADSGF)) {
			System.err.println("clearing board");
			throw new Error("don't know how to handle the command:" + command);
		} else if (compare(command, GTPConstants.REGGENMOVE)) {
			short colour = BoardI.parseColour(command
					.substring(GTPConstants.PLAY.length() + 1));
			Move move = engine.regGenMove(colour, state);
			writer.write(move.toVertexString());
			return true;
		} else
			return false;
	}

	void stuff() throws Exception {
		boolean quit = false;
		while (!quit) {
			String s = reader.readLine();
			System.err.println("GTPEngine: \"" + s + "\"");
			if (s.length() == 0)
				continue;
			if (s.charAt(0) == '#')
				continue;
			System.err.println("looks like a real command...");
			quit = this.processCommand(s);
			Thread.yield();
		}
	}

	public void run() {
		try {
			stuff();
		} catch (Throwable t) {
			t.printStackTrace();
			System.err.println(t);
		}
	}
}
