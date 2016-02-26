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
		<a href="${pageContext.request.contextPath}/newmessage"
			class="btn btn-primary btn-lg" id="newmessage">New message</a>
		<h2>Conversations</h2>
		<c:forEach var="foo" items="${foo}">
			<div class="conversations">
				<a href="<c:url value="/message/${foo}"/>">${foo}</a>
			</div>
		</c:forEach>
	</div>
</div>