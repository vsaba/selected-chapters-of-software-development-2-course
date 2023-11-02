package hr.fer.zemris.java.webserver;

/**
 * An interface which implements a server dispatcher. Handles and differentiates
 * between user originated calls and server generated calls of a dispatch
 * 
 * @author Vito Sabalic
 *
 */
public interface IDispatcher {

	/**
	 * Handles the calls of a dispatch. In other words, differentiates between user
	 * originated calls of a dispatch request and server generated calls of a
	 * dispatch request
	 * 
	 * @param urlPath A string of the requested url
	 * @throws Exception
	 */
	void dispatchRequest(String urlPath) throws Exception;

}
