package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet which handles the process of adding a comment to a specific blog
 * 
 * @author Vito Sabalic
 *
 */
@WebServlet("/servleti/comment/*")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Long EID = Long.parseLong(req.getPathInfo().replaceFirst("/", "").trim());
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(EID);
		String email = "anonymous";

		if (req.getSession().getAttribute("current.user.id") != null) {
			String nick = (String) req.getSession().getAttribute("current.user.nick");
			BlogUser user = DAOProvider.getDAO().getBlogUserByNick(nick);
			email = user.getEmail();
		}

		DAOProvider.getDAO().createNewComment(email, req.getParameter("comment"), entry);

		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + entry.getCreator().getNick() + "/" + EID);
		return;
	}

}
