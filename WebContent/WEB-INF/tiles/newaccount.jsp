<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<div class="container">

<h2>Create new account</h2>

<sf:form id="details" method="post" action="${pageContext.request.contextPath}/createaccount" commandName="user">
<table>
<tr><td class="label">Username: </td><td><sf:input class="control" path="username" name="username" type="text" placeholder="Username" /><br/><div class="error"><sf:errors path="username"></sf:errors></div></td></tr>
<tr><td class="label">Email: </td><td><sf:input class="control" path="email" name="email" type="text" placeholder="Email"/><br/><div class="error"><sf:errors path="email"></sf:errors></div></td></tr>
<tr><td class="label">Password: </td><td><sf:input id="password" class="control" path="password" name="password" type="password" placeholder="Password"/><br/><div class="error"><sf:errors path="password"></sf:errors></div></td></tr>
<tr><td class="label">Confirm Password: </td><td><input id="confirmPassword"  class="control" name="confirmpassword" type="password" placeholder="Confirm Password"/><div id="matchpass"></div><br/></td></tr>


<tr><td class="label">Name: </td><td><sf:input class="control" path="name" name="name" type="text" placeholder="Name"/><br/><div class="error"><sf:errors path="name"></sf:errors></div></td></tr>
<tr><td class="label">Date of birth: </td><td><sf:input class="control" path="dob" name="dob"  type="date" placeholder="Date of birth"/><br/><div class="error"><sf:errors path="dob"></sf:errors></div></td></tr>
<tr><td class="label">Phone: </td><td><sf:input class="control"  maxlength="10" path="phone" name="phone" type="text" placeholder="Phone Number"/><br/><div class="error"><sf:errors path="phone"></sf:errors></div></td></tr>
<tr><td class="label">Work: </td><td><sf:input class="control" path="work" name="work" type="text" placeholder="Work"/><br/><div class="error"><sf:errors path="work"></sf:errors></div></td></tr>
<tr><td class="label">College: </td><td><sf:input class="control" path="college" name="college" type="text" placeholder="College"/><br/><div class="error"><sf:errors path="college"></sf:errors></div></td></tr>
<tr><td class="label">Relationship status: </td><td><sf:select class="control" path="relationship" name="relationship"> <option>Single</option>
  <option>In a relationship</option></sf:select><br/><div class="error"><sf:errors path="relationship"></sf:errors></div></td></tr>

<tr><td class="label">Hobbies: </td><td><sf:input class="control" path="hobbies" name="hobbies" type="text" placeholder="Hobbies"/><br/><div class="error"><sf:errors path="hobbies"></sf:errors></div></td></tr>



<tr><td class="label"> </td><td><input class="btn btn-default" id="control" value="Create Account" type="submit" /></td></tr>
</table>
</sf:form>
</div>
