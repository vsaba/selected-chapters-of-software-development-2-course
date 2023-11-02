package hr.fer.zemris.java.tecaj_13.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class which represents a blog entry
 * 
 * @author Vito Sabalic
 *
 */
@NamedQueries({
		@NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when") })
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
public class BlogEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private List<BlogComment> comments = new ArrayList<>();
	private Date createdAt;
	private Date lastModifiedAt;
	private String title;
	private String text;
	private BlogUser creator;

	/**
	 * A getter for the blog entry id
	 * 
	 * @return The blog entry id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Sets the current blog entry id to the provided id
	 * 
	 * @param id The provided id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * A getter for the comments on the current blog entry
	 * 
	 * @return The comments
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Sets the current comments to the provided comments
	 * 
	 * @param comments The provided comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * A getter for the creation date of the blog
	 * 
	 * @return The creation date of the blog
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the current creation date to the provided date
	 * 
	 * @param createdAt The provided date
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * A getter for the date of the last modification of the blog entry
	 * 
	 * @return The date of the last modification
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Sets the current date of last modification to the provided value
	 * 
	 * @param lastModifiedAt The provided value
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * A getter for the blog title
	 * 
	 * @return Returns the blog title
	 */
	@Column(length = 200, nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the current blog title to the provided title
	 * 
	 * @param title The provided title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * A getter for the text of the blog
	 * 
	 * @return The text of the blog
	 */
	@Column(length = 4096, nullable = false)
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

	/**
	 * A getter for the creator of the blog
	 * 
	 * @return The creator of the blog
	 */
	@ManyToOne
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Sets the current creator of the blog to the provided creator of the blog
	 * 
	 * @param creator
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}