package jgogears;
import java.io.*;
import java.util.*;


/**
 * External representation of an SGF GoGame
 * 
 * @author syeates
 */
public final class GoGame {

    protected short            size        = 0;

    private String             blackPlayer = "";
    private String             whitePlayer = "";
    private short              komi        = 0;
    private short              handicap    = 0;
    private GTPScore           score       = null;
    private int                commentCount= 0;
    private boolean            branched    = false;
    
    private Rank               blackRank  = new Rank();
    private Rank               whiteRank = new Rank();
    private String             ruleset = "";
    private String             date = "";
    private String             maintime = "";
    private String             extraTime = "";
    private String             PC = "";
    private LinkedList<GoMove> movelist    = new LinkedList<GoMove>();
    private LinkedList<GoBoard> boardlist    = new LinkedList<GoBoard>();
    /**
     * default constractor
     */
    private GoGame() {
    }
    /**
     * build a game from a gametree
     * 
     * @param gameTree
     */
    public GoGame(SGFGameTree gameTree) {
        gameTree.init(this);
//         System.err.println("\"" + this.blackPlayer + "\" (" + this.blackRank + ") vs" + "\"" + 
//                         this.blackPlayer + "\" (" + this.blackRank + ") " + this.size + "x" + this.size + " h=" +
//                                this.handicap + " s=\"" + this.score+ "\" b=" + this.branched + " r="
//                                + this.ruleset + " date=\"" + this.date +"\" t=\"" + this.maintime  + 
//                                "\" e=\"" + this.extraTime +"\" PC=\"" + this.PC +"\"");
    }

    public short getSize() {
        return size;
    }

    public static GoGame loadFromFilename(String filename) {
        return loadFromFile(new File(filename));
    }

    public static GoGame loadFromFile(File file) {
  
            return SGFGameTree.loadFromFile(file);
    }

    public Iterator<GoMove> getMoves() {
        return this.movelist.iterator();
    }

    public Iterator<GoBoard> getBoards() {
        if (this.boardlist != null) {
        
        Iterator<GoMove> moves = getMoves();
        boardlist = new LinkedList<GoBoard>();
        GoBoard board = new GoBoard(size);
        boardlist.add(board);
        while (moves.hasNext()) {
            GoMove move = moves.next();
            board = board.newBoard(move);
            boardlist.add(board);
        }
        }
        return boardlist.iterator();
    }

    public short getHandicap() {
        return handicap;
    }
    public void setHandicap(short handicap) {
        this.handicap = handicap;
    }
    public short getKomi() {
        return komi;
    }
    public void setKomi(short komi) {
        this.komi = komi;
    }
    public GTPScore getScore() {
        return score;
    }
    public void setScore(GTPScore score) {
        this.score = score;
    }
    public String getWhitePlayer() {
        return whitePlayer;
    }
    public void setMoveList(LinkedList<GoMove> moves) {
        this.movelist = moves;
    }
    public LinkedList<GoMove> getMoveList() {
        return this.movelist;
    }
    public String getBlackPlayer() {
        return blackPlayer;
    }
    public void setSize(short size) {
        this.size = size;
    }
    public boolean isBranched() {
        return branched;
    }
    public int getCommentCount() {
        return commentCount;
    }
    public LinkedList<GoMove> getMovelist() {
        return movelist;
    }
    public void setMovelist(LinkedList<GoMove> movelist) {
        this.movelist = movelist;
    }
    public void setBlackPlayer(String blackPlayer) {
        this.blackPlayer = blackPlayer;
    }
    public void setBranched(boolean branched) {
        this.branched = branched;
    }
    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
    public void setWhitePlayer(String whitePlayer) {
        this.whitePlayer = whitePlayer;
    }
    public Rank getBlackRank() {
        return blackRank;
    }
    public void setBlackRank(String blackRank) {
        this.blackRank = new Rank(blackRank);
    }
    public void setBlackRank(Rank blackRank) {
        this.blackRank = blackRank;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getExtraTime() {
        return extraTime;
    }
    public void setExtraTime(String extraTime) {
        this.extraTime = extraTime;
    }
    public String getMaintime() {
        return maintime;
    }
    public void setMaintime(String maintime) {
        this.maintime = maintime;
    }
    public String getPC() {
        return PC;
    }
    public void setPC(String pc) {
        PC = pc;
    }
    public String getRuleset() {
        return ruleset;
    }
    public void setRuleset(String ruleset) {
        this.ruleset = ruleset;
    }
    public Rank getWhiteRank() {
        return whiteRank;
    }
    public void setWhiteRank(String whiteRank) {
        this.whiteRank = new Rank(whiteRank);
    }
    public void setWhiteRank(Rank whiteRank) {
        this.whiteRank = whiteRank;
    }

}