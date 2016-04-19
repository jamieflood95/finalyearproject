package com.jamie.spring.web.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jamie.spring.web.dao.House;
import com.jamie.spring.web.dao.User;
import com.jamie.spring.web.service.HouseService;
import com.jamie.spring.web.service.UsersService;

/**
 * 
 * @author Jamie
 *         <p>
 *         This class is a controller for any tasks that take place on the home
 *         page. These tasks include: displaying the home page, searching for an
 *         address and an advanced search.
 *         <p>
 *         The @Controller annotation indicates that a particular class serves
 *         the role of a controller. A controller generates an output view.
 *         <p>
 *         An instance of the HouseService class is declared and instantiated so
 *         that data can be received from the DAO's. The @Autowired annotation
 *         is used which marks a method as to be autowired by Spring's
 *         dependency injection facilities. This method is autowired with a
 *         matching bean in the Spring container.
 *         <p>
 * @RequestMapping is annotation for mapping web requests onto specific handler
 *                 methods. These methods return a string which represents a
 *                 view.
 *                 <p>
 *                 The showHome method's main responsibility is to display all
 *                 the data on the home page. This method takes a model as a
 *                 parameter which allows for adding attributes to the model.
 *                 Two lists of all recent houses, called house and houses, are
 *                 retrieved from the houseService and this list is then added
 *                 to the model. The home page is then displayed.
 *                 <p>
 *                 The search method's main responsibility is to search for a
 *                 particular address and display all the data relating to this
 *                 address. This method takes a model as a parameter which
 *                 allows for adding attributes to the model. It also takes the
 *                 address that the user has entered as a parameter and uses the
 *                 houseService to check if this house address exists. This
 *                 attribute is then added to the model and the results are
 *                 displayed on the search page.
 *                 <p>
 *                 The searchRoomie method's main responsibility is to search
 *                 for a particular user and display all the data relating to
 *                 this user. This method takes a model as a parameter which
 *                 allows for adding attributes to the model. It also takes the
 *                 username that the user has entered as a parameter and uses
 *                 the houseService to check if this user exists. This attribute
 *                 is then added to the model and the results are displayed on
 *                 the search page.
 *                 <p>
 *                 The searchrent method's main responsibility is to advance
 *                 search for potential addresses and display all the data
 *                 relating to these addresses. This method takes a model as a
 *                 parameter which allows for adding attributes to the model. It
 *                 also takes the address, minimum rent, maximum rent, minimum
 *                 number of rooms, and maximum number of rooms that the user
 *                 has entered as parameters and uses the houseService to check
 *                 if there are any results. These results are then added to the
 *                 model and displayed on the search page. If any of the
 *                 parameters passed are null then an error page is diplayed.
 *                 <p>
 *                 <p>
 *                 The showUpload method's main responsibility is to display the
 *                 page so the user can upload a picture. It returns a string
 *                 value which displays the upload jsp.
 *                 <p>
 *                 The handleFormUpload method's main responsibility is to
 *                 handle the file that the user has uploaded from the form. The
 *                 file is validated to make sure it isn't empty and then the
 *                 path where it is to be stored is declared. The file is
 *                 written to this location and the house page is displayed.
 */
@Controller
public class HomeController {

	// declare and instantiate the service classes so that you can access the
	// methods
	private HouseService houseService;
	private UsersService userService;

	@Autowired
	public void setHouseService(HouseService houseService) {

		this.houseService = houseService;
	}

	@Autowired
	public void setUserService(UsersService userService) {

		this.userService = userService;
	}

	@RequestMapping("/")
	public String showHome(Model model) {
		// get two lists of the most recent houses and add them to the model
		List<House> house = houseService.getRecent();
		model.addAttribute("house", house);

		List<House> houses = houseService.getRecent();
		model.addAttribute("houses", houses);

		return "home";
	}

	@RequestMapping(value = "/search")
	public String search(@RequestParam("searchString") String address, Model model) {
		// search for the address that the user has entered and return the
		// results
		List<House> house = houseService.getHouseAddress(address);
		model.addAttribute("house", house);

		return "search";
	}

	@RequestMapping(value = "/searchroomie")
	public String searchRoomie(@RequestParam("searchString") String username, Model model) {
		// search for the user that the user has entered and return the
		// results
		List<User> users = userService.getUserSearch(username);
		model.addAttribute("users", users);

		return "searchroomie";
	}

	@RequestMapping(value = "/searchrent")
	public String searchrent(@RequestParam("searchAddress") String address,
			@RequestParam("searchMinRent") Integer minrent, @RequestParam("searchMaxRent") Integer maxrent,
			@RequestParam("searchMinRooms") Integer minrooms, @RequestParam("searchMaxRooms") Integer maxrooms,
			Model model) {
		// rent or room variables cannot be null
		if (minrent == null || maxrent == null || minrooms == null || maxrooms == null) {
			return "searcherror";
		} else {
			// return the results of the advanced house search in a list
			List<House> house = houseService.getHouseSearch(address, minrent, maxrent, minrooms, maxrooms);
			model.addAttribute("house", house);

			return "search";
		}
	}

	@RequestMapping("/showupload")
	public String showUpload(Model model) {
		// display the upload form
		return "upload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String handleFormUpload(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
		if (!file.isEmpty()) {
			String username = principal.getName();
			// read in the file
			BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
			// set the files destination
			File destination = new File(
					"\\C:\\Users\\Jamie\\workspace\\FYP\\WebContent\\resources\\images\\profilepictures\\" + username
							+ ".png");
			// write the file to the destination
			ImageIO.write(src, "png", destination);

		}
		return "accountcreated";
	}
}
