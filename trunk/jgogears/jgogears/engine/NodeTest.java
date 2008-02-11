/**
 * 
 */
package jgogears.engine;

import junit.framework.TestCase;

/**
 * Test class Node
 * @author syeates
 *
 */
public class NodeTest extends TestCase {

	/**
	 * Test method for {@link jgogears.engine.Node#Node()}.
	 */
	public void testNode() {
		Node node = new Node();
		assertNotNull(node);
	}

	/**
	 * Test method for {@link jgogears.engine.Node#getCount()}.
	 */
	public void testGetCount() {
		Node node = new Node();
		Node node2 = new Node();
		assertNotNull(node);
		assertNotNull(node2);
		assertTrue(node.getCount() == 0);
		assertTrue(node2.getCount() == 0);
		node.setCount(10);
		node2.setCount(10);
		assertTrue(node.getCount() == 10);
		assertTrue(node2.getCount() == 10);
	}

	/**
	 * Test method for {@link jgogears.engine.Node#setCount(long)}.
	 */
	public void testSetCount() {
		Node node = new Node();
		Node node2 = new Node();
		assertNotNull(node);
		assertNotNull(node2);
		assertTrue(node.getCount() == 0);
		assertTrue(node2.getCount() == 0);
		node.setCount(10);
		node2.setCount(10);
		assertTrue(node.getCount() == 10);
		assertTrue(node2.getCount() == 10);
	}

	/**
	 * Test method for {@link jgogears.engine.Node#getWhite()}.
	 */
	public void testGetWhite() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link jgogears.engine.Node#setWhite(jgogears.engine.Node)}.
	 */
	public void testSetWhite() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link jgogears.engine.Node#getOff()}.
	 */
	public void testGetOff() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link jgogears.engine.Node#setOff(jgogears.engine.Node)}.
	 */
	public void testSetOff() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link jgogears.engine.Node#getBlack()}.
	 */
	public void testGetBlack() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link jgogears.engine.Node#setBlack(jgogears.engine.Node)}.
	 */
	public void testSetBlack() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link jgogears.engine.Node#getEmpty()}.
	 */
	public void testGetEmpty() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link jgogears.engine.Node#setEmpty(jgogears.engine.Node)}.
	 */
	public void testSetEmpty() {
		fail("Not yet implemented");
	}

}
