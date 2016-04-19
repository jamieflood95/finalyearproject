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

		<c:choose>
			<c:when test="${hasHouse}">
				<img width="500px" height="500px"
					src="${pageContext.request.contextPath}/static/images/housepictures/<c:url value="${house.id}"/>.png">

			</c:when>
			<c:otherwise>
				<img
					src="https://maps.googleapis.com/maps/api/streetview?size=800x500&location=<c:url value="${house.lat}"/>,<c:url value="${house.lng}"/>">
				<br> *Images taken from Google Street View may not be 100% accurate.
				<br>


			</c:otherwise>
		</c:choose>

		<sec:authorize access="isAuthenticated()">
			<sec:authentication var="principal" property="principal" />
			<c:choose>
				<c:when test="${principal.username==house.username}">
					<a class="btn btn-primary btn-lg"
						href="${pageContext.request.contextPath}/showhouseupload">Upload
						new picture</a>
				</c:when>
			</c:choose>
		</sec:authorize>
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
	<div id="house-content"></div>

	<div class="house-object-info" id="address_box">
		<div class="house-object-header">
			<h1>Overview</h1>
		</div>
		<div id="house-summary-items">
			<span class="header-text">${house.description} 
				<br> 
				<br>Wifi
				<c:choose>
					<c:when test="${house.wifi}">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					</c:when>
					<c:otherwise>
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</c:otherwise>
				</c:choose> 
				<br>Furnished 
				<c:choose>
					<c:when test="${house.furnished}">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					</c:when>
					<c:otherwise>
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</c:otherwise>
				</c:choose> 
				<br>Parking
				<c:choose>
					<c:when test="${house.parking}">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					</c:when>
					<c:otherwise>
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</c:otherwise>
				</c:choose> 
				<br>Central heating
				 <c:choose>
					<c:when test="${house.central_heating}">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					</c:when>
					<c:otherwise>
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</c:otherwise>
				</c:choose> 
				<br>House alarm
				<c:choose>
					<c:when test="${house.house_alarm}">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					</c:when>
					<c:otherwise>
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</c:otherwise>
				</c:choose> 
				<br>TV
				<c:choose>
					<c:when test="${house.television}">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					</c:when>
					<c:otherwise>
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</c:otherwise>
				</c:choose> 
				<br>Washing machine
				<c:choose>
					<c:when test="${house.washing_machine}">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					</c:when>
					<c:otherwise>
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</c:otherwise>
				</c:choose> 
				<br>Dryer
				<c:choose>
					<c:when test="${house.dryer}">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					</c:when>
					<c:otherwise>
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</c:otherwise>
				</c:choose> 
				<br>Dishwasher
				<c:choose>
					<c:when test="${house.dishwasher}">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					</c:when>
					<c:otherwise>
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</c:otherwise>
				</c:choose> 
				<br>Microwave
				<c:choose>
					<c:when test="${house.microwave}">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					</c:when>
					<c:otherwise>
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</c:otherwise>
				</c:choose>  
				<br>Garden
				<c:choose>
					<c:when test="${house.garden}">
						<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					</c:when>
					<c:otherwise>
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</c:otherwise>
				</c:choose> 
			</span>
		</div>
	</div>

	<div class="house-object-info" id="address_box">
		<div class="house-object-header">
			<h1>Comments</h1>
		</div>
		<div id="house-summary-items">

			<div class="comments-list">
				<c:forEach var="comments" items="${comments}">

					<div class="comment comment-post bogr1 cleared no-replies">
						<div class="avatar">
							<img
								src="${pageContext.request.contextPath}/static/images/profilepictures/<c:url value="${comments.username}"/>.png"
								style="width: 55px; clip: rect(0px, 55px, 55px, 0px);"
								onerror="this.style.display='none';">
						</div>
						<div class="container">
							<p class="user-info">
								<a href="<c:url value="/user/${comments.username}"/>">${comments.username}</a>,
								${comments.date}
							</p>
							<p class="comment-body comment-text">${comments.text}</p>
						</div>
					</div>
				</c:forEach>
			</div>

			<br> <br>

			<div class="form-group">
				<sf:form id="details" method="post"
					action="${pageContext.request.contextPath}/createcomment/${house.id}"
					commandName="comment">
					<table>



						<tr>
							<td><sf:textarea rows="8" class="form-control" path="text"
									name="text" type="text" placeholder="Add a comment..." /><br />
								<div class="error">
									<sf:errors path="text"></sf:errors>
								</div></td>
						</tr>


						<tr>
							<td><input class="btn btn-default" id="control"
								value="Add comment" type="submit" /></td>
						</tr>
					</table>
				</sf:form>
			</div>
		</div>
	</div>
</div>