package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * The provider for the entity manager factory
 * 
 * @author Vito Sabalic
 *
 */
public class JPAEMFProvider {

	public static EntityManagerFactory emf;

	/**
	 * Getter for the entity manager factory
	 * 
	 * @return The entity manager factory
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Sets the entity manager factory to the provided value
	 * 
	 * @param emf The provided value
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}