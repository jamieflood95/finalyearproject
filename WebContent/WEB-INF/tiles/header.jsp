<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-inverse">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar-collapse-3">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${pageContext.request.contextPath}/">myroom</a>
		</div>

		<div class="collapse navbar-collapse" id="navbar-collapse-3">
			<ul class="nav navbar-nav navbar-right">


				<!--  HOME BUTTON -->
				<li><a href="${pageContext.request.contextPath}/">Home</a></li>


				<!--  HOUSE INFORMATION -->
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">House<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/houses">Houses</a></li>
						<li><sec:authorize access="isAuthenticated()">
								<a href="${pageContext.request.contextPath}/createhouse">Create/Edit
									House</a>
							</sec:authorize></li>
						<li><a href="${pageContext.request.contextPath}/map">Map</a></li>

					</ul></li>


				<!--  ROOMIE INFORMATION -->
				<li><sec:authorize access="isAuthenticated()">
						<a href="${pageContext.request.contextPath}/roomies">Roomies</a>
					</sec:authorize></li>


				<!--  LOGIN/REGISTER INFORMATION -->
				<sec:authorize access="!isAuthenticated()">
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Profile<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><sec:authorize access="!isAuthenticated()">
									<a class="login" data-toggle="collapse" href="#nav-collapse2">Login</a>
								</sec:authorize></li>
							<li><sec:authorize access="!isAuthenticated()">
									<a class="login" href="<c:url value="/newaccount"/>">Register</a>
								</sec:authorize></li>
						</ul></li>
				</sec:authorize>


				<!--  USER INFORMATION -->
				<sec:authorize access="isAuthenticated()">
					<sec:authentication var="principal" property="principal" />
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">${principal.username}<span
							class="caret"></span></a>

						<ul class="dropdown-menu">
							<li class="username"><sec:authorize
									access="isAuthenticated()">
									<sec:authentication var="principal" property="principal" />
									<a href="<c:url value="/user/${principal.username}"/>">My
										profile</a>
								</sec:authorize></li>
							<li><sec:authorize access="hasRole('ROLE_ADMIN')">
									<a href="${pageContext.request.contextPath}/admin">Admin</a>
								</sec:authorize></li>
							<li><sec:authorize access="isAuthenticated()">
									<a href="${pageContext.request.contextPath}/messages">Messages</a>
								</sec:authorize></li>
							<li><sec:authorize access="isAuthenticated()">
									<c:url var="logoutUrl" value="/logout" />
									<a href="${logoutUrl}">Log out</a>
								</sec:authorize></li>

						</ul></li>
				</sec:authorize>


				<li><a class="btn btn-default btn-outline btn-circle"
					data-toggle="collapse" href="#nav-collapse3" aria-expanded="false"
					aria-controls="nav-collapse3">Search</a></li>
			</ul>

			<div class="collapse nav navbar-nav nav-collapse" id="nav-collapse3">
				<form class="navbar-form navbar-right" method="get"
					action="${pageContext.request.contextPath}/searchroomie"
					commandName="user" role="search">
					<div class="form-group">


						<input type="text" name="searchString" placeholder="Name of user"
							class="form-control" />
					</div>
					<button type="submit" class="btn btn-danger">
						<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					</button>
				</form>
			</div>

			<div class="collapse nav navbar-nav nav-collapse slide-down"
				id="nav-collapse2">
				<form class="navbar-form navbar-right form-inline" role="form"
					name='f' action='${pageContext.request.contextPath}/login'
					method='POST'>
					<div class="form-group">
						<input type="text" name="username" placeholder="Username"
							class="form-control" autofocus required /> <input type="hidden"
							name="${_csrf.parameterName}" value="${_csrf.token}" />
					</div>
					<div class="form-group">
						<input type="password" name="password" placeholder="Password"
							class="form-control" required />
					</div>
					<button name="submit" type="submit" value="Login"
						class="btn btn-success">Log in</button>
				</form>
			</div>
		</div>
	</div>
</nav>
