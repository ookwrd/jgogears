package jgogears.graphold;

import java.util.Vector;

public class Connection {

	protected Node first = null;
	protected Node second = null;

	protected short first_row = -1;
	protected short second_row = -1;

	protected short first_column = -1;
	protected short second_column = -1;

	public Connection(Node first, Node second, Vector<Short> first_i,
			Vector<Short> second_i) {
		this.first = first;
		this.second = second;

		this.first_row = first_i.elementAt(0);
		this.first_column = first_i.elementAt(1);

		this.second_row = second_i.elementAt(0);
		this.second_column = second_i.elementAt(1);
	}

	public Connection(Node first, Node second, short first_r, short first_c,
			short second_r, short second_c) {
		this.first = first;
		this.second = second;

		this.first_row = first_r;
		this.first_column = first_c;

		this.second_row = second_r;
		this.second_column = second_c;
	}

	public Node getFirst() {
		return first;
	}

	public void setFirst(Node first) {
		this.first = first;
	}

	public short getFirst_column() {
		return first_column;
	}

	public void setFirst_column(short first_column) {
		this.first_column = first_column;
	}

	public short getFirst_row() {
		return first_row;
	}

	public void setFirst_row(short first_row) {
		this.first_row = first_row;
	}

	public Node getSecond() {
		return second;
	}

	public void setSecond(Node second) {
		this.second = second;
	}

	public short getSecond_column() {
		return second_column;
	}

	public void setSecond_column(short second_column) {
		this.second_column = second_column;
	}

	public short getSecond_row() {
		return second_row;
	}

	public void setSecond_row(short second_row) {
		this.second_row = second_row;
	}

}
