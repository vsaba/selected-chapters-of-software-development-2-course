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
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * A servlet that creates and populates a new Excel file with the stored option
 * names and the number of their votes
 * 
 * @author Vito Sabalic
 *
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeExcelServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"rezultati.xls\"");

		if (req.getParameter("pollID") == null) {
			req.setAttribute("code", 400);
			req.setAttribute("message", "Expected exactly one parameter, id");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		List<PollOption> options = DAOProvider.getDao()
				.getPollOptionByPollId(Integer.parseInt(req.getParameter("pollID")));
		
		Collections.sort(options, new Comparator<PollOption>() {
			@Override
			public int compare(PollOption o1, PollOption o2) {
				return Long.compare(o2.getVotesCount(), o1.getVotesCount());
			}
		});

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Names and votes");

		int rowCounter = 0;
		for (PollOption option : options) {

			HSSFRow row = sheet.createRow(rowCounter++);
			row.createCell(0).setCellValue(option.getName());
			row.createCell(1).setCellValue(option.getVotesCount());

		}

		workbook.write(resp.getOutputStream());
		workbook.close();

	}

}
