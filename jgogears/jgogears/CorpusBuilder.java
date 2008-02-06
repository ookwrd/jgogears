package jgogears;

import java.io.*;
import java.util.*;
import java.util.zip.*;

// troll throught a directory heirachry containing sgf files and acrhives of sgf
// files. each file is parsed as an sgf file and copied to a directory if it meets
// the tests

public class CorpusBuilder {

	public static void main(String[] args) throws Exception {
		CorpusBuilder builder = new CorpusBuilder();

		builder.files.push(new File(builder.from));

	}

	/**
	 * @param args
	 */
	public static void mainold(String[] args) throws Exception {
		Stack<String> files = new Stack<String>();
		files.push("sgf/");

		while (files.size() > 0) {
			String filename = files.pop();
			File file = new File(filename);
			// System.err.println("examining \"" + filename + "\"");
			if (file.exists()) {
				if (!file.isDirectory()) {
					// System.err.println("\"" + filename + "\" is not a
					// directory");
					if (testForSeki(file)) {
						System.err.println("\"" + filename + "\" contains a seki");
					}
				} else {
					System.err.println("\"" + filename + "\" is a directory");
					String[] children = file.list();
					for (int i = 0; i < children.length; i++) {
						// System.err.println("pushing \"" + children[i] +
						// "\"");
						files.push(filename + "/" + children[i]);
					}
				}
			}
		}

		// testForSeki("sgf/testing/seki.sgf");
	}

	static public boolean testForSeki(File file) throws IOException {

		Game game = Game.loadFromFile(file);
		if (game == null) {
			System.err.println("failed to load \"" + file + "\"");
			return false;
		}
		if (!game.getScore().getScored() || (game.getSize() != 19))
			return false;
		if (game.isBranched())
			return false;
		Iterator<Move> moves = game.getMoves();

		GnuGoEngine engine = new GnuGoEngine();
		while (moves.hasNext()) {
			Move move = moves.next();
			// System.err.println(move);
			engine.play(move);
		}
		// engine.showBoard();
		// engine.getFinalScore();
		Date before = new Date();
		TreeSet<Vertex> stonesInSeki = engine.finalStatusList("seki");
		Date after = new Date();
		System.err.println("\"" + file + "\" time = \"" + (after.getTime() - before.getTime()) + "\" result = \""
				+ game.getScore() + "\"");
		if ((stonesInSeki != null) && (stonesInSeki.size() != 0)) {
			System.err.println("\"" + file + "\" stonesInSeki = \"" + stonesInSeki + "\"");
			return true;
		} else {
			return false;
		}
	}

	static public boolean testForSeki(String filename) throws IOException {
		return testForSeki(new File(filename));
	}

	public String from = "sgf/zipped";

	public String to = "sgf/archive";

	public Stack<File> files = new Stack<File>();

	final int BUFFER = 2048;

	public void examinefiles() {
		while (this.files.size() > 0) {
			File file = this.files.pop();
			if (file.isDirectory()) {
				File[] fs = file.listFiles();
				for (int i = 0; i < fs.length; i++)
					this.files.add(fs[i]);
			} else if (file.getName().endsWith(".zip")) {
				this.process(file);
			} else if (file.getName().endsWith(".zip")) {

			}
		}
	}

	void process(File file) {

		try {
			if (!file.getName().endsWith(".zip"))
				throw new Error();

			BufferedOutputStream dest = null;
			FileInputStream fis = new FileInputStream(file);
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				System.out.println("Extracting: " + entry);
				int count;
				byte data[] = new byte[this.BUFFER];
				// write the files to the disk
				FileOutputStream fos = new FileOutputStream(entry.getName());
				dest = new BufferedOutputStream(fos, this.BUFFER);
				while ((count = zis.read(data, 0, this.BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
			}
			zis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class SGFFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return name.endsWith(".sgf");
		}
	}

	class ZIPFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return name.endsWith(".zip");
		}
	}
}
