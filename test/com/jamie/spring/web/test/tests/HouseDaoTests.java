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

@ActiveProfiles("dev")
@ContextConfiguration(locations = { "classpath:com/jamie/spring/web/config/dao-context.xml",
		"classpath:com/jamie/spring/web/config/security-context.xml",
		"classpath:com/jamie/spring/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class HouseDaoTests {

	@Autowired
	private HouseDao houseDao;

	@Autowired
	private DataSource dataSource;
	private House house1 = new House("35 Heathfield, Kinnegad, Westmeath", 1500, 4, "Description", "lat", "lng",
			"jamieflood", false, false, false, false, false, false, false, false, false, false, false);
	private House house2 = new House("31 Cabra Park, Phibsborough, Dublin", 450, 1, "Description", "lat", "lng",
			"jamieflood", false, false, false, false, false, false, false, false, false, false, false);

	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		jdbc.execute("delete from house");
		jdbc.execute("delete from users");
	}

	// test 1
	@Test
	public void testCreateHouse() throws IOException {
		houseDao.saveOrUpdate(house1);

		House getHouse = houseDao.getHouse(house1.getId());
		assertNotNull("House with ID " + house1.getId() + " should not be null", getHouse);

	}

	// test 2
	@Test
	public void testDeleteHouseById() throws IOException {
		houseDao.saveOrUpdate(house1);

		House getHouse = houseDao.getHouse(house1.getId());
		assertNotNull("House with ID " + house1.getId() + " should not be null", getHouse);

		houseDao.delete(house1.getId());

		House getHouse2 = houseDao.getHouse(house1.getId());
		assertNull("House with ID " + house1.getId() + " should be null", getHouse2);

	}

	// test 3
	@Test
	public void testDeleteHouseByUsername() throws IOException {
		houseDao.saveOrUpdate(house1);

		House getHouse = houseDao.getHouse(house1.getId());
		assertNotNull("House with ID " + house1.getId() + " should not be null", getHouse);

		houseDao.deleteUsername(house1.getUsername());

		House getHouse2 = houseDao.getHouse(house1.getId());
		assertNull("House with ID " + house1.getId() + " should be null", getHouse2);

	}

	// test 4
	@Test
	public void testGetHouseById() throws IOException {
		houseDao.saveOrUpdate(house2);

		House retrievedHouse = houseDao.getHouse(house2.getId());
		assertEquals(house2.getId(), retrievedHouse.getId());

	}

	// test 5
	@Test
	public void testHouseList() throws IOException {
		houseDao.saveOrUpdate(house1);
		houseDao.saveOrUpdate(house2);

		List<House> houses = houseDao.getHouses();
		assertEquals("Should be two houses.", 2, houses.size());

	}

	// test 6
	@Test
	public void testHouseUpdate() throws IOException {
		houseDao.saveOrUpdate(house1);

		house1.setAddress("I have updated the house address");
		houseDao.saveOrUpdate(house1);

		House retrieved = houseDao.getHouse(house1.getId());
		assertEquals("House should be updated", house1.getAddress(), retrieved.getAddress());
	}

	// test 7
	@Test
	public void testGetHouseSearch() throws IOException {
		houseDao.saveOrUpdate(house1);

		List<House> houses = houseDao.getHouseSearch(house1.getAddress(), 0, 10000, 0, 100);
		assertNotNull(houses);
	}

	// test 8
	@Test
	public void testHouseExists() throws IOException {
		houseDao.saveOrUpdate(house1);

		assertTrue(houseDao.exists(house1.getUsername()));
	}
}
