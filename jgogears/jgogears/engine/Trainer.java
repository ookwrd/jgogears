/**
 * 
 */
package jgogears.engine;

import java.io.*;
import java.util.*;

import jgogears.*;

/**
 * TODO
 * @author syeates
 *
 */
public class Trainer {
	 static final private boolean DEBUG = false;
	
	
	/**
	 * Train n files.
	 * 
	 * @param count
	 *            the count
	 * @param model
	 *            the model
	 * @return the model
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Model trainNFiles(int count, Model model) throws IOException {
		Stack<String> files = new Stack<String>();
		files.push("sgf/2004-12");

		int filecount = 0;

		while (files.size() > 0 && filecount < count) {
			String filename = files.pop();
			File file = new File(filename);
			if (file.exists()) {
				if (!file.isDirectory()) {

					Game game = Game.loadFromFile(file);
					if (game.getSize() == 19) {
						filecount++;
						train(model, game);
					}
				} else {
					String[] children = file.list();
					for (int i = 0; i < children.length; i++) {
						files.push(filename + "/" + children[i]);
					}
				}
			}
		}
		if (DEBUG)
			System.err.println("Trainer::trainNFiles loaded " + filecount + " files ");
		return model;
	}
	

	/**
	 * Train.
	 * 
	 * @param game
	 *            the game
	 */
	public void train(Model model, Game game) {
		short size = game.getSize();
		Iterator<BoardI> boards = game.getBoards();
		if (boards == null)
			throw new Error();
		Iterator<Move> moves = game.getMoves();
		if (moves == null)
			throw new Error();
		int movecounter = 1;

		while (boards.hasNext() && moves.hasNext()) {
			movecounter++;
			BoardI board = boards.next();
			if (board == null)
				throw new Error();
			Move move = moves.next();
			if (move == null)
				throw new Error();
			if (DEBUG)
				System.err.println("Model::train next board is: \n" + board);
			if (DEBUG)
				System.err.println("Model::train about to train on: " + move);
			int colour = move.getColour();
			boolean isBlack = colour == BoardI.VERTEX_BLACK;
			// float str = (float) (isBlack ? strengthB : strengthW);

			if (game != null || move.getPass()) {
				movecounter++;
				for (short i = 0; i < size; i++)
					for (short j = 0; j < size; j++)
						for (short sym = 0; sym < 8; sym++) {
							// TODO this needs a lot of work, i think

							VertexLineariser linear = new VertexLineariser(board, i, j, sym, !isBlack);
							if (!move.getPass() && move.getRow() != i && move.getColumn() != j)
								train(model.getRoot(),linear, true, true, 100);
							else
								train(model.getRoot(),linear, false, true, 30);
						}
			}
		}
	}

	/**
	 * Train.
	 * 
	 * @param linear
	 *            the linear
	 * @param expand
	 *            are we expanding?
	 * @param depth
	 *            the depth to expand to
	 * @param playeda
	 *            the played
	 */
	public void train(Node root, VertexLineariser linear, boolean playeda, boolean expand, int depth) {
		if (depth <= 0)
			expand = false;
		if (root.getNotPlayed() + root.getPlayed() < 100)
			expand = false;
		depth--;
		if (playeda)
			root.setPlayed(root.getPlayed() +1);
		else
			root.setNotPlayed(root.getNotPlayed() +1);
		if (!linear.hasNext())
			return;
		Short colour = linear.next();

		switch (colour) {
		case BoardI.VERTEX_BLACK:
			if (root.getBlack() == null)
				if (expand) {
					root.setBlack(new Node());
					train(root.getBlack(),linear, playeda, false, depth);
				} else
					return;
			else
				train(root.getBlack(),linear, playeda, expand, depth);
			break;
		case BoardI.VERTEX_WHITE:
			if (root.getWhite() == null)
				if (expand) {
					root.setWhite(new Node());
					train(root.getWhite(),linear, playeda, false, depth);
				} else
					return;
			else
				train(root.getWhite(),linear, playeda, expand, depth);
			break;
		case BoardI.VERTEX_KO:
		case BoardI.VERTEX_EMPTY:
			if (root.getEmpty() == null)
				if (expand) {
					root.setEmpty(new Node());
					train(root.getEmpty(),linear, playeda, false, depth);
				} else
					return;
			else
				train(root.getEmpty(),linear, playeda, expand, depth);
			break;
		case BoardI.VERTEX_OFF_BOARD:
			if (root.getOff() == null)
				if (expand) {
					root.setOff(new Node());
					train(root.getOff(),linear, playeda, false, depth);
				} else
					return;
			else
				train(root.getOff(),linear, playeda, expand, depth);
			break;
		default:
			throw new Error();
		}
	}

}
