package com.jamie.spring.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jamie.spring.web.dao.Contact;
import com.jamie.spring.web.dao.ContactDao;
import com.jamie.spring.web.dao.House;

@Service("contactService")
public class ContactService {

	private ContactDao contactDAO;

	@Autowired
	public void setContactDAO(ContactDao contactDAO) {
		this.contactDAO = contactDAO;
	}

	public List<Contact> getContacts(String username) {
		List<Contact> contacts = contactDAO.getContacts(username);

		return contacts;
	}

	public void saveOrUpdate(Contact contact) {
		contactDAO.saveOrUpdate(contact);		
	}

	public Contact getContact(int id) {
		Contact contact = contactDAO.getContact(id);
		
		return contact;
	}

}
