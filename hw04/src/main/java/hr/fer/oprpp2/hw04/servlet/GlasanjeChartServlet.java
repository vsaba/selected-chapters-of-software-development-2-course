package hr.fer.oprpp2.hw04.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.oprpp2.hw04.band.Band;

/**
 * Creates a chart as a PNG based on the stored band names and their votes
 * @author Vito Sabalic
 *
 */
public class GlasanjeChartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("image/png");

		@SuppressWarnings("unchecked")
		List<Band> bands = (List<Band>) req.getSession().getAttribute("bands");

		JFreeChart chart = getPieChart(bands);

		int width = 700;
		int height = 500;

		ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, width, height);

	}

	/**
	 * Creates a pie chart based on the number of votes of each band in the provided list
	 * @param bands The provided list
	 * @return Returns a newly created chart
	 */
	private JFreeChart getPieChart(List<Band> bands) {
		DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

		for (Band band : bands) {
			if (band.getVotes() > 0) {
				dataset.setValue(band.getName(), band.getVotes());
			}
		}

		return ChartFactory.createPieChart("Operating Systems", dataset, true, true, false);
	}

}
