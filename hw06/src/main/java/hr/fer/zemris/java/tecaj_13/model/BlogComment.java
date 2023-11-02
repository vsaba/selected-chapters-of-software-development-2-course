package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class which represents a comment of a single blog
 * 
 * @author Vito Sabalic
 *
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	private Long id;
	private BlogEntry blogEntry;
	private String usersEMail;
	private String message;
	private Date postedOn;

	/**
	 * Getter for the comment id
	 * 
	 * @return The comment id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Sets the current comment id to the provided comment id
	 * 
	 * @param id The provided id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for the blog of the comment
	 * 
	 * @return The comment blog
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Sets the current comment blog entry to the provided blog entry
	 * 
	 * @param blogEntry The provided blog entry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Getter for the email of the user which created the comment
	 * 
	 * @return The email of the creator
	 */
	@Column(length = 100, nullable = false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets the current email of the user which created the comment to the provided
	 * value
	 * 
	 * @param usersEMail The provided value
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Getter for the comment message
	 * 
	 * @return The comment message
	 */
	@Column(length = 4096, nullable = false)
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the current message of the comment to the provided message
	 * 
	 * @param message The provided message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter for the date the comment was posted
	 * 
	 * @return The comment date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets the current date of posting to the provided date
	 * 
	 * @param postedOn The provided date
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
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
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}