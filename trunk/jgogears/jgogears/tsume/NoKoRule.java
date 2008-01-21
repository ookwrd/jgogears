/**
 * 
 */
package jgogears.tsume;

import java.util.*;

import jgogears.GoBoard;
import jgogears.GoGame;
import jgogears.GoMove;

/**
 * @author Stuart
 * 
 */
public class NoKoRule extends KoRule {

	TreeSet <Vertex> getString(short rowb, short columnb, GoBoard board){
		TreeSet <Vertex> string = new TreeSet <Vertex>();
		Vertex seed = new Vertex(rowb,columnb);
		string.add(seed);
		short colour = (short)board.getColour(rowb, columnb);
	
		boolean changed = false;
		TreeSet <Vertex> newstring = new TreeSet <Vertex>(string);
		do {
		Iterator<Vertex> i = string.iterator();
		while (i.hasNext()){
			Vertex current = i.next();
			short row = current.getRow();
			short column = current.getColumn();
			if (board.getColour(row, column+1) == colour){
				Vertex adjacent = new Vertex(row, column+1);
				newstring.add(adjacent);
			}
			if (board.getColour(row, column-1) == colour){
				Vertex adjacent = new Vertex(row, column+1);
				newstring.add(adjacent);
			}
			if (board.getColour(row+1, column) == colour){
				Vertex adjacent = new Vertex(row+1, column);
				newstring.add(adjacent);
			}
			if (board.getColour(row-1, column) == colour){
				Vertex adjacent = new Vertex(row-1, column);
				newstring.add(adjacent);
			}
		
		}
		changed = string.size() == newstring.size();
		if (string.size() > newstring.size())
			throw new Error("string has got smaller!");
		string = newstring;
		} while (changed);
		
		return string;
	}
	
	short getLiberties(short rowb, short columnb, GoBoard board){
		TreeSet <Vertex> string = getString(rowb,columnb,board);
		TreeSet <Vertex> liberties = new TreeSet <Vertex>();
		
		Iterator<Vertex> i = string.iterator();
		while (i.hasNext()){
			Vertex current = i.next();
			short row = current.getRow();
			short column = current.getColumn();
			if (board.getColour(row, column+1) == GoBoard.VERTEX_EMPTY || 
					board.getColour(row, column+1) == GoBoard.VERTEX_KO){
				Vertex adjacent = new Vertex(row, column+1);
				liberties.add(adjacent);
			}
			if (board.getColour(row, column-1) == GoBoard.VERTEX_EMPTY || 
					board.getColour(row, column-1) == GoBoard.VERTEX_KO){
				Vertex adjacent = new Vertex(row, column-1);
				liberties.add(adjacent);
			}
			if (board.getColour(row+1, column) == GoBoard.VERTEX_EMPTY || 
					board.getColour(row+1, column) == GoBoard.VERTEX_KO){
				Vertex adjacent = new Vertex(row+1, column);
				liberties.add(adjacent);
			}
			if (board.getColour(row-1, column) == GoBoard.VERTEX_EMPTY || 
					board.getColour(row-1, column) == GoBoard.VERTEX_KO){
				Vertex adjacent = new Vertex(row-1, column);
				liberties.add(adjacent);
			}
		}
		return (short)liberties.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.tsume.KoRule#captures(jgogears.GoGame, jgogears.GoBoard,
	 *      jgogears.GoMove)
	 */
	@Override
	public Iterator<GoMove> captures(GoGame game, GoBoard Board, GoMove move) {

		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.tsume.KoRule#getDescription()
	 */
	@Override
	public String getDescription() {
		return "A ko rule which doesn't recognise any form of Ko whatsoever and allows unbounded loops and repetition.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.tsume.KoRule#getName()
	 */
	@Override
	public String getName() {
		return "No Ko";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.tsume.KoRule#leavesKo(jgogears.GoGame, jgogears.GoBoard,
	 *      jgogears.GoMove)
	 */
	@Override
	public boolean leavesKo(GoGame game, GoBoard Board, GoMove move) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jgogears.tsume.KoRule#moveIsLegal(jgogears.GoGame, jgogears.GoBoard,
	 *      jgogears.GoMove)
	 */
	@Override
	public boolean moveIsLegal(GoGame game, GoBoard Board, GoMove move) {
		// TODO Auto-generated method stub
		return false;
	}

}
