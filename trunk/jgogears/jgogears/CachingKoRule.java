package jgogears;

import java.util.TreeSet;

/**
 * A RuleSet that caches the results to speed things up
 * 
 * @author syeates
 */
public class CachingKoRule extends RuleSet {
	/**
	 * the inner ruleset
	 */
	private final RuleSet rule;

	/**
	 * The constructor, passing in the inner rule whose results are cached
	 * 
	 * @param rule
	 *            the inner rule whose results are cached
	 */
	public CachingKoRule(RuleSet rule) {
		this.rule = rule;
	}

	@Override
	public TreeSet<Vertex> captures(Game game, BoardI Board, Move move) {
		return this.rule.captures(game, Board, move);
	}

	@Override
	public String getDescription() {
		return this.rule.getDescription() + "+ caching";
	}

	@Override
	public TreeSet<Vertex> getLiberties(short rowb, short columnb, BoardI board) {
		return this.rule.getLiberties(rowb, columnb, board);
	}

	@Override
	public String getName() {
		return this.rule.getName() + "+ caching";
	}

	@Override
	public TreeSet<Vertex> getString(short row, short column, BoardI board) {
		return this.rule.getString(row, column, board);
	}

	@Override
	public TreeSet<Vertex> leavesKo(Game game, BoardI Board, Move move) {
		return this.rule.leavesKo(game, Board, move);
	}

	@Override
	public boolean moveIsLegal(Game game, BoardI Board, Move move) {
		return this.rule.moveIsLegal(game, Board, move);
	}

}
