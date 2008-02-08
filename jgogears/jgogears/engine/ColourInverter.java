package jgogears.engine;

import java.util.Iterator;

import jgogears.BoardI;

/**
 * A simple wrapper class that inverts the colour of an Iterator<Short>.
 * 
 * @see java.util.Iterator
 * @author stuart
 */
public final class ColourInverter implements Iterator<Short> {

	/** The inner iterator. */
	Iterator<Short> iterator = null;

	/**
	 * Instantiates a new colour inverter.
	 * 
	 * @param iterator
	 *            the inner iterator
	 */
	public ColourInverter(Iterator<Short> iterator) {
		this.iterator = iterator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#next()
	 */
	public Short next() {
		short colour = this.iterator.next();
		switch (colour) {
		case BoardI.VERTEX_EMPTY:
			return BoardI.VERTEX_EMPTY;
		case BoardI.VERTEX_KO:
			return BoardI.VERTEX_KO;
		case BoardI.VERTEX_BLACK:
			return BoardI.VERTEX_WHITE;
		case BoardI.VERTEX_WHITE:
			return BoardI.VERTEX_BLACK;
		case BoardI.VERTEX_OFF_BOARD:
			return BoardI.VERTEX_OFF_BOARD;
		default:
			throw new Error("" +colour);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		this.iterator.remove();
	}

}
