package jgogears.tsume;

import java.util.TreeSet;

import junit.framework.TestCase;
import jgogears.*;

public class NoKoRuleTest extends TestCase {

	public void testinherits() {
		NoKoRule rule = new NoKoRule();
		assertNotNull(rule);
		KoRule rule2 = rule;
		assertNotNull(rule2);
	}

	public void testStringEmptyBoard() {
		NoKoRule rule = new NoKoRule();
		short size = 19;
		GoBoard board = new GoBoard(size);
		
		for (short i=0;i<size;i++)
			for (short j=0;j<size;j++){
				TreeSet <Vertex> string = rule.getString(i, j, board);
				assertNotNull(string);
				assertTrue("" + i + " / " + j + " / " + string.size() + " / " + size* size  + " (" + string + ")", string.size() == size * size);
			}
	}

	public void testStringOffBoard() {
		NoKoRule rule = new NoKoRule();
		short size = 7;
		GoBoard board = new GoBoard(size);
		
		
				TreeSet <Vertex> string = rule.getString((short)-1,(short) -1, board);
				assertNotNull(string);
				assertTrue( string.size() == 0);

				string = rule.getString((short)0,(short) -1, board);
				assertNotNull(string);
				assertTrue( string.size() == 0);
				string = rule.getString((short)15,(short) -1, board);
				assertNotNull(string);
				assertTrue( string.size() == 0);
				string = rule.getString((short)-15,(short) -15, board);
				assertNotNull(string);
				assertTrue( string.size() == 0);
	}

	public void testStringAlmostEmptyBoard() {
		NoKoRule rule = new NoKoRule();
		short size = 7;
		
		for (short i=0;i<size;i++)
			for (short j=0;j<size;j++)
				for (short k=0;k<size;k++)
					for (short l=0;l<size;l++){
						GoBoard board = new GoBoard(size);
						assertNotNull(board);
						GoMove move = new GoMove(k,l,GoBoard.VERTEX_BLACK);
						assertNotNull(move);
						assertTrue(move.getPlay());
						assertTrue(move.getRow() == k);
						assertTrue(move.getColour() == GoBoard.VERTEX_BLACK);
						assertTrue(move.getColumn() == l);
						
						board = board.newBoard(move);
						assertNotNull(board);
						
						TreeSet <Vertex> string = rule.getString(i, j, board);
						assertNotNull(string);
						if (i != k || j != l)
							assertTrue("" + i + " / " + j + " / " + k + " / " + l + " / " + string.size() + " / " + (size* size -1)  + " (" + string + ")", string.size() == size * size -1 );
						else
							assertTrue("" + i + " / " + j + " / " + k + " / " + l + " / " + string.size() + " / " + size* size  + " (" + string + ")", string.size() == 1);
						
					}
	}

}
