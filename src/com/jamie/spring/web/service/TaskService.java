package com.jamie.spring.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jamie.spring.web.dao.Task;
import com.jamie.spring.web.dao.TaskDao;

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
@Service("taskService")
public class TaskService {

	private TaskDao taskDAO;

	@Autowired
	public void setTaskDAO(TaskDao taskDAO) {
		this.taskDAO = taskDAO;
	}

	/**
	 * Calls the DAO class to get all tasks for a user
	 * 
	 * @param username
	 * @return
	 */
	public List<Task> getTasks(String username) {
		List<Task> tasks = taskDAO.getTasks(username);

		return tasks;
	}

	/**
	 * Calls the DAO class to save or update a task
	 * 
	 * @param task
	 */
	public void saveOrUpdate(Task task) {
		taskDAO.saveOrUpdate(task);
	}

	/**
	 * Calls the DAO class to get a task based on its id
	 * 
	 * @param id
	 * @return
	 */
	public Task getTask(int id) {
		Task task = taskDAO.getTask(id);

		return task;
	}

}
