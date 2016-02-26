<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


<script type="text/javascript">
	$(document).ready(function() {
		document.f.username.focus();
	});
</script>
<div class="container">
	<h3>Login</h3>

	<c:if test="${param.error != null}">

		<p class="loginerror">Login failed. Check that your username and
			password are correct.</p>
	</c:if>


	<form name='f' action='${pageContext.request.contextPath}/login'
		method='POST'>
		<div class="input-group">
			<input type="text" name="username" placeholder="Username"
				class="form-control" /> <input
				type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="password" name="password" placeholder="Password"
				class="form-control" /> <br>
			
		</div>
			 <div class="col-lg-6">
	    <div class="input-group">
		 <span class="input-group-addon">
		 <input type="checkbox"	name="_spring_security_remember_me" checked="checked" />
		</span>
		</div>
		</div>
		<input class="btn btn-default" name="submit" type="submit"
				value="Login" />

	</form>
	<br />
	<p>
		<a href="<c:url value="/newaccount" />">Register</a>
	</p>
</div>
