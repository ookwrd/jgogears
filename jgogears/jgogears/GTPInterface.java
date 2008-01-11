package jgogears;

import jgogears.*;

public interface GTPInterface {
    /** get the protocol version */
    int getProtocolVersion();
    /** get the name of the engine */
    String getEngineName();
    /** get the version of the engine */
    String getEngineVersion();
    boolean getKnownCommand(String command);
    String[] getListCommands();
    boolean quit();
    GTPScore getFinalScore(GTPState state);
    void setBoardSize(short size, GTPState state);
    void clearBoard(GTPState state);
    void setKomi(double komi, GTPState state);
    GoMove[] fixedHandicap(int handicap, GTPState state);
    GoMove[] placeFreeHandicap(int handicap, GTPState state);
    void placeFreeHandicap(jgogears.GoMove[] stones, GTPState state);
    void play(GoMove move, GTPState state);
    GoMove genMove(short colour, GTPState state);
    boolean undo(GTPState state);
    void setTimeSettings(double mainTime, double byoYomiTime,
            double byoYomiStones, GTPState state);
    void setTimeLeft(int colour, double byoYomiTime, double byoYomiStones,
            GTPState state);
    GoMove[] finalStatusList(String status, GTPState state);
    void loadsgf(String filename, int moveNumber, GTPState state);
    GoMove regGenMove(int colour, GTPState state);
    BoardInterface showBoard(GTPState state);

}
