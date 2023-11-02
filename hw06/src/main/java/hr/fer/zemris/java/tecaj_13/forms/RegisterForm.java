package hr.fer.zemris.java.tecaj_13.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.crypto.Crypto;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

/**
 * Represents the registration form of the application
 * 
 * @author Vito Sabalic
 *
 */
public class RegisterForm {

	private String firstName;
	private String lastName;
	private String email;
	private String nick;
	private String passwordHash;
	private Map<String, String> errors = new HashMap<>();

	/**
	 * A simple constructor
	 */
	public RegisterForm() {
		this.firstName = "";
		this.lastName = "";
		this.email = "";
	}

	/**
	 * Initializes the class to the provided values in the request
	 * 
	 * @param req The provided request
	 */
	public void fillFromRequest(HttpServletRequest req) {

		this.firstName = req.getParameter("firstName");
		this.lastName = req.getParameter("lastName");
		this.email = req.getParameter("email");
		this.nick = req.getParameter("nick");
		this.passwordHash = req.getParameter("password");

		return;
	}

	/**
	 * Validates the form
	 */
	public void validate() {

		errors.clear();

		if (this.firstName.isEmpty()) {
			errors.put("firstName", "Ime je obavezno!");
		}

		if (this.lastName.isEmpty()) {
			errors.put("lastName", "Prezime je obavezno!");
		}

		if (this.email.isEmpty()) {
			errors.put("email", "EMail je obavezan!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if (l < 3 || p == -1 || p == 0 || p == l - 1) {
				errors.put("email", "EMail nije ispravnog formata.");
			}
		}

		if (nick.isEmpty()) {
			errors.put("nick", "Nickname cannot be empty");
		} else if (DAOProvider.getDAO().getBlogUserByNick(nick) != null) {
			errors.put("nick", "Nickname already exists");
		}

		if (passwordHash.isEmpty()) {
			errors.put("password", "Password cannot be empty");
		} else {
			passwordHash = Crypto.hashPassword(passwordHash);
		}
	}

	/**
	 * Helper method that transforms null strings to an empty string
	 * 
	 * @param s The provided string
	 * @return A modified string
	 */
	private String prepare(String s) {
		if (s == null)
			return "";
		return s.trim();
	}

	/**
	 * Determines if errors exist in the form
	 * 
	 * @return True if errors exist, false otherwise
	 */
	public boolean errorsExist() {
		return !errors.isEmpty();
	}

	/**
	 * Gets the message to the specific error
	 * 
	 * @param errorName The specific error
	 * @return The error message
	 */
	public String getError(String errorName) {
		return prepare(errors.get(errorName));
	}

	/**
	 * Determines if the provided error has occurred
	 * 
	 * @param errorName The provided error
	 * @return True if it occurred, false otherwise
	 */
	public boolean errorExists(String errorName) {
		return errors.containsKey(errorName);
	}

	/**
	 * A getter for the first name provided through the form
	 * 
	 * @return Returns the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the current first name to the provided first name
	 * 
	 * @param firstName The provided first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * A getter for the last name provided through the form
	 * 
	 * @return The provided last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the current last name to the provided last name
	 * 
	 * @param lastName The provided last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * A getter for the email provided through the form
	 * 
	 * @return The provided email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the current email to the provided email
	 * 
	 * @param email The provided email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * A getter for the nickname provided through the forms
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
	 * A getter for the errors that occurred in the form
	 * 
	 * @return The error map
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * Sets the current error map to the provided map
	 * 
	 * @param errors The provided map
	 */
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

}
