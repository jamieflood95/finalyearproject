package com.jamie.spring.web.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jamie.spring.web.dao.Contact;
import com.jamie.spring.web.dao.FormValidationGroup;
import com.jamie.spring.web.dao.Roomie;
import com.jamie.spring.web.service.ContactService;
import com.jamie.spring.web.service.RoomieService;

/**
 * 
 * @author Jamie
 *         <p>
 *         This class is a controller for any tasks that take place for tasks.
 *         These include: showing all contacts, creating a contact and showing
 *         an individual contact
 *         <p>
 *         The @Controller annotation indicates that a particular class serves
 *         the role of a controller. A controller generates an output view.
 *         <p>
 *         An instance of the RoomieService and ContactService classes are
 *         declared and instantiated so that data can be received from the
 *         DAO's. The @Autowired annotation is used which marks a method as to
 *         be autowired by Spring's dependency injection facilities. This method
 *         is autowired with a matching bean in the Spring container.
 *         <p>
 * @RequestMapping is annotation for mapping web requests onto specific handler
 *                 methods. These methods return a string which represents a
 *                 view.
 *                 <p>
 *                 The showContacts method's main responsibility is to display
 *                 all the data for all contacts. This method takes a model as a
 *                 parameter which allows for adding attributes to the model. A
 *                 Principal variable is also used to get a list of all contacts
 *                 and is retrieved from the contactService and this list is
 *                 then added to the model. Principal represents an entity. The
 *                 page is then displayed.
 *                 <p>
 *                 The showContact method's main responsibility is to view a
 *                 particular contact. This method takes a model as a parameter
 *                 which allows for adding attributes to the model. It also
 *                 takes the contact id as a parameter and uses the
 *                 contactService to retrieve the contact information.
 *                 <p>
 *                 The newContact method's main responsibility is to display the
 *                 form for users to create a new contact. This method takes a
 *                 model as a parameter which allows for adding attributes to
 *                 the model. A contact object is declared and added to the
 *                 model.
 *                 <p>
 *                 The createContact method's main responsibility is to actually
 *                 create the contact. This method takes a model as a parameter
 *                 which allows for adding attributes to the model.
 *                 The @Validated annotation is used which supports the
 *                 specification of validation groups. A contact object and the
 *                 BindingResult is sent from the form. If the BindingResult has
 *                 errors present then the form is displayed again with the
 *                 errors. If not then the contact is assigned to all of the
 *                 logged in users roomies and saved in the database.
 * 
 */
@Controller
public class ContactController {

	// declare and instantiate the service classes so that you can access the
	// methods
	private ContactService contactService;
	private RoomieService roomieService;

	@Autowired
	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	@Autowired
	public void setRoomieService(RoomieService roomieService) {
		this.roomieService = roomieService;
	}

	@RequestMapping("/contacts")
	public String showContacts(Model model, Principal principal) throws IOException {

		// get the username of the logged in username and get a list of all
		// contacts for this user
		String username = principal.getName();
		List<Contact> contacts = contactService.getContacts(username);

		// add the list to the model
		model.addAttribute("contacts", contacts);

		return "contacts";
	}

	@RequestMapping(value = "/newcontact")
	public String newContact(Model model, Principal principal) {

		// new contact object so user can assign attributes to it in the form
		model.addAttribute("contact", new Contact());

		return "newcontact";

	}

	@RequestMapping(value = "/createcontact", method = RequestMethod.POST)
	public String createContact(@Validated(FormValidationGroup.class) Contact contact, BindingResult result,
			Principal principal, Model model) throws IOException {

		if (result.hasErrors()) {

			return "newcontact";
		}

		// set the username of the contact
		String username = principal.getName();
		contact.getUser().setUsername(username);
		
		contactService.saveOrUpdate(contact);

		// get a list of all the users roomies
		List<Roomie> roomies = roomieService.getRoomies(username);
		// loop through this list and assign a contact to each of the users roomies
		for (int i = 0; i < roomies.size(); i++) {
			Roomie roomie = roomies.get(i);
			Contact c = new Contact(contact);
			c.getUser().setUsername(roomie.getRoomie_username());
			contactService.saveOrUpdate(c);
		}

		return "redirect:" + "contacts/" + contact.getId();
	}

	@RequestMapping("/contacts/{id}")
	public String showContact(@PathVariable int id, Model model) {
		// get a task based on the id and add it to the model
		Contact contact = contactService.getContact(id);
		model.addAttribute("contact", contact);

		return "contact";

	}
}
