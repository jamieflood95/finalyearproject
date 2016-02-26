package com.jamie.spring.web.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "roomie")
public class Roomie {
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "roomie_username")
	private String roomie_username;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "username", referencedColumnName="username")
	private User user;
//	@Column(name = "username")
//	private String username;
	
	public Roomie() {
		this.user = new User();
	}

	public Roomie(int id, String roomie_username, User user) {
		super();
		this.id = id;
		//this.username = username;
		this.roomie_username = roomie_username;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoomie_username() {
		return roomie_username;
	}

	public void setRoomie_username(String roomie_username) {
		this.roomie_username = roomie_username;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}

	
	
	
	
	
}
