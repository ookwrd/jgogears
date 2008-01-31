package jgogears.graph;

import jgogears.*;
import junit.framework.TestCase;

public class NodeTest extends TestCase {

	public void testNodeTestb() {
		Node node = new Node(BoardI.VERTEX_BLACK);
		assertNotNull(node);
		assertTrue(node.getColour() == BoardI.VERTEX_BLACK);
		assertNotNull(node.getPoints());
		assertTrue(node.getPoints().size() == 0);
	}

	public void testNodeTestk() {
		Node node = new Node(BoardI.VERTEX_KO);
		assertNotNull(node);
		assertTrue(node.getColour() == BoardI.VERTEX_KO);
		assertNotNull(node.getPoints());
		assertTrue(node.getPoints().size() == 0);
	}

	public void testNodeTestw() {
		Node node = new Node(BoardI.VERTEX_WHITE);
		assertNotNull(node);
		assertTrue(node.getColour() == BoardI.VERTEX_WHITE);
		assertNotNull(node.getPoints());
		assertTrue(node.getPoints().size() == 0);
	}

	public void testnodeteste() {
		Node node = new Node(BoardI.VERTEX_EMPTY);
		assertNotNull(node);
		assertTrue(node.getColour() == BoardI.VERTEX_EMPTY);
		assertNotNull(node.getPoints());
		assertTrue(node.getPoints().size() == 0);
	}

	public void testnodetestbl() {
		Node node = new Node(BoardI.VERTEX_BLACK);
		assertNotNull(node);
		assertTrue(node.getColour() == BoardI.VERTEX_BLACK);
		assertNotNull(node.getPoints());
		assertTrue(node.getPoints().size() == 0);

	}

}
