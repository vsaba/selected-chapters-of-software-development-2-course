package hr.fer.zemris.java.p12;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * A servlet that updates the number of votes of the option which contains the
 * provided id, and redirects to the {@link GlasanjeRezultatiServlet} servlet
 * 
 * @author Vito Sabalic
 *
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getParameter("id") == null || req.getParameter("pollID") == null) {
			req.setAttribute("code", 400);
			req.setAttribute("message", "Expected two parameters, id and pollID");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		int id = Integer.parseInt(req.getParameter("id"));

		DAOProvider.getDao().incrementVotesCount(id);
		
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + req.getParameter("pollID"));
	}

}
