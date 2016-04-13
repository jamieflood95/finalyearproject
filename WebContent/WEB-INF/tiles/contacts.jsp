<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container">
	<h2>Contacts</h2>

	<table class="table table-hover">
		<thead class="thead-default">
			<tr>
				<th>Name</th>
				<th>Role</th>
				<th>Phone</th>
				<th>Email</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="contacts" items="${contacts}">
				<tr>
					<td><a href="<c:url value="/contacts/${contacts.id}"/>">${contacts.name}</a></td>
					<td>${contacts.role}</td>
					<td>${contacts.phone}</td>
					<td>${contacts.email}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="${pageContext.request.contextPath}/newcontact"
		class="btn btn-primary btn-lg" id="newmessage">New contact</a>
</div>