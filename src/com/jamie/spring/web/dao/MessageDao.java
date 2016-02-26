package com.jamie.spring.web.dao;

import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Component("messageDao")
public class MessageDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	public void saveOrUpdate(Message message) {
		session().saveOrUpdate(message);
	}

	@SuppressWarnings("unchecked")
	public List<Message> getMessagesSent(String username, String myusername) {
		Criteria crit = session().createCriteria(Message.class);
		crit.createAlias("user", "u");
		crit.add(Restrictions.eq("u.username", username));
		crit.add(Restrictions.eq("recipient", myusername));
		
		return crit.list();
		
	}

	@SuppressWarnings("unchecked")
	public List<Message> getMessagesReceived(String username, String myusername) {
		Criteria crit = session().createCriteria(Message.class);
		crit.createAlias("user", "u");
		crit.add(Restrictions.eq("u.username", myusername));
		crit.add(Restrictions.eq("recipient", username));
		
		return crit.list();
	}
	
	public boolean exists(String username) {
		Criteria crit = session().createCriteria(Message.class);
		crit.add(Restrictions.eq("recipient", username));
		Message message = (Message) crit.uniqueResult();
		return message != null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> getAllMessages(String username) {		
		Criteria crit = session().createCriteria(Message.class);
		crit.add(Restrictions.eq("recipient", username));
		
		return crit.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> getAllMessagesSent(String username) {		
		Criteria crit = session().createCriteria(Message.class);
		crit.createAlias("user", "u");
		crit.add(Restrictions.eq("u.username", username));
		
		return crit.list();
	}

}
