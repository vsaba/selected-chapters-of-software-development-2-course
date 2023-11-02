package hr.fer.oprpp2.hw04.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.oprpp2.hw04.band.Band;
import hr.fer.oprpp2.hw04.util.DatabaseParser;

/**
 * A servlet that reads all the bands stored in the glasanje-definicija.txt,
 * stores them in the session cookie and renders a jsp-file
 * @author Vito Sabalic
 *
 */
public class GlasanjeServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String path = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
				
		List<Band> bands = DatabaseParser.parseVotingDefinition(path);
		
		Collections.sort(bands, new Comparator<Band>() {
			public int compare(Band o1, Band o2) {
				return Integer.compare(o1.getId(), o2.getId());
			};
		});
		
		req.setAttribute("bands", bands);
		req.getSession().setAttribute("bands", bands);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
