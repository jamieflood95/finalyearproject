<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="container">

<h2>Create a new task</h2>

<div class="error">
<c:if test="${not empty error}">
   ${error}
</c:if>
</div>

<div class="form-group">
<sf:form id="details" method="post" action="${pageContext.request.contextPath}/createtask" commandName="task">
<table>

<tr><td><sf:input class="form-control" path="name" name="name" type="text" placeholder="Heading" /><br/><div class="error"><sf:errors path="name"></sf:errors></div></td></tr>
<tr><td><sf:input class="form-control" path="details" name="details" type="text" placeholder="Details" /><br/><div class="error"><sf:errors path="details"></sf:errors></div></td></tr>
<tr><td><sf:input class="form-control" path="date_complete" name="date_complete" type="text" placeholder="Complete Date" /><br/><div class="error"><sf:errors path="date_complete"></sf:errors></div></td></tr>

<tr><td><input class="btn btn-default" id="control" value="Create task" type="submit" /></td></tr>
</table>
</sf:form>
</div>
</div>