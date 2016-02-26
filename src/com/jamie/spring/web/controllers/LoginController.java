package com.jamie.spring.web.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jamie.spring.web.dao.FormValidationGroup;
import com.jamie.spring.web.dao.House;
import com.jamie.spring.web.dao.Message;
import com.jamie.spring.web.dao.Roomie;
import com.jamie.spring.web.dao.User;
import com.jamie.spring.web.service.HouseService;
import com.jamie.spring.web.service.RoomieService;
import com.jamie.spring.web.service.UsersService;

@Controller
public class LoginController {

	private UsersService usersService;
	private HouseService houseService;
	private RoomieService roomieService;

	@RequestMapping("/login")
	public String showLogin() {
		return "login";
	}

	@RequestMapping("/denied")
	public String showDenied() {
		return "denied";
	}

	@RequestMapping("/admin")
	public String showAdmin(Model model) {
		List<User> users = usersService.getAllUsers();

		model.addAttribute("users", users);

		List<House> houses = houseService.getCurrent();

		model.addAttribute("houses", houses);

		return "admin";
	}

	@RequestMapping("/loggedout")
	public String showLoggedOut() {
		return "loggedout";
	}

	@RequestMapping("/newaccount")
	public String showNewAccount(Model model) {

		model.addAttribute("user", new User());

		return "newaccount";
	}

	@RequestMapping(value = "/createaccount", method = RequestMethod.POST)
	public String createAccount(
			@Validated(FormValidationGroup.class) User user,
			BindingResult result) {
		if (result.hasErrors()) {

			return "newaccount";
		}

		user.setAuthority("ROLE_USER");
		user.setEnabled(true);

		if (usersService.exists(user.getUsername())) {
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}

		try {
			usersService.create(user);
		} catch (DuplicateKeyException e) {
			result.rejectValue("username", "DuplicateKey.user.username");
		}

		return "accountcreated";
	}

	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	@Autowired
	public void setHouseService(HouseService houseService) {
		this.houseService = houseService;
	}

	@Autowired
	public void setRoomieService(RoomieService roomieService) {
		this.roomieService = roomieService;
	}

	@RequestMapping("/user/{username}")
	public String showUser(@PathVariable String username, Model model,
			Principal principal) {
		User user = usersService.getUser(username);

		model.addAttribute("user", user);

		House house = null;

		if (username != null) {
			house = houseService.getHouse(username);
		}

		if (house == null) {
			house = new House();
		}

		model.addAttribute("house", house);

		Roomie roomie = new Roomie();
		roomie.setRoomie_username(username);

		model.addAttribute("roomie", roomie);

		List<Roomie> roomies = roomieService.getRoomies(username);

		model.addAttribute("roomies", roomies);

		boolean isFriends = false;

		isFriends = roomieService.exists(username, principal.getName());

		model.addAttribute("isFriends", isFriends);
		
		Message message = new Message();

		message.setRecipient(house.getUsername());

		model.addAttribute("message", message);
		
		boolean hasHouse = false;

		if (principal != null) {
			hasHouse = houseService.hasHouse(principal.getName());
		}

		model.addAttribute("hasHouse", hasHouse);

		return "user";

	}

	@RequestMapping("/deleteuser/{username}")
	public String deleteUser(@PathVariable String username) {
		usersService.delete(username);

		return "userdeleted";

	}

}
