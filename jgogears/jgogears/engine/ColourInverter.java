package jgogears.engine;

import java.util.Iterator;

import jgogears.BoardI;

/**
 * A simple wrapper class that inverts the colour of
 * 
 * @see jgogears.Board
 * @see java.util.Iterator
 * @author stuart
 */
public final class ColourInverter implements Iterator<Short> {

	Iterator<Short> iterator = null;

	public ColourInverter(Iterator<Short> i) {
		iterator = i;
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public Short next() {
		int s = iterator.next();
		switch (s) {
		case BoardI.VERTEX_EMPTY:
			return BoardI.VERTEX_EMPTY;
		case BoardI.VERTEX_KO:
			return BoardI.VERTEX_KO;
		case BoardI.VERTEX_BLACK:
			return BoardI.VERTEX_WHITE;
		case BoardI.VERTEX_WHITE:
			return BoardI.VERTEX_BLACK;
		default:
			throw new Error();
		}
	}

	public void remove() {
		iterator.remove();
	}

}
