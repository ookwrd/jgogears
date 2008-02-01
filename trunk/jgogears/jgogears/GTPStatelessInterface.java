package jgogears;

import jgogears.*;
import java.util.*;

public interface GTPStatelessInterface {
	/** get the protocol version */
	int getProtocolVersion();

	/** get the name of the engine */
	String getEngineName();

	/** get the version of the engine */
	String getEngineVersion();

	boolean getKnownCommand(String command);

	TreeSet<String> getListCommands();

	boolean quit();

	GTPScore getFinalScore();

	boolean setBoardSize(short size);

	boolean clearBoard();

	boolean setKomi(double komi);

	TreeSet<Vertex> fixedHandicap(short handicap);

	TreeSet<Vertex> placeFreeHandicap(short handicap);

	boolean placeFreeHandicap(TreeSet<Vertex> stones);

	boolean play(Move move);

	Move genMove(short colour);

	boolean undo();

	boolean setTimeSettings(double mainTime, double byoYomiTime,
			double byoYomiStones);

	boolean setTimeLeft(short colour, double byoYomiTime, double byoYomiStones);

	TreeSet<Vertex> finalStatusList(String status);

	boolean loadsgf(String filename, int moveNumber);

	Move regGenMove(short colour);

	BoardI showBoard();
}
