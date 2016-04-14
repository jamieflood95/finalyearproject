package com.jamie.spring.web.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.jamie.spring.web.dao.House;
import com.jamie.spring.web.dao.HouseDao;

@Service("houseService")
public class HouseService {

	private HouseDao houseDAO;

	@Autowired
	public void setHouseDAO(HouseDao houseDAO) {
		this.houseDAO = houseDAO;
	}

	public List<House> getAllHouses() {
		return houseDAO.getHouses();
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public void create(House house) throws IOException {
		houseDAO.saveOrUpdate(house);
	}

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

	public void saveOrUpdate(House house) throws IOException {
		houseDAO.saveOrUpdate(house);
	}
	
	public void update(House house) throws IOException {
		houseDAO.update(house);

	}

	public void delete(int id) {
		houseDAO.delete(id);
	}
	
	public void deleteUsername(String username) {
		houseDAO.deleteUsername(username);
	}

	public House getHouse(int id) {

		House house = houseDAO.getHouse(id);

		if (house == null) {
			return null;
		}

		return house;
	}
	
	public List<House> getHouseAddress(String address) {

		List<House> house = houseDAO.getHouseAddress(address);

		return house;
	}

	public List<House> getHouseSearch(String address, int minrent, int maxrent, int minrooms, int maxrooms) {
		List<House> house = houseDAO.getHouseSearch(address, minrent, maxrent, minrooms, maxrooms);

		return house;
	}

	public List<House> getRecent() {
		List<House> house = houseDAO.getRecent();

		return house;
	}

}
