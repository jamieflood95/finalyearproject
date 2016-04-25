package com.jamie.spring.web.test.tests;

import static org.junit.Assert.*;

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
import com.jamie.spring.web.dao.MessageDao;
import com.jamie.spring.web.dao.User;
import com.jamie.spring.web.dao.UsersDao;

@ActiveProfiles("dev")
@ContextConfiguration(locations = { "classpath:com/jamie/spring/web/config/dao-context.xml",
		"classpath:com/jamie/spring/web/config/security-context.xml",
		"classpath:com/jamie/spring/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class MessageDaoTests {

	@Autowired
	private MessageDao messageDao;

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

	Message message1 = new Message(1, null, "title", "simonduffy", "this is my text", user1);
	Message message2 = new Message(2, null, "title 2", "jamieflood", "this is my text", user2);
	Message message3 = new Message();

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

	// test 15
	@Test
	public void testSaveMessage() throws IOException {
		houseDao.saveOrUpdate(house2);
		usersDao.create(user2);

		message3.setRecipient("jamieflood");
		message3.setSend_date(null);
		message3.setText("text");
		message3.setTitle("title");
		message3.setUser(user2);

		messageDao.saveOrUpdate(message3);

		Message getMessage = messageDao.getMessage(message3.getMessage_id());
		assertSame(message3.getMessage_id(), getMessage.getMessage_id());

	}

	// test 16
	@Test
	public void testGetMessagesSent() throws IOException {
		houseDao.saveOrUpdate(house2);
		usersDao.create(user2);

		message3.setRecipient("jamieflood");
		message3.setSend_date(null);
		message3.setText("text");
		message3.setTitle("title");
		message3.setUser(user2);

		messageDao.saveOrUpdate(message3);

		List<Message> messages = messageDao.getMessagesSent(message3.getRecipient(), message3.getUsername());
		for (int i = 0; i < messages.size(); i++) {
			assertEquals(messages.get(i).getRecipient(), message3.getRecipient());
		}
	}

	// test 17
	@Test
	public void testGetMessagesReceived() throws IOException {
		houseDao.saveOrUpdate(house2);
		usersDao.create(user2);

		message3.setRecipient("jamieflood");
		message3.setSend_date(null);
		message3.setText("text");
		message3.setTitle("title");
		message3.setUser(user2);

		messageDao.saveOrUpdate(message3);

		List<Message> messages = messageDao.getMessagesReceived(message3.getRecipient(), message3.getUsername());
		for (int i = 0; i < messages.size(); i++) {
			assertEquals(messages.get(i).getRecipient(), message3.getRecipient());
		}
	}

	// test 18
	@Test
	public void testMessageExists() throws IOException {
		houseDao.saveOrUpdate(house2);
		usersDao.create(user2);

		message3.setRecipient("jamieflood");
		message3.setSend_date(null);
		message3.setText("text");
		message3.setTitle("title");
		message3.setUser(user2);

		messageDao.saveOrUpdate(message3);

		List<Message> messages = messageDao.getAllMessages(message3.getRecipient());
		for (int i = 0; i < messages.size(); i++) {
			assertEquals(messages.get(i).getRecipient(), message3.getRecipient());
		}
	}

	// test 19
	@Test
	public void testGetAllMessages() throws IOException {
		houseDao.saveOrUpdate(house2);
		usersDao.create(user2);

		message3.setRecipient("jamieflood");
		message3.setSend_date(null);
		message3.setText("text");
		message3.setTitle("title");
		message3.setUser(user2);

		messageDao.saveOrUpdate(message3);

		assertTrue(messageDao.exists(message3.getRecipient()));
	}
}
