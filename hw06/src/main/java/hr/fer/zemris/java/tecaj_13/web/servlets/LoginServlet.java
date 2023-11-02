package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.forms.LoginForm;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet which handles the login of a user
 * 
 * @author Vito Sabalic
 *
 */
@WebServlet("/servleti/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nick = request.getParameter("nick");
		BlogUser user = DAOProvider.getDAO().getBlogUserByNick(nick);

		LoginForm form = new LoginForm();

		form.fillFromRequest(request);
		form.validate();

		if (form.isError()) {
			request.setAttribute("form", form);
			List<BlogUser> users = DAOProvider.getDAO().getAllBlogUsers();
			request.setAttribute("allUsers", users);
			request.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(request, response);
			return;
		}

		request.getSession().setAttribute("current.user.id", user.getId());
		request.getSession().setAttribute("current.user.fn", user.getFirstName());
		request.getSession().setAttribute("current.user.ln", user.getLastName());
		request.getSession().setAttribute("current.user.nick", user.getNick());

		response.sendRedirect(request.getContextPath());
		return;
	}

}
