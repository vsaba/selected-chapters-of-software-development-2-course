package hr.fer.oprpp2.hw04.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that accepts 2 parameters, and calculates the trigonometric 
 * values of sin and cos based on those parameters
 * @author Vito Sabalic
 *
 */
public class TrigonometricServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int a = 0;
		int b = 0;

		if (req.getParameter("a") == null) {
			a = 0;
		} else {
			a = Integer.parseInt(req.getParameter("a"));
		}

		if (req.getParameter("b") == null) {
			b = 360;
		} else {
			b = Integer.parseInt(req.getParameter("b"));
		}

		if (a > b) {
			int pom = a;
			a = b;
			b = pom;
		}

		if (b > a + 720) {
			b = a + 720;
		}
		
		double[] sinValues = new double[b - a];
		double[] cosValues = new double[b - a];
		
		
		int pomA = a;
		for(int i = 0; i < (b - a); i++) {
			sinValues[i] = Math.sin(Math.toRadians(pomA));
			cosValues[i] = Math.cos(Math.toRadians(pomA++));
		}
		
		
		
		req.setAttribute("a", a);
		req.setAttribute("b", b);
		req.setAttribute("sinValues", sinValues);
		req.setAttribute("cosValues", cosValues);
		
		
		req.getRequestDispatcher("WEB-INF/pages/trigonometric.jsp").forward(req, resp);

	}

}
