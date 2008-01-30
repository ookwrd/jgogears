/**
 * 
 */
package jgogears.tsume;

import java.util.*;

import jgogears.GoBoard;
import jgogears.GoGame;
import jgogears.GoMove;

/**
 * @author Stuart
 * 
 */
final public class NoKoRule extends KoRule {

	final static boolean DEBUG = false;
	final static TreeSet<Vertex> EMPTY = new TreeSet<Vertex>();

	TreeSet<Vertex> getString(int rowb, int columnb, GoBoard board) {
		return this.getString((short) rowb, (short) columnb, board);
	}

	public TreeSet<Vertex> getString(short rowb, short columnb, GoBoard board) {
		TreeSet<Vertex> string = new TreeSet<Vertex>();
		short colour = (short) board.getColour(rowb, columnb);
		if (colour == GoBoard.VERTEX_OFF_BOARD)
			return string;

		Vertex seed = new Vertex(rowb, columnb);
		string.add(seed);
		TreeSet<Vertex> newstring = new TreeSet<Vertex>(string);
		newstring.add(seed);

		boolean changed = false;
		do {
			changed = false;
			Iterator<Vertex> i = string.iterator();
			while (i.hasNext()) {
				Vertex current = i.next();
				short row = current.getRow();
				short column = current.getColumn();
				if (board.getColour(row, column + 1) == colour) {
					Vertex adjacent = new Vertex(row, column + 1);
					newstring.add(adjacent);
				}
				if (board.getColour(row, column - 1) == colour) {
					Vertex adjacent = new Vertex(row, column - 1);
					newstring.add(adjacent);
				}
				if (board.getColour(row + 1, column) == colour) {
					Vertex adjacent = new Vertex(row + 1, column);
					newstring.add(adjacent);
				}
				if (board.getColour(row - 1, column) == colour) {
					Vertex adjacent = new Vertex(row - 1, column);
					newstring.add(adjacent);
				}

			}
			changed = string.size() != newstring.size();
			if (DEBUG)
				System.err.print(" " + string.size() + " " + newstring.size());
			if (string.size() > newstring.size())
				throw new Error("string has got smaller! " + string + " / "
						+ newstring);
			string.addAll(newstring);
		} while (changed);
		if (DEBUG)
			System.err.println();

		return string;
	}

	public TreeSet<Vertex> getLiberties(short rowb, short columnb, GoBoard board) {
		if (board.getColour(rowb, columnb) == GoBoard.VERTEX_EMPTY
				|| board.getColour(rowb, columnb) == GoBoard.VERTEX_EMPTY
				|| board.getColour(rowb, columnb) == GoBoard.VERTEX_EMPTY) {
			throw new Error("empty sqaures don't have liberties");
		}

		TreeSet<Vertex> string = getString(rowb, columnb, board);
		TreeSet<Vertex> liberties = new TreeSet<Vertex>();

		Iterator<Vertex> i = string.iterator();
		while (i.hasNext()) {
			Vertex current = i.next();
			short row = current.getRow();
			short column = current.getColumn();
			if (board.getColour(row, column + 1) == GoBoard.VERTEX_EMPTY
					|| board.getColour(row, column + 1) == GoBoard.VERTEX_KO) {
				Vertex adjacent = new Vertex(row, column + 1);
				liberties.add(adjacent);
			}
			if (board.getColour(row, column - 1) == GoBoard.VERTEX_EMPTY
					|| board.getColour(row, column - 1) == GoBoard.VERTEX_KO) {
				Vertex adjacent = new Vertex(row, column - 1);
				liberties.add(adjacent);
			}
			if (board.getColour(row + 1, column) == GoBoard.VERTEX_EMPTY
					|| board.getColour(row + 1, column) == GoBoard.VERTEX_KO) {
				Vertex adjacent = new Vertex(row + 1, column);
				liberties.add(adjacent);
			}
			if (board.getColour(row - 1, column) == GoBoard.VERTEX_EMPTY
					|| board.getColour(row - 1, column) == GoBoard.VERTEX_KO) {
				Vertex adjacent = new Vertex(row - 1, column);
				liberties.add(adjacent);
			}
		}
		return liberties;
	}

