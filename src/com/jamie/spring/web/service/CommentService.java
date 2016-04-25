package com.jamie.spring.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jamie.spring.web.dao.Comment;
import com.jamie.spring.web.dao.CommentDao;

/**
 * 
 * @author Jamie
 *         <p>
 *         This is a service-layer class which handles the business logic of the
 *         application. The @Service annotation marks the class as a bean so it
 *         can be put into the application context.
 *         <p>
 *         The @Autowired annotation is used which marks a method as to be
 *         autowired by Spring's dependency injection facilities. This method is
 *         autowired with a matching bean in the Spring container.
 *
 */
@Service("commentService")
public class CommentService {

	private CommentDao commentDAO;

	@Autowired
	public void setCommentDAO(CommentDao commentDAO) {
		this.commentDAO = commentDAO;
	}

	/**
	 * Calls the DAO class to get all comments for a user
	 * 
	 * @param username
	 * @return
	 */
	public List<Comment> getComments(String username) {
		List<Comment> comments = commentDAO.getComments(username);

		return comments;
	}

	/**
	 * Calls the DAO class to save the comment
	 * 
	 * @param comment
	 */
	public void saveOrUpdate(Comment comment) {
		commentDAO.saveOrUpdate(comment);
	}

	/**
	 * Calls the DAO class to get all comments for a house
	 * 
	 * @param house_id
	 * @return
	 */
	public List<Comment> getComments(int house_id) {
		List<Comment> comments = commentDAO.getComments(house_id);

		return comments;
	}

}
