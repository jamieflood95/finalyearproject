<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<br>
<br>

<div class="container">
	<div class="row row-offcanvas row-offcanvas-right">

		<div class="col-xs-12 col-sm-9">
			<p class="pull-right visible-xs">
				<button type="button" class="btn btn-primary btn-xs"
					data-toggle="offcanvas"></button>
			</p>


			<sec:authorize access="!isAuthenticated()">
				<div style="background-color: transparent !important;"
					class="jumbotron">
					<h1>Welcome!</h1>
					<p>Please log in or register below</p>
					<p>
						<a class="btn btn-primary btn-lg" data-toggle="collapse"
							href="#nav-collapse2">Log in</a> <a
							class="btn btn-primary btn-lg"
							href="<c:url value="/newaccount"/>">Register</a>
				</div>
			</sec:authorize>

			<sec:authorize access="isAuthenticated()">
				<div style="background-color: #2F72FF !important;" class="jumbotron">
					<div class="home-text"><h2>Search for a house</h2></div>
					<form method="get"
						action="${pageContext.request.contextPath}/searchrent"
						commandName="house" role="search">
						<div class="form-group">
							<table>
								<tr>
									<td class="bottom-padding"><input type="text" class="form-control"
										placeholder="Town, county, etc." id="txt" name="searchAddress"></td>
								</tr>
								<tr>
									<td class="bottom-padding"><input type="text" class="form-control"
										placeholder="Min Rent" id="txt" name="searchMinRent">
									</td>
								</tr>
								<tr>
									<td class="bottom-padding"><input type="text" class="form-control"
										placeholder="Max Rent" id="txt" name="searchMaxRent"></td>
								</tr>
								<tr>
									<td class="bottom-padding"><input type="text" class="form-control"
										placeholder="Min Rooms" id="txt" name="searchMinRooms"></td>
								</tr>
								<tr>
									<td class="bottom-padding"><input type="text" class="form-control"
										placeholder="Max Rooms" id="txt" name="searchMaxRooms"></td>
								</tr>
								<tr>
									<td class="label"></td>
									<td><input class="btn btn-primary btn-lg sharp"
										class="control" value="Search" type="submit" /></td>
								</tr>
							</table>
						</div>
					</form>
				</div>
			</sec:authorize>


			<div class="row">
				<c:forEach var="houses" items="${houses}">
					<div class="col-xs-6 col-lg-4">
						<a href="<c:url value="/house/${houses.id}"/>"> <img
							class="images"
							src="https://maps.googleapis.com/maps/api/streetview?size=800x500&location=<c:url value="${houses.lat}"/>,<c:url value="${houses.lng}"/>">
						</a> <br> <a href="<c:url value="/house/${houses.id}"/>"><div
								class="link">${houses.address}</div></a> <br>&euro;
						<c:out value="${houses.rent}"></c:out>

					</div>
				</c:forEach>
			</div>


		</div>


		<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar">
			<div class="list-group">
				<a class="list-group-item active">Recent Houses</a>
				<c:forEach var="house" items="${house}">
					<a href="<c:url value="/house/${house.id}"/>"
						class="list-group-item">${house.address}, &#0128;${house.rent}</a>
				</c:forEach>
			</div>
		</div>
	</div>
</div>


