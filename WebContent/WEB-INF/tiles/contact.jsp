<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="container">
	<div class="house-object-info" id="address_box">
		<div class="house-object-header">
			<h1>${contact.role}</h1>
		</div>
		<div id="house-summary-items">
			<div id="house-price">${contact.name}</div>
			<span class="header-text">${contact.phone}</span>
			<span class="header-text">${contact.email}</span>
		</div>
	</div>
</div>

