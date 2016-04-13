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

import com.jamie.spring.web.dao.Contact;
import com.jamie.spring.web.dao.FormValidationGroup;
import com.jamie.spring.web.dao.House;
import com.jamie.spring.web.dao.Message;
import com.jamie.spring.web.dao.Roomie;
import com.jamie.spring.web.dao.Task;
import com.jamie.spring.web.service.ContactService;
import com.jamie.spring.web.service.HouseService;
import com.jamie.spring.web.service.MessageService;
import com.jamie.spring.web.service.RoomieService;
import com.jamie.spring.web.service.TaskService;

/**
 * 
 * @author Jamie
 *         <p>
 *         This class is a controller for any tasks that take place for roomies.
 *         These tasks include: adding a roomie and deleting a roomie.
 *         <p>
 *         The @Controller annotation indicates that a particular class serves
 *         the role of a controller. A controller generates an output view.
 *         <p>
 *         An instance of the RoomieService, MessageService and UserService
 *         classes are declared and instantiated so that data can be received
 *         from the DAO's. The @Autowired annotation is used which marks a
 *         method as to be autowired by Spring's dependency injection
 *         facilities. These methods are autowired with a matching bean in the
 *         Spring container.
 *         <p>
 * @RequestMapping is annotation for mapping web requests onto specific handler
 *                 methods. These methods return a string which represents a
 *                 view.
 *                 <p>
 *                 The addRoomie method's main responsibility connect to a user.
 *                 A new message is created to notify the user that the current
 *                 user has added them as a roomie. The users house is then
 *                 removed as they are no longer looking for roomies. It then
 *                 checks if a connection already exists or if they are trying
 *                 to connect to themselves. If these tests pass then a record
 *                 of the roomies is added to the database and the message is
 *                 sent.
 *                 <p>
 *                 The deleteRoomie method's main responsibility is to delete a
 *                 particular roomie from the database. It takes the roomie
 *                 username as a parameter and uses the roomieService to delete
 *                 the roomie entity from the database. The home page is then
 *                 displayed.
 */
@Controller
public class RoomieController {

	private RoomieService roomieService;
	private MessageService messageService;
	private HouseService houseService;
	private ContactService contactService;
	private TaskService taskService;

	@Autowired
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	@Autowired
	public void setRoomieService(RoomieService roomieService) {
		this.roomieService = roomieService;
	}

	@Autowired
	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	@Autowired
	public void setHouseService(HouseService houseService) {
		this.houseService = houseService;
	}

	@Autowired
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@RequestMapping(value = "/addRoomie", method = RequestMethod.POST)
	public String addRoomie(Principal principal, @Validated(value = FormValidationGroup.class) Roomie roomie)
			throws IOException {

		Message message = new Message();

		String currentUsername = principal.getName();

		House house = houseService.getHouse(currentUsername);

		if (currentUsername.equals(roomie.getRoomie_username())) {
			return "cannotadd";
		} else {
			roomie.getUser().setUsername(currentUsername);

			if (roomieService.exists(roomie.getRoomie_username(), currentUsername)) {

				return "cannotadd";
			} else {
				if (house.getRooms() > 0) {

					message.setRecipient(roomie.getRoomie_username());
					message.setText(currentUsername
							+ " has sent a request to be your roomie. Please go onto his page to connect with him.");
					message.getUser().setUsername(currentUsername);

					DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
					Date send_date = new Date();
					message.setSend_date(send_date);

					messageService.saveOrUpdate(message);

					roomieService.saveOrUpdate(roomie);

					house.setRooms(house.getRooms() - 1);
					houseService.update(house);

					return "roomieadded";
				} else {
					return "noroomsavailable";
				}
			}

		}

	}

	@RequestMapping("/deleteroomie/{username}")
	public String deleteRoomie(@PathVariable String username, Model model, Principal principal) throws IOException {
		String currentUsername = principal.getName();

		House house = houseService.getHouse(currentUsername);

		roomieService.delete(currentUsername, username);
		
		house.setRooms(house.getRooms() + 1);
		
		houseService.saveOrUpdate(house);

		model.addAttribute("roomie", new Roomie());
		
		return "home";

	}

	@RequestMapping("/roomies")
	public String showRoomies(Model model, Principal principal) {
		String username = principal.getName();

		List<Roomie> roomies = roomieService.getRoomies(username);

		model.addAttribute("roomies", roomies);

		boolean isFriends = false;

		isFriends = roomieService.exists(username, principal.getName());

		model.addAttribute("isFriends", isFriends);

		List<Task> tasks = taskService.getTasks(username);

		model.addAttribute("tasks", tasks);

		List<Contact> contacts = contactService.getContacts(username);

		model.addAttribute("contacts", contacts);

		return "roomies";

	}

	@RequestMapping("/roomies/all")
	public String showAllRoomies(Model model, Principal principal) {
		String username = principal.getName();

		List<Roomie> roomies = roomieService.getRoomies(username);

		model.addAttribute("roomies", roomies);

		return "myroomies";

	}

}
