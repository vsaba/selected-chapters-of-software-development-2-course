package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.dao.sql.SQLConnectionProvider;

/**
 * An implementation of {@link ServletContextListener}. Initializes the
 * connection poll to the database at server startup. Also, if the necessary
 * tables, Poll and PollOptions are not present in the database during startup,
 * creates them and populates them with the values stored in the
 * default_population.txt file the WEB-INF folder. During server shutdown,
 * destroys the connection poll to the database
 * 
 * @author Vito Sabalic
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		String connectionURL = new String();

		try (InputStream is = Files
				.newInputStream(Paths.get(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties")))) {

			Properties props = new Properties();
			props.load(is);
			String host = props.getProperty("host");
			String port = props.getProperty("port");
			String dbName = props.getProperty("name");
			String user = props.getProperty("user");
			String password = props.getProperty("password");

			connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName + ";user=" + user + ";password="
					+ password;

		} catch (IOException e) {
			e.printStackTrace();
		}

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.client.ClientAutoloadedDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("An error has occurred during initaliziation.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		try (Connection con = cpds.getConnection()) {

			SQLConnectionProvider.setConnection(con);
			DAO dao = DAOProvider.getDao();

			if (!dao.checkIfTableExists("POLLS")) {
				String table = """
						CREATE TABLE Polls
						(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
						title VARCHAR(150) NOT NULL,
						message CLOB(2048) NOT NULL)
						""";

				String population = """
						INSERT INTO Polls (title, message) VALUES
						('Glasanje za omiljeni bend:', 'Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!'),
						('Glasanje za najdraži gol:', 'Od sljedećih golova, koji Vam je gol najdraži? Kliknite na link kako biste glasali!')
						""";
				dao.createAndPopulateTable(table, population);
			}

			if (!dao.checkIfTableExists("POLLOPTIONS")) {

				String table = """
						CREATE TABLE PollOptions
						(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
						optionTitle VARCHAR(100) NOT NULL,
						optionLink VARCHAR(150) NOT NULL,
						pollID BIGINT,
						votesCount BIGINT,
						FOREIGN KEY (pollID) REFERENCES Polls(id))
						""";

				String population = """
						INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES
						(
						""";
				List<String> populations = Files.readAllLines(
						Paths.get(sce.getServletContext().getRealPath("/WEB-INF/default_population.txt")));

				int pollId = dao.getNextLargestPollId(-1);

				for (int i = 0; i < populations.size(); i++) {
					String line = populations.get(i);

					if (line.isBlank()) {
						pollId = dao.getNextLargestPollId(pollId);
						continue;
					}

					String[] splitLine = populations.get(i).split("\t");

					if (i == populations.size() - 1) {
						population += "'" + splitLine[0] + "', '" + splitLine[1] + "', " + pollId + ", " + 0 + ")";
						break;
					}

					population += "'" + splitLine[0] + "', '" + splitLine[1] + "', " + pollId + ", " + 0 + "),\n(";

				}
				dao.createAndPopulateTable(table, population);
			}

		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			SQLConnectionProvider.setConnection(null);
		}

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
