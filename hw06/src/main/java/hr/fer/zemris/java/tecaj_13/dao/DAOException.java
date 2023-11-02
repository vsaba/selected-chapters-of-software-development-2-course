package hr.fer.zemris.java.tecaj_13.dao;

/**
 * A custom exception, thrown when an error occurs in the communication with the
 * database
 * 
 * @author Vito Sabalic
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * A constructor which receives a message and the cause of the exception
	 * 
	 * @param message The message
	 * @param cause   The cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * A constructor which receives only the message of the exception
	 * 
	 * @param message The message
	 */
	public DAOException(String message) {
		super(message);
	}
}