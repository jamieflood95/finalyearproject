package com.jamie.spring.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jamie.spring.web.dao.House;
import com.jamie.spring.web.service.HouseService;

@Controller
public class HomeController {

	private HouseService houseService;

	@Autowired
	public void setHouseService(HouseService houseService) {

		this.houseService = houseService;
	}

	@RequestMapping("/")
	public String showHome(Model model) {
		List<House> house = houseService.getRecent();

		model.addAttribute("house", house);

		List<House> houses = houseService.getRecent();

		model.addAttribute("houses", houses);

		return "home";
	}

	@RequestMapping(value = "/search")
	public String Search(@RequestParam("searchString") String address , Model model) {

		List<House> house = houseService.getHouseAddress(address);

		model.addAttribute("house", house);

		return "search";
	}

	@RequestMapping(value = "/searchrent")
	public String Searchrent(@RequestParam("searchAddress") String address, @RequestParam("searchMinRent") Integer minrent, @RequestParam("searchMaxRent") Integer maxrent, @RequestParam("searchMinRooms") Integer minrooms, @RequestParam("searchMaxRooms") Integer maxrooms
			, Model model) {
		
		if(minrent == null || maxrent == null || minrooms == null || maxrooms == null) {
			return "searcherror";
		}else {

		List<House> house = houseService.getHouseSearch(address, minrent, maxrent, minrooms, maxrooms);

		model.addAttribute("house", house);

		return "search";
		}
	}

}
