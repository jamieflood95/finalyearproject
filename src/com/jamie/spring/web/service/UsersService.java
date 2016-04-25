package com.jamie.spring.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.jamie.spring.web.dao.User;
import com.jamie.spring.web.dao.UsersDao;

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
@Service("usersService")
public class UsersService {

	private UsersDao usersDAO;

	@Autowired
	public void setUsersDAO(UsersDao usersDAO) {
		this.usersDAO = usersDAO;
	}

	/**
	 * Calls the DAO class to register a user
	 * 
	 * @param user
	 */
	public void create(User user) {
		usersDAO.create(user);
	}

	/**
	 * Calls the DAO class to edit a users records
	 * 
	 * @param user
	 */
	public void edit(User user) {
		System.out.println("Service: attempt update");
		usersDAO.edit(user);
	}

	/**
	 * Calls the DAO class to check if a user exists
	 * 
	 * @param username
	 * @return
	 */
	public boolean exists(String username) {
		return usersDAO.exists(username);
	}

	/**
	 * Calls the DAO class to get all users
	 * 
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	public List<User> getAllUsers() {
		return usersDAO.getAllUsers();
	}

	/**
	 * Calls the DAO class to get a specific user
	 * 
	 * @param username
	 * @return
	 */
	public User getUser(String username) {
		User user = usersDAO.getUser(username);

		if (user == null) {
			return null;
		}

		return user;
	}

	/**
	 * Calls the DAO class to delete a user based on its username
	 * 
	 * @param username
	 */
	public void delete(String username) {
		usersDAO.delete(username);
	}

	/**
	 * Calls the DAO class to search for a user
	 * 
	 * @param username
	 * @return
	 */
	public List<User> getUserSearch(String name) {
		List<User> user = usersDAO.getUserSearch(name);
		return user;
	}
}
