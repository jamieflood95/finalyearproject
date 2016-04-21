package com.jamie.spring.web.test.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jamie.spring.web.dao.Contact;
import com.jamie.spring.web.dao.ContactDao;
import com.jamie.spring.web.dao.House;
import com.jamie.spring.web.dao.HouseDao;
import com.jamie.spring.web.dao.User;
import com.jamie.spring.web.dao.UsersDao;

@ActiveProfiles("dev")
@ContextConfiguration(locations = { "classpath:com/jamie/spring/web/config/dao-context.xml",
		"classpath:com/jamie/spring/web/config/security-context.xml",
		"classpath:com/jamie/spring/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ContactDaoTests {

	@Autowired
	private ContactDao contactDao;

	@Autowired
	private HouseDao houseDao;

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private DataSource dataSource;

	private House house1 = new House("35 Heathfield, Kinnegad, Westmeath", 1500, 4, "Description", "lat", "lng",
			"jamieflood", false, false, false, false, false, false, false, false, false, false, false);
	private House house2 = new House("31 Cabra Park, Phibsborough, Dublin", 450, 1, "Description", "lat", "lng",
			"jamieflood", false, false, false, false, false, false, false, false, false, false, false);

	private User user1 = new User("jamieflood", "Jamie Flood", "password", "jamie@gmail.com", "", "", "", "", "", "",
			true, "ROLE_USER", house1);
	private User user2 = new User("simonduffy", "Simon Duffy", "password", "simon@gmail.com", "", "", "", "", "", "",
			true, "ROLE_USER", house2);

	Contact contact1 = new Contact();

	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		jdbc.execute("delete from contact");
		jdbc.execute("delete from task");
		jdbc.execute("delete from message");
		jdbc.execute("delete from users");
		jdbc.execute("delete from house");
	}

	// test 23
	@Test
	public void testSaveContact() throws IOException {
		houseDao.saveOrUpdate(house2);
		usersDao.create(user2);

		contact1.setEmail("jamieflood95@gmail.com");
		contact1.setName("Jamie Flood");
		contact1.setPhone("0831527861");
		contact1.setRole("Wifi");
		contact1.setUser(user2);
		
		contactDao.saveOrUpdate(contact1);

		Contact getContact = contactDao.getContact(contact1.getId());
		assertSame(contact1.getId(), getContact.getId());
	}

	// test 24
	@Test
	public void testGetContactById() throws IOException {
		houseDao.saveOrUpdate(house2);
		usersDao.create(user2);

		contact1.setEmail("jamieflood95@gmail.com");
		contact1.setName("Jamie Flood");
		contact1.setPhone("0831527861");
		contact1.setRole("Wifi");
		contact1.setUser(user2);
		
		contactDao.saveOrUpdate(contact1);

		Contact retrievedContact = contactDao.getContact(contact1.getId());
		assertEquals(contact1.getId(), retrievedContact.getId());
	}

	// test 25
	@Test
	public void testTaskList() throws IOException {
		houseDao.saveOrUpdate(house2);
		usersDao.create(user2);

		contact1.setEmail("jamieflood95@gmail.com");
		contact1.setName("Jamie Flood");
		contact1.setPhone("0831527861");
		contact1.setRole("Wifi");
		contact1.setUser(user2);
		
		contactDao.saveOrUpdate(contact1);

		List<Contact> contacts = contactDao.getContacts(contact1.getUsername());
		assertEquals(1, contacts.size());

	}
}
