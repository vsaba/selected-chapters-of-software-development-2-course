package hr.fer.zemris.java.tecaj_13.forms;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.crypto.Crypto;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Class which represents the login form of the application
 * 
 * @author Vito Sabalic
 *
 */
public class LoginForm {

	private String nick;
	private String passwordHash;
	private boolean error = false;

	/**
	 * A simple constructor
	 */
	public LoginForm() {
	}

	public void fillFromRequest(HttpServletRequest req) {
		this.nick = req.getParameter("nick");
		this.passwordHash = req.getParameter("password");
	}

	/**
	 * Validates the form
	 */
	public void validate() {

		error = false;

		if (nick.isEmpty() || passwordHash.isEmpty()) {
			error = true;
			return;
		}

		String hashedPass = Crypto.hashPassword(passwordHash);
		BlogUser user = DAOProvider.getDAO().getBlogUserByNick(nick);

		if (user == null || !(hashedPass.equals(user.getPasswordHash()))) {
			error = true;
		}

		return;
	}

	/**
	 * A getter for the nickname provided through the form
	 * 
	 * @return The provided nickname
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Sets the current nickname to the provided nickname
	 * 
	 * @param nick The provided nickname
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * A getter for the hash created from the password provided through the form
	 * 
	 * @return The hash of the provided password
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Sets the current password hash to the provided hash
	 * 
	 * @param passwordHash The provided hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Determines if an error has occurred in the form
	 * 
	 * @return True if an error has indeed occurred, false otherwise
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * Sets the current error flag to the provided value
	 * 
	 * @param error The provided value
	 */
	public void setError(boolean error) {
		this.error = error;
	}

}
