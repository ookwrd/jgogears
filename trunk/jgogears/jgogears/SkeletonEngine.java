package jgogears;

import jgogears.GoBoard;
import jgogears.GoMove;

public abstract class SkeletonEngine implements GTPInterface {

	public SkeletonEngine() {
		super();
	}

	public GoMove[] fixedHandicap(int handicap, GTPState state) {
		GoMove[] stones = GTPHandicaps.fixedHandicap(handicap);
		for (int i = 0; i < stones.length; i++) {
			state.playMove(stones[i]);
		}
		return stones;
	}

	public GoMove genMove(short colour, GTPState state) {
		GoMove result = regGenMove(colour, state);
		state.playMove(result);
		return result;
	}

	public boolean getKnownCommand(String command) {
		// TODO Auto-generated method stub
		return false;
	}

	public String[] getListCommands() {
		// TODO
		return null;
	}

	public int getProtocolVersion() {
		return 2;
	}

	public void loadsgf(String filename, int moveNumber, GTPState state) {
		// TODO Auto-generated method stub

	}

	public GoMove[] placeFreeHandicap(int handicap, GTPState state) {
		GoMove[] stones = GTPHandicaps.freeHandicap(handicap);
		for (int i = 0; i < stones.length; i++) {
			state.playMove(stones[i]);
		}
		return stones;
	}

	public void placeFreeHandicap(GoMove[] stones, GTPState state) {
		for (int i = 0; i < stones.length; i++) {
			state.playMove(stones[i]);
		}
	}

	public void play(GoMove move, GTPState state) {
		state.playMove(move);
	}

	public boolean quit() {
		return true;
	}

	public void setBoardSize(short size, GTPState state) {
		System.err.println("setting boardsize to: \"" + size + "\"");
		state.setBoardsize(size);
	}

	public void clearBoard(GTPState state) {
		System.err.println("clearing board");
		state.clearBoard();
	}

	public void setKomi(double komi, GTPState state) {
		state.setKomi(komi);
	}

	public void setTimeLeft(int colour, double byoYomiTime,
			double byoYomiStones, GTPState state) {
		state.setByoYomiStones(byoYomiStones);
		state.setByoYomiTime(byoYomiTime);
	}

	public void setTimeSettings(double mainTime, double byoYomiTime,
			double byoYomiStones, GTPState state) {
		state.setByoYomiStones(byoYomiStones);
		state.setByoYomiTime(byoYomiTime);
		state.setMainTime(mainTime);

	}

	public BoardInterface showBoard(GTPState state) {
		return state.getBoard();
	}

	public boolean undo(GTPState state) {
		// TODO Auto-generated method stub
		GTPState newState = new GTPState();
		newState.byoYomiStones = state.byoYomiStones;
		newState.byoYomiTime = state.byoYomiTime;
		newState.komi = state.komi;
		newState.boardsize = state.boardsize;
		newState.mainTime = state.mainTime;
		newState.board = new GoBoard(newState.boardsize);

		// TODO generate new board

		return false;
	}

}
