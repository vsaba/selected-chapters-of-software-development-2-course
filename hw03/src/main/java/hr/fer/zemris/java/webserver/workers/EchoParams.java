package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A worker which generates a table from all the names provided as an argument
 * @author Vito Sabalic
 *
 */
public class EchoParams implements IWebWorker{
	
	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		context.setMimeType("text/html");
		context.setStatusCode(200);
		
		context.write("<html><head><title>Imena</title></head><body>");
		context.write("<h1>Imena</h1>");
		context.write("<p>U nastavku su prikazana sva imena predana kao argumenti</p>");
		context.write("<table>");
		context.write("<thead><tr><td>Imena</td></tr></thead>");
		context.write("<tbody>");
		
		for(String keys: context.getParameterNames()) {
			context.write("<tr><td>" + context.getParameter(keys) + "</td></tr>");
		}
		
		context.write("</tbody>");
		context.write("</table>");
		context.write("</body></html>");
		
		
	}

}
