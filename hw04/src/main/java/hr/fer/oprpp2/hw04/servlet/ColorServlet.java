package hr.fer.oprpp2.hw04.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * A servlet that initializes or changes the stored color in the session cookie
 * @author Vito Sabalic
 *
 */
public class ColorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Object LOCK = new Object();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();

		String color = null;

		synchronized (LOCK) {
			if (session.getAttribute("pickedBgCol") == null) {
				session.setAttribute("pickedBgCol", "FFFFFF");
			} else if (req.getParameter("pickedBgCol") != null) {
				color = req.getParameter("pickedBgCol");
				String newColor = "FFFFFF";

				switch (color) {
				case "white":
					break;
				case "red":
					newColor = "FF0000";
					break;
				case "green":
					newColor = "00FF00";
					break;
				case "cyan":
					newColor = "00FFFF";
					break;
				}
				
				session.setAttribute("pickedBgCol", newColor);
			}
		}

		req.getRequestDispatcher("/WEB-INF/pages/colors.jsp").forward(req, resp);

	}

}
