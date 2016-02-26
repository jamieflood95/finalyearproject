package com.jamie.spring.web.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

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

import com.jamie.spring.web.dao.FormValidationGroup;
import com.jamie.spring.web.dao.House;
import com.jamie.spring.web.dao.Message;
import com.jamie.spring.web.dao.User;
import com.jamie.spring.web.service.HouseService;

@Controller
public class HouseController {

	private HouseService houseService;

	@Autowired
	public void setHouseService(HouseService houseService) {
		this.houseService = houseService;
	}

	@RequestMapping("/houses")
	public String showHouses(Model model, Principal principal)
			throws IOException {

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

		return "house";

	}

	@RequestMapping("/deletehouse/{id}")
	public String deleteHouse(@PathVariable int id) {
		houseService.delete(id);

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
	public String doCreate(Model model,
			@Validated(value = FormValidationGroup.class) House house,
			BindingResult result, Principal principal,
			@RequestParam(value = "delete", required = false) String delete)
			throws IOException {
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
				String geo = "https://maps.googleapis.com/maps/api/geocode/xml?address="
						+ addressText
						+ "&region=irl&key=AIzaSyCQeiWnMEZkkcRi8HhhhgI3zOat2t6ztPw";
				Document doc2 = Jsoup.connect(geo).get();
				Elements elt = doc2.getElementsByTag("location");

				String[] str_array = elt.get(0).text().split(" ");
				house.setLat(str_array[0]);
				house.setLng(str_array[1]);

				houseService.saveOrUpdate(house);

				return "housecreated";
			} catch (Throwable t) {
				System.out.println("Error saving house");
				return "housenotfound";
			}

		} else {
			houseService.delete(house.getId());
			return "housedeleted";
		}
	}
}