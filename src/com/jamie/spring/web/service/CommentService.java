package com.jamie.spring.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jamie.spring.web.dao.Comment;
import com.jamie.spring.web.dao.CommentDao;

@Service("commentService")
public class CommentService {

	private CommentDao commentDAO;

	@Autowired
	public void setCommentDAO(CommentDao commentDAO) {
		this.commentDAO = commentDAO;
	}

	public List<Comment> getComments(String username) {
		List<Comment> comments = commentDAO.getComments(username);

		return comments;
	}

	public void saveOrUpdate(Comment comment) {
		commentDAO.saveOrUpdate(comment);
	}

	public List<Comment> getComments(int house_id) {
		List<Comment> comments = commentDAO.getComments(house_id);

		return comments;
	}

}
