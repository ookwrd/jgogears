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
	 * @return 
	 */
	public abstract String getName();
	/**
	 * Get a description of this Ko rule
	 * @return
	 */
	public abstract String getDescription();
	/**
	 * Is this move legal, given this board in this game?
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
	public abstract Iterator<GoMove> captures(GoGame game, GoBoard Board, GoMove move);
	/**
	 * 
	 * @param game
	 * @param Board
	 * @param move
	 * @return
	 */
	public abstract boolean leavesKo(GoGame game, GoBoard Board, GoMove move);
	
}
