package jgogears;

import java.util.*;

/**
 * A bit-wise board representation using a naive bit ordering.
 * 
 * @author syeates
 */
public class FastBoard extends BoardI {
	/**
	 * How many bits we're allocating per square on the board
	 */
	final static short BITS_PER_VERTEX = 4;
	/**
	 * The underlying BitSet
	 */
	private BitSet bits = null;
	/**
	 * the size of the board
	 */
	private short size = 19;
	/**
	 * The ruleset in use on this board
	 */
	private RuleSet rule = new NoKoRuleSet();

	/**
	 * Constructor for default board size
	 */

	public FastBoard() {
		this.bits = new BitSet();
	}

	/**
	 * Constructor for a particular size board
	 * 
	 * @param size
	 *            size of the board
	 */

	public FastBoard(int size) {
		this.bits = new BitSet();
		this.size = (short) size;
	}

	/**
	 * constructor of specially sized boards
	 * 
	 * @param size
	 *            the size of the board
	 * @param rule
	 *            the ruleset to use
	 */
	public FastBoard(int size, RuleSet rule) {
		this.size = (short) size;
		this.rule = rule;
	}

	/**
	 * Default constructor
	 */
	public FastBoard(RuleSet rule) {
		this.rule = rule;
	}

	/**
	 * Constructor for a particular size board
	 * 
	 * @param size
	 *            size of the board
	 */

	public FastBoard(short size) {
		this.bits = new BitSet();
		this.size = size;
	}

	/**
	 * constructor of specially sized boards
	 * 
	 * @param size
	 *            the size of the board
	 * @param rule
	 *            the ruleset to use
	 */
	public FastBoard(short size, RuleSet rule) {
		this.size = size;
		this.rule = rule;
	}

	/*
	 * @see jgogears.BoardInterface#
	 */
	@Override
	public int getColour(int row, int column) {
		if ((row < 0) || (column < 0) || (row >= this.size) || (column >= this.size))
			return VERTEX_OFF_BOARD;

		int offset = (row * BITS_PER_VERTEX * this.size) + (column * BITS_PER_VERTEX);
		int result = -1;
		if (this.bits.get(offset))
			if (this.bits.get(offset + 1))
				return VERTEX_WHITE;
			else
				return VERTEX_BLACK;
		else if (this.bits.get(offset + 1))
			return VERTEX_KO;
		else
			return VERTEX_EMPTY;

	}

	/**
	 * Make a new board based on this board, with an extra move
	 */
	@Override
	public BoardI newBoard(Move move) {
		FastBoard result = new FastBoard(this.size);
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
	protected int setColour(int row, int column, short colour) {
		int offset = (row * BITS_PER_VERTEX * this.size) + (column * BITS_PER_VERTEX);
		int result = this.getColour(row, column);

		switch (colour) {
		case VERTEX_EMPTY:
			this.bits.set(offset, false);
			this.bits.set(offset + 1, false);
			break;
		case VERTEX_KO:
			this.bits.set(offset, false);
			this.bits.set(offset + 1, true);
			break;
		case VERTEX_BLACK:
			this.bits.set(offset, true);
			this.bits.set(offset + 1, false);
			break;
		case VERTEX_WHITE:
			this.bits.set(offset, true);
			this.bits.set(offset + 1, true);
			break;
		default:
			throw new Error();
		}

		return result;
	}

}
