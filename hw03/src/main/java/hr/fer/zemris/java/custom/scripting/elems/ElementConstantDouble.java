package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.lexer.LexerToken;
import hr.fer.zemris.java.custom.scripting.nodes.Node;

/**
 * A class which represents a double value which is stored inside a {@link Node} class
 * @author Vito Sabalic
 *
 */
public class ElementConstantDouble extends Element{
	
	private final LexerToken token = LexerToken.DOUBLE;

	/**
	 * the double value
	 */
	private double value;
	
	/**
	 * A simple constructor which assigns the provided value to the current double value
	 * @param value the value to be assigned
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(this.value);
	}
	
	/**
	 * Returns the double value
	 * @return returns the double value
	 */
	public double getValue() {
		return value;
	}
	
	@Override
	public LexerToken getToken() {
		return token;
	}
	
	
}
