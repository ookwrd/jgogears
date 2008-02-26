/**
 * 
 */
package jgogears;

import junit.framework.TestCase;

/**
 * test TwoGTP with a pair of GnuGo Players
 * @author syeates
 *
 */
public class TwoGTPTest extends TestCase {

	public void testRaw() throws Exception { 
		TwoGTPRaw two = new TwoGTPRaw();
		assertNotNull(two);
		two.setBlack(new GnuGoEngine(9));
		two.setWhite(new GnuGoEngine(9));
		two.playOutGame();
		GTPScore scoreb = two.getBlack().getFinalScore();
		GTPScore scorew = two.getWhite().getFinalScore();
		assertTrue(scoreb +  " " + scorew, scoreb.equals(scorew));
		}

}
