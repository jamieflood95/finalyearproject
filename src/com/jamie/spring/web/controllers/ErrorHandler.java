package com.jamie.spring.web.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
/**
 * 
 * @author Jamie
 * <p>
 * This class handles any exceptions that may be caught and is annoted
 * with @ControllerAdvice which indicates that the class assists 
 * a "Controller". It serves as a specialization of @Component, allowing 
 * for implementation classes to be autodetected through classpath scanning.
 * <p>
 * The @ExceptionHandler annotation is used for handling exceptions in 
 * specific handler classes and/or handler methods. The method returns
 * a String value which is interpreted as view name.
 * <p>
 * The handleDataAccessException method lets me know how to find and 
 * handle the kind of error encountered without knowing the details of 
 * the particular data access API in use (e.g. JDBC).
 * <p>
 * The handleAccessDeniedException method catches any unauthenticated
 * users that don't hold the right authority.
 *
 */
@ControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(DataAccessException.class)
	public String handleDataAccessException(DataAccessException ex) {
		ex.printStackTrace();
		return "error";		
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public String handleAccessDeniedException(AccessDeniedException ex) {
		ex.printStackTrace();
		return "denied";		
	}
	
}
