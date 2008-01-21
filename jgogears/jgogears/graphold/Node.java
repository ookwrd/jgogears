/**
 * 
 */
package jgogears.graphold;

import java.util.*;

import jgogears.*;

/**
 * @author syeates
 * 
 */
public final class Node implements Comparable<Node> {

	class VComparer implements Comparator<Vector<Short>> {
		public int compare(Vector<Short> first, Vector<Short> second) {
			if (first.hashCode() == second.hashCode())
				return 0;
			if (first.size() > second.size())
				return 1;
			if (first.size() < second.size())
				return -1;
			if (first.elementAt(0) > second.elementAt(0))
				return -1;
			if (first.elementAt(0) < second.elementAt(0))
				return 1;
			if (first.elementAt(1) > second.elementAt(1))
				return -1;
			if (first.elementAt(1) < second.elementAt(1))
				return 1;
			return 0;
		}

	}

	protected short colour = BoardInterface.VERTEX_EMPTY;
	protected TreeSet<Connection> connections = new TreeSet<Connection>();
	protected TreeSet<Vector<Short>> vertexes = new TreeSet<Vector<Short>>(
			new VComparer());

	public int compareTo(Node node) {
		Integer i = new Integer(this.hashCode());
		Integer j = new Integer(node.hashCode());

		return i.compareTo(j);
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("[").append(GoMove.colourString(colour)).append(",");
		buf.append("{").append(connections.size()).append("}");
		buf.append("{").append(vertexes.size()).append("}");
		Iterator<Vector<Short>> i = vertexes.iterator();
		while (i.hasNext()) {
			Vector<Short> v = i.next();
			buf.append("(").append(v.elementAt(0)).append(",").append(
					v.elementAt(1)).append(")");
		}
		buf.append("]");
		return buf.toString();
	}

	/**
	 * default constructor
	 * 
	 */
	public Node() {

	}

	/**
	 * Produce a new Node for a specific vertex
	 * 
	 * @param colour
	 *            the colour of the vertex
	 * @param row
	 *            the row
	 * @param column
	 *            the column
	 */
	public Node(short colour, short row, short column) {
		this.colour = colour;
		Vector<Short> vert = new Vector<Short>();
		vert.add(row);
		vert.add(column);
		this.vertexes.add(vert);
		// System.err.println(this);
	}

	/**
	 * Produce a new Node by merging two old ones
	 * 
	 * @param first
	 * @param second
	 */

	public Node(Node first, Node second) {
		// check for plausability
		if (first.getColour() != second.getColour())
			throw new Error("Merging nodes of different colour");
		this.setColour(first.getColour());
		this.getVertexes().addAll(first.getVertexes());
		this.getVertexes().addAll(second.getVertexes());
	}

	public short addConnection(Connection connection) {
		this.connections.add(connection);
		return (short) this.connections.size();
	}

	public boolean removeConnection(Node node) {
		boolean result = this.connections.remove(node);
		return result;
	}

	public short getColour() {
		return colour;
	}

	public void setColour(short colour) {
		this.colour = colour;
	}

	public TreeSet<Connection> getConnections() {
		return connections;
	}

	public void setConnections(TreeSet<Connection> connections) {
		this.connections = connections;
	}

	public TreeSet<Vector<Short>> getVertexes() {
		return vertexes;
	}

	public void setVertexes(TreeSet<Vector<Short>> vertexes) {
		this.vertexes = vertexes;
	}

	// begin go-specific material
	/**
	 * Return connections from this group that are liberties
	 */
	public TreeSet<Connection> getLiberties() {
		TreeSet<Connection> libs = new TreeSet<Connection>();
		Iterator<Connection> iterator = connections.iterator();
		while (iterator.hasNext()) {
			Connection connection = iterator.next();
			Node node = null;
			if (connection.getFirst() == this)
				node = connection.getSecond();
			else
				node = connection.getFirst();
			if (node.getColour() == BoardInterface.VERTEX_EMPTY
					|| node.getColour() == BoardInterface.VERTEX_KO)
				libs.add(connection);
			if (node.getColour() == this.getColour())
				throw new Error("Internal error in Node.getLiberties "
						+ node.getColour() + " " + this.getColour());
		}
		return libs;
	}
}
