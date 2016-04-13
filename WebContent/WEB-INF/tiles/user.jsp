<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="container">
<sec:authentication var="principal" property="principal" />
${principal.username }
			


	<c:set var="string2" value="${fn:toUpperCase(user.username)}" />
	<h3>${string2}</h3>
	<c:choose>
		<c:when test="${hasImage}">
			<img width="200px" height="200px"
				src="${pageContext.request.contextPath}/static/images/profilepictures/<c:url value="${user.username}"/>.png">
		</c:when>
	</c:choose>
	<sec:authorize access="isAuthenticated()">
		<sec:authentication var="principal" property="principal" />
		<c:choose>
			<c:when test="${principal.username==user.username}">
				<a class="btn btn-primary btn-lg"
					href="${pageContext.request.contextPath}/showupload">Upload new
					profile picture</a>
			</c:when>
			<c:when test="${isFriends}">
				<br>This user is your roomie!<br>
			</c:when>
			<c:otherwise>
				<br>
				<sf:form id="details" method="post"
					action="${pageContext.request.contextPath}/addRoomie"
					commandName="roomie">
					<sf:input type="hidden" name="roomie_username"
						path="roomie_username" />
					<input class="btn btn-default" class="control" value="Add roomie"
						type="submit" />
				</sf:form>
			</c:otherwise>
		</c:choose>
	</sec:authorize>

	<div class="house-object-info" id="address_box">
		<div class="house-object-header">
			<h1>About Me</h1>
		</div>
		<b>Name</b> ${user.name} <br> <b>Date of birth:</b> ${user.dob} <br>
		<b>Work:</b> ${user.work} <br> <b>College:</b> ${user.college} <br>
		<b>Hobbies:</b> ${user.hobbies} <br> <b>Relationship status:</b>
		${user.relationship} <br>

	</div>

	<div class="house-user-info" id="address_box">
		<div class="house-user-header">
			<h1>Contact</h1>
		</div>
		<div>
			<sec:authorize access="isAuthenticated()">
				<sec:authentication var="principal" property="principal" />
				<c:choose>
					<c:when test="${principal.username==house.username}">
						View your messages
						<br>
						<a class="btn btn-primary btn-lg"
							href="${pageContext.request.contextPath}/messages">Messages</a>
					</c:when>
					<c:otherwise>
						<div class="form-group">
							<sf:form id="details" method="post"
								action="${pageContext.request.contextPath}/sendmessage"
								commandName="message">
								<table>

									<tr>
										<td><a href="<c:url value="/user/${user.username}"/>">${user.username}</a></td>
									</tr>

									<tr>
										<td><sf:input class="form-control" path="recipient"
												name="recipient" type="hidden" /><br />
											<div class="error">
												<sf:errors path="recipient"></sf:errors>
											</div></td>
									</tr>


									<tr>
										<td><sf:textarea rows="4" class="form-control"
												path="text" name="text" type="text" placeholder="Text" /><br />
											<div class="error">
												<sf:errors path="text"></sf:errors>
											</div></td>
									</tr>


									<tr>
										<td><input class="btn btn-default" id="control"
											value="Send message" type="submit" /></td>
									</tr>
								</table>
							</sf:form>
							<div>
								<br> <b>OR</b>
							</div>
							${user.email} <br>${user.phone}
						</div>
					</c:otherwise>
				</c:choose>

			</sec:authorize>
			<sec:authorize access="!isAuthenticated()">
				<div>
					Log in to contact the user<br> <a
						class="btn btn-primary btn-lg" data-toggle="collapse"
						href="#nav-collapse2">Log in</a>
				</div>
			</sec:authorize>
		</div>
	</div>

	<div class="house-object-info" id="address_box">
		<div class="house-object-header">
			<h1>My House</h1>
		</div>

		<div id="house-summary-items">
			<c:choose>
				<c:when test="${hasImage}">
					<img width="500px" height="500px"
						src="${pageContext.request.contextPath}/static/images/housepictures/<c:url value="${house.id}"/>.png">
					<br>
					<a href="<c:url value="/house/${house.id}"/>">${house.address}</a>
					<br>
					<br>Rooms available: ${house.rooms} <br>
					<br>Rent per
				month: ${house.rent} <br>${house.description}
				</c:when>

				<c:when test="${hasHouse}">
					<img
						src="https://maps.googleapis.com/maps/api/streetview?size=800x500&location=<c:url value="${house.lat}"/>,<c:url value="${house.lng}"/>">
					<br> *Images taken from Google Street View may not be 100%
				accurate. <br>
					<br>
					<a href="<c:url value="/house/${house.id}"/>">${house.address}</a>
					<br>
					<br>Rooms available: ${house.rooms} <br>
					<br>Rent per
				month: ${house.rent} <br>${house.description}
				</c:when>
				<c:otherwise>
					<br>No house available</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>