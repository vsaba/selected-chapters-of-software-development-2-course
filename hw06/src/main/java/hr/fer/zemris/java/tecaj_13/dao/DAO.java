package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.forms.BlogForm;
import hr.fer.zemris.java.tecaj_13.forms.RegisterForm;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * An interface which models the communication with the database
 * 
 * @author Vito Sabalic
 *
 */
public interface DAO {

	/**
	 * Retireves the entry mapped to the provided id
	 * 
	 * @param id The provided id
	 * @return The mapped entry if it exists, null otherwise
	 * @throws DAOException
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Retrieves the blog user mapped to the provided nickname
	 * 
	 * @param nick The provided nickname
	 * @return The mapped user if it exists, null otherwise
	 * @throws DAOException
	 */
	BlogUser getBlogUserByNick(String nick) throws DAOException;

	/**
	 * Creates a new user based on the provided form
	 * 
	 * @param form The provided form
	 * @return The created user
	 * @throws DAOException
	 */
	BlogUser createNewUser(RegisterForm form) throws DAOException;

	/**
	 * Retrieves all the users in the database
	 * 
	 * @return All the users in the database
	 * @throws DAOException
	 */
	List<BlogUser> getAllBlogUsers() throws DAOException;

	/**
	 * Creates a new blog entry based on the provided user and form
	 * 
	 * @param user The provided user
	 * @param form The provided form
	 * @throws DAOException
	 */
	void createNewBlogEntry(BlogUser user, BlogForm form) throws DAOException;

	/**
	 * Creates a new comment based on the provided email, text, and entry
	 * 
	 * @param email The provided email
	 * @param text  The provided text
	 * @param entry The provided entry
	 * @throws DAOException
	 */
	void createNewComment(String email, String text, BlogEntry entry) throws DAOException;

}