package com.jamie.spring.web.service;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jamie.spring.web.dao.Message;
import com.jamie.spring.web.dao.MessageDao;
import com.jamie.spring.web.dao.Roomie;

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
@Service("messageService")
public class MessageService {

	private MessageDao messageDAO;

	@Autowired
	public void setMessageDAO(MessageDao messageDAO) {
		this.messageDAO = messageDAO;
	}

	/**
	 * Calls the DAO class to save or update a message
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void saveOrUpdate(Message message) throws IOException {
		messageDAO.saveOrUpdate(message);
	}

	/**
	 * Calls the DAO class to get a list of messages sent by users
	 * 
	 * @param username
	 * @param myusername
	 * @return
	 */
	public List<Message> getMessagesSent(String username, String myusername) {
		List<Message> messages = messageDAO.getMessagesSent(username, myusername);

		return messages;
	}

	/**
	 * Calls the DAO class to get a list of messages received by users
	 * 
	 * @param username
	 * @param myusername
	 * @return
	 */
	public List<Message> getMessagesReceived(String username, String myusername) {
		List<Message> messages = messageDAO.getMessagesReceived(username, myusername);

		return messages;
	}

	/**
	 * Calls the DAO class to check if a message receives to a user
	 * 
	 * @param recipient
	 * @return
	 */
	public boolean exists(String recipient) {
		return messageDAO.exists(recipient);
	}

	/**
	 * Calls the DAO class to get all messages for a user
	 * 
	 * @param username
	 * @return
	 */
	public List<Message> getAllMessages(String username) {
		List<Message> messages = messageDAO.getAllMessages(username);

		return messages;
	}

	/**
	 * Calls the DAO class to get all messages sent by a user
	 * 
	 * @param username
	 * @return
	 */
	public List<Message> getAllMessagesSent(String username) {
		List<Message> messages = messageDAO.getAllMessagesSent(username);

		return messages;
	}

}
