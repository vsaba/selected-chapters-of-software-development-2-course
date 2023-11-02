package hr.fer.zemris.java.custom.scripting.parser;

public class SmartScriptParserException extends RuntimeException{

	
	/**
	 * Default searialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * A basic constructor, it sends the construction process to its parent class {@link RuntimeException}
	 */
	public SmartScriptParserException() {
		super();
	}
	
	
	/**
	 * Sends the construction process to its parent class {@link RuntimeException} with the provided message
	 * @param message the provided exception message
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
}
