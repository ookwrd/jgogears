package jgogears.graph;

import java.util.*;

import jgogears.*;

public class Graph {
    
    protected final TreeSet<Node> nodeset;
    protected final Node[][] nodegrid;
    protected final Point[][] pointgrid;
    protected final GoBoard board;
    protected final static boolean DEBUG = false;
    
    public Graph(GoBoard board){
        final int size = board.getSize();
        this.board = board;
        this.nodegrid = new Node[size][size];
        this.pointgrid = new Point[size][size];
        this.nodeset = new TreeSet<Node>();
        for (short i=0;i<size;i++){
            for (short j=0;j<size;j++){
                pointgrid[i][j] = new Point(i,j);
            }
        }
        for (short i=0;i<size;i++){
            for (short j=0;j<size;j++){
                if (nodegrid[i][j] == null){
                 Node node =    new Node((short)board.getColour(i, j));
                 nodeset.add(node);
                 int s = spread(i,j,node);
                 if (DEBUG) System.err.println("created a node of size " + s);
                }
            }
        }
    }
    
    
    public int spread(short i, short j, Node node){
        int count = 0;
        Stack<Vector<Short>> stack = new  Stack<Vector<Short>>();
        Vector<Short> live = new Vector<Short>();
           live.add(new Short(i));
           live.add(new Short(j));
        
        while(stack.size() > 0){
            live = stack.pop();
            i = live.elementAt(0).shortValue();
            j = live.elementAt(1).shortValue();
            if (i<0||j<0||j>=board.getSize()||i>=board.getSize())
                continue;
            if (nodegrid[i][j] != null){
                if (nodegrid[i][j].getColour() == node.getColour() && nodegrid[i][j] != node)
                    throw new Error("adjacent nodes have the same colour!");
                continue;
            }
            if (board.getColour(i, j) == node.getColour()){
                nodegrid[i][j] = node;
                node.getPoints().add(pointgrid[i][j]);
                count++;
                live = new Vector<Short>();
                live.set(0, new Short((short)(i+1)));
                live.set(1, new Short(j));
                
                live = new Vector<Short>();
                live.set(0, new Short((short)(i-1)));
                live.set(1, new Short(j));
                
                live = new Vector<Short>();
                live.set(0, new Short(i));
                live.set(1, new Short((short)(j+1)));
                
                live = new Vector<Short>();
                live.set(0, new Short(i));
                live.set(1, new Short((short)(j-1)));
             }

        }
        return count;
    }
    public Collection<Point> getNeighboringPoints(Point point){
        Vector<Point> result = new Vector<Point>(4);
        if (point.getColumn() > 0) result.add(this.getPointgrid()[point.getRow()][point.getColumn()-1]);
        if (point.getColumn() < board.getSize()-1) result.add(this.getPointgrid()[point.getRow()][point.getColumn()+1]);
        if (point.getRow() > 0) result.add(this.getPointgrid()[point.getRow()-1][point.getColumn()]);
        if (point.getRow() < board.getSize()-1) result.add(this.getPointgrid()[point.getRow()+1][point.getColumn()]);
        return result;
    }
    public GoBoard getBoard() {
        return board;
    }
    public Node[][] getNodegrid() {
        return nodegrid;
    }
    public TreeSet<Node> getNodeset() {
        return nodeset;
    }
    public Point[][] getPointgrid() {
        return pointgrid;
    }
    

}
