package hr.fer.oprpp2.hw04.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.oprpp2.hw04.band.Band;
import hr.fer.oprpp2.hw04.util.DatabaseParser;

/**
 * A servlet that reads from the glasanje-rezultati.txt and assigns all the values
 * stored in the aforementioned file to the stored {@link Band} collection
 * @author Vito Sabalic
 *
 */
public class GlasanjeRezultatiServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String path = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		
		Map<Integer, Integer> votingResults = DatabaseParser.parseVotingResults(path);
		
		@SuppressWarnings("unchecked")
		List<Band> bands = (List<Band>) req.getSession().getAttribute("bands");
		
		if(bands == null) {
			req.setAttribute("code", 400);
			req.setAttribute("message", "You must vote for a band before seeing this page!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		for(Band band : bands) {
			int bandId = band.getId();
			
			if(votingResults.containsKey(bandId)) {
				band.setVotes(votingResults.get(bandId));
			}
		}
		
		Collections.sort(bands, new Comparator<Band>() {
			@Override
			public int compare(Band o1, Band o2) {
				return Integer.compare(o2.getVotes(), o1.getVotes());
			}
		});
		
		req.setAttribute("bands", bands);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
		
	}
	

}
