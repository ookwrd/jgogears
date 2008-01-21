package jgogears.tsume;

import java.util.Iterator;

import jgogears.GoBoard;
import jgogears.GoGame;
import jgogears.GoMove;

public class CachingKoRule extends KoRule {
	
	private KoRule rule;
	
	public CachingKoRule(KoRule rule){
		this.rule = rule;
	}

	@Override
	public Iterator<GoMove> captures(GoGame game, GoBoard Board, GoMove move) {
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
	public boolean leavesKo(GoGame game, GoBoard Board, GoMove move) {
		return rule.leavesKo(game, Board, move);
	}

	@Override
	public boolean moveIsLegal(GoGame game, GoBoard Board, GoMove move) {
		return rule.moveIsLegal(game, Board, move);
	}

}
