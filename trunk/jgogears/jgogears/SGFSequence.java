package jgogears;

import java.util.*;

public class SGFSequence extends Vector<SGFNode> {

	static final long serialVersionUID = 1;

	@Override
	public String toString() {
		Iterator<SGFNode> i = this.iterator();
		String result = "";
		while (i.hasNext()) {
			result = result + i.next().toString();
		}
		return result;
	}

}
