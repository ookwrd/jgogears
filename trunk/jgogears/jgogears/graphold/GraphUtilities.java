package jgogears.graphold;
import java.util.*;

import jgogears.*;

public final class GraphUtilities {
    
    static final boolean DEBUG = true;
    

    /**
     * 
     * @param board
     * @param move
     * @return null for an illegal move, an empty iterator for a legal move that
     *         captures no stones or an iterator of captured stones
     */
//    public static Collection<Node> getCaptures(GoBoard board, GoMove move) {
//        if (DEBUG) System.err.print(";");
//        if (board == null) throw new Error();
//        if (move == null) throw new Error();
//
//        Vector<Node> captures = new Vector<Node>();
//        Vector<Node> liberties = new Vector<Node>();
//
//        if (move.getPass()) return captures; // passing is always legal
//        if (move.getResign()) return captures; // resigning is always legal
//
//        short row = move.getRow();
//        short column = move.getColumn();
//
//        // all legal moves are on an empty vertex
//        if ((board.getColour(row, column) != GoBoard.VERTEX_EMPTY))
//            return null;
//
//        // do a set of easy cases cheaply
//        {
//            boolean cheapliberty = false;
//            boolean cheapcapture = false;
//
//            // do the easy ones first
//            if (board.getColour(row + 1, column) == GoBoard.VERTEX_EMPTY
//                    || board.getColour(row - 1, column) == GoBoard.VERTEX_EMPTY
//                    || board.getColour(row, column - 1) == GoBoard.VERTEX_EMPTY
//                    || board.getColour(row, column + 1) == GoBoard.VERTEX_EMPTY)
//                cheapliberty = true;
//            if (cheapliberty 
//                    || board.getColour(row + 1, column) == GoBoard.VERTEX_KO
//                    || board.getColour(row - 1, column) == GoBoard.VERTEX_KO
//                    || board.getColour(row, column - 1) == GoBoard.VERTEX_KO
//                    || board.getColour(row, column + 1) == GoBoard.VERTEX_KO)
//                cheapliberty = true;
//            
//            // check to see whether we're denying any stones of the opposite
//            // colour of a liberty. If not, we can't capture any stones
//            int colour = ((move.getColour() == GoBoard.VERTEX_BLACK) ? GoBoard.VERTEX_WHITE
//                    : GoBoard.VERTEX_BLACK);
//            if (board.getColour(row + 1, column) == colour
//                    || board.getColour(row - 1, column) == colour
//                    || board.getColour(row, column - 1) == colour
//                    || board.getColour(row, column + 1) == colour)
//                cheapcapture = true;
//            if (cheapliberty && !cheapcapture) {
//                if (DEBUG) System.err.print("!");
//                return captures;
//            }
//        }
//        // this is an expensive step
//        if (board.getGraph() == null) board.getGraph();
//
//        // one of four
//        {
//            int colour = board.getColour(row, column - 1);
//            switch (colour) {
//                case GoBoard.VERTEX_KO:
//                case GoBoard.VERTEX_EMPTY: {
//                    Node node = board.getGraph().getNode(row, column - 1);
//                    liberties.add(node);
//                    break;
//                }
//                case GoBoard.VERTEX_WHITE:
//                case GoBoard.VERTEX_BLACK: {
//                    Node node = board.getGraph().getNode(row, column - 1);
//                    if (move.getColour() == colour) {
//                        if (node.getLiberties().size() > 1) 
//                            liberties.addAll(node.getLiberties());
//                        } else {
//                        if (node.getLiberties().size() == 1) {
//                            captures.add(node);
//                            liberties.add(node);
//                        }
//                    }
//                    break;
//                }
//                case GoBoard.VERTEX_OFF_BOARD: {
//                    break;
//                }
//                default:
//                    throw new Error("Internal error: " + colour);
//            }
//
//        }
//
//        // two of four
//        {
//            int colour = board.getColour(row, column + 1);
//            switch (colour) {
//                case GoBoard.VERTEX_KO:
//                case GoBoard.VERTEX_EMPTY: {
//                    Node node = board.getGraph().getNode(row, column + 1);
//                    liberties.add(node);
//                    break;
//                }
//                case GoBoard.VERTEX_WHITE:
//                case GoBoard.VERTEX_BLACK: {
//                    Node node = board.getGraph().getNode(row, column + 1);
//                    if (move.getColour() == colour) {
//                        liberties.addAll(node.getLiberties());
//                    } else {
//                        if (node.getLiberties().size() == 1) {
//                            captures.add(node);
//                            liberties.add(node);
//                        }
//                    }
//                    break;
//                }
//                case GoBoard.VERTEX_OFF_BOARD: {
//                    break;
//                }
//                default:
//                    throw new Error("Internal error: " + colour);
//            }
//
//        }
//
//        // three of four
//        {
//            int colour = board.getColour(row - 1, column);
//            switch (colour) {
//                case GoBoard.VERTEX_KO:
//                case GoBoard.VERTEX_EMPTY: {
//                    Node node = board.getGraph().getNode(row - 1, column);
//                    liberties.add(node);
//                    break;
//                }
//                case GoBoard.VERTEX_WHITE:
//                case GoBoard.VERTEX_BLACK: {
//                    Node node = board.getGraph().getNode(row - 1, column);
//                    if (move.getColour() == colour) {
//                        liberties.addAll(node.getLiberties());
//                    } else {
//                        if (node.getLiberties().size() == 1) {
//                            captures.add(node);
//                            liberties.add(node);
//                        }
//                    }
//                    break;
//                }
//                case GoBoard.VERTEX_OFF_BOARD: {
//                    break;
//                }
//                default:
//                    throw new Error("Internal error: " + colour);
//            }
//
//        }
//
//        // four of four
//        {
//            int colour = board.getColour(row + 1, column);
//            switch (colour) {
//                case GoBoard.VERTEX_KO:
//                case GoBoard.VERTEX_EMPTY: {
//                    Node node = board.getGraph().getNode(row + 1, column);
//                    liberties.add(node);
//                    break;
//                }
//                case GoBoard.VERTEX_WHITE:
//                case GoBoard.VERTEX_BLACK: {
//                    Node node = board.getGraph().getNode(row + 1, column);
//                    if (move.getColour() == colour) {
//                        liberties.addAll(node.getLiberties());
//                    } else {
//                        if (node.getLiberties().size() == 1) {
//                            captures.add(node);
//                            liberties.add(node);
//                        }
//                    }
//                    break;
//                }
//                case GoBoard.VERTEX_OFF_BOARD: {
//                    break;
//                }
//                default:
//                    throw new Error("Internal error: " + colour);
//            }
//
//        }
//
//        Node node = board.getGraph().getNode(row, column);
//        captures.remove(node);
//        liberties.remove(node);
//
//        // System.err.println("(" + move.getRow() + "/" + move.getColumn() + "-"
//        // + captures + "-" + liberties + ")");
//        if (liberties.size() > 0) return captures;
//        return null;
//    }
//
//    public static boolean isMoveLegal(GoBoard board, GoMove move) {
//        if (board == null) throw new Error();
//        if (move == null) throw new Error();
//        if (move.getPass()) return true; // passing is always legal
//        if (move.getResign()) return true; // resigning is always legal
//        short row = move.getRow();
//        short column = move.getColumn();
//        if (board.getColour(row, column) == GoBoard.VERTEX_EMPTY
//                || board.getColour(row, column) == GoBoard.VERTEX_KO) {
//
//            // do the easy ones first, because these are _really_ common
//            // and _really_ cheap
//            if (board.getColour(row, column - 1) == GoBoard.VERTEX_EMPTY
//                    || board.getColour(row, column - 1) == GoBoard.VERTEX_KO)
//                return true;
//            if (board.getColour(row, column + 1) == GoBoard.VERTEX_EMPTY
//                    || board.getColour(row, column + 1) == GoBoard.VERTEX_KO)
//                return true;
//            if (board.getColour(row - 1, column) == GoBoard.VERTEX_EMPTY
//                    || board.getColour(row - 1, column) == GoBoard.VERTEX_KO)
//                return true;
//            if (board.getColour(row + 1, column) == GoBoard.VERTEX_EMPTY
//                    || board.getColour(row + 1, column) == GoBoard.VERTEX_KO)
//                return true;
//
//            // check that the board has a graph calculated:
//            Graph graph = board.getGraph();
//            if (graph == null) graph = new Graph(board); // this has an
//            // implicit cache
//
//            Node node = graph.getNode(row + 1, column);
//            if (node != null && node.getLiberties().size() > 1) return true;
//            node = graph.getNode(row - 1, column);
//            if (node != null && node.getLiberties().size() > 1) return true;
//            node = graph.getNode(row, column + 1);
//            if (node != null && node.getLiberties().size() > 1) return true;
//            node = graph.getNode(row, column - 1);
//            if (node != null && node.getLiberties().size() > 1) return true;
//
//        } else {
//            // moves must be played into empty
//            return false;
//        }
//        throw new Error();
    }
//
//}
