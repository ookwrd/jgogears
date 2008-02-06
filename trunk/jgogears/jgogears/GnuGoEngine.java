package jgogears;

import java.io.*;
import java.util.TreeSet;

/**
 * An engine wrapping an instance of the GnuGo computer-go player.
 * 
 * @author syeates
 */
public final class GnuGoEngine implements GTPStatelessInterface {

	public static final int SMALL_PAUSE = 3;
	public static final int LARGE_PAUSE = 33;

	private final String executablea = "/usr/games/gnugo";

	private final String executableb = "H:/gnugo-mingw-36.exe";

	private String args = "--mode gtp --level 1";

	protected boolean initialised = false;

	private Writer writer = null;

	private BufferedReader reader = null;

	private BufferedReader errreader = null;

	private Process process = null;
	public boolean DEBUG = false;

	public GnuGoEngine() throws IOException {
		this.initialise();
	}

	protected synchronized void check() {
		try {
			while (this.errreader.ready()) {
				System.err.print("GnuGo Process Error:\"" + this.errreader.readLine() + "\"");
			}
		} catch (Throwable t) {
			t.printStackTrace();
			System.err.println(t);
		}
	}

	public boolean clearBoard() {
		this.write(GTPConstants.CLEARBAORD + "\n\n");
		String s = this.read();
		Error e = GTPParserUtils.getError(s);
		if (null == e)
			return true;
		if (this.DEBUG)
			System.err.println("clearBoard:" + s);
		return false;
	}

	public TreeSet<Vertex> finalStatusList(String status) {
		this.write(GTPConstants.FINALSTATUSLIST + " " + status + "\n\n");
		String s = this.read();
		TreeSet<Vertex> v = GTPParserUtils.parseVertexList(s);
		if (this.DEBUG)
			System.err.println(s);
		if (this.DEBUG)
			System.err.println(v);
		return v;
	}

	public TreeSet<Vertex> fixedHandicap(short handicap) {
		this.write(GTPConstants.FIXEDHANDICAP + " " + handicap + "\n\n");
		String s = this.read();
		TreeSet<Vertex> v = GTPParserUtils.parseVertexList(s);
		if (this.DEBUG)
			System.err.println(s);
		if (this.DEBUG)
			System.err.println(v);
		return v;
	}

	public Move genMove(short colour) {
		this.write(GTPConstants.GENMOVE + " " + Move.colourString(colour) + "\n\n");
		String s = this.read();
		// GoMove move = GoMove.createVertex(s.substring(2));
		Vertex v = new Vertex(s);
		Move move = new Move(v.getRow(), v.getColumn(), colour);
		return move;
	}

	public String getArgs() {
		return this.args;
	}

	public String getEngineName() {
		this.write(GTPConstants.NAME + "\n\n");
		return this.read();
	}

	public String getEngineVersion() {
		this.write(GTPConstants.VERSION + "\n\n");
		return this.read();
	}

	public BufferedReader getErrreader() {
		return this.errreader;
	}

	public GTPScore getFinalScore() {
		this.write(GTPConstants.FINALSCORE + "\n\n");
		String result = this.read();
		result = GTPParserUtils.stripIntro(result);
		return new GTPScore(result);
	}

	public boolean getKnownCommand(String command) {
		// TODO Auto-generated method stub
		throw new Error();
	}

	public TreeSet<String> getListCommands() {
		// TODO Auto-generated method stub
		throw new Error();
	}

	public Process getProcess() {
		return this.process;
	}

	public int getProtocolVersion() {
		this.write(GTPConstants.PROTOCOLVERSION + " \n\n");
		String result = this.read();
		return Integer.parseInt(result);
	}

	public BufferedReader getReader() {
		return this.reader;
	}

	public Writer getWriter() {
		return this.writer;
	}

	public synchronized boolean initialise() throws IOException {
		if (this.initialised)
			return true;
		String executable = this.executablea;
		File exec = new File(this.executablea);
		if (!exec.exists() || !exec.canExecute()) {
			if (this.DEBUG)
				System.err.println(exec.toString() + " exists: " + exec.exists() + " exec: " + exec.canExecute());
			exec = new File(this.executableb);
			executable = this.executableb;
			if (!exec.exists() || !exec.canExecute()) {
				if (this.DEBUG)
					System.err.println(exec.toString() + " exists: " + exec.exists() + " exec: " + exec.canExecute());
				throw new java.io.IOException("Files don't exist or cannot be executed: \"" + this.executablea
						+ "\", \"" + this.executableb);
			}
		}

		try {
			String command = executable + " " + this.args;
			this.process = java.lang.Runtime.getRuntime().exec(command);
			this.reader = new java.io.BufferedReader(new InputStreamReader(this.process.getInputStream()));
			this.errreader = new java.io.BufferedReader(new InputStreamReader(this.process.getErrorStream()));
			this.writer = new OutputStreamWriter(this.process.getOutputStream());
			this.initialised = true;

			this.check();
			Thread.sleep(LARGE_PAUSE);
			return true;
		} catch (Throwable t) {
			t.printStackTrace();
			System.err.println(t);
			return false;
		}
	}

	public boolean loadsgf(String filename, int moveNumber) {
		if (moveNumber > 0)
			this.write(GTPConstants.LOADSGF + " " + filename + " " + moveNumber + "\n\n");
		else
			this.write(GTPConstants.LOADSGF + " " + filename + "\n\n");
		String s = this.read();
		Error e = GTPParserUtils.getError(s);
		if (null == e)
			return true;
		if (this.DEBUG)
			System.err.println("clearBoard:" + s);
		return false;

	}

