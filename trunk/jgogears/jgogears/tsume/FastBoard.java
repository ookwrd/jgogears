package jgogears.tsume;
import java.util.*;  

import jgogears.*;

public class FastBoard implements BoardInterface {
    
    private BitSet bits = null;
    private short size = 19;
    private boolean changed = true;
    
    private static BitSet empty = null;
    
    final static short    BITS_PER_VERTEX = 4;
    
    
    public FastBoard(short size){
        if (empty == null) {
            empty = new BitSet();
        }
        bits =new BitSet();
        bits.or(empty);
        this.size = size;
    }
    
    public FastBoard(int size){
        if (empty == null) {
            empty = new BitSet();
        }
        bits =new BitSet();
        bits.or(empty);
        this.size = (short)size;
    }
   
    public FastBoard(){
        if (empty == null) {
            empty = new BitSet();
        }
        bits =new BitSet();
        bits.or(empty);
    }
    
   public  BoardInterface newBoard(GoMove move){
        FastBoard result = new FastBoard(this.size);
        result.bits.or(this.bits);
        result.changed = true;
        
        if (move == null)
            return result;
        if (move.getResign()) {
            // do nothing
        } else if (move.getPass()) {
            // do nothing, since GoBoard doesn't know whose turn it is
        } else {
            
            result.setColour(move.getRow(),move.getColumn(), move.getColour());
            
            // TODO remove captures
        }
        
        return result;
    }

   /*
    * @see jgogears.BoardInterface#
    */
   public int setColour(int row, int column, short colour) {
       changed = true;
   int offset = (row * BITS_PER_VERTEX * size) + (column * BITS_PER_VERTEX) ;
    int result = getColour(row, column);
         
      switch (colour){
          case VERTEX_EMPTY :
              bits.set(offset, false);
              bits.set(offset+1, false);
              break;
          case VERTEX_KO :
              bits.set(offset, false);
              bits.set(offset+1, true);
              break;
          case VERTEX_BLACK :
              bits.set(offset, true);
              bits.set(offset+1, false);
              break;
          case VERTEX_WHITE :
              bits.set(offset, true);
              bits.set(offset+1, true);
              break;
          default :
              throw new Error();
      }
   
   return result;
   }
   /*
    * @see jgogears.BoardInterface#
    */
   public int getColour(int row, int column) {
       int offset = (row * BITS_PER_VERTEX * size) + (column * BITS_PER_VERTEX) ;
       int result = -1;
       if (bits.get(offset))
            if (bits.get(offset+1))
               return VERTEX_WHITE;
           else 
               return VERTEX_BLACK;
       else if (bits.get(offset+1))
           return VERTEX_KO;
       else
           return VERTEX_EMPTY;

    }

    
    /* (non-Javadoc)
     * @see jgogears.BoardInterface#getSize()
     */
    public short getSize() {
        return size;
    }

    
    
}
