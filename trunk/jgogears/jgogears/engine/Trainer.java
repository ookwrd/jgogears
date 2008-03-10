/**
 * 
 */
package jgogears.engine;

import java.io.*;
import java.util.*;

import jgogears.*;

/**
 * TODO
 * 
 * @author syeates
 */
public class Trainer {
	static final private boolean DEBUG = false;
	static final private boolean PROGRESS = true;
	private boolean onlyOneNewNodePerSymmetry = true;
	private int minBranchSize = 20;
	private int defaultNumberOfFiles = Integer.MAX_VALUE;
	final public static String LIBRARY = "sgf/2004-12";

	/**
	 * Loads all the default SGF files
	 * 
	 * @param directory
	 *            the directory to load
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @return a Collection of Strings
	 */
	public static Collection<String> loadAllSGFfiles() throws IOException {
		return loadAllSGFfiles(LIBRARY);
	}

	/**
	 * Loads all SGF files in a directory
	 * 
	 * @param directory
	 *            the directory to load
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @return a Collection of Strings
	 */
	public static Collection<String> loadAllSGFfiles(String directory) throws IOException {
		Stack<String> files = new Stack<String>();
		files.push(directory);
		Stack<String> result = new Stack<String>();

		while (files.size() > 0) {
			String filename = files.pop();
			File file = new File(filename);
			if (DEBUG)
				System.err.println("examining \"" + filename + "\"");
			if (file.exists()) {
				if (!file.isDirectory()) {
					// System.err.println("\"" + filename + "\" is not a
					// directory, parsing as an SGF file");
					if (file.getName().endsWith(".sgf"))
						result.push(file.getCanonicalPath());

				} else {
					if (DEBUG)
						System.err.println("\"" + filename + "\" is a directory");
					String[] children = file.list();
					if (!file.getName().contains(".svn"))
						for (int i = 0; i < children.length; i++) {
							// System.err.println("pushing \"" + children[i] +
							// "\"");
							files.push(filename + "/" + children[i]);
						}
				}
			}
		}
		return result;

	}

