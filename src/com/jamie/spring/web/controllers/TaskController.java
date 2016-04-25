package com.jamie.spring.web.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jamie.spring.web.dao.FormValidationGroup;
import com.jamie.spring.web.dao.Roomie;
import com.jamie.spring.web.dao.Task;
import com.jamie.spring.web.service.RoomieService;
import com.jamie.spring.web.service.TaskService;

/**
 * 
 * @author Jamie
 *         <p>
 *         This class is a controller for any tasks that take place for tasks.
 *         These include: showing all tasks, creating a task and showing an
 *         individual task
 *         <p>
 *         The @Controller annotation indicates that a particular class serves
 *         the role of a controller. A controller generates an output view.
 *         <p>
 *         An instance of the RoomieService and TaskService classes are declared
 *         and instantiated so that data can be received from the DAO's.
 *         The @Autowired annotation is used which marks a method as to be
 *         autowired by Spring's dependency injection facilities. This method is
 *         autowired with a matching bean in the Spring container.
 *         <p>
 * @RequestMapping is annotation for mapping web requests onto specific handler
 *                 methods. These methods return a string which represents a
 *                 view.
 *                 <p>
 *                 The showTasks method's main responsibility is to display all
 *                 the data for all tasks. This method takes a model as a
 *                 parameter which allows for adding attributes to the model. A
 *                 Principal variable is also used to get a list of all tasks is
 *                 retrieved from the taskService and this list is then added to
 *                 the model. Principal represents an entity. The page is then
 *                 displayed.
 *                 <p>
 *                 The showTask method's main responsibility is to view a
 *                 particular task. This method takes a model as a parameter
 *                 which allows for adding attributes to the model. It also
 *                 takes the task id as a parameter and uses the taskService to
 *                 retrieve the task information.
 *                 <p>
 *                 The newTask method's main responsibility is to display the
 *                 form for users to create a new task. This method takes a
 *                 model as a parameter which allows for adding attributes to
 *                 the model. A task object is declared and added to the model.
 *                 <p>
 *                 The createTask method's main responsibility is to actually
 *                 create the task. This method takes a model as a parameter
 *                 which allows for adding attributes to the model.
 *                 The @Validated annotation is used which supports the
 *                 specification of validation groups. A task object and the
 *                 BindingResult is sent from the form. If the BindingResult has
 *                 errors present then the form is displayed again with the
 *                 errors. If not then the task is assigned to all of the logged
 *                 in users roomies and saved in the database.
 * 
 */
@Controller
public class TaskController {

	// declare and instantiate the service classes so that you can access the
	// methods
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

		// get the username of the logged in username and get a list of all
		// tasks for this user
		String username = principal.getName();
		List<Task> tasks = taskService.getTasks(username);

		// add the list to the model
		model.addAttribute("tasks", tasks);

		return "tasks";
	}

	@RequestMapping(value = "/newtask")
	public String newTask(Model model, Principal principal) {
		// new task object so user can assign attributes to it in the form
		Task task = new Task();
		model.addAttribute("task", task);

		return "newtask";

	}

	@RequestMapping(value = "/createtask", method = RequestMethod.POST)
	public String createTask(@Validated(FormValidationGroup.class) Task task, BindingResult result, Principal principal,
			Model model) throws IOException {

		if (result.hasErrors()) {

			return "newtask";
		}

		// set the username and date of the task
		String username = principal.getName();
		Date date_created = new Date();

		task.getUser().setUsername(username);
		task.setDate_created(date_created);

		// save the task
		taskService.saveOrUpdate(task);

		// get a list of all the users roomies
		List<Roomie> roomies = roomieService.getRoomies(username);
		// loop through this list and assign a task to each of the users roomies
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
		// get a task based on the id and add it to the model
		Task task = taskService.getTask(id);
		model.addAttribute("task", task);

		return "task";

	}
}
