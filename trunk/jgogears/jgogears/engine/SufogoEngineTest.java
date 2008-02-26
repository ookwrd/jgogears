/**
 * 
 */
package jgogears.engine;

import junit.framework.TestCase;
import jgogears.GTPScore;
import jgogears.*;

/**
 * test TwoGTP with a pair of GnuGo Players
 * @author syeates
 *
 */
public class SufogoEngineTest extends TestCase {
	
	public void testSimple() throws Exception { 
		
		// TODO make sure this terminates
		GTPState state  = new GTPState ();
		Model model = new Model();
		ModelTest.trainNFiles(  10, model);
		
		SufgoEngine black = new SufgoEngine();
		black.setModel(model);
		
		SufgoEngine white = new SufgoEngine();
		white.setModel(model);
		
		TwoGTP two = new TwoGTP();
		assertNotNull(two);
		two.setBlack(new SufgoEngine());
		two.setWhite(new SufgoEngine());
		state = two.playOutGame();
		GTPScore w = two.getWhite().getFinalScore(state);
		GTPScore b = two.getBlack().getFinalScore(state);
		assertTrue(w.equals(b));
		}

}
