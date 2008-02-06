package jgogears;

import com.sun.xml.internal.bind.v2.TODO;

/**
 * Transform a board to ASCII, in the style of GnuGo
 * 
 * @author syeates
 * @see TODO
 */
public final class BoardToASCII {
	/**
	 * Transform a board to an ASCII representation
	 * 
	 * @param board
	 * @return
	 */
	static String Transform(BoardI board) {
		StringBuffer buf = new StringBuffer();
		int size = board.getSize();

		// do the header
		buf.append("   ");
		for (int i = 0; i < size; i++) {
			buf.append(i);
		}
		buf.append("\n");
		for (int i = 0; i < size; i++) {
			buf.append(" ").append(i).append(" ");
			for (int j = 0; j < size; j++)
				switch (board.getColour(i, j)) {
				case BoardI.VERTEX_KO:
					buf.append("!");
					break;
				case BoardI.VERTEX_BLACK:
					buf.append("X");
					break;
				case BoardI.VERTEX_WHITE:
					buf.append("O");
					break;
				case BoardI.VERTEX_EMPTY:
					buf.append(".");
					break;
				default:
					throw new Error();
				}

			buf.append("\n");
		}
		buf.append("\n");
		return buf.toString();
	}
}
