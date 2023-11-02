package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * An emureation which represents the possible values of tokens which the {@link Lexer} produces
 * @author Vito Sabalic
 *
 */
public enum LexerToken {

	
	/**
	 * Represents a variable
	 */
	VARIABLE,
	
	
	/**
	 * Represents a int value
	 */
	INT,
	
	/**
	 * Represents a double variable
	 */
	DOUBLE,
	
	/**
	 * Represents a string
	 */
	STRING,
	
	/**
	 * Represents a function
	 */
	FUNCTION,
	
	/**
	 * Represents an operator
	 */
	OPERATOR,
	
	/**
	 * Represents a symbol
	 */
	SYMBOL,
	
	/**
	 * Represents a For-tag
	 */
	FORTAG,
	
	/**
	 * Represents an empty-tag
	 */
	EMPTYTAG,
	
	/**
	 * Represents an end-tag
	 */
	ENDTAG,
	
	/**
	 * Represents a word
	 */
	WORD,
	
	/**
	 * Represents the end of the data type which the lexer is processing
	 */
	EOF
	
}
