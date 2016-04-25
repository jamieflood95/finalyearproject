package com.jamie.spring.web.dao;

import java.io.IOException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Jamie
 *         <p>
 *         The @Repository annotation indicates that the class has access to the
 *         database. The @Repository annotation is a specialization of
 *         the @Component annotation with similar use and functionality.
 *         <p>
 *         The SessionFactory interface serves as factory for TopLink Sessions,
 *         allowing for dependency injection on thread-safe TopLink-based DAOs
 *
 */
@Repository
@Transactional
@Component("roomieDao")
public class RoomieDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Saves a roomie in the database
	 * 
	 * @param roomie
	 * @throws IOException
	 */
	public void saveOrUpdate(Roomie roomie) throws IOException {

		session().saveOrUpdate(roomie);
	}

	/**
	 * Gets a list of roomies for a specific user from the database
	 * 
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Roomie> getRoomies(String username) {
		Criteria crit = session().createCriteria(Roomie.class);
		crit.createAlias("user", "u");
		crit.add(Restrictions.eq("u.username", username));

		return crit.list();
	}

	/**
	 * Checks if a row with the given roomie_username and username exists in the
	 * database
	 * 
	 * @param roomie_username
	 * @param username
	 * @return
	 */
	public Boolean exists(String roomie_username, String username) {
		Query query = session()
				.createQuery("select 1 from Roomie where roomie_username = :roomie_username and username = :username");
		query.setParameter("roomie_username", roomie_username);
		query.setParameter("username", username);
		return (query.uniqueResult() != null);
	}

	/**
	 * Deletes a roomie from the database
	 * 
	 * @param username
	 * @param roomie_username
	 * @return
	 */
	public boolean delete(String username, String roomie_username) {
		Query query = session()
				.createQuery("delete from Roomie where roomie_username=:roomie_username and username=:username");
		query.setString("username", username);
		query.setString("roomie_username", roomie_username);
		return query.executeUpdate() == 1;
	}

}
