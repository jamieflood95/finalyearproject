package com.jamie.spring.web.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jamie.spring.web.dao.House;
import com.jamie.spring.web.dao.Roomie;
import com.jamie.spring.web.dao.RoomieDao;

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
@Service("roomieService")
public class RoomieService {

	private RoomieDao roomieDAO;

	@Autowired
	public void setRoomieDAO(RoomieDao roomieDAO) {
		this.roomieDAO = roomieDAO;
	}

	/**
	 * Calls the DAO class to save or update a roomie
	 * 
	 * @param roomie
	 * @throws IOException
	 */
	public void saveOrUpdate(Roomie roomie) throws IOException {
		roomieDAO.saveOrUpdate(roomie);

	}

	/**
	 * Calls the DAO class to get all roomies for a user
	 * 
	 * @param username
	 * @return
	 */
	public List<Roomie> getRoomies(String username) {
		List<Roomie> roomies = roomieDAO.getRoomies(username);

		return roomies;
	}

	/**
	 * Calls the DAO class to check if a roomie exists
	 * 
	 * @param roomie_username
	 * @param username
	 * @return
	 */
	public boolean exists(String roomie_username, String username) {
		return roomieDAO.exists(roomie_username, username);
	}

	/**
	 * Calls the DAO class to delete a roomie based on the two usernames
	 * 
	 * @param currentUsername
	 * @param username
	 */
	public void delete(String currentUsername, String username) {
		roomieDAO.delete(currentUsername, username);
	}

}
