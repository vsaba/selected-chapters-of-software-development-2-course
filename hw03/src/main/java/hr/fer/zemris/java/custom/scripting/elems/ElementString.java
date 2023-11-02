package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.lexer.LexerToken;
import hr.fer.zemris.java.custom.scripting.nodes.Node;

/**
 * A class which represents a string which is stored inside a {@link Node} class
 * @author Vito Sabalic
 *
 */
public class ElementString extends Element{

	private final LexerToken token = LexerToken.STRING;
	/**
	 * The value of the string
	 */
	private String value;
	
	/**
	 * A simple constructor which assigns the value of the string to the current value
	 * @param value the value to be assigned
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
//		int index;
//		if(value.contains(String.valueOf('\"'))) {
//			index = value.indexOf('\"');
//			String s1 = value.substring(0, index) + '\\' + value.substring(index, value.length());
//			value = s1;
//			
//			index = value.indexOf('\"', index + 2);
//			value = value.substring(0, index) + '\\' + value.substring(index, value.length());
//		}
//		return "\"" + this.value + "\"";
		
		return this.value;
	}
	
	/**
	 * Returns the value of the string
	 * @return returns the value of the string
	 */
	public String getValue() {
		return value;
	}
	
	@Override
	public LexerToken getToken() {
		return token;
	}
	
	
	
}
