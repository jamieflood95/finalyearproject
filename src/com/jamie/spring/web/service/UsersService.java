package com.jamie.spring.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.jamie.spring.web.dao.User;
import com.jamie.spring.web.dao.UsersDao;

@Service("usersService")
public class UsersService {

	private UsersDao usersDAO;

	@Autowired
	public void setUsersDAO(UsersDao usersDAO) {
		this.usersDAO = usersDAO;
	}

	public void create(User user) {
		usersDAO.create(user);
	}
	
	public boolean exists(String username) {
		return usersDAO.exists(username);
	}

	@Secured("ROLE_ADMIN")
	public List<User> getAllUsers() {
		return usersDAO.getAllUsers();
	}

	public User getUser(String username) {
		User user = usersDAO.getUser(username);

		if (user == null) {
			return null;
		}

		return user;
	}
	
	public void delete(String username) {
		usersDAO.delete(username);
	}
}
