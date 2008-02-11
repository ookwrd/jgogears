package jgogears;

import java.util.*;
import java.lang.reflect.*;


/**
 * Abstract interface to a cabin. Knows about the size of the board, which stones are where, and about colours. Knows
 * nothing of the history of the board or whose turn it is to play.
 * 
 * @author Stuart
 */
public abstract class BoardI {

	/** The default board size. */
	public static final short DEFAULT_BOARD_SIZE = 19;
	
	/** The MAXIMUM value for a vertex colour. */
	public static final short VERTEX_MAX = 4;
	
	/** A vertex in ko. */
	public static final short VERTEX_OFF_BOARD = 4;
	
	/** A vertex in ko. */
	public static final short VERTEX_KO = 3;
	
	/** A vertex with a black stone. */
	public static final short VERTEX_BLACK = 2;
	
	/** A vertex with a white stone. */
	public static final short VERTEX_WHITE = 1;
	
	/** A vertex without a stone (and not in KO. */
	public static final short VERTEX_EMPTY = 0;
	
	/** The MINIMUM value for a vertex colour. */
	public static final short VERTEX_MIN = 0;
	
	/** The Constant DEFAULT_ZOBRIST. */
	public final static boolean DEFAULT_ZOBRIST = true;

	/**
	 * parse the colour of a move.
	 * 
	 * @param colourString the colour string
	 * 
	 * @return the colour as a short
	 */
	public static short parseColour(String colourString) {

		if (colourString.compareTo("w") == 0) {
			return BoardI.VERTEX_WHITE;
		} else if (colourString.compareTo("white") == 0) {
			return BoardI.VERTEX_WHITE;
		} else if (colourString.compareTo("b") == 0) {
			return BoardI.VERTEX_BLACK;
		} else if (colourString.compareTo("black") == 0) {
			return BoardI.VERTEX_BLACK;
		} else {
			throw new IllegalArgumentException("trying to parse (1) \"" + colourString + "\" as a colour");
		}
	}

	/** The zobrist. */
	protected Zobrist zobrist = null;

	/**
	 * Instantiates a new board
	 */
	public BoardI() {
		if (DEFAULT_ZOBRIST)
			this.zobrist = new Zobrist();
	}

	/**
	 * The Constructor.
	 * 
	 * @param zobrist the zobrist
	 */
	public BoardI(boolean zobrist) {
		this.zobrist = new Zobrist();
	}

	/**
	 * get the colour of a vertex.
	 * 
	 * @param row the row
	 * @param column the column
	 * 
	 * @return the colour
	 */
	public abstract short getColour(int row, int column);

	/**
	 * set the colour of a vertex.
	 * 
	 * @param row the row
	 * @param column the column
	 * 
	 * @return the colour
	 */
	public abstract void setColour(int row, int column, short colour);

	/**
	 * get the size of this board.
	 * 
	 * @return the size
	 */
	public short getSize(){
		return size;
	}
	
	/** the size of the board. */
	protected short size = 19;


	/**
	 * Gets the zobrist.
	 * 
	 * @return the zobrist
	 */
	public Zobrist getZobrist() {
		return this.zobrist;
	}


	/**
	 * Sets the zobrist.
	 * 
	 * @param zobrist the zobrist
	 */
	protected void setZobrist(Zobrist zobrist) {
		this.zobrist = zobrist;
	}
	
	/** the ruleset. */
	protected RuleSet ruleSet = new NoKoRuleSet();
	
	/**
	 * create a new board based on the current board plus a move.
	 * 
	 * @param move the move
	 * 
	 * @return the new board 
	 */
	abstract public BoardI newBoard( Move move);
		/**
		 * create a new board based on the current board plus a move.
		 * 
		 * @param move the move
		 * 
		 * @return the new board 
		 */
		protected void copydata(BoardI old , Move move) {
		size = old.getSize();
		if (size <3 || size >25)
				throw new Error();
		zobrist = old.getZobrist();
		ruleSet = old.getRuleSet();
		
		for (short i = 0; i < size; i++)
			for (short j = 0; j < size; j++){
				short colour = old.getColour(i, j);
				if (colour == VERTEX_KO)
					this.setColour(i, j, VERTEX_EMPTY);
				else
					this.setColour(i, j, colour);
			}
		if (move == null)
			return;
		if (move.getResign()) {
			return;
		} else if (move.getPass()) {
			// do nothing, since GoBoard doesn't know whose turn it is
		} else {
			// place the stone
			this.setColour(move.getRow(), move.getColumn(), move.getColour());
			if (zobrist != null)
				setZobrist(new Zobrist(this.zobrist, move.getRow(), move.getColumn(), BoardI.VERTEX_EMPTY));

			// take the captures
			TreeSet<Vertex> captures = old.getRuleSet().captures(null, old, move);
			if (captures.size() > 0){
			//System.err.println("captured" + captures);
			Iterator<Vertex> i = captures.iterator();
			while (i.hasNext()) {
				Vertex v = i.next();
				//System.err.println("captured" + v);
				if (captures.size() == 1)
					this.setColour(v.getRow(), v.getColumn(), BoardI.VERTEX_KO);
				else 
					this.setColour(v.getRow(), v.getColumn(), BoardI.VERTEX_EMPTY);
				
				if (this.zobrist != null)
					this.setZobrist(new Zobrist(this.getZobrist(), v.getRow(), v.getColumn(), BoardI.VERTEX_EMPTY));
			}
		}
		}
	}

	/**
	 * get the ruleSet
	 * @return the ruleSet
	 */
	public final RuleSet getRuleSet() {
		return ruleSet;
	}

}
