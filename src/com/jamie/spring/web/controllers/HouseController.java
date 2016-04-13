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
import com.jamie.spring.web.dao.User;
import com.jamie.spring.web.service.CommentService;
import com.jamie.spring.web.service.HouseService;
import com.jamie.spring.web.service.UsersService;

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
 *         An instance of the HouseService class is declared and instantiated so
 *         that data can be received from the DAO's. The @Autowired annotation
 *         is used which marks a method as to be autowired by Spring's
 *         dependency injection facilities. This method is autowired with a
 *         matching bean in the Spring container.
 *         <p>
 * 		   @RequestMapping is annotation for mapping web requests onto specific handler
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
 *                 to retrieve the house information. The result is then added
 *                 to the model and displayed on the house page.
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
 */
@Controller
public class HouseController {

	private HouseService houseService;

	@Autowired
	public void setHouseService(HouseService houseService) {
		this.houseService = houseService;
	}
	
	private CommentService commentService;

	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
	private UsersService usersService;

	@Autowired
	public void setUserService(UsersService usersService) {
		this.usersService = usersService;
	}


	@RequestMapping("/houses")
	public String showHouses(Model model, Principal principal) throws IOException {

		List<House> houses = houseService.getCurrent();

		model.addAttribute("houses", houses);

		boolean hasHouse = false;

		if (principal != null) {
			hasHouse = houseService.hasHouse(principal.getName());
		}

		model.addAttribute("hasHouse", hasHouse);

		return "houses";
	}

	@RequestMapping("/house/{id}")
	public String showHouse(@PathVariable int id, Model model) {
		House house = houseService.getHouse(id);

		model.addAttribute("house", house);

		Message message = new Message();

		message.setRecipient(house.getUsername());

		model.addAttribute("message", message);
		
		boolean hasHouse = false;
		
		File f = new File("\\C:\\Users\\Jamie\\workspace\\FYP\\WebContent\\resources\\images\\housepictures\\" + house.getId() + ".png");
		if(f.exists() && !f.isDirectory()) { 
		    hasHouse = true;
		}
		
		model.addAttribute("hasHouse", hasHouse);
		
		List<Comment> comments = commentService.getComments(id);
		
//		for(int i=0; i<comments.size(); i++) {
//			if(comments.get(i).getId()!=id) {
//				comments.remove(i);
//			}
//		}
		
		model.addAttribute("comments", comments);
		
		Comment comment = new Comment();
		
		model.addAttribute("comment",  comment);

		return "house";

	}
	
	@RequestMapping(value = "/createcomment/{house_id}", method = RequestMethod.POST)
	public String sendMessage(@Validated(FormValidationGroup.class) Comment comment, @PathVariable int house_id, Principal principal, Model model)
			throws IOException {

		String username = principal.getName();
		comment.getUser().setUsername(username);
		
		comment.getHouse().setId(house_id);

		Date date = new Date();
		comment.setDate(date);

		commentService.saveOrUpdate(comment);
			
		return "accountcreated";
		

	}

	@RequestMapping("/deletehouse/{id}")
	public String deleteHouse(@PathVariable int id, Model model) {
		houseService.delete(id);
		
		boolean houseDeleted = true;
		model.addAttribute("houseDeleted",  houseDeleted);

		return "housedeleted";

	}

	@RequestMapping("map")
	public String showMap(Model model) throws IOException {
		List<House> houses = houseService.getCurrent();

		model.addAttribute("houses", houses);

		return "map";
	}

	@RequestMapping("/createhouse")
	public String createHouse(Model model, Principal principal) {

		House house = null;

		if (principal != null) {
			String username = principal.getName();

			house = houseService.getHouse(username);
		}

		if (house == null) {
			house = new House();
		}

		model.addAttribute("house", house);

		return "createhouse";
	}

	@RequestMapping(value = "/docreate", method = RequestMethod.POST)
	public String doCreate(Model model, @Validated(value = FormValidationGroup.class) House house, BindingResult result,
			Principal principal, @RequestParam(value = "delete", required = false) String delete) throws IOException {
		if (result.hasErrors()) {
			return "createhouse";
		}

		if (delete == null) {
			try {
				String username = principal.getName();
				house.setUsername(username);

				String addressText = house.getAddress();
				addressText = addressText.replace(".", "");
				addressText = addressText.replace(" ", "+");
				String geo = "https://maps.googleapis.com/maps/api/geocode/xml?address=" + addressText
						+ "&region=irl&key=AIzaSyCQeiWnMEZkkcRi8HhhhgI3zOat2t6ztPw";
				Document doc2 = Jsoup.connect(geo).get();
				Elements elt = doc2.getElementsByTag("location");

				String[] str_array = elt.get(0).text().split(" ");
				house.setLat(str_array[0]);
				house.setLng(str_array[1]);

				houseService.saveOrUpdate(house);

				return "redirect:" + "house/" + house.getId();
			} catch (Throwable t) {
				System.out.println("Error saving house");
				return "housenotfound";
			}

		} else {
			houseService.delete(house.getId());
			
			return "housedeleted";
		}
	}
	
	@RequestMapping("/showhouseupload")
	public String showUpload(Model model) {

		return "houseupload";
	}

	@RequestMapping(value = "/houseupload", method = RequestMethod.POST)
	public String handleFormUpload(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
		String username = principal.getName();
		House house = houseService.getHouse(username);
		if (!file.isEmpty()) {
			BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
			File destination = new File("\\C:\\Users\\Jamie\\workspace\\FYP\\WebContent\\resources\\images\\housepictures\\" + house.getId() + ".png");
			
			ImageIO.write(src, "png", destination);

		}
		return "redirect:" + "house/" + house.getId();
	}
}