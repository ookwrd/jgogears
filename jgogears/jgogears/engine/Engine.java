/**
 * 
 */
package jgogears.engine;

import java.io.IOException;

import jgogears.*;

/**
 * TODO
 * 
 * @author syeates
 */
public class Engine {

	/**
	 * TODO
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		GTPState state = new GTPState();
		Model model = new Model();
		System.out.println("about to train model");

		new Trainer().trainFiles(100, model);
		System.out.println("model trained");

		SufgoEngine black = new SufgoEngine();
		black.setModel(model);

		SufgoEngine white = new SufgoEngine();
		white.setModel(model);

		TwoGTP two = new TwoGTP();

		two.setBlack(black);
		two.setWhite(white);
		for (int i = 0; i < 200; i++) {
			state = two.move();
			System.out.println(state.getBoard());
		}

	}

}
