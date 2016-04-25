package com.jamie.spring.web.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@Component("usersDao")
public class UsersDao {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Saves a user in the database
	 * 
	 * @param user
	 */
	@Transactional
	public void create(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		session().save(user);
	}

	/**
	 * Updates a users details in the database
	 * 
	 * @param user
	 */
	@Transactional
	public void edit(User user) {
		System.out.println("DAO: attempt update");
		session().update(user);
	}

	/**
	 * Checks if a row with a specific username exists in the database
	 * 
	 * @param username
	 * @return
	 */
	public boolean exists(String username) {
		Criteria crit = session().createCriteria(User.class);
		crit.add(Restrictions.idEq(username));
		User user = (User) crit.uniqueResult();

		return user != null;
	}

	/**
	 * Gets a list of all users from the database
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return session().createQuery("from User").list();
	}

	/**
	 * Gets a user from the database based on its username
	 * 
	 * @param username
	 * @return
	 */
	public User getUser(String username) {

		String hql = "FROM User WHERE username=:username";
		Query query = session().createQuery(hql);
		query.setString("username", username);
		List<User> results = query.list();

		return results.get(0);
	}

	/**
	 * Deletes a username from the database based on its username
	 * 
	 * @param username
	 * @return
	 */
	public boolean delete(String username) {
		Query deleteMessage = session().createQuery("delete from Message where username=:username");
		deleteMessage.setString("username", username);
		deleteMessage.executeUpdate();

		Query deleteRoomie = session().createQuery("delete from Roomie where username=:username");
		deleteRoomie.setString("username", username);
		deleteRoomie.executeUpdate();

		Query deleteUser = session().createQuery("delete from User where username=:username");
		deleteUser.setString("username", username);
		return deleteUser.executeUpdate() == 1;
	}

	/**
	 * Gets a list of users from the database with a similar name to the one
	 * submitted
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUserSearch(String name) {
		Criteria crit = session().createCriteria(User.class);
		crit.add(Restrictions.like("name", "%" + name + "%"));

		return crit.list();
	}

}