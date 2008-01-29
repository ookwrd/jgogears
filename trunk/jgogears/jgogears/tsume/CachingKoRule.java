package jgogears.tsume;

import java.util.Iterator;
import java.util.TreeSet;

import jgogears.GoBoard;
import jgogears.GoGame;
import jgogears.GoMove;

public class CachingKoRule extends KoRule {

	private KoRule rule;

	public CachingKoRule(KoRule rule) {
		this.rule = rule;
	}

	@Override
	public TreeSet<Vertex> captures(GoGame game, GoBoard Board, GoMove move) {
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
	public TreeSet<Vertex> leavesKo(GoGame game, GoBoard Board, GoMove move) {
		return rule.leavesKo(game, Board, move);
	}

	@Override
	public boolean moveIsLegal(GoGame game, GoBoard Board, GoMove move) {
		return rule.moveIsLegal(game, Board, move);
	}
	
	public TreeSet<Vertex> getLiberties(short rowb, short columnb, GoBoard board){
		return rule.getLiberties(rowb, columnb, board);
	}

}
