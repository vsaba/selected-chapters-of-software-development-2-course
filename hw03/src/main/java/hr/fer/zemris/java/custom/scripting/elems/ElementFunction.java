package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.lexer.LexerToken;
import hr.fer.zemris.java.custom.scripting.nodes.Node;

/**
 * A class which represents a function which is stored inside a {@link Node} class
 * @author Vito Sabalic
 *
 */
public class ElementFunction extends Element{
	
	private final LexerToken token = LexerToken.FUNCTION;
	
	/**
	 * The name of the function
	 */
	private String name;
	
	/**
	 * A simple constructor which assigns the provided name of the function to the current name of the function
	 * @param name the name to be assigned
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	@Override
	public String asText() {
		
		return this.name;
	}
	
	/**
	 * Returns the name of the function
	 * @return returns the name of the function
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public LexerToken getToken() {
		return token;
	}

}
