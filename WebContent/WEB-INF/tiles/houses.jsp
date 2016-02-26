<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="container">
	<table class="table table-hover">
		<thead class="thead-default">
			<tr>
				<th></th>
				<th>Address</th>
				<th>Rooms</th>
				<th>Rent</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="houses" items="${houses}">
				<tr>
					<td><img id="table-image" src="https://maps.googleapis.com/maps/api/streetview?size=400x300&location=<c:url value="${houses.lat}"/>,<c:url value="${houses.lng}"/>">
					</td>
					<td><a href="<c:url value="/house/${houses.id}"/>">${houses.address}</a></td>
					<td><c:out value="${houses.rooms}"></c:out></td>
					<td>&euro;<c:out value="${houses.rent}"></c:out></td>
					<td><a href="<c:url value="/user/${houses.username}"/>">${houses.username}</a></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>

	<c:choose>
		<c:when test="${hasHouse}">
			<a href="${pageContext.request.contextPath}/createhouse"
				class="btn btn-default">Edit or delete your current house</a>
		</c:when>
		<c:otherwise>
			<p>
				<a href="${pageContext.request.contextPath}/createhouse"
					class="btn btn-default">Add a new house</a>
			</p>
		</c:otherwise>

	</c:choose>
	<p />
	<a href="${pageContext.request.contextPath}/map"
		class="btn btn-default">View houses on map <span
		class="glyphicon glyphicon-map-marker"></span>
	</a>

</div>
<br>


