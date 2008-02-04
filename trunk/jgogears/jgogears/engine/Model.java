package jgogears.engine;

import java.io.*;
import java.util.*;

import jgogears.*;

public final class Model {

	public static float DELTA = (float) 0.001;

	public static boolean DEBUG = false;

	private static Random random = new Random();

	static public double getRandomDelta() {
		double r = random.nextDouble();
		return r * DELTA;
	}

	static public double getRandom() {
		double r = random.nextDouble();
		return r;
	}

	private Node root = new Node();

	public Node getRoot() {
		return root;
	}

	public Model() {
	}

	public void train() {
		// TODO
	}

	public long countNodes(Node node) {
		long count = 1;
		if (node.black != null)
			count = count + countNodes(node.black);
		if (node.white != null)
			count = count + countNodes(node.white);
		if (node.empty != null)
			count = count + countNodes(node.empty);
		return count;
	}

	public void train(Game game) {
		Iterator<BoardI> boards = game.getBoards();
		if (boards == null)
			throw new Error();
		Iterator<Move> moves = game.getMoves();
		if (moves == null)
			throw new Error();
		int movecounter = 1;

		double strengthB = game.getBlackRank().getRating();
		double strengthW = game.getWhiteRank().getRating();

		while (boards.hasNext() && moves.hasNext()) {
			movecounter++;
			BoardI board = boards.next();
			if (board == null)
				throw new Error();
			Move move = moves.next();
			if (move == null)
				throw new Error();
			if (DEBUG)
				System.out.println("next board is: \n" + board);
			if (DEBUG)
				System.out.println("about to train on: " + move);
			int colour = move.getColour();
			boolean isBlack = colour == BoardI.VERTEX_BLACK;
			float str = (float) (isBlack ? strengthB : strengthW);

			// mark the remaining points as not worth playing
			if (game != null || game.getScore() != null
					|| game.getScore().getScored() || move != null
					|| move.getPass()) {
				movecounter++;
				for (short i = 0; i < game.getSize(); i++)
					for (short j = 0; j < game.getSize(); j++)
						for (short sym = 0; sym < 8; sym++) {
							VertexLineariser linear = new VertexLineariser(
									board, i, j, sym);

							boolean played = false;
							if ((!move.getPass()) && move.getRow() == i
									&& move.getColumn() == j)
								played = true;
							// TODO
							train(linear, str, played);
						}
			}
		}
	}

	public void train(VertexLineariser linear, float strength, boolean played) {
		if (strength == 0.0)
			strength = DELTA;
		Node current = this.root;
		while (linear.hasNext()) {
			Short colour = linear.next();
			switch (colour) {
			case BoardI.VERTEX_BLACK:
				if (current.black == null)
					if (played)
						current.black = new Node(strength);
					else
						return;
				current = current.black;
				break;
			case BoardI.VERTEX_WHITE:
				if (current.white == null)
					if (played)
						current.white = new Node(strength);
					else
						return;
				current = current.white;
				break;
			case BoardI.VERTEX_OFF_BOARD:
				if (current.off == null)
					if (played)
						current.off = new Node(strength);
					else
						return;
				current = current.off;
				break;
			case BoardI.VERTEX_EMPTY:
			case BoardI.VERTEX_KO:
				if (current.empty == null)
					if (played)
						current.empty = new Node(strength);
					else
						return;
				current = current.empty;
				break;

			default:
				throw new Error();
			}
			if (played) {
				if (strength > current.score) {
					current.score = (float) strength;
				} else {
					current.score = current.score + DELTA;
				}
			} else {
				if (strength > current.score) {
					current.score = current.score - DELTA;
				} else {
					current.score = current.score - DELTA;
				}
			}
			current.count++;
		}
	}

	public final static float max(float a, float b) {
		if (a >= b)
			return a;
		else
			return b;
	}

	public float[][] getScores(BoardI board, short colour) {
		float[][] result = new float[board.getSize()][board.getSize()];
		for (short i = 0; i < board.getSize(); i++)
			for (short j = 0; j < board.getSize(); j++) {
				result[i][j] = -DELTA;
				for (short sym = 0; sym < 8; sym++) {
					float tmp = getScore(board, colour, i, j, sym);
					result[i][j] = max(result[i][j], tmp);
				}
			}
		return result;
	}

	public float getScore(BoardI board, short colour, short row, short column,
			short sym) {
		VertexLineariser linear = new VertexLineariser(board, row, column, sym);
		if (!linear.hasNext())
			throw new Error();
		Node current = this.root;
		int counter = 1;
		float result = DELTA;
		while (linear.hasNext()) {
			if (DEBUG)
				System.out.println("result = " + result + " current.score = "
						+ current.score);
			result = (float) (java.lang.Math.sqrt(result) + current.score);
			counter++;
			Short c = linear.next();
			switch (c) {
			case BoardI.VERTEX_BLACK:
				if (current.black == null)
					return result;
				else
					current = current.black;
				break;
			case BoardI.VERTEX_WHITE:
				if (current.white == null)
					return result;
				else
					current = current.white;
				break;
			case BoardI.VERTEX_OFF_BOARD:
				if (current.off == null)
					return result;
				else
					current = current.off;
				break;
			case BoardI.VERTEX_KO:
			case BoardI.VERTEX_EMPTY:
				if (current.empty == null)
					return result;
				else
					current = current.empty;
				break;
			default:
				throw new Error();
			}
		}
		return result;
	}

	/**
	 * Class to hold a single node in the tree.
	 * 
	 * @author stuart
	 * 
	 */
	private final class Node {
		Node() {
		};

		Node(double score) {
			this.score = (float) score;
		};

		Node(float score) {
			this.score = score;
		};

		float score = 1;
		long count = 0;
		Node white = null;
		Node off = null;
		Node black = null;
		Node empty = null;
	}

}
