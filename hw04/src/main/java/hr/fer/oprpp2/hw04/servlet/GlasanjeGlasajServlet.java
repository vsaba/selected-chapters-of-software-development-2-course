package hr.fer.oprpp2.hw04.servlet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.oprpp2.hw04.util.DatabaseParser;

/**
 * A servlet that reads and updates the glasanje-zapisi.txt file, and redirects
 * to the {@link GlasanjeRezultatiServlet} servlet
 * 
 * @author Vito Sabalic
 *
 */
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");

		if (req.getParameter("id") == null) {
			req.setAttribute("code", 400);
			req.setAttribute("message", "Expected exatcly one parameter, id");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		int id = Integer.parseInt(req.getParameter("id"));
		Map<Integer, Integer> votingResults = DatabaseParser.parseVotingResults(path);

		if (!votingResults.containsKey(id)) {
			votingResults.put(id, 1);
		} else {
			int value = votingResults.get(id);
			value++;
			votingResults.put(id, value);
		}

		String output = new String();

		int counter = 0;
		for (Map.Entry<Integer, Integer> entry : votingResults.entrySet()) {
			if (counter == votingResults.size() - 1) {
				output += entry.getKey() + "\t" + entry.getValue();
				break;
			}

			output += entry.getKey() + "\t" + entry.getValue() + "\r\n";

			counter++;
		}

		req.setAttribute("beep", "hello");

		Files.writeString(Paths.get(path), output);

		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

}
