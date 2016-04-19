<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>



<script>
$(document).ready(
        function() {
            setInterval(function() {
                $("#message").load(location.href + " #message");
            }, 3000);
        });
</script>

<div class="container">
	<div id="message">
		<sec:authorize access="isAuthenticated()">
			<sec:authentication var="principal" property="principal" />
			<div class="message-title">
				<c:set var="recipient" scope="session" value="${recipient}" />
				<h2>
					<a class="message-username" href="<c:url value="/user/${recipient}"/>">${recipient}</a>
				</h2>
			</div>
			<c:forEach var="messages" items="${messages}">
				<c:choose>
					<c:when test="${principal.username==messages.username}">
						<div class="Area">
							<div class="L">
								<div class="text L textL">${messages.text}</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="Area">
							<div class="R">
								<div class="text R textR">${messages.text}</div>
							</div>
						</div>
					</c:otherwise>

				</c:choose>
			</c:forEach>
		</sec:authorize>

		<div class="error">
			<c:if test="${not empty error}">
   ${error}
</c:if>
		</div>
	</div>
	<div class="chat-form">
		<div class="form-group">
			<sf:form id="details" method="post"
				action="${pageContext.request.contextPath}/sendmessage"
				commandName="message">
				<table>

					<tr>
						<td><sf:input class="form-control" path="recipient"
								name="recipient" type="hidden" placeholder="Recipient" /><br />
							<div class="error">
								<sf:errors path="recipient"></sf:errors>
							</div></td>
					</tr>


					<tr>
						<td><sf:textarea rows="4" cols="80" class="form-control"
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
	</div>
</div>