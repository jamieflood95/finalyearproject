//package com.caveofprogramming.spring.web.test.tests;
//
//import static org.junit.Assert.*;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.sql.DataSource;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.jamie.spring.web.dao.House;
//import com.jamie.spring.web.dao.HouseDao;
//import com.jamie.spring.web.dao.User;
//import com.jamie.spring.web.dao.UsersDao;
//
//@ActiveProfiles("dev")
//@ContextConfiguration(locations = { "classpath:com/jamie/spring/web/config/dao-context.xml",
//		"classpath:com/jamie/spring/web/config/security-context.xml",
//		"classpath:com/caveofprogramming/spring/web/test/config/datasource.xml" })
//@RunWith(SpringJUnit4ClassRunner.class)
//public class HouseDaoTests {
//
//	@Autowired
//	private HouseDao housesDao;
//
//	@Autowired
//	private UsersDao usersDao;
//
//	@Autowired
//	private DataSource dataSource;
//
//	private User user1 = new User("johnwpurcell", "John Purcell", "hellothere", "john@caveofprogramming.com", null, null, null, null, null, null, true,
//			"ROLE_USER");
//	private User user2 = new User("richardhannay", "Richard Hannay", "the39steps", "richard@caveofprogramming.com",
//			null, null, null, null, null, null, true, "ROLE_ADMIN");
//	private User user3 = new User("suetheviolinist", "Sue Black", "iloveviolins", "sue@caveofprogramming.com", null, null, null, null, null, null, true,
//			"ROLE_USER");
//	private User user4 = new User("rogerblake", "Rog Blake", "liberator", "rog@caveofprogramming.com", null, null, null, null, null, null, false, "user");
//
//	private House house1 = new House(0, user1, "This is a test offer.", 12, 12, null, null, "desc");
//
//	@Before
//	public void init() {
//		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
//
//		jdbc.execute("delete from house");
//		jdbc.execute("delete from users");
//	}
//
//	@Test
//	public void testDelete() throws IOException {
//		usersDao.create(user1);
//		usersDao.create(user2);
//		usersDao.create(user3);
//		usersDao.create(user4);
//		housesDao.saveOrUpdate(house1);
//
//		House retrieved1 = housesDao.getHouse(house1.getId());
//		assertNotNull("Offer with ID " + retrieved1.getId() + " should not be null (deleted, actual)", retrieved1);
//
//		housesDao.delete(house1.getId());
//
//	}
//
//	@Test
//	public void testGetById() throws IOException {
//		usersDao.create(user1);
//		usersDao.create(user2);
//		usersDao.create(user3);
//		usersDao.create(user4);
//		housesDao.saveOrUpdate(house1);
//		
//
//		House retrieved1 = housesDao.getHouse(house1.getId());
//		assertEquals("Houses should match", house1, retrieved1);
//	}
//
//	@Test
//	public void testCreateRetrieve() throws IOException {
//		usersDao.create(user1);
//		usersDao.create(user2);
//		usersDao.create(user3);
//		usersDao.create(user4);
//
//		housesDao.saveOrUpdate(house1);
//
//		List<House> houses1 = housesDao.getHouses();
//		assertEquals("Should be one House.", 1, houses1.size());
//
//		assertEquals("Retrieved house should equal inserted offer.", house1, houses1.get(0));
//
//	}
//
//	@Test
//	public void testUpdate() throws IOException {
//		usersDao.create(user1);
//		usersDao.create(user2);
//		usersDao.create(user3);
//		usersDao.create(user4);
//		housesDao.saveOrUpdate(house1);
//
//		house1.setAddress("This house has updated text.");
//		housesDao.saveOrUpdate(house1);
//		
//		House retrieved = housesDao.getHouse(house1.getId());
//		assertEquals("Retrieved house should be updated.", house1, retrieved);
//	}
//
//}
