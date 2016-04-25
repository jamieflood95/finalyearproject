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
@Component("taskDao")
public class TaskDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Gets a list of tasks for a specific user from the database
	 * 
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Task> getTasks(String username) {
		Criteria crit = session().createCriteria(Task.class);
		crit.createAlias("user", "u");
		crit.add(Restrictions.eq("u.username", username));

		return crit.list();

	}

	/**
	 * Saves a task in the database
	 * 
	 * @param task
	 */
	public void saveOrUpdate(Task task) {
		session().saveOrUpdate(task);
	}

	/**
	 * Gets an individual task from the database based on its id
	 * @param id
	 * @return
	 */
	public Task getTask(int id) {
		Criteria crit = session().createCriteria(Task.class);

		crit.add(Restrictions.idEq(id));

		return (Task) crit.uniqueResult();
	}
}
