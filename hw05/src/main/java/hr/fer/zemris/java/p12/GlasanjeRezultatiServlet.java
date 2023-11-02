package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * A servlet that reads the stored {@link PollOption} from the database based on
 * the provided parameter, pollID, and calss the pollResult.jsp file
 * 
 * @author Vito Sabalic
 *
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getParameter("pollID") == null) {
			req.setAttribute("code", 400);
			req.setAttribute("message", "Expected exactly one parameter, id");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		int id = Integer.parseInt(req.getParameter("pollID"));

		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptionByPollId(id);

		Collections.sort(pollOptions, new Comparator<PollOption>() {
			@Override
			public int compare(PollOption o1, PollOption o2) {
				return Long.compare(o2.getVotesCount(), o1.getVotesCount());
			}
		});

		req.setAttribute("pollOptions", pollOptions);

		req.getRequestDispatcher("/WEB-INF/pages/pollResult.jsp").forward(req, resp);

	}

}
