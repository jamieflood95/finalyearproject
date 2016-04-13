<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="container">
	<h2>My Roomies</h2>
	<table class="table table-hover">
		<thead class="thead-default">
			<tr>
				<th>Roomie</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="roomies" items="${roomies}">
				<tr>
					<td><a
						href="<c:url value="/user/${roomies.roomie_username}"/>">${roomies.roomie_username}</a>
					</td>
					<td><form
							action="${pageContext.request.contextPath}/deleteroomie/${roomies.roomie_username}">
							<input type="submit" value="Remove roomie"
								class="btn btn-default">
						</form></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<br>


