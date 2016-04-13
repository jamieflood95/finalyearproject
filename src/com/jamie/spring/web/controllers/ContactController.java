package com.jamie.spring.web.controllers;

import java.io.IOException;
import java.security.Principal;
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
import com.jamie.spring.web.dao.Roomie;
import com.jamie.spring.web.service.ContactService;
import com.jamie.spring.web.service.RoomieService;

@Controller
public class ContactController {

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

		String username = principal.getName();

		List<Contact> contacts = contactService.getContacts(username);

		model.addAttribute("contacts", contacts);

		return "contacts";
	}

	@RequestMapping(value = "/newcontact")
	public String newContact(Model model, Principal principal) {
		Contact contact = new Contact();

		model.addAttribute("contact", contact);

		return "newcontact";

	}

	@RequestMapping(value = "/createcontact", method = RequestMethod.POST)
	public String createContact(@Validated(FormValidationGroup.class) Contact contact, Principal principal, Model model)
			throws IOException {

		String username = principal.getName();
		contact.getUser().setUsername(username);

		List<Roomie> roomies = roomieService.getRoomies(username);
	
		contactService.saveOrUpdate(contact);

		for (int i=0; i<roomies.size(); i++) {
			Roomie roomie = roomies.get(i);
			Contact c = new Contact(contact);
			c.getUser().setUsername(roomie.getRoomie_username());
			contactService.saveOrUpdate(c);
		}
		
		return "redirect:" + "contacts/" + contact.getId();
	}

	@RequestMapping("/contacts/{id}")
	public String showContact(@PathVariable int id, Model model) {
		Contact contact = contactService.getContact(id);

		model.addAttribute("contact", contact);

		return "contact";

	}
}
