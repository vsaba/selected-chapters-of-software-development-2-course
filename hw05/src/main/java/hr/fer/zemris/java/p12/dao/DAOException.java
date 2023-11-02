package hr.fer.zemris.java.p12.dao;

/**
 * A custom exception, thrown when an error occurs in the communication with the
 * database
 * 
 * @author Vito Sabalic
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DAOException() {
	}

	/**
	 * The most precise constructor
	 * 
	 * @param message            The provided message
	 * @param cause              The provided cause
	 * @param enableSuppression  The provided flag
	 * @param writableStackTrace The provided stack trace flag
	 */
	public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

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

	/**
	 * A constructor which receives only the cause of the exception
	 * 
	 * @param message The cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}