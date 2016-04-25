package com.jamie.spring.web.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.jamie.spring.web.dao.House;
import com.jamie.spring.web.dao.HouseDao;

/**
 * 
 * @author Jamie
 *         <p>
 *         This is a service-layer class which handles the business logic of the
 *         application. The @Service annotation marks the class as a bean so it
 *         can be put into the application context.
 *         <p>
 *         The @Autowired annotation is used which marks a method as to be
 *         autowired by Spring's dependency injection facilities. This method is
 *         autowired with a matching bean in the Spring container.
 *
 */
@Service("houseService")
public class HouseService {

	private HouseDao houseDAO;

	@Autowired
	public void setHouseDAO(HouseDao houseDAO) {
		this.houseDAO = houseDAO;
	}

	/**
	 * Calls the DAO class to get a list of all houses
	 * 
	 * @return
	 */
	public List<House> getAllHouses() {
		return houseDAO.getHouses();
	}

	/**
	 * Calls the DAO class to save or update a house
	 * 
	 * @param house
	 * @throws IOException
	 */
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public void create(House house) throws IOException {
		houseDAO.saveOrUpdate(house);
	}

	/**
	 * Calls the DAO class to check if a user has a house
	 * 
	 * @param name
	 * @return
	 */
	public boolean hasHouse(String name) {

		if (name == null) {
			return false;
		}

		List<House> houses = houseDAO.getHouses(name);

		if (houses.size() == 0) {
			return false;
		}

		return true;
	}

	/**
	 * Calls the DAO class to get a users house
	 * 
	 * @param username
	 * @return
	 */
	public House getHouse(String username) {

		if (username == null) {
			return null;
		}

		List<House> houses = houseDAO.getHouses(username);

		if (houses.size() == 0) {
			return null;
		}

		return houses.get(0);
	}

	/**
	 * Calls the DAO class to save or update a house
	 * 
	 * @param house
	 * @throws IOException
	 */
	public void saveOrUpdate(House house) throws IOException {
		houseDAO.saveOrUpdate(house);
	}

	/**
	 * Calls the DAO class to update a house
	 * 
	 * @param house
	 * @throws IOException
	 */
	public void update(House house) throws IOException {
		houseDAO.update(house);
	}

	/**
	 * Calls the DAO class to delete a house based on its id
	 * 
	 * @param id
	 */
	public void delete(int id) {
		houseDAO.delete(id);
	}

	/**
	 * Calls the DAO class to delete a house based on its user
	 * 
	 * @param username
	 */
	public void deleteUsername(String username) {
		houseDAO.deleteUsername(username);
	}

	/**
	 * Calls the DAO class to get a house based on its id
	 * 
	 * @param id
	 * @return
	 */
	public House getHouse(int id) {

		House house = houseDAO.getHouse(id);

		if (house == null) {
			return null;
		}

		return house;
	}

	/**
	 * Calls the DAO class to get a list of houses by its address
	 * 
	 * @param address
	 * @return
	 */
	public List<House> getHouseAddress(String address) {
		List<House> house = houseDAO.getHouseAddress(address);

		return house;
	}

	/**
	 * Calls the DAO class to get a list of addresses by its address, rent and
	 * rooms
	 * 
	 * @param address
	 * @param minrent
	 * @param maxrent
	 * @param minrooms
	 * @param maxrooms
	 * @return
	 */
	public List<House> getHouseSearch(String address, int minrent, int maxrent, int minrooms, int maxrooms) {
		List<House> house = houseDAO.getHouseSearch(address, minrent, maxrent, minrooms, maxrooms);

		return house;
	}

	/**
	 * Calls the DAO class to get a list of the most recent houses
	 * 
	 * @return
	 */
	public List<House> getRecent() {
		List<House> house = houseDAO.getRecent();

		return house;
	}

}
