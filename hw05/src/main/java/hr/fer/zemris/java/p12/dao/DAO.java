package hr.fer.zemris.java.p12.dao;

import java.util.List;
import hr.fer.zemris.java.p12.model.PollOption;
import hr.fer.zemris.java.p12.model.Poll;

/**
 * An interface which models the communication with the database
 * 
 * @author Vito Sabalic
 *
 */
public interface DAO {

	/**
	 * Gets all the poll values stored in the database
	 * 
	 * @return Returns all poll values
	 * @throws DAOException
	 */
	List<Poll> getAllPolls() throws DAOException;

	/**
	 * Retrieves all poll options from the database whose id matches the provided id
	 * 
	 * @param id The provided id
	 * @return Returns the poll whose id matches the provided id
	 * @throws DAOException
	 */
	List<PollOption> getPollOptionByPollId(int id) throws DAOException;

	/**
	 * Increments the number of votes of the stored value with the provided id by 1
	 * 
	 * @param optionId The provided id
	 * @throws DAOException
	 */
	void incrementVotesCount(int optionId) throws DAOException;

	/**
	 * Retrieves a poll whose id matches the provided id
	 * 
	 * @param pollId The provided id
	 * @return Returns the retrieved poll
	 * @throws DAOException
	 */
	Poll getPollByPollId(int pollId) throws DAOException;

	/**
	 * Finds the poll with the next largest id, compared with the provided last id
	 * 
	 * @param lastID The provided last id
	 * @return Returns the next largest poll id
	 * @throws DAOException
	 */
	int getNextLargestPollId(int lastID) throws DAOException;

	/**
	 * Wraps and executes the provided SQL commands, tableCreationSQL and
	 * tablePopulationSQL
	 * 
	 * @param tableCreationSQL   The provided table creation SQL command
	 * @param tablePopulationSQL The provided table population SQL command
	 * @throws DAOException
	 */
	void createAndPopulateTable(String tableCreationSQL, String tablePopulationSQL) throws DAOException;

	/**
	 * Checks whether the table with the provided name exists
	 * 
	 * @param tableName The provided name
	 * @return Returns true if the table exists, false otherwise
	 * @throws DAOException
	 */
	boolean checkIfTableExists(String tableName) throws DAOException;

}