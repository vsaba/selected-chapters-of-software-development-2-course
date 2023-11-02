package hr.fer.zemris.java.p12.model;

/**
 * A class which represents the population of a poll stored in the database
 * 
 * @author Vito Sabalic
 *
 */
public class PollOption {

	private int id;
	private String name;
	private String link;
	private int pollId;
	private long votesCount;

	/**
	 * A simple constructor, assigns all the provided values to the current values
	 * 
	 * @param id
	 * @param name
	 * @param link
	 * @param pollId
	 * @param votesCount
	 */
	public PollOption(int id, String name, String link, int pollId, long votesCount) {
		this.id = id;
		this.name = name;
		this.link = link;
		this.pollId = pollId;
		this.votesCount = votesCount;
	}

	/**
	 * A getter for the id of the poll option
	 * 
	 * @return Returns the id of the poll option
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the current id to the value of the provided id
	 * 
	 * @param id The provided id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * A getter for the name of the poll option
	 * 
	 * @return Returns the name of the poll option
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the current name to the value of the provided name
	 * 
	 * @param name The provided name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * A getter for the link of the poll option
	 * 
	 * @return Returns the link of the poll option
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Sets the current link to the provided link value
	 * 
	 * @param link The provided link
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * A getter for the poll id of the poll option
	 * 
	 * @return Returns the poll id of the poll option
	 */
	public int getPollId() {
		return pollId;
	}

	/**
	 * Sets the current poll id to the provided poll id
	 * 
	 * @param pollId
	 */
	public void setPollId(int pollId) {
		this.pollId = pollId;
	}

	/**
	 * A getter for the votesCount of the poll option
	 * 
	 * @return Returns the votesCount of the poll option
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Sets the votesCount to the provided votesCount
	 * 
	 * @param votesCount The provided votesCount
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}

}
