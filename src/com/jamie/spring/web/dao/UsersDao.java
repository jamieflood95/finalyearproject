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

	@Transactional
	public void create(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		session().save(user);
	}

	public boolean exists(String username) {
//		Criteria crit = session().createCriteria(User.class);
//		crit.add(Restrictions.idEq(username));
//		User user = (User) crit.uniqueResult();
		
		String hql = "FROM User WHERE username=:username";
		Query query = session().createQuery(hql);
		query.setString("username",  username);
		List<User> results = query.list();
		
		User user = results.get(0);
		
		return user != null;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return session().createQuery("from User").list();
	}

	public User getUser(String username) {

//		Criteria crit = session().createCriteria(User.class);
//
//		crit.add(Restrictions.idEq("username", username));
		
		String hql = "FROM User WHERE username=:username";
		Query query = session().createQuery(hql);
		query.setString("username",  username);
		List<User> results = query.list();
		
		return results.get(0);
	}
	
	public boolean delete(String username) {
		Query q = session().createQuery("delete from Message where username=:username");
		q.setString("username", username);
		q.executeUpdate();
		
		Query q2 = session().createQuery("delete from Roomie where username=:username");
		q2.setString("username", username);
		q2.executeUpdate();
		
		Query query = session().createQuery("delete from User where username=:username");
		query.setString("username", username);
		return query.executeUpdate() == 1;
	}
	
	public List<User> getUserSearch(String name) {
		Criteria crit = session().createCriteria(User.class);

		crit.add(Restrictions.like("name", "%" + name + "%"));

		return crit.list();
	}

}