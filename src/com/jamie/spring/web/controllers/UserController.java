package com.jamie.spring.web.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jamie.spring.web.dao.FormValidationGroup;
import com.jamie.spring.web.dao.House;
import com.jamie.spring.web.dao.Message;
import com.jamie.spring.web.dao.Roomie;
import com.jamie.spring.web.dao.User;
import com.jamie.spring.web.service.HouseService;
import com.jamie.spring.web.service.RoomieService;
import com.jamie.spring.web.service.UsersService;

@Controller
public class UserController {

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

		List<House> houses = houseService.getAllHouses();

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
	public String createAccount(@Validated(FormValidationGroup.class) User user, BindingResult result,
			@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
		if (result.hasErrors()) {

			return "newaccount";
		}

		user.setAuthority("ROLE_USER");
		user.setEnabled(true);

		if (usersService.exists(user.getUsername())) {
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}

		if (!file.isEmpty()) {
			String username = user.getUsername();
			BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
			File destination = new File(
					"\\C:\\Users\\Jamie\\workspace\\FYP\\WebContent\\resources\\images\\profilepictures\\" + username
							+ ".png");

			ImageIO.write(src, "png", destination);

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
	public String showUser(@PathVariable String username, Model model, Principal principal) {

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

		message.setRecipient(user.getUsername());

		model.addAttribute("message", message);

		boolean hasHouse = false;

		if (principal != null) {
			hasHouse = houseService.hasHouse(user.getUsername());
		}

		model.addAttribute("hasHouse", hasHouse);

		boolean hasImage = false;

		File imageFile = new File("\\C:\\Users\\Jamie\\workspace\\FYP\\WebContent\\resources\\images\\profilepictures\\"
				+ username + ".png");
		if (imageFile.exists() && !imageFile.isDirectory()) {
			hasImage = true;
		}

		model.addAttribute("hasImage", hasImage);

		boolean hasHouseImage = false;
		
		System.out.println(house.getId());

		File houseFile = new File("\\C:\\Users\\Jamie\\workspace\\FYP\\WebContent\\resources\\images\\housepictures\\" + house.getId() + ".png");
		if (houseFile.exists() && !houseFile.isDirectory()) {
			hasHouseImage = true;
		}

		model.addAttribute("hasHouseImage", hasHouseImage);

		return "user";

	}

	@RequestMapping("/deleteuser/{username}")
	public String deleteUser(@PathVariable String username) {
		usersService.delete(username);

		return "userdeleted";

	}

}
