package jgogears.tsume;

import java.util.*;  

public final class Vertex extends Vector<Short> {

    public Vertex() { 
        this.add((short)-1);
        this.add((short)-1);
    }
    public Vertex(short row, short column) { 
        this.add(row);
        this.add(column);
    }
    public short getRow(){
        return this.elementAt(0);
    }

    public short getColmun(){
        return this.elementAt(1);
    }

}
