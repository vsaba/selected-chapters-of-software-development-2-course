package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * An implementation of the {@link IWebWorker} interface. Calculates the two provided parameters.
 * @author Vito Sabalic
 *
 */
public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String key1 = context.getParameter("a");
		String key2 = context.getParameter("b");
		int a = 1;
		int b = 2;
		try {
			if (key1 != null) {
				a = Integer.parseInt(key1);
			}
		} catch (NumberFormatException e) {
		}

		try {
			if (key2 != null) {
				b = Integer.parseInt(key2);
			}
		} catch (NumberFormatException e) {
		}
		
		String imagePath = (a + b) % 2 == 0 ? "./images/image1.jpg" : "./images/image2.png";
		
		context.setTemporaryParameter("varA", String.valueOf(a));
		context.setTemporaryParameter("varB", String.valueOf(b));
		context.setTemporaryParameter("zbroj", String.valueOf(a + b));
		context.setTemporaryParameter("imgName", imagePath);
		
		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
	}

}
