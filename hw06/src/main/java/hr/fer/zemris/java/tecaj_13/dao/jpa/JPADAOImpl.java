package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.forms.BlogForm;
import hr.fer.zemris.java.tecaj_13.forms.RegisterForm;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Implementation of the {@link DAO} interface
 * 
 * @author Vito Sabalic
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public BlogUser getBlogUserByNick(String nick) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogUser user = null;
		try {
			user = em.createNamedQuery("getUserByNick", BlogUser.class).setParameter("nick", nick).getSingleResult();
		} catch (NoResultException e) {
		}
		return user;
	}

	@Override
	public BlogUser createNewUser(RegisterForm form) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		BlogUser user = new BlogUser();

		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());
		user.setEmail(form.getEmail());
		user.setNick(form.getNick());
		user.setPasswordHash(form.getPasswordHash());

		em.persist(user);

		return user;
	}

	@Override
	public List<BlogUser> getAllBlogUsers() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		List<BlogUser> users = em.createNamedQuery("getAllUsers", BlogUser.class).getResultList();
		return users;
	}

	@Override
	public void createNewBlogEntry(BlogUser user, BlogForm form) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogEntry newEntry = new BlogEntry();

		newEntry.setCreatedAt(new Date());
		newEntry.setLastModifiedAt(newEntry.getCreatedAt());
		newEntry.setTitle(form.getTitle());
		newEntry.setText(form.getText());
		newEntry.setCreator(user);

		em.persist(newEntry);

		return;
	}

	@Override
	public void createNewComment(String email, String text, BlogEntry entry) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogComment newComment = new BlogComment();

		newComment.setUsersEMail(email);
		newComment.setPostedOn(new Date());
		newComment.setMessage(text);
		newComment.setBlogEntry(entry);

		em.persist(newComment);

		return;
	}

}