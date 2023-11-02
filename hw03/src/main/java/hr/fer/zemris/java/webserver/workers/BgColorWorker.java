package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A worker which sets the background color parameter to the provided value or to the default value
 * @author Vito Sabalic
 *
 */
public class BgColorWorker implements IWebWorker{
	
	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgColor = context.getParameter("bgcolor");
		String message = "The color has not been set";
		if(checkValid(bgColor)) {
			context.setPersistentParameter("bgcolor", bgColor);
			message = "The color has been set";
		}
		
		context.setTemporaryParameter("title", message);
		
		context.getDispatcher().dispatchRequest("/private/pages/color.smscr");
		
	}
	
	/**
	 * Checks if the provided argument is valid (not null and contains exactly 6 HEX chars)
	 * @param bgColor
	 * @return
	 */
	private boolean checkValid(String bgColor) {
		String hex = "0123456789ABCDEF";
		
		if(bgColor == null) {
			return false;
		}
		
		char[] data = bgColor.toCharArray();
		
		for(char c: data) {
			if(!hex.contains(String.valueOf(c))) {
				return false;
			}
		}
		
		return true;
	}

}
