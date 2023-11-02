package hr.fer.oprpp2.hw04.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * A servlet that creates and populates a Excel file based on the given parameters
 * @author Vito Sabalic
 *
 */
public class ExcelServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int a = 0;
		int b = 0;
		int n = 0;

		if (req.getParameter("a") == null) {
			sendErrorMessage(req, resp, 400, "Expected parameter \"a\"");
			return;
		} else if (req.getParameter("b") == null) {
			sendErrorMessage(req, resp, 400, "Expected parameter \"b\"");
			return;
		} else if (req.getParameter("n") == null) {
			sendErrorMessage(req, resp, 400, "Expected parameter \"n\"");
			return;
		}

		a = Integer.parseInt(req.getParameter("a"));
		b = Integer.parseInt(req.getParameter("b"));
		n = Integer.parseInt(req.getParameter("n"));

		if (a < -100 || a > 100) {
			sendErrorMessage(req, resp, 400, "The a parameter should be between [-100,100]");
			return;
		} else if (b < -100 || b > 100) {
			sendErrorMessage(req, resp, 400, "The b parameter should be between [-100,100]");
			return;
		} else if (n < 1 || n > 5) {
			sendErrorMessage(req, resp, 400, "The n parameter should be between [1,5]");
			return;
		}

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");

		HSSFWorkbook workbook = new HSSFWorkbook();

		for (int i = 1; i <= n; i++) {

			HSSFSheet sheet = workbook.createSheet("Power of " + i);
			int rowNumber = 0;
			for (int y = a; y <= b; y++) {

				HSSFRow row = sheet.createRow(rowNumber++);
				row.createCell(0).setCellValue(y);
				row.createCell(1).setCellValue(Math.pow(y, i));

			}
		}

		workbook.write(resp.getOutputStream());
		workbook.close();

	}

	/**
	 * Sends an error message and forwards it to the error.jsp servlet
	 * @param req The user request
	 * @param resp The user response
	 * @param code The message code
	 * @param message The message message
	 * @throws ServletException
	 * @throws IOException
	 */
	private void sendErrorMessage(HttpServletRequest req, HttpServletResponse resp, int code, String message) throws ServletException, IOException {

		req.setAttribute("code", code);
		req.setAttribute("message", message);

		req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);

		return;
	}

}
