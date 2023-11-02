package hr.fer.oprpp2.hw04.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Creates a chart as a PNG
 * @author Vito Sabalic
 *
 */
public class ChartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("image/png");
		JFreeChart chart = getPieChart();

		int width = 700;
		int height = 500;

		ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, width, height);

	}

	/**
	 * Creates a {@link JFreeChart}
	 * @return The new chart
	 */
	private JFreeChart getPieChart() {
		DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
		dataset.setValue("Linux", 29);
		dataset.setValue("Mac", 20);
		dataset.setValue("Windows", 51);

		return ChartFactory.createPieChart("Operating Systems", dataset, true, true, false);
	}

}
