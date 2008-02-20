/**
 * 
 */
package jgogears.engine;

import java.util.Iterator;


/**
 * An iterator for nodes.
 * 
 * @author syeates
 */

public class NodeIterator implements Iterator<Node> {
	
	/** The node we're iterating over */
	private Node node = null;
	
	/** indicator of where we are */
	private short next = 0;

	/**
	 * public constructor.
	 * 
	 * @param node the Node to iterator over
	 */
	public NodeIterator(Node node) {
		this.node = node;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public Node next() {
		switch (next) {
		case 0:
			next++;
			if (node.getBlack() != null)
				return node.getBlack();
		case 1:
			next++;
			if (node.getWhite() != null)
				return node.getWhite();
		case 2:
			next++;
			if (node.getEmpty() != null)
				return node.getEmpty();
		case 3:
			next++;
			if (node.getOff() != null)
				return node.getOff();
		default:
			throw new Error("nothing left");

		}

	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		switch (next) {
		case 0:
			if (node.getBlack() != null)
				return true;
		case 1:
			if (node.getWhite() != null)
				return true;
		case 2:
			if (node.getEmpty() != null)
				return true;
		case 3:
			if (node.getOff() != null)
				return true;
		default:
			return false;
		}

	}

	/**
	 * Not implemented. Throws an Error if called
	 */
	public void remove() {
		throw new Error("Not implemented");
	}

}
