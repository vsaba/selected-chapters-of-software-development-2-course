package hr.fer.zemris.java.p12.model;

/**
 * A class which represents the table Poll stored in the database
 * 
 * @author Vito Sabalic
 *
 */
public class Poll {

	private int id;
	private String title;
	private String message;

	/**
	 * A simple constructor, assigns all the values
	 * 
	 * @param id      The provided id
	 * @param title   The provided title
	 * @param message The provided message
	 */
	public Poll(int id, String title, String message) {
		this.id = id;
		this.title = title;
		this.message = message;

	}

	/**
	 * A getter for the id of the poll
	 * 
	 * @return Returns the id of the poll
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id of the poll to the provided id
	 * 
	 * @param id The provided id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * A getter for the title of the poll
	 * 
	 * @return Returns the title of the poll
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the poll to the provided title
	 * 
	 * @param title The provided title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * A getter for the message of the poll
	 * 
	 * @return Returns the message of the poll
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message of the poll to the provided message
	 * 
	 * @param message The provided message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
