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

	public void setRow(int row) {
		//TOD
	}

	public void setColumn(int row) {
		 //TODO
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
	

	/**
	 * Parse a vertex into this move
	 * 
	 * @param vertexString
	 */
	public  Vertex(String vertexString) {
		// System.err.println("parseVertex(\"" + vertexString + "\")");
		vertexString = vertexString.toLowerCase();
			switch (vertexString.charAt(0)) {
			case 'a':
				setRow(0);
				break;
			case 'b':
				setRow(1);
				break;
			case 'c':
				setRow(2);
				break;
			case 'd':
				setRow(3);
				break;
			case 'e':
				setRow(4);
				break;
			case 'f':
				setRow(5);
				break;
			case 'g':
				setRow(6);
				break;
			case 'h':
				setRow(7);
				break;
			case 'j':
				setRow(8);
				break;
			case 'k':
				setRow(9);
				break;
			case 'l':
				setRow(10);
				break;
			case 'm':
				setRow(11);
				break;
			case 'n':
				setRow(12);
				break;
			case 'o':
				setRow(13);
				break;
			case 'p':
				setRow(14);
				break;
			case 'q':
				setRow(15);
				break;
			case 'r':
				setRow(16);
				break;
			case 's':
				setRow(17);
				break;
			case 't':
				setRow(18);
				break;
			case 'u':
				setRow(19);
				break;
			case 'v':
				setRow(20);
				break;
			case 'w':
				setRow(21);
				break;
			case 'x':
				setRow(22);
				break;
			case 'y':
				setRow(23);
				break;
			case 'z':
				setRow(24);
				break;
			default:
				throw new IllegalArgumentException("trying to parse (3) \""
						+ vertexString + "\"");

			}
			if (vertexString.length() == 2) {
				setColumn((vertexString.charAt(1) - '1'));
			} else if (vertexString.length() == 3) {
				setColumn(((vertexString.charAt(1) - '0') * 10) + (vertexString
						.charAt(2) - '1'));
			} else
				throw new IllegalArgumentException("trying to parse (4) \""
						+ vertexString + "\", \"" + vertexString + "\"");
		}
		// System.err.println(this);

	}
