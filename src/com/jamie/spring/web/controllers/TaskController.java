package com.jamie.spring.web.controllers;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jamie.spring.web.dao.FormValidationGroup;
import com.jamie.spring.web.dao.Roomie;
import com.jamie.spring.web.dao.Task;
import com.jamie.spring.web.service.RoomieService;
import com.jamie.spring.web.service.TaskService;

@Controller
public class TaskController {

	private RoomieService roomieService;
	private TaskService taskService;

	@Autowired
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@Autowired
	public void setRoomieService(RoomieService roomieService) {
		this.roomieService = roomieService;
	}

	@RequestMapping("/tasks")
	public String showTasks(Model model, Principal principal) throws IOException {

		String username = principal.getName();

		List<Task> tasks = taskService.getTasks(username);

		model.addAttribute("tasks", tasks);

		return "tasks";
	}

	@RequestMapping(value = "/newtask")
	public String newTask(Model model, Principal principal) {
		Task task = new Task();

		model.addAttribute("task", task);

		return "newtask";

	}

	@RequestMapping(value = "/createtask", method = RequestMethod.POST)
	public String createTask(@Validated(FormValidationGroup.class) Task task, Principal principal, Model model)
			throws IOException {

		String username = principal.getName();
		task.getUser().setUsername(username);

		Date date_created = new Date();
		task.setDate_created(date_created);

		List<Roomie> roomies = roomieService.getRoomies(username);

		taskService.saveOrUpdate(task);

		for (int i = 0; i < roomies.size(); i++) {
			Roomie roomie = roomies.get(i);
			Task t = new Task(task);
			t.getUser().setUsername(roomie.getRoomie_username());
			taskService.saveOrUpdate(t);
		}

		return "redirect:" + "tasks/" + task.getId();
	}

	@RequestMapping("/tasks/{id}")
	public String showTask(@PathVariable int id, Model model) {
		Task task = taskService.getTask(id);

		model.addAttribute("task", task);

		return "task";

	}
}
