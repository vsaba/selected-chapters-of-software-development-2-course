package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * A custom exception which is used for the implemented {@link Lexer} class.
 * The exception is thrown whenever an exception in the lexing process happens.
 * @author Vito Sabalic
 *
 */
public class LexerException extends RuntimeException{
	
	/**
	 * Default searialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * A basic constructor, it sends the construction process to its parent class {@link RuntimeException}
	 */
	public LexerException() {
		super();
	}
	
	
	/**
	 * Sends the construction process to its parent class {@link RuntimeException} with the provided message
	 * @param message the provided exception message
	 */
	public LexerException(String message) {
		super(message);
	}

}
