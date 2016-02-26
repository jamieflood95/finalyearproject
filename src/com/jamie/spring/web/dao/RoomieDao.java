package com.jamie.spring.web.dao;

import java.io.IOException;
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

@Repository
@Transactional
@Component("roomieDao")
public class RoomieDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	public void saveOrUpdate(Roomie roomie) throws IOException {

		session().saveOrUpdate(roomie);
	}

	@SuppressWarnings("unchecked")
	public List<Roomie> getRoomies(String username) {
		String jpql = "from Roomie where username = :username";
		Query query = session().createQuery(jpql);
		query.setParameter("username", username);

		return query.list();
	}

	public Boolean exists(String roomie_username, String username) {
		Query query = session()
				.createQuery("select 1 from Roomie where roomie_username = :roomie_username and username = :username");
		query.setParameter("roomie_username", roomie_username);
		query.setParameter("username", username);
		return (query.uniqueResult() != null);
	}

	public boolean delete(String username, String roomie_username) {
		Query query = session().createQuery("delete from Roomie where roomie_username=:roomie_username and username=:username");
		query.setString("username", username);
		query.setString("roomie_username", roomie_username);
		return query.executeUpdate() == 1;
	}

}
