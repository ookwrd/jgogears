package jgogears;

import java.util.Iterator;
import java.util.TreeSet;

import jgogears.*;

public class CachingKoRule extends KoRule {

	private KoRule rule;

	public CachingKoRule(KoRule rule) {
		this.rule = rule;
	}

	@Override
	public TreeSet<Vertex> captures(GoGame game, BoardI Board, GoMove move) {
		return rule.captures(game, Board, move);
	}

	@Override
	public String getDescription() {
		return rule.getDescription() + "+ caching";
	}

	@Override
	public String getName() {
		return rule.getName() + "+ caching";
	}

	@Override
	public TreeSet<Vertex> leavesKo(GoGame game, BoardI Board, GoMove move) {
		return rule.leavesKo(game, Board, move);
	}

	@Override
	public boolean moveIsLegal(GoGame game, BoardI Board, GoMove move) {
		return rule.moveIsLegal(game, Board, move);
	}

	public TreeSet<Vertex> getLiberties(short rowb, short columnb, BoardI board) {
		return rule.getLiberties(rowb, columnb, board);
	}

	public TreeSet<Vertex> getString(short row, short column, BoardI board) {
		return rule.getString(row, column, board);
	}

}
