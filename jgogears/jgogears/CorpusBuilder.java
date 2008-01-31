package jgogears;

import java.io.*;
import java.util.zip.*;
import java.util.*;

// troll throught a directory heirachry  containing sgf files and acrhives of sgf 
// files. each file is parsed as an sgf file and copied to a directory if it meets 
// the tests

public class CorpusBuilder {

	public String from = "sgf/zipped";
	public String to = "sgf/archive";

	public Stack<File> files = new Stack<File>();

	public static void main(String[] args) throws Exception {
		CorpusBuilder builder = new CorpusBuilder();

		builder.files.push(new File(builder.from));

	}

	public void examinefiles() {
		while (files.size() > 0) {
			File file = files.pop();
			if (file.isDirectory()) {
				File[] fs = file.listFiles();
				for (int i = 0; i < fs.length; i++)
					files.add(fs[i]);
			} else if (file.getName().endsWith(".zip")) {
				process(file);
			} else if (file.getName().endsWith(".zip")) {

			}
		}
	}

	final int BUFFER = 2048;

	void process(File file) {

		try {
			if (!file.getName().endsWith(".zip"))
				throw new Error();

			BufferedOutputStream dest = null;
			FileInputStream fis = new FileInputStream(file);
			ZipInputStream zis = new ZipInputStream(
					new BufferedInputStream(fis));
			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				System.out.println("Extracting: " + entry);
				int count;
				byte data[] = new byte[BUFFER];
				// write the files to the disk
				FileOutputStream fos = new FileOutputStream(entry.getName());
				dest = new BufferedOutputStream(fos, BUFFER);
				while ((count = zis.read(data, 0, BUFFER)) != -1) {
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

	static public boolean testForSeki(String filename) throws IOException {
		return testForSeki(new File(filename));
	}

	static public boolean testForSeki(File file) throws IOException {

		GoGame goGame = GoGame.loadFromFile(file);
		if (goGame == null) {
			System.err.println("failed to load \"" + file + "\"");
			return false;
		}
		if (!goGame.getScore().getScored() || goGame.getSize() != 19)
			return false;
		if (goGame.isBranched())
			return false;
		Iterator<GoMove> moves = goGame.getMoves();

		GnuGoEngine engine = new GnuGoEngine();
		while (moves.hasNext()) {
			GoMove move =  moves.next();
			// System.err.println(move);
			engine.play(move);
		}
		// engine.showBoard();
		// engine.getFinalScore();
		Date before = new Date();
		GoMove[] stonesInSeki = engine.finalStatusList("seki");
		Date after = new Date();
		System.err.println("\"" + file + "\" time = \""
				+ (after.getTime() - before.getTime()) + "\" result = \""
				+ goGame.getScore() + "\"");
		if (stonesInSeki != null && stonesInSeki.length != 0) {
			System.err.println("\"" + file + "\" stonesInSeki = \""
					+ stonesInSeki + "\"");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
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
						System.err.println("\"" + filename
								+ "\" contains a seki");
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
