package jgogears;

import jgogears.*;

public interface GTPStatelessInterface {
    /** get the protocol version */
    int getProtocolVersion();
    /** get the name of the engine */
    String getEngineName();
    /** get the version of the engine */
    String getEngineVersion();
    boolean getKnownCommand(String command);
    String[] getListCommands();
    boolean quit();
    GTPScore getFinalScore();
    boolean setBoardSize(short size);
    boolean clearBoard();
    boolean setKomi(double komi);
    GoMove[] fixedHandicap(short handicap);
    GoMove[] placeFreeHandicap(short handicap);
    boolean placeFreeHandicap(jgogears.GoMove[] stones);
    boolean play(GoMove move);
    GoMove genMove(short colour);
    boolean undo();
    boolean setTimeSettings(double mainTime, double byoYomiTime,
            double byoYomiStones);
    boolean setTimeLeft(short colour, double byoYomiTime, double byoYomiStones);
    GoMove[] finalStatusList(String status);
    boolean loadsgf(String filename, int moveNumber);
    GoMove regGenMove(short colour);
    BoardInterface showBoard();
}
