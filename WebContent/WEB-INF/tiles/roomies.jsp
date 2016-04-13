<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<br>
<br>

<div class="container">
	<div class="row row-offcanvas row-offcanvas-right">
		<div class="col-xs-12 col-sm-9">
			<p class="pull-right visible-xs">
				<button type="button" class="btn btn-primary btn-xs"
					data-toggle="offcanvas"></button>
			</p>


			<sec:authorize access="isAuthenticated()">
				<div style="background-color: #2F72FF !important;" class="jumbotron">
					<h1>Roomies</h1>
					<a href="${pageContext.request.contextPath}/roomies/all"><div
							class="link">View all roomies</div></a>
				</div>
			</sec:authorize>

			<div class="row">
				<a href="${pageContext.request.contextPath}/contacts"><div
							class="link"><h2>Contacts</h2></div></a>
				<c:forEach var="contacts" items="${contacts}">
					<div class="col-xs-6 col-lg-4">
						<a href="<c:url value="/contacts/${contacts.id}"/>">${contacts.name}</a>
						<br>${contacts.role} <br>${contacts.phone} <br>${contacts.email}
						<br>
						<br>
					</div>
				</c:forEach>
			</div>
		</div>



		<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar">
			<div class="list-group">
				<a class="list-group-item active">Upcoming Tasks</a>
				<c:forEach var="tasks" items="${tasks}">
					<a href="<c:url value="/tasks/${tasks.id}"/>"
						class="list-group-item">${tasks.name}</a>
				</c:forEach>
			</div>
		</div>
	</div>
</div>