	public TreeSet<Vertex> placeFreeHandicap(short handicap) {
		// TODO write tests for this
		this.write(GTPConstants.PLACEFREEHANDHANDICAP + " " + handicap + "\n\n");
		String s = this.read();
		TreeSet<Vertex> v = GTPParserUtils.parseVertexList(s);
		if (this.DEBUG)
			System.err.println(s);
		if (this.DEBUG)
			System.err.println(v);
		return v;
	}

	public boolean placeFreeHandicap(TreeSet<Vertex> stones) {
		this.write(GTPConstants.PLACEFREEHANDHANDICAP + " " + stones.toString() + "\n\n");
		String s = this.read();
		Error e = GTPParserUtils.getError(s);
		if (null == e)
			return true;
		if (this.DEBUG)
			System.err.println("clearBoard:" + s);
		return false;

	}

	public boolean play(Move move) {
		this.write(GTPConstants.PLAY + " " + move + "\n\n");
		String s = this.read();
		Error e = GTPParserUtils.getError(s);
		if (null == e)
			return true;
		if (this.DEBUG)
			System.err.println("clearBoard:" + s);
		return false;

	}

	public synchronized boolean quit() {
		if (this.initialised) {
			try {
				// handle the remote end
				this.write(GTPConstants.QUIT + "\n\n");
				this.writer.flush();

				// handle the local end
				this.check();
				this.initialised = false;
				this.reader.close();
				this.reader = null;
				this.errreader.close();
				this.errreader = null;
				this.writer.close();
				this.writer = null;
				this.process.destroy();
				this.process = null;

			} catch (Throwable t) {
				t.printStackTrace();
				System.err.println(t);
				return false;
			}
		}
		return true;

	}

	protected synchronized String read() {
		String s = "";
		String result = "";
		try {
			Thread.sleep(SMALL_PAUSE);
			while ((s != null) && (s.compareTo("") == 0)) {
				s = this.reader.readLine();
			}
			if (s == null)
				s = "";
			result = GTPParserUtils.stripIntro(s);

			if (this.DEBUG)
				System.err.println("GnuGo Process Output:\"" + s + "\" ==> \"" + result + "\"");

			this.check();
		} catch (Throwable t) {
			t.printStackTrace();
			System.err.println(t);
		}
		return result;
	}

	protected synchronized String readAll() {
		String s = "";
		try {
			Thread.sleep(SMALL_PAUSE);
			s = this.reader.readLine();
			while (this.reader.ready()) {
				Thread.yield();
				s = s + "\n" + this.reader.readLine();
			}
			if (this.DEBUG)
				System.err.println("GnuGo Process Output:\"" + s + "\"");

			this.check();
		} catch (Throwable t) {
			t.printStackTrace();
			System.err.println(t);
		}
		return s;
	}

	public Move regGenMove(short colour) {
		return this.genMove(colour);
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public boolean setBoardSize(short size) {
		this.write(GTPConstants.BOARDSIZE + " " + size + "\n\n");
		String s = this.read();
		Error e = GTPParserUtils.getError(s);
		if (this.DEBUG)
			System.err.println("clearBoard:" + s);
		if (null == e)
			return true;
		if (this.DEBUG)
			System.err.println("clearBoard:" + s);
		return false;

	}

	public void setErrreader(BufferedReader errreader) {
		this.errreader = errreader;
	}

	public boolean setKomi(double komi) {
		this.write(GTPConstants.KOMI + " " + komi + "\n\n");
		String s = this.read();
		Error e = GTPParserUtils.getError(s);
		if (null == e)
			return true;
		if (this.DEBUG)
			System.err.println("clearBoard:" + s);
		return false;

	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public boolean setTimeLeft(short colour, double byoYomiTime, double byoYomiStones) {
		this.write(GTPConstants.TIMELEFT + " " + Move.colourString(colour) + " " + ((int) byoYomiTime) + " "
				+ ((int) byoYomiStones) + "\n\n");
		String s = this.read();
		Error e = GTPParserUtils.getError(s);
		if (null == e)
			return true;
		if (this.DEBUG)
			System.err.println("clearBoard:" + s);
		return false;

	}

	public boolean setTimeSettings(double mainTime, double byoYomiTime, double byoYomiStones) {
		this.write(GTPConstants.TIMESETTINGS + " " + ((int) mainTime) + " " + ((int) byoYomiTime) + " "
				+ ((int) byoYomiStones) + "\n\n");
		String s = this.read();
		Error e = GTPParserUtils.getError(s);
		if (null == e)
			return true;
		if (this.DEBUG)
			System.err.println("clearBoard:" + s);
		return false;

	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}

	public BoardI showBoard() {
		this.write(GTPConstants.SHOWBOARD + "\n\n");
		String result = this.readAll();
		if (this.DEBUG)
			System.err.println(result);
		// TODO
		return null;
	}

	public boolean undo() {
		this.write(GTPConstants.UNDO + "\n\n");
		String s = this.read();
		if (s.contains(GTPConstants.CANNOTUNDO)) {
			return false;
		} else {
			return true;
		}
	}

	protected synchronized void write(String s) {
		try {
			this.writer.write(s);
			this.writer.flush();
			if (this.DEBUG)
				System.err.println("GnuGo Process Input:\"" + s + "\"");

			this.check();
		} catch (Throwable t) {
			t.printStackTrace();
			System.err.println(t);
		}
	}

}
