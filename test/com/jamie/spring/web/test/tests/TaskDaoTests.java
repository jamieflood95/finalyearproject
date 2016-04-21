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

import com.jamie.spring.web.dao.House;
import com.jamie.spring.web.dao.HouseDao;
import com.jamie.spring.web.dao.Message;
import com.jamie.spring.web.dao.Task;
import com.jamie.spring.web.dao.TaskDao;
import com.jamie.spring.web.dao.User;
import com.jamie.spring.web.dao.UsersDao;

@ActiveProfiles("dev")
@ContextConfiguration(locations = { "classpath:com/jamie/spring/web/config/dao-context.xml",
		"classpath:com/jamie/spring/web/config/security-context.xml",
		"classpath:com/jamie/spring/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskDaoTests {

	@Autowired
	private TaskDao taskDao;

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

	Task task1 = new Task();

	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		jdbc.execute("delete from task");
		jdbc.execute("delete from message");
		jdbc.execute("delete from users");
		jdbc.execute("delete from house");
	}

	// test 20
	@Test
	public void testSaveTask() throws IOException {
		houseDao.saveOrUpdate(house2);
		usersDao.create(user2);

		task1.setDate_complete(null);
		task1.setDate_created(null);
		task1.setDetails("details");
		task1.setName("name is this .");
		task1.setUser(user2);

		taskDao.saveOrUpdate(task1);

		Task getTask = taskDao.getTask(task1.getId());
		assertSame(task1.getId(), getTask.getId());
	}

	// test 21
	@Test
	public void testGetTaskById() throws IOException {
		houseDao.saveOrUpdate(house2);
		usersDao.create(user2);

		task1.setDate_complete(null);
		task1.setDate_created(null);
		task1.setDetails("details");
		task1.setName("name is this .");
		task1.setUser(user2);

		taskDao.saveOrUpdate(task1);

		Task retrievedTask = taskDao.getTask(task1.getId());
		assertEquals(task1.getId(), retrievedTask.getId());
	}

	// test 22
	@Test
	public void testTaskList() throws IOException {
		houseDao.saveOrUpdate(house2);
		usersDao.create(user2);

		task1.setDate_complete(null);
		task1.setDate_created(null);
		task1.setDetails("details");
		task1.setName("name is this .");
		task1.setUser(user2);

		taskDao.saveOrUpdate(task1);

		List<Task> tasks = taskDao.getTasks(task1.getUsername());
		assertEquals(1, tasks.size());

	}
}
