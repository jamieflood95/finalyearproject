<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">
	<table class="sortable">
		<thead>
			<tr>
				<th></th>
				<th>Address</th>
				<th>Rooms</th>
				<th>Rent</th>
				<th>Owner</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="house" items="${house}">
				<tr>
					<td><img id="table-image"
						src="https://maps.googleapis.com/maps/api/streetview?size=400x300&location=<c:url value="${house.lat}"/>,<c:url value="${house.lng}"/>">
					</td>
					<td><a href="<c:url value="/house/${house.id}"/>">${house.address}</a></td>
					<td><c:out value="${house.rooms}"></c:out></td>
					<td>&euro;<c:out value="${house.rent}"></c:out></td>
					<td><a href="<c:url value="/user/${house.username}"/>">${house.username}</a></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>