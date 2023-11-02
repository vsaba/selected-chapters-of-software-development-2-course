package hr.fer.zemris.java.p12.dao.sql;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * An implementation of the {@link DAO} interface
 * 
 * @author Vito Sabalic
 * 
 *
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getAllPolls() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();

		try (PreparedStatement ps = con.prepareStatement("SELECT id, title, message FROM Polls")) {

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					int id = rs.getInt(1);
					String title = rs.getString(2);
					Clob cl = rs.getClob(3);
					String message = new String(cl.getAsciiStream().readAllBytes(), StandardCharsets.UTF_8);

					polls.add(new Poll(id, title, message));
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}

		return polls;
	}

	@Override
	public List<PollOption> getPollOptionByPollId(int id) throws DAOException {
		List<PollOption> pollOptions = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();

		try (PreparedStatement ps = con.prepareStatement(
				"SELECT id, optionTitle, optionLink, pollId, votesCount FROM PollOptions WHERE pollId=?")) {

			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					pollOptions.add(
							new PollOption(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
				}

			}

		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}

		return pollOptions;
	}

	@Override
	public void incrementVotesCount(int optionId) throws DAOException {

		Connection con = SQLConnectionProvider.getConnection();

		try (PreparedStatement ps = con.prepareStatement("SELECT votesCount FROM PollOptions WHERE id=?",
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {

			ps.setInt(1, optionId);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					int votes = rs.getInt(1);
					rs.updateInt(1, ++votes);
					rs.updateRow();
				}

			}

		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}

	}

	@Override
	public Poll getPollByPollId(int pollId) throws DAOException {
		Poll poll = null;
		Connection con = SQLConnectionProvider.getConnection();

		try (PreparedStatement ps = con.prepareStatement("SELECT id, title, message FROM Polls WHERE id=?")) {

			ps.setInt(1, pollId);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					int id = rs.getInt(1);
					String title = rs.getString(2);
					Clob cl = rs.getClob(3);
					String message = new String();
					Reader r = cl.getCharacterStream();
					int c;

					while ((c = r.read()) != -1) {
						message += String.valueOf((char) c);
					}
					poll = new Poll(id, title, message);

				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}

		return poll;
	}

	@Override
	public int getNextLargestPollId(int lastID) throws DAOException {

		Connection con = SQLConnectionProvider.getConnection();
		int id = -1;

		try (PreparedStatement ps = con.prepareStatement("SELECT id FROM Polls ORDER BY id")) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					id = rs.getInt(1);
					if (lastID < 0 || lastID < id) {
						break;
					}
				}
			}

		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}

		return id;
	}

	@Override
	public void createAndPopulateTable(String tableCreationSQL, String tablePopulationSQL) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		try (PreparedStatement ps = con.prepareStatement(tableCreationSQL)) {
			ps.executeUpdate();
		} catch (SQLException e) {
			if(!e.getSQLState().equals("X0Y32")) {
				throw new DAOException(e.getMessage());
			}
		}

		try (PreparedStatement ps = con.prepareStatement(tablePopulationSQL)) {
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}

		return;
	}

	@Override
	public boolean checkIfTableExists(String tableName) {

		tableName = tableName.toUpperCase().trim();

		Connection con = SQLConnectionProvider.getConnection();
		DatabaseMetaData metaData;
		boolean tableExists = true;
		try {
			metaData = con.getMetaData();
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
		try (ResultSet rs = metaData.getTables(null, null, tableName, null)) {
			tableExists = rs.next();
			
			if(!tableExists) {
				return false;
			}
			
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
		
		boolean tablePopulated = true;
		try(PreparedStatement ps = con.prepareStatement("SELECT * FROM " + tableName)){
			try(ResultSet rs = ps.executeQuery()){
				tablePopulated = rs.next();
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
		
		return tableExists && tablePopulated;
	}

}