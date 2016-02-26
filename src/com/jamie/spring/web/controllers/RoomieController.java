package com.jamie.spring.web.controllers;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jamie.spring.web.dao.FormValidationGroup;
import com.jamie.spring.web.dao.Message;
import com.jamie.spring.web.dao.Roomie;
import com.jamie.spring.web.dao.User;
import com.jamie.spring.web.service.HouseService;
import com.jamie.spring.web.service.MessageService;
import com.jamie.spring.web.service.RoomieService;
import com.jamie.spring.web.service.UsersService;

@Controller
public class RoomieController {

	private RoomieService roomieService;
	private MessageService messageService;
	private HouseService houseService;

	@Autowired
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	@Autowired
	public void setRoomieService(RoomieService roomieService) {
		this.roomieService = roomieService;
	}

	@Autowired
	public void setHouseService(HouseService houseService) {
		this.houseService = houseService;
	}

	@RequestMapping(value = "/addRoomie", method = RequestMethod.POST)
	public String addRoomie(Principal principal, @Validated(value = FormValidationGroup.class) Roomie roomie)
			throws IOException {

		Message message = new Message();

		String currentUsername = principal.getName();
		
		try {
			houseService.deleteUsername(currentUsername);
		} catch (Error e) {
			System.out.println("Cannot delete house");
		}

		if (currentUsername.equals(roomie.getRoomie_username())) {
			return "cannotadd";
		} else {
			roomie.getUser().setUsername(currentUsername);

			if (roomieService.exists(roomie.getRoomie_username(), currentUsername)) {

				return "cannotadd";
			} else {

				message.setRecipient(roomie.getRoomie_username());
				message.setText(currentUsername
						+ " has sent a request to be your roomie. Please go onto his page to connect with him.");
				message.getUser().setUsername(currentUsername);

				DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
				Date send_date = new Date();
				message.setSend_date(send_date);

				messageService.saveOrUpdate(message);

				roomieService.saveOrUpdate(roomie);

				return "roomieadded";
			}

		}

	}

	@RequestMapping("/deleteroomie/{username}")
	public String deleteUser(@PathVariable String username, Model model, Principal principal) {
		String currentUsername = principal.getName();

		roomieService.delete(currentUsername, username);

		model.addAttribute("roomie", new Roomie());
		return "home";

	}

}
