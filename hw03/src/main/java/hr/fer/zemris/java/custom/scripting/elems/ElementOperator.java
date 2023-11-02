package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.lexer.LexerToken;
import hr.fer.zemris.java.custom.scripting.nodes.Node;

/**
 * A class which represents an operator stored as string which is stored inside a {@link Node} class
 * @author Vito Sabalic
 *
 */
public class ElementOperator extends Element{
	
	
	private final LexerToken token = LexerToken.OPERATOR;
	
	/**
	 * The operator stored as a string
	 */
	private String symbol;
	
	/**
	 * a basic constructor which assigns the string of the provided symbol to the current symbol
	 * @param symbol the symbol to be assigned
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public String asText() {
		return this.symbol;
	}
	
	/**
	 * Returns the symbol
	 * @return returns the symbol
	 */
	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public LexerToken getToken() {
		return token;
	}
}
