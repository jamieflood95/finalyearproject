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

@Repository
@Transactional
@Component("commentDao")
public class CommentDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public List<Comment> getComments(String username) {
		Criteria crit = session().createCriteria(Comment.class);
		crit.createAlias("user", "u");
		crit.add(Restrictions.eq("u.username", username));

		return crit.list();

	}

	public void saveOrUpdate(Comment comment) {
		session().saveOrUpdate(comment);
	}

	@SuppressWarnings("unchecked")
	public List<Comment> getComments(int house_id) {
		Criteria crit = session().createCriteria(Comment.class);	
		
		crit.createAlias("house", "h");
		crit.add(Restrictions.eq("h.id",  house_id));

		return crit.list();
	}
}
