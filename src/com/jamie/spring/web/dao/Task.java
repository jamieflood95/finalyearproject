package com.jamie.spring.web.dao;

import java.util.Date;

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
@Table(name = "task")
public class Task {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@NotBlank(groups = { PersistenceValidationGroup.class,
			FormValidationGroup.class })
	@Size(min = 7, max = 60, groups = { PersistenceValidationGroup.class,
			FormValidationGroup.class })
	@Column(name = "name")
	private String name;

	@NotBlank(groups = { PersistenceValidationGroup.class,
			FormValidationGroup.class })
	@Lob
	@Column(name = "details")
	private String details;
	
	@Column(name = "date_created")
	private Date date_created;
	
	@Column(name = "date_complete")
	private Date date_complete;

	@ManyToOne(optional = false)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

	public Task() {
		this.user = new User();
	}
	
	public Task(Task task) {
		this.name = task.name;
		this.details = task.details;
		this.date_created = task.date_created;
		this.date_complete = task.date_complete;
		this.user = task.user;
	}

	public Task(String name, String details, Date date_created, Date date_complete, User user) {
		super();
		this.name = name;
		this.details = details;
		this.date_created = date_created;
		this.date_complete = date_complete;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getDate_created() {
		return date_created;
	}

	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}

	public Date getDate_complete() {
		return date_complete;
	}

	public void setDate_complete(Date date_complete) {
		this.date_complete = date_complete;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getUsername() {
		return user.getUsername();
	}

	

}