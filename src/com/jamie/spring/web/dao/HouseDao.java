package com.jamie.spring.web.dao;

import java.io.IOException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Component("housesDao")
public class HouseDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public List<House> getHouses() {

		Criteria crit = session().createCriteria(House.class);

		//crit.createAlias("user", "u").add(Restrictions.eq("u.enabled", true));

		return crit.list();
	}

	@SuppressWarnings("unchecked")
	public List<House> getHouses(String username) {
		Criteria crit = session().createCriteria(House.class);

//		crit.createAlias("user", "u");
//
//		crit.add(Restrictions.eq("u.enabled", true));
//		crit.add(Restrictions.eq("u.username", username));
		
		crit.add(Restrictions.eq("username",  username));

		return crit.list();
	}

	public void saveOrUpdate(House house) throws IOException {

		session().saveOrUpdate(house);
	}
	
	public void update(House house) throws IOException {

		session().update(house);
	}

	public boolean delete(int id) {
		Query query = session().createQuery("delete from House where id=:id");
		query.setLong("id", id);
		return query.executeUpdate() == 1;
	}
	
	public boolean deleteUsername(String username) {
		Query query = session().createQuery("delete from House where username=:username");
		query.setString("username", username);
		return query.executeUpdate() == 1;
	}

	public House getHouse(int id) {

		Criteria crit = session().createCriteria(House.class);
		crit.add(Restrictions.idEq(id));

		return (House) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<House> getHouseAddress(String address) {
		Criteria crit = session().createCriteria(House.class);

		crit.add(Restrictions.like("address", "%" + address + "%"));

		return crit.list();
	}

	@SuppressWarnings("unchecked")
	public List<House> getHouseSearch(String address, int minrent, int maxrent, int minrooms, int maxrooms) {
		Criteria crit = session().createCriteria(House.class);

		crit.add(Restrictions.ge("rent", minrent));
		crit.add(Restrictions.le("rent", maxrent));
		crit.add(Restrictions.like("address", "%" + address + "%"));
		crit.add(Restrictions.ge("rooms", minrooms));
		crit.add(Restrictions.le("rooms", maxrooms));


		return crit.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<House> getRecent() {
		
		Query query = session().createQuery("from House order by id DESC");
		query.setMaxResults(9);

		return query.list();
	}

	public boolean exists(String username) {
		Criteria crit = session().createCriteria(House.class);
		crit.add(Restrictions.eq("username", username));
		House house = (House) crit.uniqueResult();
		return house != null;
	}
	
}
