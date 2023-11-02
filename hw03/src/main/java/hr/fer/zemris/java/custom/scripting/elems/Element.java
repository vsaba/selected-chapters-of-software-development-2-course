package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.lexer.LexerToken;
import hr.fer.zemris.java.custom.scripting.nodes.Node;

/**
 * Base class which represents an element which is stored inside a {@link Node} class
 * @author Vito Sabalic
 *
 */
public abstract class Element {
	
	/**
	 * Returns the values contained inside the class as text
	 * @return the values contained inside the class as text
	 */
	public abstract String asText();
	
	public abstract LexerToken getToken();
	
}
