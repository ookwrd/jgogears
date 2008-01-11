/**
 * 
 */
package jgogears.graphold;
import java.util.*;

import jgogears.*;


/**
 * @author syeates
 * 
 */
public final class Graph implements Cloneable {
    protected Vector<Node> nodes = new Vector<Node>();
    protected BoardInterface      board = null;
    
    protected Node[][] grid = null;
    
    static final boolean DEBUG = false;
    
    protected void initGrid(){
        if (grid != null)
            return;
        this.grid = new Node[board.getSize()][board.getSize()];
        Iterator<Node> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            Iterator<Vector<Short>> vertexes = node.getVertexes().iterator();
            while (vertexes.hasNext()) {
                Vector<Short> vertex = vertexes.next();
                if (vertex.size() != 2)
                    throw new Error(
                            "Internal error in GraphUtilities::getNode()");
                grid[vertex.elementAt(0)][vertex.elementAt(1)] = node;
            }
        }
    }
    
    public Node getNode(int row, int column){
        if (row < 0 || column < 0 || row >= board.getSize()|| column >= board.getSize())
            return null;
        if (grid == null)
            initGrid();
        return grid[row][column];
    }

    public Graph(Graph g) {
        this.board = g.board;
        Hashtable<Node, Node> mapping = new Hashtable<Node, Node>();
        Iterator<Node> i = g.nodes.iterator();
        while (i.hasNext()) {
            Node oldnode = i.next();
            Node newnode = new Node();
            this.nodes.add(newnode);
            mapping.put(oldnode, newnode);
            mapping.put(newnode, oldnode);
            newnode.setColour(oldnode.getColour());
            Iterator<Vector<Short>> j = oldnode.vertexes.iterator();
            while (j.hasNext()) {
                newnode.vertexes.add(new Vector<Short>(j.next()));
            }
        }
        // System.err.println("node count "+ g.nodes.size() + "/" +
        // nodes.size());
        i = g.nodes.iterator();
        while (i.hasNext()) {
            Node oldnode = i.next();
            Node newnode = mapping.get(oldnode);
            Iterator<Connection> j = oldnode.getConnections().iterator();
            while (j.hasNext()) {
                Connection c = j.next();
                Node first = c.getFirst();
                if (first == oldnode)
                    first = newnode;
                Node second = c.getSecond();
                if (second == oldnode)
                    second = newnode;
               
                newnode.addConnection(new Connection(first, second, 
                        c.getFirst_row(), c.getFirst_column(), 
                        c.getSecond_row(), c.getSecond_column()));
            }
        }
    }

    public Object clone() {

        return new Graph(this);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("========================\n");
        Iterator<Node> i = nodes.iterator();
        while (i.hasNext()) {

            buf.append(i.next().toString()).append("\n");
        }
        buf.append("========================\n");
        return buf.toString();
    }

    public Graph(GoBoard board) {
        if (DEBUG) System.err.print(".");

        this.board = board;
        Vector<Vector<Node>> vecvec = new Vector<Vector<Node>>();
        for (short i = 0; i < board.getSize(); i++) {
            Vector<Node> vec = new Vector<Node>();
            for (short j = 0; j < board.getSize(); j++) {
                Node node = new Node((short) board.getColour(i, j), i, j);
                vec.add(node);
                this.nodes.add(node);
            }
            vecvec.add(vec);
        }
        for (short i = 0; i < board.getSize(); i++) {
            for (short j = 0; j < board.getSize(); j++) {
                if (i > 0) {
                    Connection c = new Connection(vecvec.elementAt(i).elementAt(j),
                                vecvec.elementAt(i - 1).elementAt(j),
                                i,j,(short) ((short) i-1), j );
                    vecvec.elementAt(i).elementAt(j).addConnection(c);
                    vecvec.elementAt(i - 1).elementAt(j).addConnection(c);
                }
                if (j > 0){ 
                    Connection c = new Connection(vecvec.elementAt(i).elementAt(j),
                            vecvec.elementAt(i - 1).elementAt(j),
                            i,j,(short) ((short) i),(short) ((short)j) );
                vecvec.elementAt(i).elementAt(j).addConnection(c);
                vecvec.elementAt(i - 1).elementAt(j).addConnection(c);
               }
            }
        }
        merge();
    }
    public boolean merge(Node first, Node second) {
        grid = null;
        Node n = new Node(first, second);
        nodes.remove(first);
        nodes.remove(second);
        nodes.add(n);

        // stitch the new node into the graph in place of the old
        Iterator<Node> i = nodes.iterator();
        while (i.hasNext()) {
            Node current = i.next();
            if (current.getConnections().contains(first)) {
                current.getConnections().remove(first);
                //current.getConnections().add(n);
                //n.addConnection(current);
            }
            if (current.getConnections().contains(second)) {
                current.getConnections().remove(second);
                //current.getConnections().add(n);
                //n.addConnection(current);
            }
        }

        // System.err.println(first);
        // System.err.println(second);
        // System.err.println("merged to create");
        // System.err.println(n);
        // make things fail fast if first and second are used
        first.setConnections(null);
        first.setVertexes(null);
        second.setConnections(null);
        second.setVertexes(null);
        return true;
    }
    public short merge() {
        grid = null;
        System.err.print(":");
        boolean done = false;
        while (!done) {
            boolean altered = false;
            Iterator<Node> i = nodes.iterator();
            while (!altered && i.hasNext()) {
                Node first = i.next();
                Iterator<Connection> j = first.connections.iterator();
                while (!altered && j.hasNext()) {
                    Connection second = j.next();
 /*                   if (first.compareTo(second) != 0
                            && first.getColour() == second.getColour()) {
                        merge(first, second);
                        altered = true;
                    }
*/                }
            }
            done = !altered;
        }
        return (short) nodes.size();
    }

    public BoardInterface getBoard() {
        return board;
    }

    public void setBoard(BoardInterface board) {
        this.board = board;
    }

    public Vector<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Vector<Node> nodes) {
        this.nodes = nodes;
    }

}
