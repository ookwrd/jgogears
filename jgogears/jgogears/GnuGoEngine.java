package jgogears;

import java.io.*;
/**
 * 
 * An engine wrapping an instance of the GnuGo computer-go player.
 * 
 * @author syeates
 */
public final class GnuGoEngine implements GTPStatelessInterface {

    private String         executable  = "/usr/games/gnugo";

    private String         args        = "--mode gtp --level 1";

    protected boolean      initialised = false;

    private Writer         writer      = null;

    private BufferedReader reader      = null;

    private BufferedReader errreader   = null;

    private Process        process     = null;

    public boolean         DEBUG       = true;

    public static final int SMALL_PAUSE = 3;
    public static final int LARGE_PAUSE = 33;
    
    public GnuGoEngine() {
        initialise();
    }

    public synchronized boolean initialise() {
        if (!this.initialised)
            try {
                String command = this.executable + " " + this.args;
                this.process = java.lang.Runtime.getRuntime().exec(command);
                this.reader = new java.io.BufferedReader(new InputStreamReader(
                        process.getInputStream()));
                this.errreader = new java.io.BufferedReader(
                        new InputStreamReader(process.getErrorStream()));
                this.writer = new OutputStreamWriter(process.getOutputStream());
                this.initialised = true;
                
                this.check();
                Thread.sleep(LARGE_PAUSE);
                return true;
            } catch (Throwable t) {
                t.printStackTrace();
                System.err.println(t);
                return false;
            }
            return true;
    }

    protected synchronized void write(String s) {
        try {
            writer.write(s);
            writer.flush();
            if (DEBUG) System.err.println("GnuGo Process Input:\"" + s + "\"");

            this.check();
        } catch (Throwable t) {
            t.printStackTrace();
            System.err.println(t);
        }
    }

    protected synchronized String read() {
        String s = "";
        String result = "";
        try {
            Thread.sleep(SMALL_PAUSE);
            while (s != null && s.compareTo("") == 0) {
                s = reader.readLine();
            }
            if (s == null)
                s = "";
            result = GTPParserUtils.stripIntro(s);
            
            if (DEBUG)
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
            s = reader.readLine();
            while (reader.ready()) {
                Thread.yield();
                s = s + "\n" + reader.readLine();
            }
            if (DEBUG)
                System.err.println("GnuGo Process Output:\"" + s + "\"");

            this.check();
        } catch (Throwable t) {
            t.printStackTrace();
            System.err.println(t);
        }
        return s;
    }

