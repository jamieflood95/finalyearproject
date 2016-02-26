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
				<div style="background-color: #2F72FF  !important;" class="jumbotron">
					<h2>Search for accommodation</h2>
					<p>
						<form method="get"
						action="${pageContext.request.contextPath}/search"
						commandName="house" role="search">
						<select class="form-control" id="txt" name="searchString">
							<option value="antrim">Antrim</option>
							<option value="armagh">Armagh</option>
							<option value="carlow">Carlow</option>
							<option value="cavan">Cavan</option>
							<option value="clare">Clare</option>
							<option value="cork">Cork</option>
							<option value="derry">Derry</option>
							<option value="donegal">Donegal</option>
							<option value="down">Down</option>
							<option value="dublin">Dublin</option>
							<option value="fermanagh">Fermanagh</option>
							<option value="galway">Galway</option>
							<option value="kerry">Kerry</option>
							<option value="kildare">Kildare</option>
							<option value="kilkenny">Kilkenny</option>
							<option value="laois">Laois</option>
							<option value="leitrim">Leitrim</option>
							<option value="limerick">Limerick</option>
							<option value="longford">Longford</option>
							<option value="louth">Louth</option>
							<option value="mayo">Mayo</option>
							<option value="meath">Meath</option>
							<option value="monaghan">Monaghan</option>
							<option value="offaly">Offaly</option>
							<option value="roscommon">Roscommon</option>
							<option value="sligo">Sligo</option>
							<option value="tipperary">Tipperary</option>
							<option value="tyrone">Tyrone</option>
							<option value="waterford">Waterford</option>
							<option value="westmeath">Westmeath</option>
							<option value="wexford">Wexford</option>
							<option value="wicklow">Wicklow</option>
						</select>
						<button type="submit" class="btn btn-danger">
							<span class="glyphicon glyphicon-search"></span>
						</button>
						</form>

					
					
				
				</div>
			</sec:authorize>


			<div class="row">
				<c:forEach var="houses" items="${houses}">
					<div class="col-xs-6 col-lg-4">
						<a href="<c:url value="/house/${houses.id}"/>"> <img
							src="https://maps.googleapis.com/maps/api/streetview?size=800x500&location=<c:url value="${houses.lat}"/>,<c:url value="${houses.lng}"/>">
						</a> <br>
						 <a href="<c:url value="/house/${houses.id}"/>"><div class="link">${houses.address}</div></a>
						<br>&euro;
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


