package com.jamie.spring.web.dao;

import java.util.List;

import org.hibernate.Criteria;
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
@Component("contactDao")
public class ContactDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Gets a list of contacts for a specific user from the database
	 * 
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Contact> getContacts(String username) {
		Criteria crit = session().createCriteria(Contact.class);
		crit.createAlias("user", "u");
		crit.add(Restrictions.eq("u.username", username));

		return crit.list();

	}

	/**
	 * Saves a contact in the database
	 * 
	 * @param contact
	 */
	public void saveOrUpdate(Contact contact) {
		session().saveOrUpdate(contact);
	}

	/**
	 * Gets an individual contact based on its id from the database
	 * 
	 * @param id
	 * @return
	 */
	public Contact getContact(int id) {
		Criteria crit = session().createCriteria(Contact.class);
		crit.add(Restrictions.idEq(id));

		return (Contact) crit.uniqueResult();
	}
}
