package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * A {@link HttpServlet} that reads all the {@link PollOption} values whose pollID is equal
 * to the provided parameter, pollID. Calls the pollVoting.jsp file
 * 
 * @author Vito Sabalic
 *
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

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

		DAO dao = DAOProvider.getDao();

		Poll requestedPoll = dao.getPollByPollId(id);

		req.setAttribute("poll", requestedPoll);

		List<PollOption> pollOptions = dao.getPollOptionByPollId(id);

		req.setAttribute("pollOptions", pollOptions);

		req.getRequestDispatcher("/WEB-INF/pages/pollVoting.jsp").forward(req, resp);
	}
}
