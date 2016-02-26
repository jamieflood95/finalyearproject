<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<div class="container">
	<h1>Admin</h1>

	<h3>Users</h3>

	<table class="table table-hover">
	<thead>
		<tr>
			<td>Username</td>
			<td>Email</td>
			<td>Role</td>
			<td>Enabled</td>
		</tr>
		</thead>
		<tbody>

		<c:forEach var="user" items="${users}">
			<tr>
				<td><c:out value="${user.username}"></c:out></td>
				<td><c:out value="${user.email}"></c:out></td>
				<td><c:out value="${user.authority}"></c:out></td>
				<td><c:out value="${user.enabled}"></c:out></td>
				<td><form
						action="${pageContext.request.contextPath}/deleteuser/${user.username}">
						<input type="submit" value="Delete" name="delete" id="delete"
							class="btn btn-default">
					</form></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

	<h3>Houses</h3>

	<table class="table table-hover">
	<thead>
		<tr>
			<td>Address</td>
			<td>Rooms</td>
			<td>Rent</td>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="houses" items="${houses}">
			<tr>
				<td><c:out value="${houses.address}"></c:out></td>
				<td><c:out value="${houses.rooms}"></c:out></td>
				<td>&euro;<c:out value="${houses.rent}"></c:out></td>
				<td><form
						action="${pageContext.request.contextPath}/deletehouse/${houses.id}"
						id="delete">
						<input type="submit" value="Delete" name="delete" id="delete"
							class="btn btn-default">
					</form></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
