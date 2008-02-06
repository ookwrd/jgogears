package jgogears;

import java.io.*;
import java.util.*;

/**
 * External representation of a game of go. This is currently very heavily influenced by SGF, it needs to be
 * generalised.
 * 
 * @author syeates
 */
public final class Game {
	/**
	 * Load an SGF file into memory
	 * 
	 * @param file
	 *            the file to load
	 * @return
	 * @throws IOException
	 */

	public static Game loadFromFile(File file) throws IOException {
		try {

			return SGFGameTree.loadFromFile(file);
		} catch (jgogears.SGF.TokenMgrError e) {
			throw new java.io.IOException("Error reading \"" + file + "\" " + e.getLocalizedMessage());
		}
	}

	/**
	 * Load an SGF file into memory
	 * 
	 * @param filename
	 *            the filename to load
	 * @return
	 * @throws IOException
	 */

	public static Game loadFromFilename(String filename) throws IOException {
		return loadFromFile(new File(filename));
	}

	protected short size = 0;
	private String blackPlayer = "";
	private String whitePlayer = "";
	private short komi = 0;
	private short handicap = 0;
	private GTPScore score = null;

	private int commentCount = 0;
	private boolean branched = false;
	private Rank blackRank = new Rank();
	private Rank whiteRank = new Rank();
	private String ruleset = "";
	private String date = "";
	private String maintime = "";
	private String extraTime = "";
	private String PC = "";

	private LinkedList<Move> movelist = new LinkedList<Move>();

	private LinkedList<BoardI> boardlist = new LinkedList<BoardI>();

	/**
	 * default constractor
	 */
	public Game() {
	}

	/**
	 * default constractor
	 */
	public Game(int size) {
		this.size = (short) size;
	}

	/**
	 * get the size
	 * @return the size
	 */
	public final short getSize() {
		return size;
	}

	/**
	 * set the size
	 * @param size the size to set
	 */
	public final void setSize(short size) {
		this.size = size;
	}

	/**
	 * get the blackPlayer
	 * @return the blackPlayer
	 */
	public final String getBlackPlayer() {
		return blackPlayer;
	}

	/**
	 * set the blackPlayer
	 * @param blackPlayer the blackPlayer to set
	 */
	public final void setBlackPlayer(String blackPlayer) {
		this.blackPlayer = blackPlayer;
	}

	/**
	 * get the whitePlayer
	 * @return the whitePlayer
	 */
	public final String getWhitePlayer() {
		return whitePlayer;
	}

	/**
	 * set the whitePlayer
	 * @param whitePlayer the whitePlayer to set
	 */
	public final void setWhitePlayer(String whitePlayer) {
		this.whitePlayer = whitePlayer;
	}

	/**
	 * get the komi
	 * @return the komi
	 */
	public final short getKomi() {
		return komi;
	}

	/**
	 * set the komi
	 * @param komi the komi to set
	 */
	public final void setKomi(short komi) {
		this.komi = komi;
	}

	/**
	 * get the handicap
	 * @return the handicap
	 */
	public final short getHandicap() {
		return handicap;
	}

	/**
	 * set the handicap
	 * @param handicap the handicap to set
	 */
	public final void setHandicap(short handicap) {
		this.handicap = handicap;
	}

	/**
	 * get the score
	 * @return the score
	 */
	public final GTPScore getScore() {
		return score;
	}

	/**
	 * set the score
	 * @param score the score to set
	 */
	public final void setScore(GTPScore score) {
		this.score = score;
	}

	/**
	 * get the commentCount
	 * @return the commentCount
	 */
	public final int getCommentCount() {
		return commentCount;
	}

	/**
	 * set the commentCount
	 * @param commentCount the commentCount to set
	 */
	public final void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	/**
	 * get the branched
	 * @return the branched
	 */
	public final boolean isBranched() {
		return branched;
	}

	/**
	 * set the branched
	 * @param branched the branched to set
	 */
	public final void setBranched(boolean branched) {
		this.branched = branched;
	}

	/**
	 * get the blackRank
	 * @return the blackRank
	 */
	public final Rank getBlackRank() {
		return blackRank;
	}

