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
			<h1>${house.address}</h1>
		</div>
		<div id="house-summary-items">
			<div id="house-price">&euro;${house.rent}</div>
			<span class="header-text">${house.rooms} Room(s)</span>
		</div>
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
						This is your house
						<br>
						<a class="btn btn-primary btn-lg"
							href="${pageContext.request.contextPath}/createhouse">Edit</a>
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
	<div id="house-content">
		<img
			src="https://maps.googleapis.com/maps/api/streetview?size=800x500&location=<c:url value="${house.lat}"/>,<c:url value="${house.lng}"/>">
		<br> *Images taken from Google Street View may not be 100%
		accurate.
	</div>

	<div class="house-object-info" id="address_box">
		<div class="house-object-header">
			<h1>Overview</h1>
		</div>
		<div id="house-summary-items">
			<span class="header-text">${house.description}</span>
		</div>
	</div>
</div>

