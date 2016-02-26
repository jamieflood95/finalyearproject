package com.jamie.spring.web.service;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jamie.spring.web.dao.Message;
import com.jamie.spring.web.dao.MessageDao;
import com.jamie.spring.web.dao.Roomie;

@Service("messageService")
public class MessageService {

	private MessageDao messageDAO;

	@Autowired
	public void setMessageDAO(MessageDao messageDAO) {
		this.messageDAO = messageDAO;
	}

	public void saveOrUpdate(Message message) throws IOException {
		messageDAO.saveOrUpdate(message);
	}

	public List<Message> getMessagesSent(String username, String myusername) {
		List<Message> messages = messageDAO.getMessagesSent(username, myusername);

		return messages;
	}

	public List<Message> getMessagesReceived(String username, String myusername) {
		List<Message> messages = messageDAO.getMessagesReceived(username, myusername);

		return messages;
	}

	public boolean exists(String recipient) {
		return messageDAO.exists(recipient);
	}

	public List<Message> getAllMessages(String username) {
		List<Message> messages = messageDAO.getAllMessages(username);

		return messages;
	}
	
	public List<Message> getAllMessagesSent(String username) {
		List<Message> messages = messageDAO.getAllMessagesSent(username);

		return messages;
	}

}
