<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="container">

<h2>Create a new contact</h2>

<div class="error">
<c:if test="${not empty error}">
   ${error}
</c:if>
</div>

<div class="form-group">
<sf:form id="details" method="post" action="${pageContext.request.contextPath}/createcontact" commandName="contact">
<table>

<tr><td><sf:input class="form-control" path="name" name="name" type="text" placeholder="Name" /><br/><div class="error"><sf:errors path="name"></sf:errors></div></td></tr>
<tr><td><sf:input class="form-control" path="email" name="email" type="text" placeholder="Email" /><br/><div class="error"><sf:errors path="email"></sf:errors></div></td></tr>
<tr><td><sf:input class="form-control" path="phone" name="phone" type="text" placeholder="Phone number" /><br/><div class="error"><sf:errors path="phone"></sf:errors></div></td></tr>
<tr><td><sf:input class="form-control" path="role" name="role" type="text" placeholder="Role (electrician, plumber etc.)" /><br/><div class="error"><sf:errors path="role"></sf:errors></div></td></tr>


<tr><td><input class="btn btn-default" id="control" value="Create contact" type="submit" /></td></tr>
</table>
</sf:form>
</div>
</div>