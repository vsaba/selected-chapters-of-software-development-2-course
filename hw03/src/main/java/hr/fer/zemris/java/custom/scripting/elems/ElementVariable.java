package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.lexer.LexerToken;
import hr.fer.zemris.java.custom.scripting.nodes.Node;

/**
 * A class which represents a variable stored as a String which is stored inside a {@link Node} class
 * @author Vito Sabalic
 *
 */
public class ElementVariable extends Element{
	
	private final LexerToken token = LexerToken.VARIABLE;

	
	/**
	 * The name of the variable
	 */
	private String name;
	
	/**
	 * A simple constructor which assigns the provided name to this name
	 * @param name the provided name of the variable
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	

	@Override
	public String asText() {
		return this.name;
	}
	
	
	/**
	 * Returns the name of the variable
	 * @return returns the name of the variable
	 */
	public String getName() {
		return name;
	}


	@Override
	public LexerToken getToken() {
		return token;
	}
}
