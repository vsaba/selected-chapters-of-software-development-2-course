package hr.fer.zemris.java.webserver;

/**
 * An interface which represents an implementation of a web worker.
 * A web worker performs all necessary computations before delegating the rendering part to a specific
 * Smart Script file
 * @author Vito Sabalic
 *
 */
public interface IWebWorker {
	
	/**
	 * Processes the user generated request
	 * @param context The current {@link RequestContext}
	 * @throws Exception
	 */
	public void processRequest(RequestContext context) throws Exception;

}
