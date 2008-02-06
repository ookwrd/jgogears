package jgogears;

import java.util.*;

/**
 * A better bitset-based board implementation
 * 
 * @author syeates
 */
public class FasterBoard extends BoardI {
	/**
	 * the bit number for empty
	 */
	final static short OFFSET_EMPTY = 0;
	/**
	 * the bit number for colour / ko
	 */
	final static short OFFSET_COLOUR = 1;
	/**
	 * verbose debugging info
	 */
	private final boolean DEBUG = true;
	/**
	 * the underlying bitset holding the data
	 */
	private final BitSet bits = new BitSet();
	/**
	 * the size of the board
	 */
	private short size = 19;
	/**
	 * the ruleset
	 */
	private RuleSet rule = new NoKoRuleSet();

	/**
	 * Create a new board
	 */
	public FasterBoard() {
	}

	/**
	 * Create a new board
	 * 
	 * @param size
	 *            the size of the board
	 */
	public FasterBoard(int size) {
		this.size = (short) size;
	}

	/**
	 * Create a new board
	 * 
	 * @param size
	 *            the size of the board
	 * @param rule
	 *            the ruleset to use
	 */
	public FasterBoard(int size, RuleSet rule) {
		this.size = (short) size;
		this.rule = rule;
	}

	/**
	 * Create a new board
	 * 
	 * @param rule
	 *            the ruleset to use
	 */
	public FasterBoard(RuleSet rule) {
		this.rule = rule;
	}

	/**
	 * Create a new board
	 * 
	 * @param size
	 *            the size of the board
	 */

	public FasterBoard(short size) {
		this.size = size;
	}

	/**
	 * Create a new board
	 * 
	 * @param size
	 *            the size of the board
	 * @param rule
	 *            the ruleset to use
	 */
	public FasterBoard(short size, RuleSet rule) {
		this.size = size;
		this.rule = rule;
	}

	/*
	 * @see jgogears.BoardInterface#
	 */
	@Override
	public int getColour(int row, int column) {
		if ((row < 0) || (row >= this.size))
			return VERTEX_OFF_BOARD;
		if ((column < 0) || (column >= this.size))
			return VERTEX_OFF_BOARD;

		boolean empty = this.bits.get(this.getEmptyOffSet(row, column));
		boolean colour = this.bits.get(this.getColourOffSet(row, column));

		if (empty)
			if (colour)
				return VERTEX_WHITE;
			else
				return VERTEX_BLACK;
		else if (colour)
			return VERTEX_KO;
		else
			return VERTEX_EMPTY;
	}

	int getColourOffSet(int row, int column) {
		return OFFSET_COLOUR * this.size * this.size + (row * this.size) + (column);
	}

	int getEmptyOffSet(int row, int column) {
		return OFFSET_EMPTY * this.size * this.size + (row * this.size) + (column);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.BoardInterface#getSize()
	 */
	@Override
	public short getSize() {
		return this.size;
	}

	@Override
	public BoardI newBoard(Move move) {
		FasterBoard result = new FasterBoard(this.size);
		result.bits.or(this.bits);

		if (move == null)
			return result;
		if (move.getResign()) {
			// do nothing
		} else if (move.getPass()) {
			// do nothing, since GoBoard doesn't know whose turn it is
		} else {

			result.setColour(move.getRow(), move.getColumn(), move.getColour());

			// take the captures
			TreeSet<Vertex> captures = this.rule.captures(null, this, move);
			Iterator<Vertex> i = captures.iterator();
			while (i.hasNext()) {
				Vertex v = i.next();
				result.setColour(v.getRow(), v.getColumn(), BoardI.VERTEX_EMPTY);
			}

		}

		return result;
	}

	/*
	 * @see jgogears.BoardInterface#
	 */
	private int setColour(int row, int column, short colour) {
		if ((row < 0) || (column < 0) || (row >= this.size) || (column >= this.size)) {
			if (this.DEBUG)
				System.err.println("attempt to set a colour off-board");
			return VERTEX_OFF_BOARD;
		}
		int emptyB = this.getEmptyOffSet(row, column);
		int colourB = this.getColourOffSet(row, column);
		int result = this.getColour(row, column);

		switch (colour) {
		case VERTEX_EMPTY:
			this.bits.set(emptyB, false);
			this.bits.set(colourB, false);
			break;
		case VERTEX_KO:
			this.bits.set(emptyB, false);
			this.bits.set(colourB, true);
			break;
		case VERTEX_BLACK:
			this.bits.set(emptyB, true);
			this.bits.set(colourB, false);
			break;
		case VERTEX_WHITE:
			this.bits.set(emptyB, true);
			this.bits.set(colourB, true);
			break;
		default:
			throw new Error();
		}

		return result;
	}

}
