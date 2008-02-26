/**
 * 
 */
package jgogears.engine;

import jgogears.BoardI;
import java.util.*;

/**
 * Class to hold a single node in the tree.
 * 
 * @author stuart
 */
public final class Node implements Comparable<Node> {

	public int compareTo(Node node) {
		if (node == this)
			return 0;
		return (node.hashCode() > this.hashCode() ? 1 : -1);
	}

	/** are we doing expensive checking? */
	private final static boolean CHECKING = true;

	/** are we being verbose? */
	public static boolean DEBUG = false;

	/** The count. Should be equal to or higher than the sum of the childrens counts */
	private long count = 0;

	/** The white child */
	private Node white = null;;

	/** The off child */
	private Node off = null;

	/** The black child */
	private Node black = null;

	/** The empty child */
	private Node empty = null;

	/**
	 * Instantiates a new node.
	 */
	Node() {
	}

	/**
	 * get the count
	 * 
	 * @return the count
	 */
	public final long getCount() {
		return count;
	}

	/**
	 * set the count
	 * 
	 * @param count
	 *            the count to set
	 */
	public final void setCount(long count) {
		if (CHECKING)
			if (count < 0)
				throw new Error();
		this.count = count;
	}

	/**
	 * get the white
	 * 
	 * @return the white
	 */
	public final Node getWhite() {
		return white;
	}

	/**
	 * set the white
	 * 
	 * @param white
	 *            the white to set
	 */
	public final void setWhite(Node white) {
		if (CHECKING)
			if (white == this)
				throw new Error();
		this.white = white;
	}

	/**
	 * get the off
	 * 
	 * @return the off
	 */
	public final Node getOff() {
		return off;
	}

	/**
	 * set the off
	 * 
	 * @param off
	 *            the off to set
	 */
	public final void setOff(Node off) {
		if (CHECKING)
			if (white == this)
				throw new Error();
		this.off = off;
	}

	/**
	 * get the black
	 * 
	 * @return the black
	 */
	public final Node getBlack() {
		return black;
	}

	/**
	 * set the black
	 * 
	 * @param black
	 *            the black to set
	 */
	public final void setBlack(Node black) {
		if (CHECKING)
			if (white == this)
				throw new Error();
		this.black = black;
	}

	/**
	 * get the empty
	 * 
	 * @return the empty
	 */
	public final Node getEmpty() {
		return empty;
	}

	/**
	 * set the empty
	 * 
	 * @param empty
	 *            the empty to set
	 */
	public final void setEmpty(Node empty) {
		if (CHECKING)
			if (white == this)
				throw new Error();
		this.empty = empty;
	}
/**
 * get the size of this (sub)tree
 * 
 * @return the size
 */
	public int size() {
		int result = 1;
		if (black != null)
			result = result + black.size();
		if (white != null)
			result = result + white.size();
		if (empty != null)
			result = result + empty.size();
		if (off != null)
			result = result + off.size();
		return result;
	}
	/**
	 * return an iterator over the children of this Node;
	 * @return the iterator
	 */
	public Iterator<Node> iterator(){
		return new NodeIterator(this);
	}
	/**
	 * return an iterator over the children of this Node;
	 * @return the iterator
	 */
	public Iterator<Node> getChildren(){
		return new NodeIterator(this);
	}
	/**
	 * return an iterator over the decendents of this Node;
	 * @return the iterator
	 */
	public Iterator<Node> getDecendents(){
		return new TreeIterator(this);
	}

	public Node getLeaf(BoardI board, short colour, short row, short column, short sym, Node node) {
		if (board == null)
			throw new Error();
		VertexLineariser linear = null;
		boolean invert = colour == BoardI.VERTEX_WHITE;

		linear = new VertexLineariser(board, row, column, sym, invert);
		if (!linear.hasNext())
			throw new Error();
		return getLeaf(linear, node);

	}

	public Node getLeaf(VertexLineariser linear, Node node) {
		if (!linear.hasNext())
			throw new Error();

		while (linear.hasNext()) {
			Node child = null;
			Short colour = linear.next();
			switch (colour) {
			case BoardI.VERTEX_BLACK:
				child = node.black;
				break;
			case BoardI.VERTEX_WHITE:
				child = node.white;
				break;
			case BoardI.VERTEX_OFF_BOARD:
				child = node.off;
				break;
			case BoardI.VERTEX_KO:
			case BoardI.VERTEX_EMPTY:
				child = node.empty;
				break;
			default:
				throw new Error("Unknown vertex colour: " + colour);
			}
			if (child == null)
				return node;
			node = child;
		}
		return node;
	}

	/**
	 * Train.
	 * 
	 * @param linear
	 *            the linear
	 * @param expand are we expanding?
	 * @param depth the depth to expand to
	 * @param played
	 *            the played
	 */
	public void train(VertexLineariser linear, boolean played, boolean expand, int depth) {
		if (depth < 0)
			expand = false;
		depth--;
		if (played)
			count++;
		if (!linear.hasNext())
			return;
		Short colour = linear.next();

		switch (colour) {
		case BoardI.VERTEX_BLACK:
			if (black == null)
				if (expand)
					black = new Node();
				else
					return;
			black.train(linear, played, expand, depth);
			break;
		case BoardI.VERTEX_WHITE:
			if (white == null)
				if (expand)
					white = new Node();
				else
					return;
			white.train(linear, played, expand,  depth);

			break;
		case BoardI.VERTEX_OFF_BOARD:
			if (off == null)
				if (expand)
					off = new Node();
				else
					return;
			off.train(linear, played, expand,  depth);

			break;
		case BoardI.VERTEX_EMPTY:
		case BoardI.VERTEX_KO:
			if (empty == null)
				if (expand)
					empty = new Node();
				else
					return;
			empty.train(linear, played, expand,  depth);
			break;
		default:
			throw new Error();
		}
	}
}
