package com.jamie.spring.web.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Lob
	@Column(name = "text")
	private String text;
	
	@Column(name = "date")
	private Date date;

	@ManyToOne(targetEntity = User.class)
	private User user;
	
	@ManyToOne(targetEntity = House.class)
	private House house;
	
	public Comment() {
		this.user = new User();
		this.house = new House();
	}

	public Comment(int id, String text, Date date, User user, House house) {
		super();
		this.id = id;
		this.text = text;
		this.date = date;
		this.user = user;
		this.house = house;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public House getHouse() {
		return house;
	}
	
	public int getHouse_id() {
		return house.getId();
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public User getUser() {
		return user;
	}
	
	public String getUsername() {
		return user.getUsername();
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}