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

import org.hibernate.validator.constraints.NotBlank;

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

	@NotBlank(groups = { PersistenceValidationGroup.class,
			FormValidationGroup.class })
	@Lob
	@Column(name = "description")
	private String description;

	@Column(name = "lng")
	private String lng;

	@Column(name = "lat")
	private String lat;

	@Column(name = "username")
	String username;

	@Column(name = "wifi")
	boolean wifi = false;
	
	@Column(name = "furnished")
	boolean furnished = false;
	
	@Column(name = "parking")
	boolean parking = false;
	
	@Column(name = "central_heating")
	boolean central_heating = false;
	
	@Column(name = "house_alarm")
	boolean house_alarm = false;
	
	@Column(name = "television")
	boolean television = false;
	
	@Column(name = "washing_machine")
	boolean washing_machine = false;
	
	@Column(name = "dryer")
	boolean dryer = false;
	
	@Column(name = "dishwasher")
	boolean dishwasher = false;
	
	@Column(name = "microwave")
	boolean microwave = false;
	
	@Column(name = "garden")
	boolean garden = false;
	

	public House() {

	}

	

	public House(String address, int rent, int rooms, String description, String lng, String lat, String username,
			boolean wifi, boolean furnished, boolean parking, boolean central_heating, boolean house_alarm,
			boolean television, boolean washing_machine, boolean dryer, boolean dishwasher, boolean microwave,
			boolean garden) {
		this.address = address;
		this.rent = rent;
		this.rooms = rooms;
		this.description = description;
		this.lng = lng;
		this.lat = lat;
		this.username = username;
		this.wifi = wifi;
		this.furnished = furnished;
		this.parking = parking;
		this.central_heating = central_heating;
		this.house_alarm = house_alarm;
		this.television = television;
		this.washing_machine = washing_machine;
		this.dryer = dryer;
		this.dishwasher = dishwasher;
		this.microwave = microwave;
		this.garden = garden;
	}



	public House(int id, String address, int rooms, int rent, String lng, String lat, String description,
			String username, boolean wifi, boolean furnished, boolean parking, boolean central_heating, boolean house_alarm,
			boolean television, boolean washing_machine, boolean dryer, boolean dishwasher, boolean microwave,
			boolean garden) {
		this.id = id;
		this.address = address;
		this.rent = rent;
		this.rooms = rooms;
		this.description = description;
		this.lng = lng;
		this.lat = lat;
		this.username = username;
		this.wifi = wifi;
		this.furnished = furnished;
		this.parking = parking;
		this.central_heating = central_heating;
		this.house_alarm = house_alarm;
		this.television = television;
		this.washing_machine = washing_machine;
		this.dryer = dryer;
		this.dishwasher = dishwasher;
		this.microwave = microwave;
		this.garden = garden;
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

	public boolean isWifi() {
		return wifi;
	}

	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}

	public boolean isFurnished() {
		return furnished;
	}

	public void setFurnished(boolean furnished) {
		this.furnished = furnished;
	}

	public boolean isParking() {
		return parking;
	}

	public void setParking(boolean parking) {
		this.parking = parking;
	}

	public boolean isCentral_heating() {
		return central_heating;
	}

	public void setCentral_heating(boolean central_heating) {
		this.central_heating = central_heating;
	}

	public boolean isHouse_alarm() {
		return house_alarm;
	}

	public void setHouse_alarm(boolean house_alarm) {
		this.house_alarm = house_alarm;
	}

	public boolean isTelevision() {
		return television;
	}

	public void setTelevision(boolean television) {
		this.television = television;
	}

	public boolean isWashing_machine() {
		return washing_machine;
	}

	public void setWashing_machine(boolean washing_machine) {
		this.washing_machine = washing_machine;
	}

	public boolean isDryer() {
		return dryer;
	}

	public void setDryer(boolean dryer) {
		this.dryer = dryer;
	}

	public boolean isDishwasher() {
		return dishwasher;
	}

	public void setDishwasher(boolean dishwasher) {
		this.dishwasher = dishwasher;
	}

	public boolean isMicrowave() {
		return microwave;
	}

	public void setMicrowave(boolean microwave) {
		this.microwave = microwave;
	}

	public boolean isGarden() {
		return garden;
	}

	public void setGarden(boolean garden) {
		this.garden = garden;
	}

	@Override
	public String toString() {
		return "House [id=" + id + ", address=" + address + ", rent=" + rent + ", rooms=" + rooms + ", description="
				+ description + ", lng=" + lng + ", lat=" + lat + ", username=" + username + ", wifi=" + wifi
				+ ", furnished=" + furnished + ", parking=" + parking + ", central_heating=" + central_heating
				+ ", house_alarm=" + house_alarm + ", television=" + television + ", washing_machine=" + washing_machine
				+ ", dryer=" + dryer + ", dishwasher=" + dishwasher + ", microwave=" + microwave + ", garden=" + garden
				+ "]";
	}

	

	
}