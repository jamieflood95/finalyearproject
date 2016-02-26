package com.jamie.spring.web.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "house")
public class House {
	@Id
	@GeneratedValue
	private int id;

	@Size(min = 20, max = 255, message = "Text must be between 20 and 255 characters", groups = {
			PersistenceValidationGroup.class, FormValidationGroup.class })
	@Column(name = "address")
	private String address;

	@Column(name = "rent", columnDefinition = "int")
	private int rent;

	@Column(name = "rooms")
	private int rooms;
	
	@Lob
	@Column(name = "description")
	private String description;

	@Column(name = "lng")
	private String lng;

	@Column(name = "lat")
	private String lat;
	
	@Column(name = "username")
	String username;

	public House() {
		
	}

	public House(String address, int rooms, int rent, String description, String username) {
		this.address = address;
		this.rooms = rooms;
		this.rent = rent;
		this.description = description;
		this.username = username;
	}

	public House(int id, String address, int rooms, int rent,
			String lng, String lat, String description, String username) {
		this.id = id;
		this.address = address;
		this.rooms = rooms;
		this.rent = rent;
		this.lng = lng;
		this.lat = lat;
		this.description = description;
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	public int getRooms() {
		return rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((lat == null) ? 0 : lat.hashCode());
		result = prime * result + ((lng == null) ? 0 : lng.hashCode());
		result = prime * result + rent;
		result = prime * result + rooms;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		House other = (House) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (lat == null) {
			if (other.lat != null)
				return false;
		} else if (!lat.equals(other.lat))
			return false;
		if (lng == null) {
			if (other.lng != null)
				return false;
		} else if (!lng.equals(other.lng))
			return false;
		if (rent != other.rent)
			return false;
		if (rooms != other.rooms)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "House [id=" + id + ", address=" + address + ", rent=" + rent
				+ ", rooms=" + rooms + ", description=" + description
				+ ", lng=" + lng + ", lat=" + lat + "]";
	}

	
}
