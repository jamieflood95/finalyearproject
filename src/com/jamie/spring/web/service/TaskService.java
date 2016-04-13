package com.jamie.spring.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jamie.spring.web.dao.Task;
import com.jamie.spring.web.dao.TaskDao;

@Service("taskService")
public class TaskService {

	private TaskDao taskDAO;

	@Autowired
	public void setTaskDAO(TaskDao taskDAO) {
		this.taskDAO = taskDAO;
	}

	public List<Task> getTasks(String username) {
		List<Task> tasks = taskDAO.getTasks(username);

		return tasks;
	}

	public void saveOrUpdate(Task task) {
		taskDAO.saveOrUpdate(task);
	}

	public Task getTask(int id) {
		Task task = taskDAO.getTask(id);

		return task;
	}

}