	public TreeSet<Vertex> captureshelper(GoBoard board, GoMove move,int row, int column) {
		if (DEBUG && EMPTY.size() != 0)
			throw new Error("EMPTY not empty");
		short colour = move.getColour();
		short acolour = (short) board.getColour(row, column);
		if (acolour == GoBoard.VERTEX_EMPTY ||acolour == GoBoard.VERTEX_KO){
			if (DEBUG) System.err.println("captures == empty");
			return EMPTY;
		}
		if ((colour == GoBoard.VERTEX_BLACK && acolour == GoBoard.VERTEX_WHITE) ||  
				(colour == GoBoard.VERTEX_WHITE && acolour == GoBoard.VERTEX_BLACK)) {
			int libs = this.countLiberties(row, column, board);
			if (libs == 1){
				TreeSet<Vertex> string = this.getString(row, column, board);
				if (DEBUG) System.err.println("captures == single liberty! capture! " + libs + " " + string);
				return string;
			} else {
				if (DEBUG) System.err.println("captures == multiple liberties");
			}
		} 
		if (DEBUG) System.err.println("captures == ? " + colour + " " + acolour);
		return EMPTY;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.tsume.KoRule#captures(jgogears.GoGame, jgogears.GoBoard,
	 *      jgogears.GoMove)
	 */
	@Override
	public TreeSet<Vertex> captures(GoGame game, GoBoard board, GoMove move) {
		if (board == null)
			throw new Error();
		if (move == null)
			throw new Error();
		if (!move.getPlay())
			return new TreeSet<Vertex>();

		short row = move.getRow();
		short column = move.getColumn();
		short colour = move.getColour();

		if (colour != GoBoard.VERTEX_BLACK && colour != GoBoard.VERTEX_WHITE)
			throw new Error();
		TreeSet<Vertex> captures = new TreeSet<Vertex>();
		
		captures.addAll(this.captureshelper(board, move, row+1, column));
		captures.addAll(this.captureshelper(board, move, row-1, column));
		captures.addAll(this.captureshelper(board, move, row, column+1));
		captures.addAll(this.captureshelper(board, move, row, column-1));

		return captures;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.tsume.KoRule#getDescription()
	 */
	@Override
	public String getDescription() {
		return "A ko rule which doesn't recognise any form of Ko whatsoever and allows unbounded loops and repetition.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.tsume.KoRule#getName()
	 */
	@Override
	public String getName() {
		return "No Ko";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.tsume.KoRule#leavesKo(jgogears.GoGame, jgogears.GoBoard,
	 *      jgogears.GoMove)
	 */
	@Override
	public TreeSet<Vertex> leavesKo(GoGame game, GoBoard board, GoMove move) {
		// TODO write test cases for this method
		return new TreeSet<Vertex>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.tsume.KoRule#moveIsLegal(jgogears.GoGame, jgogears.GoBoard,
	 *      jgogears.GoMove)
	 */
	@Override
	public boolean moveIsLegal(GoGame game, GoBoard board, GoMove move) {
		if (board == null)
			throw new Error();
		if (move == null)
			throw new Error();
		if (move.getPass())
			return true;
		if (move.getResign())
			return true;
		if (!move.getPlay())
			throw new Error("Internal error, bad move");

		short row = move.getRow();
		short column = move.getColumn();
		short colour = move.getColour();
		
		if (board.getColour(row, column) != GoBoard.VERTEX_EMPTY){
			System.err.println("illegal move, not empty");
			return false;
		}

		if (colour != GoBoard.VERTEX_BLACK && colour != GoBoard.VERTEX_WHITE)
			throw new Error();
		int liberties = 0;
//TODO
		//liberties.addAll(this.legelsfrompos(row, column, colour, board));
	
		return liberties > 0;
	}

	/**
	 * Helper for moveIsLegal
	 * 
	 * @param row
	 *            the row of the position that is a potential liberty
	 * @param column
	 *            the column of the position that is a potential liberty
	 * @param colour
	 *            the colour of the stone we want to use these liberties for
	 * @param board
	 * @return the number of liberties through this position
	 */
	TreeSet<Vertex> legelsfrompos(int row, int column, short colour, GoBoard board) {
		return this.legelsfrompos((short)row, (short)column, colour, board);
	}
		/**
		 * Helper for moveIsLegal
		 * 
		 * @param row
		 *            the row of the position that is a potential liberty
		 * @param column
		 *            the column of the position that is a potential liberty
		 * @param colour
		 *            the colour of the stone we want to use these liberties for
		 * @param board
		 * @return the number of liberties through this position
		 */
	TreeSet<Vertex> legelsfrompos(short row, short column, short colour, GoBoard board) {
		TreeSet<Vertex> liberties = new TreeSet<Vertex>();
		short acolour = (short) board.getColour(row, column);
		if (acolour == colour) {
			liberties.addAll(this.getLiberties(row, column, board));
			if (DEBUG) System.err.println("position == same");
		}
		return liberties;
	}

}