	/**
	 * set the blackRank
	 * @param blackRank the blackRank to set
	 */
	public final void setBlackRank(Rank blackRank) {
		this.blackRank = blackRank;
	}

	/**
	 * get the whiteRank
	 * @return the whiteRank
	 */
	public final Rank getWhiteRank() {
		return whiteRank;
	}

	/**
	 * set the whiteRank
	 * @param whiteRank the whiteRank to set
	 */
	public final void setWhiteRank(Rank whiteRank) {
		this.whiteRank = whiteRank;
	}

	/**
	 * get the ruleset
	 * @return the ruleset
	 */
	public final String getRuleset() {
		return ruleset;
	}

	/**
	 * set the ruleset
	 * @param ruleset the ruleset to set
	 */
	public final void setRuleset(String ruleset) {
		this.ruleset = ruleset;
	}

	/**
	 * get the date
	 * @return the date
	 */
	public final String getDate() {
		return date;
	}

	/**
	 * set the date
	 * @param date the date to set
	 */
	public final void setDate(String date) {
		this.date = date;
	}

	/**
	 * get the maintime
	 * @return the maintime
	 */
	public final String getMaintime() {
		return maintime;
	}

	/**
	 * set the maintime
	 * @param maintime the maintime to set
	 */
	public final void setMaintime(String maintime) {
		this.maintime = maintime;
	}

	/**
	 * get the extraTime
	 * @return the extraTime
	 */
	public final String getExtraTime() {
		return extraTime;
	}

	/**
	 * set the extraTime
	 * @param extraTime the extraTime to set
	 */
	public final void setExtraTime(String extraTime) {
		this.extraTime = extraTime;
	}

	/**
	 * get the pC
	 * @return the pC
	 */
	public final String getPC() {
		return PC;
	}

	/**
	 * set the pC
	 * @param pc the pC to set
	 */
	public final void setPC(String pc) {
		PC = pc;
	}

	/**
	 * get the movelist
	 * @return the movelist
	 */
	public final LinkedList<Move> getMovelist() {
		return movelist;
	}
	/**
	 * get the movelist
	 * @return the movelist
	 */
	public final Iterator<Move> getMoves() {
		return movelist.iterator();
	}

	/**
	 * set the movelist
	 * @param movelist the movelist to set
	 */
	public final void setMovelist(LinkedList<Move> movelist) {
		this.movelist = movelist;
	}

	/**
	 * get the boardlist
	 * @return the boardlist
	 */
	public final LinkedList<BoardI> getBoardlist() {
		return boardlist;
	}


	/**
	 * set the boardlist
	 * @param boardlist the boardlist to set
	 */
	public final void setBoardlist(LinkedList<BoardI> boardlist) {
		this.boardlist = boardlist;
	}

	/**
	 * build a game from a gametree
	 * 
	 * @param gameTree
	 */
	public Game(SGFGameTree gameTree) {
		gameTree.init(this);
		// System.err.println("\"" + this.blackPlayer + "\" (" + this.blackRank
		// + ") vs" + "\"" +
		// this.blackPlayer + "\" (" + this.blackRank + ") " + this.size + "x" +
		// this.size + " h=" +
		// this.handicap + " s=\"" + this.score+ "\" b=" + this.branched + " r="
		// + this.ruleset + " date=\"" + this.date +"\" t=\"" + this.maintime +
		// "\" e=\"" + this.extraTime +"\" PC=\"" + this.PC +"\"");
	}


	/**
	 * Get an iterator of all the boards in the game
	 * 
	 * @return an iterator
	 */
	public Iterator<BoardI> getBoards() {
		if (this.boardlist != null) {

			Iterator<Move> moves = this.getMoves();
			this.boardlist = new LinkedList<BoardI>();
			BoardI board = new Board(this.size);
			this.boardlist.add(board);
			while (moves.hasNext()) {
				Move move = moves.next();
				board = board.newBoard(move);
				this.boardlist.add(board);
			}
		}
		return this.boardlist.iterator();
	}



}
