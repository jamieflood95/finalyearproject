<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">
	<table class="table table-hover">
		<thead>
			<tr>
				<td>Username</td>
				<td>Name</td>
				<td>Email</td>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="user" items="${users}">
				<tr>
					<td><a href="<c:url value="/user/${user.username}"/>"><c:out value="${user.username}"></c:out></a></td>
					<td><c:out value="${user.name}"></c:out></td>
					<td><c:out value="${user.email}"></c:out></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>