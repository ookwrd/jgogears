package jgogears.graph;

import java.util.*;

import jgogears.*;

public final class Node implements Comparable<Node> {
	protected final short colour;
	protected final TreeSet<Point> points;
	protected TreeSet<Point> liberties = null;

	public Node(short colour) {
		if (colour == BoardI.VERTEX_OFF_BOARD)
			throw new Error("Point off board");
		this.colour = colour;
		this.points = new TreeSet<Point>();
	}

	public int compareTo(Node node) {
		if (this == node)
			return 0;
		if (this.colour > node.colour)
			return 1;
		if (this.colour < node.colour)
			return -1;
		if (this.points.size() < node.points.size())
			return -1;
		if (this.points.size() > node.points.size())
			return 1;
		Iterator<Point> ps1 = this.points.iterator();
		Iterator<Point> ps2 = node.points.iterator();
		while (ps1.hasNext() && ps2.hasNext()) {
			Point p1 = ps1.next();
			Point p2 = ps2.next();
			if (p1.getRow() > p2.getRow())
				return -1;
			if (p1.getRow() < p2.getRow())
				return 1;
			if (p1.getColumn() > p2.getColumn())
				return 1;
			if (p1.getColumn() < p2.getColumn())
				return -1;
		}
		return 0;
	}

	public short getColour() {
		return colour;
	}

	protected int calcLiberties(Graph graph) {
		short libertyColour = this.getColour() == BoardI.VERTEX_BLACK ? BoardI.VERTEX_BLACK
				: BoardI.VERTEX_WHITE;
		Board board = graph.getBoard();
		if (points.size() == 0)
			throw new Error();
		Iterator<Point> points = this.points.iterator();
		while (points.hasNext()) {
			Point point = points.next();
			Iterator<Point> libs = graph.getNeighboringPoints(point).iterator();
			while (libs.hasNext()) {
				Point p = libs.next();
				if (board.getColour(p.getRow(), p.getColumn()) == libertyColour) {
					this.liberties.add(p);
				}
			}
		}
		return this.liberties.size();
	}

	public TreeSet<Point> getLiberties(Graph graph) {
		if (this.liberties == null) {
			liberties = new TreeSet<Point>();
			this.calcLiberties(graph);
		}
		return this.liberties;
	}

	public TreeSet<Point> getPoints() {
		return points;
	}

}
