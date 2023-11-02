package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * A singleton provider class for the {@link DAO} implementation
 * 
 * @author Vito Sabalic
 *
 */
public class DAOProvider {

	private static DAO dao = new JPADAOImpl();

	/**
	 * Getter for the {@link DAO} implementation
	 * 
	 * @return
	 */
	public static DAO getDAO() {
		return dao;
	}

}