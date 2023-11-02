package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * An emureation which represents the possible states in which the {@link Lexer} class can be found
 * @author Vito Sabalic
 *
 */
public enum LexerState {
	
	/**
	 * Represents the state in which a tag is processed
	 */
	TAGSTATE,
	
	/**
	 * Represents the normal state of processing
	 */
	NORMALSTATE

}
