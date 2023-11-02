package hr.fer.oprpp2.hw04.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.oprpp2.hw04.band.Band;

/**
 * A servlet that creates and populates a new Excel file with the stored band names and the number of their votes
 * @author Vito Sabalic
 *
 */
public class GlasanjeExcelServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"rezultati.xls\"");
		
		@SuppressWarnings("unchecked")
		List<Band> bands = (List<Band>) req.getSession().getAttribute("bands");

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Band names and votes");

		int rowCounter = 0;
		for(Band band : bands) {
			
			HSSFRow row = sheet.createRow(rowCounter++);
			row.createCell(0).setCellValue(band.getName());
			row.createCell(1).setCellValue(band.getVotes());
			
		}
		
		workbook.write(resp.getOutputStream());
		workbook.close();

	}
	

}
