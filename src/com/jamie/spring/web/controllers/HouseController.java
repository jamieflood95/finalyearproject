package com.jamie.spring.web.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jamie.spring.web.dao.Comment;
import com.jamie.spring.web.dao.FormValidationGroup;
import com.jamie.spring.web.dao.House;
import com.jamie.spring.web.dao.Message;
import com.jamie.spring.web.service.CommentService;
import com.jamie.spring.web.service.HouseService;

/**
 * 
 * @author Jamie
 *         <p>
 *         This class is a controller for any tasks that take place for houses.
 *         These tasks include: viewing all houses, viewing a specific house,
 *         deleting a specific house, displaying houses on a map and creating a
 *         new house.
 *         <p>
 *         The @Controller annotation indicates that a particular class serves
 *         the role of a controller. A controller generates an output view.
 *         <p>
 *         An instance of the HouseService, CommentService and UserService
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
 *                 The showHouses method's main responsibility is to display all
 *                 the data for all houses. This method takes a model as a
 *                 parameter which allows for adding attributes to the model. A
 *                 list of all houses called houses is retrieved from the
 *                 houseService and this list is then added to the model. A
 *                 Principal variable is also used to check if the logged in
 *                 user has a house already created. Principal represents an
 *                 entity. The home page is then displayed. The hasHouse method
 *                 in houseService is then called to check if the user has a
 *                 house which will enable them to edit or delete their current
 *                 house in the jsp page. The houses view is then displayed.
 *                 <p>
 *                 The showHouse method's main responsibility is to view a
 *                 particular house. This method takes a model as a parameter
 *                 which allows for adding attributes to the model. It also
 *                 takes the house id as a parameter and uses the houseService
 *                 to retrieve the house information. Message and comment objects
 *                 are instantiated and added to the model so a user can message
 *                 the owner or post a comment. A list of all comments on that
 *                 house is also retrieved from the database and displayed.
 *                 <p>
 *                 The createComment method's main responsibility is to create a
 *                 comment on a house's page. This method takes a model as a
 *                 parameter which allows for adding attributes to the model. It
 *                 also takes the house id as a parameter and uses the
 *                 houseService to retrieve the house information. A comment is
 *                 created based on the information the user has entered, the
 *                 username, house id and date. The comment is then saved and
 *                 the user is notified that the comment has been created.
 *                 <p>
 *                 The deleteHouse method's main responsibility is to delete a
 *                 particular house from the database. It takes the house id as
 *                 a parameter and uses the houseService to delete the house
 *                 entity from the database. The housedeleted page is then
 *                 displayed to inform the user that the house has been deleted.
 *                 <p>
 *                 The showMap method's main responsibility is to display all
 *                 the data for all houses on a map. This method takes a model
 *                 as a parameter which allows for adding attributes to the
 *                 model. A list of all houses called houses is retrieved from
 *                 the houseService and this list is then added to the model.
 *                 The map view is then displayed.
 *                 <p>
 *                 The createHouse method's main responsibility is to display
 *                 the form for users to create or edit their house. This method
 *                 takes a model as a parameter which allows for adding
 *                 attributes to the model. A house object is first declared as
 *                 null. A Principal variable is used to check if the logged in
 *                 user has a house already created. If it does, the house
 *                 details are displayed on the form for the user to edit. If
 *                 not, the form is displayed and the house object is added to
 *                 the model.
 *                 <p>
 *                 The doCreate method's main responsibility is to actually
 *                 create the house. This method takes a model as a parameter
 *                 which allows for adding attributes to the model.
 *                 The @Validated annotation is used which supports the
 *                 specification of validation groups. A house object and the
 *                 BindingResult is sent from the form. It also checks whether
 *                 the user wishes to save the house or delete from the jsp. If
 *                 the BindingResult has errors present then the form is
 *                 displayed again with the errors. If the user wishes to save
 *                 the house, the Google Maps API is used to check if the house
 *                 address actually exists. If it does then the LatLng
 *                 coordinates are set and the house is added to the database by
 *                 the houseService saveOrUpdate method and a "success" page is
 *                 displayed. Otherwise an error page is displayed. If the user
 *                 wishes to delete then the id of the house is retrieved from
 *                 the object and the houseService method for deletion is
 *                 called.
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
public class HouseController {

	// declare and instantiate the service classes so that you can access the
	// methods
	private HouseService houseService;
	private CommentService commentService;

	@Autowired
	public void setHouseService(HouseService houseService) {
		this.houseService = houseService;
	}

	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	@RequestMapping("/houses")
	public String showHouses(Model model, Principal principal) throws IOException {

		// get a list of all houses and add to the model
		List<House> houses = houseService.getAllHouses();
		model.addAttribute("houses", houses);

		boolean hasHouse = false;
		// check if the logged in user has a house
		if (principal != null) {
			hasHouse = houseService.hasHouse(principal.getName());
		}

		// add the boolean to the model
		model.addAttribute("hasHouse", hasHouse);

		return "houses";
	}

	@RequestMapping("/house/{id}")
	public String showHouse(@PathVariable int id, Model model) {
		// get an individual house based on its id
		House house = houseService.getHouse(id);
		model.addAttribute("house", house);

		// create a new message so anyone can message
		// the owner of the house
		Message message = new Message();
		message.setRecipient(house.getUsername());
		model.addAttribute("message", message);

		boolean hasHouse = false;
		// check if the owner has decided to upload an image for the house
		File houseFile = new File("\\C:\\Users\\Jamie\\workspace\\FYP\\WebContent\\resources\\images\\housepictures\\"
				+ house.getId() + ".png");
		if (houseFile.isFile()) {
			hasHouse = true;
		}

		// if true then an image exists
		model.addAttribute("hasHouse", hasHouse);

		// get all comments on this house page
		List<Comment> comments = commentService.getComments(id);
		model.addAttribute("comments", comments);

		// create a new comment so users can comment on
		// the house
		Comment comment = new Comment();
		model.addAttribute("comment", comment);

		return "house";

	}

	@RequestMapping(value = "/createcomment/{house_id}", method = RequestMethod.POST)
	public String createComment(@Validated(FormValidationGroup.class) Comment comment, @PathVariable int house_id,
			Principal principal, Model model) throws IOException {

		// get the logged in user
		String username = principal.getName();
		// set the user and house foreign keys for a comment
		comment.getUser().setUsername(username);
		comment.getHouse().setId(house_id);
		// set the date that the comment is posted
		Date date = new Date();
		comment.setDate(date);
		// save the comment
		commentService.saveOrUpdate(comment);

		return "commentsent";

	}

	@RequestMapping("/deletehouse/{id}")
	public String deleteHouse(@PathVariable int id, Model model) {
		// delete a house based on an id
		houseService.delete(id);

		return "housedeleted";

	}

	@RequestMapping("map")
	public String showMap(Model model) throws IOException {
		// get a list of all houses and add to the model
		List<House> houses = houseService.getAllHouses();
		model.addAttribute("houses", houses);

		return "map";
	}

	@RequestMapping("/createhouse")
	public String createHouse(Model model, Principal principal) {

		House house = null;

		// check if the user already has a house - then they
		// can edit it
		if (principal != null) {
			String username = principal.getName();
			house = houseService.getHouse(username);
		}

		// create a new house
		if (house == null) {
			house = new House();
		}
		// add the house object to the model
		model.addAttribute("house", house);

		return "createhouse";
	}

	@RequestMapping(value = "/docreate", method = RequestMethod.POST)
	public String doCreate(Model model, @Validated(value = FormValidationGroup.class) House house, BindingResult result,
			Principal principal, @RequestParam(value = "delete", required = false) String delete) throws IOException {
		if (result.hasErrors()) {
			return "createhouse";
		}

		// when the user doesn't want to delete the house
		if (delete == null) {
			try {
				// set the username of the house
				String username = principal.getName();
				house.setUsername(username);

				// get the address
				String addressText = house.getAddress();
				// format the string so the geocode data can be accessed from
				// the google api
				addressText = addressText.replace(".", "");
				addressText = addressText.replace(" ", "+");
				// scrape the data from the geo address
				String geo = "https://maps.googleapis.com/maps/api/geocode/xml?address=" + addressText
						+ "&region=irl&key=AIzaSyCQeiWnMEZkkcRi8HhhhgI3zOat2t6ztPw";
				Document doc2 = Jsoup.connect(geo).get();
				Elements elt = doc2.getElementsByTag("location");
				// get the longitude and latitude details from the result and
				// assign them to the house
				String[] str_array = elt.get(0).text().split(" ");
				house.setLat(str_array[0]);
				house.setLng(str_array[1]);

				// save the house
				houseService.saveOrUpdate(house);

				return "redirect:" + "house/" + house.getId();
			} catch (Throwable t) {
				// the house address cannot be found from google
				System.out.println("Error saving house");
				return "housenotfound";
			}

		} else {
			// delete the house
			houseService.delete(house.getId());

			return "housedeleted";
		}
	}

	@RequestMapping("/showhouseupload")
	public String showUpload() {
		// display the upload form
		return "houseupload";
	}

	@RequestMapping(value = "/houseupload", method = RequestMethod.POST)
	public String handleFormUpload(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
		// get the house of the logged in user
		String username = principal.getName();
		House house = houseService.getHouse(username);
		if (!file.isEmpty()) {
			// read in the file
			BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
			// set the files destination
			File destination = new File(
					"\\C:\\Users\\Jamie\\workspace\\FYP\\WebContent\\resources\\images\\housepictures\\" + house.getId()
							+ ".png");
			// write the file to the destination
			ImageIO.write(src, "png", destination);

		}
		return "redirect:" + "house/" + house.getId();
	}
}