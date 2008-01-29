/**
 * 
 */
package jgogears.tsume;

import jgogears.*;

import java.util.*;

/**
 * @author Stuart
 * 
 */
public abstract class KoRule {
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
	public abstract boolean moveIsLegal(GoGame game, GoBoard Board, GoMove move);

	/**
	 * 
	 * @param game
	 * @param Board
	 * @param move
	 * @return
	 */
	public abstract TreeSet<Vertex> captures(GoGame game, GoBoard Board,
			GoMove move);

	/**
	 * 
	 * @param game
	 * @param Board
	 * @param move
	 * @return
	 */
	public abstract TreeSet<Vertex> leavesKo(GoGame game, GoBoard Board,
			GoMove move);

	/**
	 * 
	 * @param rowb
	 * @param columnb
	 * @param board
	 * @return
	 */
	public short countLiberties(int rowb, int columnb, GoBoard board) {
		return this.countLiberties((short) rowb, (short) columnb, board);
	}

	/**
	 * 
	 * @param rowb
	 * @param columnb
	 * @param board
	 * @return
	 */
	public short countLiberties(short rowb, short columnb, GoBoard board) {
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
	public TreeSet<Vertex> getLiberties(int rowb, int columnb, GoBoard board) {
		return getLiberties((short) rowb, (short) columnb, board);
	}

	/**
	 * 
	 * @param rowb
	 * @param columnb
	 * @param board
	 * @return
	 */
	abstract public TreeSet<Vertex> getLiberties(short rowb, short columnb, GoBoard board);

}
