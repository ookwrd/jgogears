package jgogears.graph;

import jgogears.*;
import junit.framework.TestCase;

public class NodeTest extends TestCase {

    public void testNodeTestb(){
        Node node = new Node(BoardInterface.VERTEX_BLACK);
        assertNotNull(node);
        assertTrue(node.getColour() == BoardInterface.VERTEX_BLACK);
        assertNotNull(node.getPoints());
        assertTrue(node.getPoints().size() == 0);
    }

    public void testNodeTestk(){
        Node node = new Node(BoardInterface.VERTEX_KO);
        assertNotNull(node);
        assertTrue(node.getColour() == BoardInterface.VERTEX_KO);
        assertNotNull(node.getPoints());
        assertTrue(node.getPoints().size() == 0);
    }

    public void testNodeTestw(){
        Node node = new Node(BoardInterface.VERTEX_WHITE);
        assertNotNull(node);
        assertTrue(node.getColour() == BoardInterface.VERTEX_WHITE);
        assertNotNull(node.getPoints());
        assertTrue(node.getPoints().size() == 0);
    }

    public void testnodeteste(){
        Node node = new Node(BoardInterface.VERTEX_EMPTY);
        assertNotNull(node);
        assertTrue(node.getColour() == BoardInterface.VERTEX_EMPTY);
        assertNotNull(node.getPoints());
        assertTrue(node.getPoints().size() == 0);
    }

    public void testnodetestbl(){
        Node node = new Node(BoardInterface.VERTEX_BLACK);
        assertNotNull(node);
        assertTrue(node.getColour() == BoardInterface.VERTEX_BLACK);
        assertNotNull(node.getPoints());
        assertTrue(node.getPoints().size() == 0);
        
        
        
    }
    
    

}
