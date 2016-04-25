package com.jamie.spring.web.dao;

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
@Component("messageDao")
public class MessageDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Saves a message in the database
	 * 
	 * @param message
	 */
	public void saveOrUpdate(Message message) {
		session().saveOrUpdate(message);
	}

	/**
	 * Gets a list of messages sent between users from the database
	 * 
	 * @param username
	 * @param myusername
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Message> getMessagesSent(String username, String myusername) {
		Criteria crit = session().createCriteria(Message.class);
		crit.createAlias("user", "u");
		crit.add(Restrictions.eq("u.username", username));
		crit.add(Restrictions.eq("recipient", myusername));

		return crit.list();
	}

	/**
	 * Gets a list of messages received between users from the database
	 * 
	 * @param username
	 * @param myusername
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Message> getMessagesReceived(String username, String myusername) {
		Criteria crit = session().createCriteria(Message.class);
		crit.createAlias("user", "u");
		crit.add(Restrictions.eq("u.username", myusername));
		crit.add(Restrictions.eq("recipient", username));

		return crit.list();
	}

	/**
	 * Check if a message to a specific user exists in the database
	 * 
	 * @param username
	 * @return
	 */
	public boolean exists(String username) {
		Criteria crit = session().createCriteria(Message.class);
		crit.add(Restrictions.eq("recipient", username));
		Message message = (Message) crit.uniqueResult();
		return message != null;
	}

	/**
	 * Gets a list of all messages sent to a user from the database
	 * 
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Message> getAllMessages(String username) {
		Criteria crit = session().createCriteria(Message.class);
		crit.add(Restrictions.eq("recipient", username));

		return crit.list();
	}

	/**
	 * Gets a list of all messages sent from a user from the database
	 * 
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Message> getAllMessagesSent(String username) {
		Criteria crit = session().createCriteria(Message.class);
		crit.createAlias("user", "u");
		crit.add(Restrictions.eq("u.username", username));

		return crit.list();
	}

	/**
	 * Gets a specific message from the database based on its id
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Message getMessage(int id) {
		String hql = "FROM Message WHERE id=:id";
		Query query = session().createQuery(hql);
		query.setInteger("id", id);
		List<Message> results = query.list();

		return results.get(0);
	}
}
