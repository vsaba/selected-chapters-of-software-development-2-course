package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.forms.BlogForm;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet which handles viewing, editing, and adding blogs
 * 
 * @author Vito Sabalic
 *
 */
@WebServlet("/servleti/author/*")
public class EntriesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo().replaceFirst("/", "").trim();
		String[] URLparameters = path.split("/");
		String nick = URLparameters[0].trim();

		BlogForm form = new BlogForm();
		form.fillFromHttpRequest(req);
		form.validate();

		req.setAttribute("path", req.getPathInfo());

		if (form.errorsExist()) {
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/entryForm.jsp").forward(req, resp);
			return;
		}

		if (URLparameters[1].equals("new")) {
			DAO dao = DAOProvider.getDAO();
			dao.createNewBlogEntry(dao.getBlogUserByNick(nick), form);
		} else {
			Long EID = Long.parseLong(req.getParameter("EID"));
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(EID);
			entry.setTitle(form.getTitle());
			entry.setText(form.getText());
			entry.setLastModifiedAt(new Date());
		}

		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nick);
		return;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = request.getPathInfo().replaceFirst("/", "").trim();

		String[] parameters = path.split("/");

		switch (parameters.length) {

		case 1:
			listEntries(parameters[0], request, response);
			break;

		case 2:
			if (parameters[1].equals("new") || parameters[1].equals("edit")) {

				if (request.getSession().getAttribute("current.user.nick") == null) {
					request.setAttribute("error", "Must be logged in to view this page");
					request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
					return;
				}

				if (!request.getSession().getAttribute("current.user.nick").equals(parameters[0].trim())) {
					request.setAttribute("error", "Must be original poster to view this page");
					request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
					return;
				}

				BlogForm form = new BlogForm();
				String URL = request.getPathInfo();
				if (parameters[1].equals("edit")) {
					Long id = Long.parseLong(request.getParameter("EID"));
					form.fillFromBlogEntry(DAOProvider.getDAO().getBlogEntry(id));
					URL += "?EID=" + request.getParameter("EID");
				}
				request.setAttribute("path", URL);
				request.setAttribute("form", form);
				request.getRequestDispatcher("/WEB-INF/pages/entryForm.jsp").forward(request, response);
				return;

			} else {
				selectedBlog(parameters, request, response);
			}
			break;

		}

		return;
	}

	/**
	 * Lists all blog entries from the user specified in the parameter
	 * 
	 * @param parameter The user parameter
	 * @param req       The http request
	 * @param resp      The http response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void listEntries(String parameter, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		parameter = parameter.trim();
		BlogUser user = DAOProvider.getDAO().getBlogUserByNick(parameter);

		req.setAttribute("entries", user.getEntries());
		req.setAttribute("loggedIn", false);

		if (req.getSession().getAttribute("current.user.nick") != null) {
			if (req.getSession().getAttribute("current.user.nick").equals(parameter)) {
				req.setAttribute("loggedIn", true);
			}
		}

		req.getRequestDispatcher("/WEB-INF/pages/entries.jsp").forward(req, resp);

	}

	/**
	 * Extracts the specific blog specified in the parameters, and prepares it for
	 * viewing
	 * 
	 * @param URLparameters The paramter in which the blog is specified
	 * @param req           The http request
	 * @param resp          The http response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void selectedBlog(String[] URLparameters, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String EID = URLparameters[1].trim();
		Long id = null;
		try {
			id = Long.valueOf(EID);
		} catch (Exception ignorable) {
		}
		if (id != null) {
			BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(id);
			if (blogEntry != null) {
				req.setAttribute("blogEntry", blogEntry);
			}
		}

		req.setAttribute("loggedIn", false);
		if (req.getSession().getAttribute("current.user.nick") != null) {
			if (URLparameters[0].trim().equals(req.getSession().getAttribute("current.user.nick"))) {
				req.setAttribute("loggedIn", true);
			}
		}

		req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);

	}

}
