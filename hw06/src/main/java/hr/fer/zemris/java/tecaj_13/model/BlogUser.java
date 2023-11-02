package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class which represents a user of the blog application
 * 
 * @author Vito Sabalic
 *
 */
@NamedQueries({ @NamedQuery(name = "getAllUsers", query = "Select b from BlogUser as b"),
		@NamedQuery(name = "getUserByNick", query = "Select b from BlogUser as b where b.nick=:nick"), })
@Entity
@Table(name = "blog_users")
@Cacheable(true)
public class BlogUser {

	private Long id;
	private String firstName;
	private String lastName;
	private String nick;
	private String email;
	private String passwordHash;
	private List<BlogEntry> entries = new ArrayList<>();

	/**
	 * A getter for the id of the user
	 * 
	 * @return The id of the user
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Sets the current id of the user to the provided id
	 * 
	 * @param id The provided id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * A getter for the first name of the user
	 * 
	 * @return The first name of the user
	 */
	@Column(length = 50, nullable = false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the current first name of the user to the provided value
	 * 
	 * @param firstName The provided first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * A getter for the last name of the user
	 * 
	 * @return The last name of the user
	 */
	@Column(length = 100, nullable = false)
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the current last name of the user to the provided value
	 * 
	 * @param lastName The provided last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * A getter for the nickname of the user
	 * 
	 * @return The nickname of the user
	 */
	@Column(length = 200, nullable = false, unique = true)
	public String getNick() {
		return nick;
	}

	/**
	 * Sets the current nickname of the user to the provided nickname
	 * 
	 * @param nick The provided nickname
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * A getter for the email of the user
	 * 
	 * @return The email of the user
	 */
	@Column(length = 200, nullable = false)
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the current email of the user to the provided email
	 * 
	 * @param email The provided email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * A getter for the password hash of the user
	 * 
	 * @return The password hash of the user
	 */
	@Column(length = 200, nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Sets the current password hash to the provided password hash
	 * 
	 * @param passwordHash The provided password hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * A getter for the blog entries of the user
	 * 
	 * @return The blog entries
	 */
	@OneToMany(mappedBy = "creator")
	public List<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * Sets the current blog entries to the provided entries
	 * 
	 * @param entries The provided entries
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
