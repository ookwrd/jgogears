package jgogears;

import java.util.*;

/**
 * various utilities to help parsing GTP output
 * 
 * @author syeates
 * 
 */
public class GTPParserUtils {
    /**
     * Takes a string returned from the GTP protocol and returns either an Error
     * describing the error in the string or a null if this is not an error
     * 
     * @return the error represented by this string
     */
    static public Error getError(String s) {
        if (s == null) return null;
        if (s.length() < 1) return null;
        if (s.charAt(0) == ' ') return getError(s.substring(1));
        if (s.charAt(0) == '?') {
            int i;
            for (i = 1; s.length() > i && Character.isDigit(s.charAt(i)); i++);
            return new Error(s.substring(i));
        }
        return null;
    }
    //TODO make this mildy efficient by not creating a billion temp orary strings.
    // probably use a stringbuffer
    static String stripIntro(String s){
        if (s == null) return null;
        if (s.length() == 0) return s;
        // strip leading whitespace
        while (s.length() != 0 && s.charAt(0) == ' '){
            s = s.substring(1);
        }
        // strip the equal sign
        while (s.length() != 0 && s.charAt(0) == '='){
            s = s.substring(1);
        }
        // strip the number
        while (s.length() != 0 && Character.isDigit(s.charAt(0))){
            s = s.substring(1);
        }
        // strip leading whitespace
        while (s.length() != 0 && s.length() != 0 && s.charAt(0) == ' '){
            s = s.substring(1);
        }
        if (s.length() == 0) return s;
        if (s.charAt(0) == '?') { throw getError(s); }

        return s;
    }
    
    static BoardInterface parseGnuGoBoard(String s){
        
        // first create an empty board of the right size.
        BoardInterface board = null;
     if (s.contains("   A B C\n")){
        board = new GoBoard(3);
    } else if (s.contains("   A B C D E\n")){
        board = new GoBoard(5);
    } else if (s.contains("   A B C D E F G\n")){
        board = new GoBoard(7);
    } else if (s.contains("   A B C D E F G H J\n")){
        board = new GoBoard(9);
    } else if (s.contains("   A B C D E F G H J K L\n")){
        board = new GoBoard(11);
    } else if (s.contains("   A B C D E F G H J K L M N\n")){
        board = new GoBoard(13);
    } else if (s.contains("   A B C D E F G H J K L M N O P\n")){
        board = new GoBoard(15);
    } else if (s.contains("   A B C D E F G H J K L M N O P Q R\n")){
        board = new GoBoard(17);
    } else if (s.contains("   A B C D E F G H J K L M N O P Q R S T\n")){
        board = new GoBoard(19);
    } else if (s.contains("   A B C D E F G H J K L M N O P Q R S T U V\n")){
        board = new GoBoard(21);
    } else if (s.contains("   A B C D E F G H J K L M N O P Q R S T U V W X\n")){
        board = new GoBoard(23);
    } else if (s.contains("   A B C D E F G H J K L M N O P Q R S T U V W X Y Z\n")){
        board = new GoBoard(25);
        } else {
            throw new Error("Unknown board or board size" + s);
        }
     // parse the main table
     short row =0, column =0;
     int offset = 0;
     boolean seenADot = false;
     boolean seenAPlus = false;
     boolean seenAnOh = false;
     boolean seenACross = false;
     boolean seenADigit = false;
     while (offset < s.length()){
         switch (s.charAt(offset)){
             
             // EOL
             case '\n': {
                 seenADot = seenAPlus = seenAnOh = seenACross =  seenADigit = false;
                 row++; 
                 if (row >= board.getSize())
                     return board;
                 column = 0;
                 break;
             }
             // whitespace
             case ' ': 
             case '(': 
             case ')': {
                 break;
             }
             case 'O' :
             {
                 if (row > board.getSize()) 
                     break;
                 GoMove move =  new GoMove(row,column, BoardInterface.VERTEX_BLACK);
                 board = board.newBoard(move);
                 column++;
                 break;
             }
             case 'X' :
             {
                 if (row > board.getSize()) 
                     break;
                 GoMove move =  new GoMove(row,column, BoardInterface.VERTEX_BLACK);
                 board = board.newBoard(move);
                 column++;
                 break;
             }
                 
         }
         offset++;
     }
        return board;
    }
    
    static GoMove[] parseVertexList(String s) {
        s = stripIntro(s);
        Stack<GoMove> vert = new Stack<GoMove>();
        if (s != null && s.length() != 0) {
          
        do {
            // System.err.println("parseVertexList(\"" + s + "\")");
            GoMove move = new GoMove();
            while (s.indexOf(' ') == 1 || s.indexOf(' ') == 0) {
                s = s.substring(1);
            }
            if (s.indexOf(' ') != -1) {
                move.parseVertex(s.substring(0, s.indexOf(' ')));
                s = s.substring(s.indexOf(' '));
            } else {
                move.parseVertex(s);
                s = "";
            }
            vert.add(move);
        } while (s.length() > 0);
        }
        return vert.toArray(new GoMove[vert.size()]);
    }

}
