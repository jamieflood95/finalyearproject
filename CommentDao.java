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
 *
 */
@Repository
@Transactional
@Component("commentDao")
public class CommentDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Gets a list of comments for a specific user from the database
	 * 
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Comment> getComments(String username) {
		Criteria crit = session().createCriteria(Comment.class);
		crit.createAlias("user", "u");
		crit.add(Restrictions.eq("u.username", username));

		return crit.list();

	}

	/**
	 * Saves a comment in the database
	 * 
	 * @param comment
	 */
	public void saveOrUpdate(Comment comment) {
		session().saveOrUpdate(comment);
	}

	/**
	 * Gets a list of contacts for a house from the database
	 * 
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Comment> getComments(int house_id) {
		Criteria crit = session().createCriteria(Comment.class);

		crit.createAlias("house", "h");
		crit.add(Restrictions.eq("h.id", house_id));

		return crit.list();
	}
}
