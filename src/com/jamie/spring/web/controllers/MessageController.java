package com.jamie.spring.web.controllers;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jamie.spring.web.dao.FormValidationGroup;
import com.jamie.spring.web.dao.Message;
import com.jamie.spring.web.service.MessageService;
import com.jamie.spring.web.service.UsersService;

@Controller
public class MessageController {

	private MessageService messageService;
	private UsersService usersService;

	@Autowired
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	@RequestMapping(value = "/newmessage")
	public String newMessage(Model model, Principal principal) {
		Message message = new Message();

		model.addAttribute("message", message);

		return "newmessage";

	}

	@RequestMapping(value = "/sendmessage", method = RequestMethod.POST)
	public String sendMessage(@Validated(FormValidationGroup.class) Message message, Principal principal, Model model)
			throws IOException {

		String username = principal.getName();
		message.getUser().setUsername(username);

		DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
		Date send_date = new Date();
		message.setSend_date(send_date);

		if (!usersService.exists(message.getRecipient())) {
			model.addAttribute("error", "User doesn't exist");
			return "newmessage";
		}

		if (message.getRecipient().equals(username)) {
			System.out.println("Cannot send to yourself");
			model.addAttribute("error", "You cannot send a message to yourself");
			return "newmessage";
		} else {
			messageService.saveOrUpdate(message);
			return "messagesent";
		}

	}

	@RequestMapping(value = "/messages")
	public String messages(Model model, Principal principal) {

		String username = principal.getName();

		List<Message> messagessent = messageService.getAllMessagesSent(username);
		List<Message> messagesreceived = messageService.getAllMessages(username);

		final ArrayList<String> mylist = new ArrayList<String>();
		final ArrayList<String> mysent = new ArrayList<String>();
		
		Collections.sort(messagesreceived, new Comparator<Message>() {
			public int compare(Message m1, Message m2) {
				return m2.getSend_date().compareTo(m1.getSend_date());
			}
		});
		
		Collections.sort(messagessent, new Comparator<Message>() {
			public int compare(Message m1, Message m2) {
				return m2.getSend_date().compareTo(m1.getSend_date());
			}
		});

		for (int i = 0; i < messagesreceived.size(); i++) {
			mylist.add(messagesreceived.get(i).getUsername());
		}
		
		for (int i = 0; i < messagessent.size(); i++) {
			mysent.add(messagessent.get(i).getRecipient());
		}
		
		final ArrayList<String> main = new ArrayList<String>();
		main.addAll(mylist);
		main.addAll(mysent);
		
		Set<String> foo = new HashSet<String>(main);
		
		model.addAttribute("foo", foo);

		return "messages";

	}

	@RequestMapping("/message/{username}")
	public String showUser(@PathVariable String username, Model model, Principal principal) throws IOException {

		String myusername = principal.getName();

		List<Message> mymessages = messageService.getMessagesReceived(username, myusername);
		List<Message> usermessages = messageService.getMessagesSent(username, myusername);

		List<Message> messages = new ArrayList<Message>();
		messages.addAll(usermessages);
		messages.addAll(mymessages);

		Collections.sort(messages, new Comparator<Message>() {
			public int compare(Message m1, Message m2) {
				return m1.getSend_date().compareTo(m2.getSend_date());
			}
		});

		model.addAttribute("messages", messages);

		Message message = new Message();

		message.setRecipient(username);

		model.addAttribute("message", message);

		model.addAttribute("recipient", username);

		return "message";
	}

}