	/**
	 * Train on files.
	 * 
	 * @param model
	 *            the model
	 * @return the model
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Model train(Model model) throws IOException {
		return train(defaultNumberOfFiles, model);
	}

	/**
	 * Train on files.
	 * 
	 * @param count
	 *            the count
	 * @param model
	 *            the model
	 * @return the model
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Model train(int count, Model model) throws IOException {
		Collection<String> files = loadAllSGFfiles();
		int filecount = 0;
		int examined = 0;
		Iterator<String> iterator = files.iterator();
		while (iterator.hasNext() && filecount < count) {
			String filename = iterator.next();

			Game game = Game.loadFromFile(new File(filename));
			examined++;
			if (game.getSize() == 19) {
				filecount++;
				train(model, game);
			} else {
				if (DEBUG)
					System.err.print("!");

			}
			// if (PROGRESS || DEBUG)
			// System.err.print(".");
			if (PROGRESS)
				System.err.println(filecount + "/" + examined + "/" + files.size() + "/" + count + " "
						+ model.getBoardsTrained() + "b " + model.size() + "n "
						+ ((Runtime.getRuntime().totalMemory() / 1000) - (Runtime.getRuntime().freeMemory() / 1000))
						+ "K " + Runtime.getRuntime().totalMemory() / 1000 + "K " + Runtime.getRuntime().maxMemory()
						/ 1000 + "K");

		}

		if (DEBUG)
			System.err.println("\nTrainer::trainNFiles loaded " + filecount + " files ");
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
		model.setGamesTrained(model.getGamesTrained() + 1);

		while (boards.hasNext() && moves.hasNext()) {
			movecounter++;
			model.setBoardsTrained(model.getBoardsTrained() + 1);
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

			if (game != null && !move.getPass()) {
				movecounter++;
				for (short i = 0; i < size; i++)
					for (short j = 0; j < size; j++)
						for (short sym = 0; sym < 8; sym++) {
							// TODO this needs a lot of work, i think

							VertexLineariser linear = new VertexLineariser(board, i, j, sym, !isBlack);
							if (!move.getPass() && move.getRow() != i && move.getColumn() != j)
								train(model.getRoot(), linear, true, true, 100);
							else
								train(model.getRoot(), linear, false, true, 30);
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
	 * @param root
	 *            the root of the model
	 * @param playeda
	 *            the played
	 */
	public void train(Node root, VertexLineariser linear, boolean playeda, boolean expand, int depth) {
		while (root !=null && linear.hasNext()){
		if (depth <= 0)
			expand = false;
		if (root.getNotPlayed() + root.getPlayed() < minBranchSize)
			expand = false;
		depth--;
		if (playeda)
			root.setPlayed(root.getPlayed() + 1);
		else
			root.setNotPlayed(root.getNotPlayed() + 1);
		Short colour = linear.next();
		boolean expandMore = !onlyOneNewNodePerSymmetry;

		switch (colour) {
		case BoardI.VERTEX_BLACK:
			if (root.getBlack() == null)
				if (expand) {
					root.setBlack(new Node());
					root = root.getBlack();
					expand = expandMore;
				} else
					return;
			else
				root = root.getBlack();
			break;
		case BoardI.VERTEX_WHITE:
			if (root.getWhite() == null)
				if (expand) {
					root.setWhite(new Node());
					root = root.getWhite();
					expand = expandMore;
				} else
					return;
			else
				root = root.getWhite();
			break;
		case BoardI.VERTEX_KO:
		case BoardI.VERTEX_EMPTY:
			if (root.getEmpty() == null)
				if (expand) {
					root.setEmpty(new Node());
					expand = expandMore;
					root = root.getEmpty();
				} else
					return;
			else
				root = root.getEmpty();
			break;
		case BoardI.VERTEX_OFF_BOARD:
			if (root.getOff() == null)
				if (expand) {
					root.setOff(new Node());
					expand = expandMore;
					root = root.getOff();
				} else
					return;
			else
				root = root.getOff();
			break;
		default:
			throw new Error();
		}
		}
	}

	/**
	 * get the onlyOneNewNodePerSymmetry
	 * 
	 * @return the onlyOneNewNodePerSymmetry
	 */
	public final boolean isOnlyOneNewNodePerSymmetry() {
		return onlyOneNewNodePerSymmetry;
	}

	/**
	 * set the onlyOneNewNodePerSymmetry
	 * 
	 * @param onlyOneNewNodePerSymmetry
	 *            the onlyOneNewNodePerSymmetry to set
	 */
	public final void setOnlyOneNewNodePerSymmetry(boolean onlyOneNewNodePerSymmetry) {
		this.onlyOneNewNodePerSymmetry = onlyOneNewNodePerSymmetry;
	}

	/**
	 * get the minBranchSize
	 * 
	 * @return the minBranchSize
	 */
	public final int getMinBranchSize() {
		return minBranchSize;
	}

	/**
	 * set the minBranchSize
	 * 
	 * @param minBranchSize
	 *            the minBranchSize to set
	 */
	public final void setMinBranchSize(int minBranchSize) {
		this.minBranchSize = minBranchSize;
	}

	/**
	 * get the defaultNumberOfFiles
	 * 
	 * @return the defaultNumberOfFiles
	 */
	public final int getDefaultNumberOfFiles() {
		return defaultNumberOfFiles;
	}

	/**
	 * set the defaultNumberOfFiles
	 * 
	 * @param defaultNumberOfFiles
	 *            the defaultNumberOfFiles to set
	 */
	public final void setDefaultNumberOfFiles(int defaultNumberOfFiles) {
		this.defaultNumberOfFiles = defaultNumberOfFiles;
	}

}
