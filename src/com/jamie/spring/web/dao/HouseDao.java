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
@Component("housesDao")
public class HouseDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Gets a list of all houses in the database
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<House> getHouses() {
		Criteria crit = session().createCriteria(House.class);

		return crit.list();
	}

	/**
	 * Gets a list of all houses for a specific user in the database
	 * 
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<House> getHouses(String username) {
		Criteria crit = session().createCriteria(House.class);
		crit.add(Restrictions.eq("username", username));

		return crit.list();
	}

	/**
	 * Saves a house in the database
	 * 
	 * @param house
	 * @throws IOException
	 */
	public void saveOrUpdate(House house) throws IOException {
		session().saveOrUpdate(house);
	}

	/**
	 * Updates a house in the database
	 * 
	 * @param house
	 * @throws IOException
	 */
	public void update(House house) throws IOException {
		session().update(house);
	}

	/**
	 * Deletes a house from the database based on its id
	 * 
	 * @param id
	 * @return
	 */
	public boolean delete(int id) {
		Query query = session().createQuery("delete from House where id=:id");
		query.setLong("id", id);
		return query.executeUpdate() == 1;
	}

	/**
	 * Deletes a house from the database based on its user
	 * 
	 * @param username
	 * @return
	 */
	public boolean deleteUsername(String username) {
		Query query = session().createQuery("delete from House where username=:username");
		query.setString("username", username);
		return query.executeUpdate() == 1;
	}

	/**
	 * Gets a house from the database based on its id
	 * 
	 * @param id
	 * @return
	 */
	public House getHouse(int id) {
		Criteria crit = session().createCriteria(House.class);
		crit.add(Restrictions.idEq(id));

		return (House) crit.uniqueResult();
	}

	/**
	 * Gets a list of houses from the database if any of the addresses are
	 * similar to the variable
	 * 
	 * @param address
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<House> getHouseAddress(String address) {
		Criteria crit = session().createCriteria(House.class);

		crit.add(Restrictions.like("address", "%" + address + "%"));

		return crit.list();
	}

	/**
	 * Gets a list of houses from the database if any of them match the
	 * parameters
	 * 
	 * @param address
	 * @param minrent
	 * @param maxrent
	 * @param minrooms
	 * @param maxrooms
	 * @return
	 */
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

	/**
	 * Gets the 9 most recent houses from the database
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<House> getRecent() {

		Query query = session().createQuery("from House order by id DESC");
		query.setMaxResults(9);

		return query.list();
	}

	/**
	 * Checks if a record exists for a specific user
	 * 
	 * @param username
	 * @return
	 */
	public boolean exists(String username) {
		Criteria crit = session().createCriteria(House.class);
		crit.add(Restrictions.eq("username", username));
		House house = (House) crit.uniqueResult();
		return house != null;
	}

}
