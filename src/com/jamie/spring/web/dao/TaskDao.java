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
@Component("taskDao")
public class TaskDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public List<Task> getTasks(String username) {
		Criteria crit = session().createCriteria(Task.class);
		crit.createAlias("user", "u");
		crit.add(Restrictions.eq("u.username", username));

		return crit.list();

	}

	public void saveOrUpdate(Task task) {
		session().saveOrUpdate(task);
	}

	public Task getTask(int id) {
		Criteria crit = session().createCriteria(Task.class);

		crit.add(Restrictions.idEq(id));

		return (Task) crit.uniqueResult();
	}
}
