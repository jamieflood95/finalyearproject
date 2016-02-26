<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script>
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip(); 
});
</script>

<div class="container">
	<c:set var="string2" value="${fn:toUpperCase(user.username)}" />
	<h3>${string2}</h3>
	<sec:authorize access="isAuthenticated()">
		<sec:authentication var="principal" property="principal" />
		<c:choose>
			<c:when test="${principal.username==user.username}">
				<br>
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
					<input class="btn btn-default" class="control" value="Add roomie (this will delete your house)"
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
										<td><a href="<c:url value="/user/${house.username}"/>">${house.username}</a></td>
									</tr>

									<tr>
										<td><sf:input class="form-control" path="recipient"
												name="recipient" type="hidden" placeholder="Recipient" /><br />
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
			<img
				src="https://maps.googleapis.com/maps/api/streetview?size=800x500&location=<c:url value="${house.lat}"/>,<c:url value="${house.lng}"/>">
			<br> *Images taken from Google Street View may not be 100%
			accurate. <br> <br> <a
				href="<c:url value="/house/${house.id}"/>">${house.address}</a><br>
			<br>${house.description}
		</div>
	</div>
	
	<div class="house-object-info" id="address_box">
		<div class="house-object-header">
			<h1>Roomies</h1>
		</div>
		<div id="house-summary-items">
			<c:forEach var="roomies" items="${roomies}">
				<a href="<c:url value="/user/${roomies.roomie_username}"/>">${roomies.roomie_username}</a>
				<form
						action="${pageContext.request.contextPath}/deleteroomie/${roomies.roomie_username}">
						<input type="submit" value="Remove roomie" class="btn btn-default">
					</form> <br />
		</c:forEach>
		</div>
	</div>
</div>