<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script>
	$(document).ready(function() {
		setInterval(function() {
			$("#messages").load(location.href + " #messages");
		}, 3000);
	});
</script>

<div class="container">

	<div id="messages">
	<h2>Conversations</h2>
		<table class="table table-hover">
			<thead class="thead-default">
				<tr>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="foo" items="${foo}">
					<tr>
						<td><img width="50px" height="50px"
				src="${pageContext.request.contextPath}/static/images/profilepictures/<c:url value="${foo}"/>.png"><a href="<c:url value="/message/${foo}"/>">${foo}</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<a href="${pageContext.request.contextPath}/newmessage"
			class="btn btn-primary btn-lg" id="newmessage">New message</a>
</div>