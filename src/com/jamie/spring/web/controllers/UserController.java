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

/**
 * 
 * @author Jamie
 *         <p>
 *         This class is a controller for any tasks that take place for users.
 *         These tasks include:
 *         <p>
 *         The @Controller annotation indicates that a particular class serves
 *         the role of a controller. A controller generates an output view.
 *         <p>
 *         An instance of the HouseService, RoomieService and UserService
 *         classes are declared and instantiated so that data can be received
 *         from the DAO's. The @Autowired annotation is used which marks a
 *         method as to be autowired by Spring's dependency injection
 *         facilities. This method is autowired with a matching bean in the
 *         Spring container.
 *         <p>
 * @RequestMapping is annotation for mapping web requests onto specific handler
 *                 methods. These methods return a string which represents a
 *                 view.
 *                 <p>
 *                 The showLogin method returns a string which displays a login
 *                 form.
 *                 <p>
 *                 The showLoggedOut method returns a string which displays a
 *                 page notifying the user that they have been logged out.
 *                 <p>
 *                 The accessDenied method returns a string which displays a
 *                 page notifying the user that they cannot access this page.
 *                 <p>
 *                 The admin method's main responsibility is to display all the
 *                 data for all administrators. This method takes a model as a
 *                 parameter which allows for adding attributes to the model. A
 *                 list of all users and houses are retrieved from the
 *                 houseService and the usersService. These lists are then added
 *                 to the model.
 *                 <p>
 *                 The showCreateAccount method's main responsibility is to
 *                 display the form for users to create a new account. This
 *                 method takes a model as a parameter which allows for adding
 *                 attributes to the model. A user object is declared and added
 *                 to the model.
 *                 <p>
 *                 The createAccount method's main responsibility is to actually
 *                 create the account. This method takes a model as a parameter
 *                 which allows for adding attributes to the model.
 *                 The @Validated annotation is used which supports the
 *                 specification of validation groups. A user object and the
 *                 BindingResult is sent from the form. If the BindingResult has
 *                 errors present then the form is displayed again with the
 *                 errors. Validation is performed to check if the username
 *                 already exists. All attributes for the user are set. If they
 *                 uploaded an image this file is saved in a folder. The object
 *                 is then saved in the database.
 *                 <p>
 *                 The showUser method's main responsibility is to view a
 *                 particular user. This method takes a model as a parameter
 *                 which allows for adding attributes to the model. It also
 *                 takes the users username as a parameter and uses the
 *                 userService to retrieve the information. It also checks if
 *                 the user has a house and this is added to the model. A
 *                 message object is instantiated and added to the model so
 *                 another user can message the user.
 *                 <p>
 *                 The deleteUser method's main responsibility is to delete a
 *                 particular user from the database. It takes the users
 *                 username as a parameter and uses the userService to delete
 *                 the user entity from the database.
 * 
 */
@Controller
public class UserController {

	// declare and instantiate the service classes so that you can access the
	// methods
	private UsersService usersService;
	private HouseService houseService;
	private RoomieService roomieService;

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

	@RequestMapping("/login")
	public String showLogin() {
		// display the login page
		return "login";
	}

	@RequestMapping("/loggedout")
	public String showLoggedOut() {
		// display the logged out page
		return "loggedout";
	}

	@RequestMapping("/denied")
	public String accessDenied() {
		// display the access denied page
		return "denied";
	}

	@RequestMapping("/admin")
	public String admin(Model model) {
		// get a list of all users and add to the model
		List<User> users = usersService.getAllUsers();
		model.addAttribute("users", users);

		// get a list of all houses and add to the model
		List<House> houses = houseService.getAllHouses();
		model.addAttribute("houses", houses);

		return "admin";
	}

	@RequestMapping("/newaccount")
	public String showCreateAccount(Model model) {
		// new user object added so a user can register
		model.addAttribute("user", new User());

		return "newaccount";
	}

	@RequestMapping(value = "/createaccount", method = RequestMethod.POST)
	public String createAccount(@Validated(FormValidationGroup.class) User user, BindingResult result,
			@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
		if (result.hasErrors()) {
			return "newaccount";
		}
		// set the users roles
		user.setAuthority("ROLE_USER");
		user.setEnabled(true);

		// check if the username already exists
		if (usersService.exists(user.getUsername())) {
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}

		if (!file.isEmpty()) {
			String username = user.getUsername();
			// read in the file
			BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
			// set the files destination
			File destination = new File(
					"\\C:\\Users\\Jamie\\workspace\\FYP\\WebContent\\resources\\images\\profilepictures\\" + username
							+ ".png");
			// write the file to the destination
			ImageIO.write(src, "png", destination);

		}

		// attempt to register the user
		try {
			usersService.create(user);
		} catch (DuplicateKeyException e) {
			result.rejectValue("username", "DuplicateKey.user.username");
		}

		return "accountcreated";
	}

	@RequestMapping(value = "/editaccount", method = RequestMethod.POST)
	public String editAccount(User user, Principal principal) throws IOException {
		// attempt to update the user
		user.setAuthority("ROLE_USER");
		user.setEnabled(true);
		
		usersService.edit(user);

		return "accountcreated";
	}

	@RequestMapping("/user/{username}")
	public String showUser(@PathVariable String username, Model model, Principal principal) {
		// get the user object and add to the model
		User user = usersService.getUser(username);
		model.addAttribute("user", user);

		// check if the user has a house and add it to the model
		House house = null;
		if (username != null) {
			house = houseService.getHouse(username);
		}

		if (house == null) {
			house = new House();
		}

		model.addAttribute("house", house);

		// create a roomie object so a user can add them as their roomie
		Roomie roomie = new Roomie();
		roomie.setRoomie_username(username);
		model.addAttribute("roomie", roomie);

		// check if the users are already roomies
		boolean isFriends = false;
		isFriends = roomieService.exists(username, principal.getName());
		model.addAttribute("isFriends", isFriends);

		// create a new message object and add to the model
		Message message = new Message();
		message.setRecipient(user.getUsername());
		model.addAttribute("message", message);

		// check if the user has a house
		boolean hasHouse = false;
		if (principal != null) {
			hasHouse = houseService.hasHouse(user.getUsername());
		}
		model.addAttribute("hasHouse", hasHouse);

		// check if the user has uploaded a profile picture
		boolean hasImage = false;
		File imageFile = new File("\\C:\\Users\\Jamie\\workspace\\FYP\\WebContent\\resources\\images\\profilepictures\\"
				+ username + ".png");
		if (imageFile.exists() && !imageFile.isDirectory()) {
			hasImage = true;
		}
		model.addAttribute("hasImage", hasImage);

		// check if the user has uploaded an image for their house
		boolean hasHouseImage = false;
		File houseFile = new File("\\C:\\Users\\Jamie\\workspace\\FYP\\WebContent\\resources\\images\\housepictures\\"
				+ house.getId() + ".png");
		if (houseFile.exists() && !houseFile.isDirectory()) {
			hasHouseImage = true;
		}
		model.addAttribute("hasHouseImage", hasHouseImage);

		return "user";

	}

	@RequestMapping("/deleteuser/{username}")
	public String deleteUser(@PathVariable String username) {
		// delete the user based on the username
		usersService.delete(username);

		return "userdeleted";

	}

}
