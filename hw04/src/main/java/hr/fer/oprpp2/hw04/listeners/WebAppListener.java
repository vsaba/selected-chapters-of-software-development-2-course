package hr.fer.oprpp2.hw04.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * A context listener, used for storing the time of the startup at the startup
 * @author Vito Sabalic
 *
 */
public class WebAppListener implements ServletContextListener{
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("startup", System.currentTimeMillis());
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		sce.getServletContext().removeAttribute("startup");
	}

}
