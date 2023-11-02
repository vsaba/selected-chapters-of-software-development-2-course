package hr.fer.zemris.java.custom.scripting.lexer;


/**
 * Represents the implementation of a simple token used in the lexing process
 * @author Vito Sabalic
 *
 */
public class Token {
	
	
	/**
	 * Represents the {@link LexerToken} value
	 */
	LexerToken type;
	
	
	/**
	 * Represents the object value
	 */
	Object value;
	
	/**
	 * A simple constructor which assigns the {@link TokenType} and the value of the object to the token
	 * @param type
	 * @param value
	 */
	public Token(LexerToken type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Returns the value of the object of the token
	 * @return the value of the object of the token
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Returns the {@link TokenType} value of the token
	 * @return the {@link TokenType} value of the token
	 */
	public LexerToken getType() {
		return this.type;
	}


}
