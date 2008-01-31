package jgogears;

import java.util.*;

public final class Vertex extends Vector<Short> implements Comparable {

	public Vertex() {
		this.add((short) -1);
		this.add((short) -1);
	}

	public Vertex(short row, short column) {
		this.add(row);
		this.add(column);
	}

	public Vertex(int row, int column) {
		this.add((short) row);
		this.add((short) column);
	}

	public short getRow() {
		return this.elementAt(0);
	}

	public short getColumn() {
		return this.elementAt(1);
	}

	public boolean equals(Object o) {
		if (this == null && o == null)
			return true;
		if (o == null || this == null)
			return false;
		if (Object.class != this.getClass())
			return super.equals(o);
		try {
			Vertex v = (Vertex) o;
			if (v.getRow() == this.getRow()
					&& v.getColumn() == this.getColumn())
				return true;
			else
				return false;
		} catch (Throwable t) {
			return false;
		}
	}

	public int compareTo(Object o) {
		if (this == null && o == null)
			return 0;
		if (o == null)
			return -1;
		if (this == null)
			return 1;
		try {
			Vertex v = (Vertex) o;

			if (v.getRow() > this.getRow())
				return 1;
			if (v.getRow() < this.getRow())
				return -1;
			if (v.getColumn() > this.getColumn())
				return 1;
			if (v.getColumn() < this.getColumn())
				return -1;
		} catch (Throwable t) {
			if (o.hashCode() > this.hashCode())
				return 1;
			if (o.hashCode() < this.hashCode())
				return -1;
		}
		return 0;
	}

}
