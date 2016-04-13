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


	<div class="form-group">


		<form name='f' action='${pageContext.request.contextPath}/login'
			method='POST'>
			<table>
				<tr>
					<td class="label">Username<input type="text" name="username"
						class="form-control" /> <input
						type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					</td>
				</tr>
				<tr>
					<td class="label">Password<input type="password" name="password" 
					class="form-control" /> <br></td>
				</tr>
				<tr>
					<td class="label"><input type="checkbox"
						name="_spring_security_remember_me" checked="checked" />Remember me</td>
				</tr>
				<tr>
					<td class="label"><input class="btn btn-default" name="submit"
						type="submit" value="Login" /></td>
				</tr>

			</table>
		</form>
	</div>

	<br />
	<p>
		<a href="<c:url value="/newaccount" />">Register</a>
	</p>
</div>
