package hr.fer.zemris.java.tecaj_13.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * Class which represents the form to create a new blog, or edit an existing one
 * 
 * @author Vito Sabalic
 *
 */
public class BlogForm {

	private String title;
	private String text;
	private Map<String, String> errors = new HashMap<>();

	/**
	 * A simple constructor
	 */
	public BlogForm() {
		title = new String();
		text = new String();
	}

	/**
	 * Initalizes the class from the provided http request
	 * 
	 * @param req The provided http request
	 */
	public void fillFromHttpRequest(HttpServletRequest req) {
		this.title = req.getParameter("title");
		this.text = req.getParameter("text");
	}

	/**
	 * Initializes the class from the provided blog entry
	 * 
	 * @param entry The provided blog entry
	 */
	public void fillFromBlogEntry(BlogEntry entry) {
		this.title = entry.getTitle();
		this.text = entry.getText();
	}

	/**
	 * Validates the form
	 */
	public void validate() {
		errors.clear();

		if (title.isEmpty()) {
			errors.put("title", "The title cannot be empty");
		}

		if (text.isEmpty()) {
			errors.put("text", "The text cannot be empty");
		}

		return;
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
	 * A getter for the title of the blog provided through the form
	 * 
	 * @return The provided title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the current title to the provided title
	 * 
	 * @param title The provided title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * A getter for the text of the blog provided through the form
	 * 
	 * @return Returns the text provided through the form
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the current text of the blog to the provided text
	 * 
	 * @param text The provided text
	 */
	public void setText(String text) {
		this.text = text;
	}

}
