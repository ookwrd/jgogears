package jgogears;

import java.util.TreeSet;

public interface GTPStatelessInterface {
	boolean clearBoard();

	TreeSet<Vertex> finalStatusList(String status);

	TreeSet<Vertex> fixedHandicap(short handicap);

	Move genMove(short colour);

	/** get the name of the engine */
	String getEngineName();

	/** get the version of the engine */
	String getEngineVersion();

	GTPScore getFinalScore();

	boolean getKnownCommand(String command);

	TreeSet<String> getListCommands();

	/** get the protocol version */
	int getProtocolVersion();

	boolean loadsgf(String filename, int moveNumber);

	TreeSet<Vertex> placeFreeHandicap(short handicap);

	boolean placeFreeHandicap(TreeSet<Vertex> stones);

	boolean play(Move move);

	boolean quit();

	Move regGenMove(short colour);

	boolean setBoardSize(short size);

	boolean setKomi(double komi);

	boolean setTimeLeft(short colour, double byoYomiTime, double byoYomiStones);

	boolean setTimeSettings(double mainTime, double byoYomiTime, double byoYomiStones);

	BoardI showBoard();

	boolean undo();
}
