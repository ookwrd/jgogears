package jgogears;

import java.util.TreeSet;
/**
 * A GTP interface for which a computer-go player need not keep any state. All necessary state is passed to the player each time the player is called.
 * @author syeates
 *
 */
public interface GTPInterface {
	
	void clearBoard(GTPState state);

	Move[] finalStatusList(String status, GTPState state);

	Move[] fixedHandicap(int handicap, GTPState state);

	Move genMove(short colour, GTPState state);

	/** get the name of the engine */
	String getEngineName();

	/** get the version of the engine */
	String getEngineVersion();

	GTPScore getFinalScore(GTPState state);

	boolean getKnownCommand(String command);

	String[] getListCommands();

	/** get the protocol version */
	int getProtocolVersion();

	void loadsgf(String filename, int moveNumber, GTPState state);

	Move[] placeFreeHandicap(int handicap, GTPState state);

	void placeFreeHandicap(jgogears.Move[] stones, GTPState state);

	void play(Move move, GTPState state);

	boolean quit();

	Move regGenMove(int colour, GTPState state);

	void setBoardSize(short size, GTPState state);

	void setKomi(double komi, GTPState state);

	void setTimeLeft(int colour, double byoYomiTime, double byoYomiStones, GTPState state);

	void setTimeSettings(double mainTime, double byoYomiTime, double byoYomiStones, GTPState state);

	BoardI showBoard(GTPState state);

	boolean undo(GTPState state);

}
