package jgogears.graph;

public final class Point  implements Comparable<Point>{

    final protected short row;
    final protected short column;

    public Point(short row, short column){
        if (row < 0) throw new Error("negative row");
        if (column < 0) throw new Error("negative column");
        this.row = row;
        this.column= column;
    }

    public int compareTo(Point p) {
        if (this == p)
            return 0;
        if (row > p.row )
            return 1;
        if (row < p.row )
            return -1;
        if (column > p.column )
            return 1;
        if (column < p.column )
            return -1;
        return 0;
    }

    public short getColumn() {
        return column;
    }

    public short getRow() {
        return row;
    }
}
