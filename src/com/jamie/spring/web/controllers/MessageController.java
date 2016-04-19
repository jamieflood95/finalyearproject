package com.jamie.spring.web.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jamie.spring.web.dao.FormValidationGroup;
import com.jamie.spring.web.dao.Message;
import com.jamie.spring.web.service.MessageService;
import com.jamie.spring.web.service.UsersService;

/**
 * 
 * @author Jamie
 *         <p>
 *         This class is a controller for any tasks that take place for
 *         messages. These tasks include: sending a message, viewing all
 *         messages and viewing a specific conversation.
 *         <p>
 *         The @Controller annotation indicates that a particular class serves
 *         the role of a controller. A controller generates an output view.
 *         <p>
 *         An instance of the MessageService and UserService classes are
 *         declared and instantiated so that data can be received from the
 *         DAO's. The @Autowired annotation is used which marks a method as to
 *         be autowired by Spring's dependency injection facilities. These
 *         methods are autowired with a matching bean in the Spring container.
 *         <p>
 * @RequestMapping is annotation for mapping web requests onto specific handler
 *                 methods. These methods return a string which represents a
 *                 view.
 *                 <p>
 *                 The newMessage method's main responsibility is to display the
 *                 form for users to create a new message. This method takes a
 *                 model as a parameter which allows for adding attributes to
 *                 the model. A new message object is created and added to the
 *                 model. The form page is then returned.
 *                 <p>
 *                 The sendMessage method's main responsibility is to actually
 *                 send the message. This method takes a model as a parameter
 *                 which allows for adding attributes to the model.
 *                 The @Validated annotation is used which supports the
 *                 specification of validation groups. A message object is sent
 *                 from the form. The logged in username and current date are
 *                 then applied to this object. The userService checks if the
 *                 user actually exists before sending. It also makes sure that
 *                 the user isn't trying to send it to themselves. If all these
 *                 tests pass then the messageService use the saveOrUpdate
 *                 method to persist the data.
 *                 <p>
 *                 The messages method's main responsibility is to display all
 *                 messages for a user. This method takes a model as a parameter
 *                 which allows for adding attributes to the model. A list of
 *                 all messages sent and received are retrieved from the
 *                 messageService. These lists are then sorted and the usernames
 *                 are added into two new arraylists. These two arraylists are
 *                 then added into one main arraylist which is then converted to
 *                 a HashSet so each value will be unique. This HashSet is then
 *                 added to the model. The messages view is then displayed.
 *                 <p>
 *                 The showConversation method's main responsibility is to view
 *                 a conversation between two users. This method takes a model
 *                 as a parameter which allows for adding attributes to the
 *                 model. It also takes the users username as a parameter and
 *                 uses the messageService to retrieve both messages sent and
 *                 received between the two users. These are then sorted and
 *                 added to the model. A messages object is also created so that
 *                 the users can chat in the conversation page.
 */
@Controller
public class MessageController {

	// declare and instantiate the service classes so that you can access the
	// methods
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
		// create a new message object for the form page
		Message message = new Message();

		model.addAttribute("message", message);

		return "newmessage";

	}

	@RequestMapping(value = "/sendmessage", method = RequestMethod.POST)
	public String sendMessage(@Validated(FormValidationGroup.class) Message message, BindingResult result, Principal principal, Model model)
			throws IOException {

		if(result.hasErrors()) {
			return "newmessage";
		}
		
		// get the logged in user and assign this user to the message
		String username = principal.getName();
		message.getUser().setUsername(username);
		// set the date of the message
		Date send_date = new Date();
		message.setSend_date(send_date);

		// check if the user exists
		if (!usersService.exists(message.getRecipient())) {
			model.addAttribute("error", "User doesn't exist");
			return "newmessage";
		}

		// check if the user is not trying to send the message to themselves
		if (message.getRecipient().equals(username)) {
			model.addAttribute("error", "You cannot send a message to yourself");
			return "newmessage";
		} else {
			// save the message
			messageService.saveOrUpdate(message);
			return "redirect:" + "message/" + message.getRecipient();
		}

	}

	@RequestMapping(value = "/messages")
	public String messages(Model model, Principal principal) {

		String username = principal.getName();
		// get all messages sent and received by the logged in user
		List<Message> messagessent = messageService.getAllMessagesSent(username);
		List<Message> messagesreceived = messageService.getAllMessages(username);

		// declare two ArrayLists that usernames will be added to
		final ArrayList<String> myreceived = new ArrayList<String>();
		final ArrayList<String> mysent = new ArrayList<String>();

		// sort the list by newest
		Collections.sort(messagesreceived, new Comparator<Message>() {
			public int compare(Message m1, Message m2) {
				return m2.getSend_date().compareTo(m1.getSend_date());
			}
		});

		// sort the list by newest
		Collections.sort(messagessent, new Comparator<Message>() {
			public int compare(Message m1, Message m2) {
				return m2.getSend_date().compareTo(m1.getSend_date());
			}
		});

		// add all usernames of the messages to the arraylist
		for (int i = 0; i < messagesreceived.size(); i++) {
			myreceived.add(messagesreceived.get(i).getUsername());
		}

		// add all usernames of the messages to the arraylist
		for (int i = 0; i < messagessent.size(); i++) {
			mysent.add(messagessent.get(i).getRecipient());
		}

		// declare an arraylist where the myreceieved and mysent arraylists will
		// be added to
		final ArrayList<String> main = new ArrayList<String>();
		main.addAll(myreceived);
		main.addAll(mysent);
		// declare a HashSet that will make sure that a string will only appear
		// once
		Set<String> foo = new HashSet<String>(main);

		// add the hashset to the model
		model.addAttribute("foo", foo);

		return "messages";

	}

	@RequestMapping("/message/{username}")
	public String showConversation(@PathVariable String username, Model model, Principal principal) throws IOException {

		// the logged in user
		String myusername = principal.getName();

		// get the messages sent and received between the two users
		List<Message> mymessages = messageService.getMessagesReceived(username, myusername);
		List<Message> usermessages = messageService.getMessagesSent(username, myusername);

		// add these two arraylists to the messages arraylist
		List<Message> messages = new ArrayList<Message>();
		messages.addAll(usermessages);
		messages.addAll(mymessages);

		// sort the list by date
		Collections.sort(messages, new Comparator<Message>() {
			public int compare(Message m1, Message m2) {
				return m1.getSend_date().compareTo(m2.getSend_date());
			}
		});

		// add the arraylist to the model
		model.addAttribute("messages", messages);

		// create a new message object so the user can send another message
		Message message = new Message();
		message.setRecipient(username);
		model.addAttribute("message", message);

		// add the recipient of the message to the model
		model.addAttribute("recipient", username);

		return "message";
	}

}
