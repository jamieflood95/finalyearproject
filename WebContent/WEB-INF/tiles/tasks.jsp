<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container">
	<h2>Tasks</h2>

	<table class="table table-hover">
		<thead class="thead-default">
			<tr>
				<th>Name</th>
				<th>Details</th>
				<th>Date Created</th>
				<th>Complete Date</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tasks" items="${tasks}">
				<tr>
					<td><a href="<c:url value="/tasks/${tasks.id}"/>">${tasks.name}</a></td>
					<td>${tasks.details}</td>
					<td>${tasks.date_created}</td>
					<td>${tasks.date_complete}</td>
					<td>${tasks.username}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="${pageContext.request.contextPath}/newtask"
		class="btn btn-primary btn-lg" id="newtask">New task</a>
</div>