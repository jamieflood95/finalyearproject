package com.jamie.spring.web.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jamie.spring.web.dao.House;
import com.jamie.spring.web.dao.Roomie;
import com.jamie.spring.web.dao.RoomieDao;

@Service("roomieService")
public class RoomieService {
	
	private RoomieDao roomieDAO;

	@Autowired
	public void setRoomieDAO(RoomieDao roomieDAO) {
		this.roomieDAO = roomieDAO;
	}

	public void saveOrUpdate(Roomie roomie) throws IOException {
		roomieDAO.saveOrUpdate(roomie);
		
	}

	public List<Roomie> getRoomies(String username) {
		List<Roomie> roomies = roomieDAO.getRoomies(username);

		return roomies;
	}
	
	public boolean exists(String roomie_username, String username) {
		return roomieDAO.exists(roomie_username, username);
	}

	public void delete(String currentUsername, String username) {
		roomieDAO.delete(currentUsername, username);
	}

}
