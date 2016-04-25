package com.jamie.spring.web.test.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

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
import com.jamie.spring.web.dao.Roomie;
import com.jamie.spring.web.dao.RoomieDao;
import com.jamie.spring.web.dao.User;
import com.jamie.spring.web.dao.UsersDao;

@ActiveProfiles("dev")
@ContextConfiguration(locations = { "classpath:com/jamie/spring/web/config/dao-context.xml",
		"classpath:com/jamie/spring/web/config/security-context.xml",
		"classpath:com/jamie/spring/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class RoomieDaoTests {

	@Autowired
	private RoomieDao roomieDao;

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

	Roomie roomie1 = new Roomie();

	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		jdbc.execute("delete from roomie");
		jdbc.execute("delete from comment");
		jdbc.execute("delete from contact");
		jdbc.execute("delete from task");
		jdbc.execute("delete from message");
		jdbc.execute("delete from users");
		jdbc.execute("delete from house");
	}

	// test 29
	@Test
	public void testSaveRoomie() throws IOException {
		houseDao.saveOrUpdate(house2);
		usersDao.create(user2);

		roomie1.setUser(user2);
		roomie1.setRoomie_username("myusername");

		roomieDao.saveOrUpdate(roomie1);

		Roomie getRoomie = roomieDao.getRoomies(roomie1.getUser().getUsername()).get(0);
		assertSame(roomie1.getId(), getRoomie.getId());
	}

	// test 30
	@Test
	public void testGetRoomiesByUsername() throws IOException {
		houseDao.saveOrUpdate(house2);
		usersDao.create(user2);

		roomie1.setUser(user2);
		roomie1.setRoomie_username("myusername");

		roomieDao.saveOrUpdate(roomie1);

		Roomie retrievedRoomie = roomieDao.getRoomies(roomie1.getUser().getUsername()).get(0);
		assertEquals(roomie1.getId(), retrievedRoomie.getId());
	}

	// test 31
	@Test
	public void testRoomieExists() throws IOException {
		houseDao.saveOrUpdate(house2);
		usersDao.create(user2);

		roomie1.setUser(user2);
		roomie1.setRoomie_username("myusername");

		roomieDao.saveOrUpdate(roomie1);

		assertTrue(roomieDao.exists(roomie1.getRoomie_username(), roomie1.getUser().getUsername()));
		assertFalse(roomieDao.exists(roomie1.getUser().getUsername(), roomie1.getRoomie_username()));
	}

	// test 32
	@Test
	public void testDeleteRoomie() throws IOException {
		houseDao.saveOrUpdate(house2);
		usersDao.create(user2);

		roomie1.setUser(user2);
		roomie1.setRoomie_username("myusername");

		roomieDao.saveOrUpdate(roomie1);

		Roomie getRoomie = roomieDao.getRoomies(roomie1.getUser().getUsername()).get(0);
		assertNotNull(user1.getUsername(), getRoomie.getUser().getUsername());

		roomieDao.delete(roomie1.getUser().getUsername(), roomie1.getRoomie_username());

		try {
			Roomie getRoomie2 = roomieDao.getRoomies(roomie1.getUser().getUsername()).get(0);
			assertNull(user1.getUsername(), getRoomie2.getUser().getUsername());
		} catch (IndexOutOfBoundsException e) {

		}
	}
}
