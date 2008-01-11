package jgogears.engine;

import java.util.Iterator;

import jgogears.BoardInterface;

/**
 * A simple wrapper class that inverts the colour of 
 * @see jgogears.GoBoard
 * @see java.util.Iterator
 * @author stuart
 */
public final class ColourInverter implements Iterator<Short> {
	
	Iterator<Short> iterator = null;
	
	public ColourInverter(Iterator<Short> i){
		iterator = i;
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public Short next() {
		int s = iterator.next();
		switch (s){
			case BoardInterface.VERTEX_EMPTY:return BoardInterface.VERTEX_EMPTY;
			case BoardInterface.VERTEX_KO:return BoardInterface.VERTEX_KO;
			case BoardInterface.VERTEX_BLACK:return BoardInterface.VERTEX_WHITE;
			case BoardInterface.VERTEX_WHITE: return BoardInterface.VERTEX_BLACK;
			default: throw new Error();
		}
	}

	public void remove() {
		iterator.remove();
	}

}
