<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="container">

<h2>Send a message</h2>

<div class="error">
<c:if test="${not empty error}">
   ${error}
</c:if>
</div>

<div class="form-group">
<sf:form id="details" method="post" action="${pageContext.request.contextPath}/sendmessage" commandName="message">
<table>

<tr><td><sf:input class="form-control" path="recipient" name="recipient" type="text" placeholder="Recipient" /><br/><div class="error"><sf:errors path="recipient"></sf:errors></div></td></tr>
<tr><td><sf:textarea class="form-control" rows="8" path="text" name="text" type="text" placeholder="Text" /><br/><div class="error"><sf:errors path="text"></sf:errors></div></td></tr>


<tr><td><input class="btn btn-default" id="control" value="Send message" type="submit" /></td></tr>
</table>
</sf:form>
</div>
</div>