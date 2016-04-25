package com.jamie.spring.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jamie.spring.web.dao.Contact;
import com.jamie.spring.web.dao.ContactDao;
import com.jamie.spring.web.dao.House;

/**
 * 
 * @author Jamie
 *         <p>
 *         This is a service-layer class which handles the business logic of the
 *         application. The @Service annotation marks the class as a bean so it
 *         can be put into the application context.
 *         <p>
 *         The @Autowired annotation is used which marks a method as to be
 *         autowired by Spring's dependency injection facilities. This method is
 *         autowired with a matching bean in the Spring container.
 *
 */
@Service("contactService")
public class ContactService {

	private ContactDao contactDAO;

	@Autowired
	public void setContactDAO(ContactDao contactDAO) {
		this.contactDAO = contactDAO;
	}

	/**
	 * Calls the DAO class to get a list of contacts for a user
	 * 
	 * @param username
	 * @return
	 */
	public List<Contact> getContacts(String username) {
		List<Contact> contacts = contactDAO.getContacts(username);

		return contacts;
	}

	/**
	 * Calls the DAO class to save a contact
	 * 
	 * @param contact
	 */
	public void saveOrUpdate(Contact contact) {
		contactDAO.saveOrUpdate(contact);
	}

	/**
	 * Calls the DAO class to get a contact based on its id
	 * 
	 * @param id
	 * @return
	 */
	public Contact getContact(int id) {
		Contact contact = contactDAO.getContact(id);

		return contact;
	}

}
