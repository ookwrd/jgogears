package jgogears;

import java.util.*;

import jgogears.*;

public class FasterBoard implements BoardI {
	
	private boolean DEBUG = true;

	private BitSet bits = new BitSet();
	private short size = 19;
	private KoRule rule = new NoKoRule();

	
	final static short OFFSET_EMPTY = 0;
	final static short OFFSET_COLOUR = 1;

	public FasterBoard(short size) {
		this.size = size;
	}

	public FasterBoard(int size) {
		this.size = (short) size;
	}

	public FasterBoard() {
	}
	/**
	 * Default constructor
	 */
	public FasterBoard(KoRule rule) {
		this.rule = rule;
	}

	/**
	 * constructor of specially sized boards
	 */
	public FasterBoard(short size,KoRule rule) {
		this.size = size;
		this.rule = rule;
	}

	/**
	 * constructor of specially sized boards
	 */
	public FasterBoard(int size, KoRule rule) {
		this.size = (short) size;
		this.rule = rule;
	}

	public BoardI newBoard(GoMove move) {
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
			TreeSet<Vertex> captures = rule.captures(null, this, move);
			Iterator<Vertex> i = captures.iterator();
			while (i.hasNext()){
				Vertex v = i.next();
				result.setColour(v.getRow(), v.getColumn(), Board.VERTEX_EMPTY);
			}

		}

		return result;
	}

	int getEmptyOffSet(int row, int column) {
		return OFFSET_EMPTY * size * size + (row * size) + (column);
	}

	int getColourOffSet(int row, int column) {
		return OFFSET_COLOUR * size * size + (row * size) + (column);
	}

	/*
	 * @see jgogears.BoardInterface#
	 */
	private int setColour(int row, int column, short colour) {
		if (row < 0 || column < 0 || row >= size || column >= size){
			if (DEBUG)
				System.err.println("attempt to set a colour off-board");
			return VERTEX_OFF_BOARD;
		}
		int emptyB = getEmptyOffSet(row, column);
		int colourB = getColourOffSet(row, column);
		int result = getColour(row, column);

		switch (colour) {
		case VERTEX_EMPTY:
			bits.set(emptyB, false);
			bits.set(colourB, false);
			break;
		case VERTEX_KO:
			bits.set(emptyB, false);
			bits.set(colourB, true);
			break;
		case VERTEX_BLACK:
			bits.set(emptyB, true);
			bits.set(colourB, false);
			break;
		case VERTEX_WHITE:
			bits.set(emptyB, true);
			bits.set(colourB, true);
			break;
		default:
			throw new Error();
		}

		return result;
	}

	/*
	 * @see jgogears.BoardInterface#
	 */
	public int getColour(int row, int column) {
		if (row < 0 || row >= this.size)
			return VERTEX_OFF_BOARD;
		if (column < 0 || column >= this.size)
			return VERTEX_OFF_BOARD;

		boolean empty = bits.get(getEmptyOffSet(row, column));
		boolean colour = bits.get(getColourOffSet(row, column));

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.BoardInterface#getSize()
	 */
	public short getSize() {
		return size;
	}

}
