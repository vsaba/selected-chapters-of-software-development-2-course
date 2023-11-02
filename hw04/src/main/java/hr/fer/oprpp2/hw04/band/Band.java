package hr.fer.oprpp2.hw04.band;

/**
 * Represents a band 
 * @author Vito Sabalic
 *
 */
public class Band {

	private int id;
	private String name;
	private String song;
	private int votes;

	/**
	 * A simple constructor
	 * @param id
	 * @param name
	 * @param song
	 */
	public Band(int id, String name, String song) {
		this.id = id;
		this.name = name;
		this.song = song;
		this.votes = 0;
	}

	/**
	 * A setter for the number of votes
	 * @param votes The provided number of votes
	 */
	public void setVotes(int votes) {
		this.votes = votes;
	}

	/**
	 * A getter for the band name
	 * @return Returns the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * A getter for the band id
	 * @return Returns the band id
	 */
	public int getId() {
		return id;
	}

	/**
	 * A getter for the link to the bands most popular song
	 * @return Returns the song youtube link
	 */
	public String getSong() {
		return song;
	}

	/**
	 * A getter for the number of votes the band has received
	 * @return Returns the number of votes
	 */
	public int getVotes() {
		return votes;
	}

	@Override
	public String toString() {
		return "Band [name=" + name + "]";
	}

}
