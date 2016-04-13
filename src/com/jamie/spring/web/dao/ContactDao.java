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
@Component("contactDao")
public class ContactDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}


	@SuppressWarnings("unchecked")
	public List<Contact> getContacts(String username) {
		Criteria crit = session().createCriteria(Contact.class);
		crit.createAlias("user", "u");
		crit.add(Restrictions.eq("u.username", username));
		
		return crit.list();
		
	}


	public void saveOrUpdate(Contact contact) {
		session().saveOrUpdate(contact);		
	}


	public Contact getContact(int id) {
		Criteria crit = session().createCriteria(Contact.class);

		crit.add(Restrictions.idEq(id));

		return (Contact) crit.uniqueResult();
	}
}
