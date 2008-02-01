/**
 * 
 */
package jgogears;

import jgogears.*;

import java.util.*;

/**
 * @author Stuart
 * 
 */
public abstract class RuleSet {
	/**
	 * Get the name of this Ko rule
	 * 
	 * @return
	 */
	public abstract String getName();

	/**
	 * Get a description of this Ko rule
	 * 
	 * @return
	 */
	public abstract String getDescription();

	/**
	 * Is this move legal, given this board in this game?
	 * 
	 * @param game
	 * @param Board
	 * @param move
	 * @return
	 */
	public abstract boolean moveIsLegal(Game game, BoardI board, Move move);

	/**
	 * 
	 * @param game
	 * @param Board
	 * @param move
	 * @return
	 */
	public abstract TreeSet<Vertex> captures(Game game, BoardI board,
			Move move);

	/**
	 * 
	 * @param game
	 * @param Board
	 * @param move
	 * @return
	 */
	public abstract TreeSet<Vertex> leavesKo(Game game, BoardI board,
			Move move);

	/**
	 * 
	 * @param rowb
	 * @param columnb
	 * @param board
	 * @return
	 */
	public short countLiberties(int rowb, int columnb, BoardI board) {
		return this.countLiberties((short) rowb, (short) columnb, board);
	}

	/**
	 * 
	 * @param rowb
	 * @param columnb
	 * @param board
	 * @return
	 */
	public short countLiberties(short rowb, short columnb, BoardI board) {
		return (short) this.getLiberties((short) rowb, (short) columnb, board)
				.size();
	}

	/**
	 * 
	 * @param rowb
	 * @param columnb
	 * @param board
	 * @return
	 */
	public TreeSet<Vertex> getLiberties(int rowb, int columnb, BoardI board) {
		return getLiberties((short) rowb, (short) columnb, board);
	}

	/**
	 * 
	 * @param rowb
	 * @param columnb
	 * @param board
	 * @return
	 */
	abstract public TreeSet<Vertex> getLiberties(short rowb, short columnb,
			BoardI board);

	TreeSet<Vertex> getString(int row, int column, BoardI board) {
		return this.getString((short) row, (short) column, board);
	}

	/**
	 * Helper function to get a string containing this position
	 * 
	 * @param row
	 * @param column
	 * @param board
	 * @return
	 */
	abstract public TreeSet<Vertex> getString(short row, short column, BoardI board);

}
