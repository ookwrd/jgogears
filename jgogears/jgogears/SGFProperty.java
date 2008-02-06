package jgogears;

import java.util.*;

public class SGFProperty {
	/**
	 * String all square brackets from a string. DOES NOT LEAVE escaped squre bracekets!
	 * 
	 * @param s
	 * @return the new string
	 */
	protected static String stripSquareBrackets(String s) {
		if (s == null)
			return "";
		return s.replaceAll("\\[", "").replaceAll("\\]", "");
	}

	private String identifier = null;

	private Vector<String> values = new Vector<String>();

	public String first() {
		return this.getValues().firstElement();
	}

	public String firstStripped() {
		return stripSquareBrackets(this.first());
	}

	/**
	 * @return the propIdent
	 */
	public String getIdentifier() {
		return this.identifier;
	}

	/**
	 * @return the propvalues
	 */
	public Vector<String> getValues() {
		return this.values;
	}

	/**
	 * @param propIdent
	 *            the propIdent to set
	 */
	public void setIdentifier(String propIdent) {
		if (this.identifier != null)
			throw new Error();
		this.identifier = propIdent;
	}

	/**
	 * @param propvalues
	 *            the propvalues to set
	 */
	public void setValues(Vector<String> propvalues) {
		if (this.values != null)
			throw new Error();
		this.values = propvalues;
	}

	public String stripped() {
		return stripSquareBrackets(this.toString());
	}

	@Override
	public String toString() {
		String result = this.getIdentifier();
		Iterator<String> i = this.getValues().iterator();
		while (i.hasNext()) {
			result = result + i.next();
		}
		return result;
	}
}
