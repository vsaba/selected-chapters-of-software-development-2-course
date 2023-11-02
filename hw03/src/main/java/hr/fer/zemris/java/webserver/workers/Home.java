package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * An implementation of an {@link IWebWorker}. Handles the home page
 * 
 * @author Vito Sabalic
 *
 */
public class Home implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {

		String background = context.getPersistentParameter("bgcolor");

		if (background == null) {
			background = "7F7F7F";
		}

		context.setTemporaryParameter("background", background);

		context.getDispatcher().dispatchRequest("/private/pages/home.smscr");

	}

}