    protected synchronized void check() {
        try {
            while (this.errreader.ready()) {
                System.err.print("GnuGo Process Error:\""
                        + this.errreader.readLine() + "\"");
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
        if (null == e) return true;
        if (DEBUG) System.err.println("clearBoard:" + s); 
        return false;
    }

    public GoMove[] finalStatusList(String status) {
        this.write(GTPConstants.FINALSTATUSLIST + " " + status + "\n\n");
        String s = this.read();
        GoMove[] v = GTPParserUtils.parseVertexList(s);
        if (DEBUG) System.err.println(s);
        if (DEBUG) for (int i=0;i<v.length;i++)System.err.println(v[i]);
        return v;
    }

    public GoMove[] fixedHandicap(short handicap) {
        this.write(GTPConstants.FIXEDHANDICAP + " " + handicap + "\n\n");
        String s = this.read();
        GoMove[] v = GTPParserUtils.parseVertexList(s);
        if (DEBUG) System.err.println(s);
        if (DEBUG) System.err.println(v);
        return v;
    }

    public GoMove genMove(short colour) {
        this.write(GTPConstants.GENMOVE + " " + GoMove.colourString(colour)
                + "\n\n");
        String s = this.read();
//        GoMove move = GoMove.createVertex(s.substring(2));
        GoMove move = GoMove.createVertex(s);
        move.setColour(colour);
        return move;
    }

    public String getEngineName() {
        this.write(GTPConstants.NAME + "\n\n");
        return this.read();
    }

    public String getEngineVersion() {
        this.write(GTPConstants.VERSION + "\n\n");
        return this.read();
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

    public String[] getListCommands() {
        // TODO Auto-generated method stub
       throw new Error();
    }

    public int getProtocolVersion() {
        this.write(GTPConstants.PROTOCOLVERSION + " \n\n");
        String result = this.read();
        return Integer.parseInt(result);
    }

    public boolean loadsgf(String filename, int moveNumber) {
        if (moveNumber > 0)
            this.write(GTPConstants.LOADSGF + " " + filename + " " + moveNumber
                    + "\n\n");
        else
            this.write(GTPConstants.LOADSGF + " " + filename + "\n\n");  
        String s = this.read();
        Error e = GTPParserUtils.getError(s);
        if (null == e) return true;
        if (DEBUG) System.err.println("clearBoard:" + s); 
        return false;

    }

    public boolean placeFreeHandicap(GoMove[] stones) {
        this.write(GTPConstants.PLACEFREEHANDHANDICAP + " " + stones.toString()
                + "\n\n");
        String s = this.read();
        Error e = GTPParserUtils.getError(s);
        if (null == e) return true;
        if (DEBUG) System.err.println("clearBoard:" + s); 
        return false;

    }

    public GoMove[] placeFreeHandicap(short handicap) {
        //TODO write tests for this
        this.write(GTPConstants.PLACEFREEHANDHANDICAP + " " + handicap + "\n\n");
        String s = this.read();
        GoMove[] v = GTPParserUtils.parseVertexList(s);
        if (DEBUG) System.err.println(s);
        if (DEBUG) System.err.println(v);
        return v;
    }

    public boolean play(GoMove move) {
        this.write(GTPConstants.PLAY + " " + move + "\n\n");
        String s = this.read();
        Error e = GTPParserUtils.getError(s);
        if (null == e) return true;
        if (DEBUG) System.err.println("clearBoard:" + s); 
        return false;


    }

    public synchronized boolean quit() {
        if (this.initialised) {
            try {
                // handle the remote end
                this.write(GTPConstants.QUIT + "\n\n");
                writer.flush();

                // handle the local end
                check();
                this.initialised = false;
                reader.close();
                reader = null;
                errreader.close();
                errreader = null;
                writer.close();
                writer = null;
                process.destroy();
                process = null;

            } catch (Throwable t) {
                t.printStackTrace();
                System.err.println(t);
                return false;
            }
        }
        return true;

    }

    public GoMove regGenMove(short colour) {
        return genMove(colour);
    }

    public boolean setBoardSize(short size) {
        this.write(GTPConstants.BOARDSIZE + " " + size + "\n\n");
        String s = this.read();
        Error e = GTPParserUtils.getError(s);
        if (null == e) return true;
        if (DEBUG) System.err.println("clearBoard:" + s); 
        return false;

    }

    public boolean setKomi(double komi) {
        this.write(GTPConstants.KOMI + " " + komi + "\n\n");
        String s = this.read();
        Error e = GTPParserUtils.getError(s);
        if (null == e) return true;
        if (DEBUG) System.err.println("clearBoard:" + s); 
        return false;

    }

    public boolean setTimeLeft(short colour, double byoYomiTime, double byoYomiStones) {
        this.write(GTPConstants.TIMELEFT + " " + GoMove.colourString(colour)
                + " " + ((int)byoYomiTime) + " " + ((int)byoYomiStones) + "\n\n");
        String s = this.read();
        Error e = GTPParserUtils.getError(s);
        if (null == e) return true;
        if (DEBUG) System.err.println("clearBoard:" + s); 
        return false;

    }

    public boolean setTimeSettings(double mainTime, double byoYomiTime,
            double byoYomiStones) {
        this.write(GTPConstants.TIMESETTINGS + " " + ((int)mainTime) + " "
                + ((int)byoYomiTime) + " " + ((int)byoYomiStones) + "\n\n");
        String s = this.read();
        Error e = GTPParserUtils.getError(s);
        if (null == e) return true;
        if (DEBUG) System.err.println("clearBoard:" + s); 
        return false;

    }

    public BoardInterface showBoard() {
        this.write(GTPConstants.SHOWBOARD + "\n\n");
        String result = this.readAll();
        if (DEBUG) System.err.println(result);
        // TODO
        return GTPParserUtils.parseGnuGoBoard(result);
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

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getExecutable() {
        return executable;
    }

    public void setExecutable(String executable) {
        this.executable = executable;
    }

    public BufferedReader getErrreader() {
        return errreader;
    }

    public void setErrreader(BufferedReader errreader) {
        this.errreader = errreader;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

}