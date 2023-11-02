package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Creates a chart as a PNG based on the stored option names and their votes
 * 
 * @author Vito Sabalic
 *
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeChartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getParameter("pollID") == null) {
			req.setAttribute("code", 400);
			req.setAttribute("message", "Expected exactly one parameter, id");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		resp.setContentType("image/png");

		JFreeChart chart = getPieChart(
				DAOProvider.getDao().getPollOptionByPollId(Integer.parseInt(req.getParameter("pollID"))));

		int width = 700;
		int height = 500;

		ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, width, height);

	}

	/**
	 * Creates a pie chart based on the number of votes of each option in the
	 * provided list
	 * 
	 * @param options The provided list
	 * @return Returns a newly created chart
	 */
	private JFreeChart getPieChart(List<PollOption> options) {
		DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

		for (PollOption option : options) {
			if (option.getVotesCount() > 0) {
				dataset.setValue(option.getName(), option.getVotesCount());
			}
		}

		return ChartFactory.createPieChart("Voting results", dataset, true, true, false);
	}

}
