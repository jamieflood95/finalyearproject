package com.jamie.spring.web.test.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

import com.jamie.spring.web.dao.House;
import com.jamie.spring.web.dao.HouseDao;
import com.jamie.spring.web.dao.User;
import com.jamie.spring.web.dao.UsersDao;

@ActiveProfiles("dev")
@ContextConfiguration(locations = { "classpath:com/jamie/spring/web/config/dao-context.xml",
		"classpath:com/jamie/spring/web/config/security-context.xml",
		"classpath:com/jamie/spring/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTests {

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

	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		jdbc.execute("delete from users");
		jdbc.execute("delete from house");
	}

	// test 9
	@Test
	public void testCreateUser() throws IOException {
		houseDao.saveOrUpdate(house1);
		usersDao.create(user1);

		User getUser = usersDao.getUser(user1.getUsername());
		assertNotNull("User with ID " + user1.getUsername() + " should not be null", getUser.getUsername());

	}

	// test 10
	@Test
	public void testUserExists() throws IOException {
		houseDao.saveOrUpdate(house1);
		usersDao.create(user1);

		assertTrue(usersDao.exists(user1.getUsername()));
	}

	// test 11
	@Test
	public void testUserList() throws IOException {
		houseDao.saveOrUpdate(house1);
		houseDao.saveOrUpdate(house2);
		usersDao.create(user1);
		usersDao.create(user2);

		List<User> users = usersDao.getAllUsers();
		assertEquals("Should be two users.", 2, users.size());
	}

	// test 12
	@Test
	public void testGetUser() throws IOException {
		houseDao.saveOrUpdate(house1);
		houseDao.saveOrUpdate(house2);
		usersDao.create(user1);
		usersDao.create(user2);

		User retrievedUser = usersDao.getUser(user2.getUsername());
		assertEquals(user2.getUsername(), retrievedUser.getUsername());
	}

	// test 13
	@Test
	public void testDeleteUserByUsername() throws IOException {
		houseDao.saveOrUpdate(house1);
		usersDao.create(user1);

		User getUser = usersDao.getUser(user1.getUsername());
		assertNotNull(user1.getUsername(), getUser.getUsername());

		usersDao.delete(user1.getUsername());

		try {
			User getUser2 = usersDao.getUser(user1.getUsername());
			assertNull(user1.getUsername(), getUser2.getUsername());
		} catch (IndexOutOfBoundsException e) {

		}
	}

	// test 14
	@Test
	public void testGetUserSearch() throws IOException {
		houseDao.saveOrUpdate(house1);
		houseDao.saveOrUpdate(house2);
		usersDao.create(user1);
		usersDao.create(user2);

		List<User> users = usersDao.getUserSearch(user1.getName());

		for (int i = 0; i < users.size(); i++) {
			assertEquals(users.get(i).getName(), user1.getName());
		}

	}
}
