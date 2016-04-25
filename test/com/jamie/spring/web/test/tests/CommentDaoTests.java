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

import com.jamie.spring.web.dao.Comment;
import com.jamie.spring.web.dao.CommentDao;
import com.jamie.spring.web.dao.Contact;
import com.jamie.spring.web.dao.House;
import com.jamie.spring.web.dao.HouseDao;
import com.jamie.spring.web.dao.User;
import com.jamie.spring.web.dao.UsersDao;

@ActiveProfiles("dev")
@ContextConfiguration(locations = { "classpath:com/jamie/spring/web/config/dao-context.xml",
		"classpath:com/jamie/spring/web/config/security-context.xml",
		"classpath:com/jamie/spring/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentDaoTests {

	@Autowired
	private CommentDao commentDao;

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

	Comment comment1 = new Comment();

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

	// test 26
	@Test
	public void testSaveComment() throws IOException {
		houseDao.saveOrUpdate(house2);
		usersDao.create(user2);

		comment1.setHouse(house2);
		comment1.setUser(user2);
		comment1.setText("this is a test");

		commentDao.saveOrUpdate(comment1);

		Comment getComment = commentDao.getComments(house2.getId()).get(0);
		assertSame(comment1.getId(), getComment.getId());
	}

	// test 27
	@Test
	public void testGetCommentsById() throws IOException {
		houseDao.saveOrUpdate(house2);
		usersDao.create(user2);

		comment1.setHouse(house2);
		comment1.setUser(user2);
		comment1.setText("this is a test");

		commentDao.saveOrUpdate(comment1);

		Comment retrievedComment = commentDao.getComments(house2.getId()).get((0));
		assertEquals(comment1.getId(), retrievedComment.getId());
	}

	// test 28
	@Test
	public void testGetCommentsByUsername() throws IOException {
		houseDao.saveOrUpdate(house2);
		usersDao.create(user2);

		comment1.setHouse(house2);
		comment1.setUser(user2);
		comment1.setText("this is a test");

		commentDao.saveOrUpdate(comment1);

		Comment retrievedComment = commentDao.getComments(user2.getUsername()).get((0));
		assertEquals(comment1.getId(), retrievedComment.getId());
	}
}
