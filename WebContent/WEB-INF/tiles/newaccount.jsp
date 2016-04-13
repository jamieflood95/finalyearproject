<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<div class="container">

<h2>Create new account</h2>
<div class="form-group">
<sf:form id="details" method="post" action="${pageContext.request.contextPath}/createaccount" enctype="multipart/form-data" commandName="user">
<table>
<tr><td class="label"></td><td>Username<sf:input class="form-control"  path="username" name="username" type="text"/><br/><div class="error"><sf:errors path="username"></sf:errors></div></td></tr>
<tr><td class="label"></td><td>Email<sf:input class="form-control" path="email" name="email" type="text"/><br/><div class="error"><sf:errors path="email"></sf:errors></div></td></tr>
<tr><td class="label"></td><td>Password<sf:input id="password" class="form-control"  path="password" name="password" type="password"/><br/><div class="error"><sf:errors path="password"></sf:errors></div></td></tr>
<tr><td class="label"></td><td>Password confirmation<input id="confirmPassword"  class="form-control" name="confirmpassword" type="password"/><div id="matchpass"></div><br/></td></tr>


<tr><td class="label"></td><td>Name<sf:input class="form-control"  path="name" name="name" type="text"/><br/><div class="error"><sf:errors path="name"></sf:errors></div></td></tr>
<tr><td class="label"></td><td>Date of birth<sf:input class="form-control" path="dob" name="dob"  type="date"/><br/><div class="error"><sf:errors path="dob"></sf:errors></div></td></tr>
<tr><td class="label"></td><td>Phone number<sf:input class="form-control" maxlength="10" path="phone" name="phone" type="text"/><br/><div class="error"><sf:errors path="phone"></sf:errors></div></td></tr>
<tr><td class="label"></td><td>Work<sf:input class="form-control" path="work" name="work" type="text"/><br/><div class="error"><sf:errors path="work"></sf:errors></div></td></tr>
<tr><td class="label"></td><td>College<sf:input class="form-control" path="college" name="college" type="text"/><br/><div class="error"><sf:errors path="college"></sf:errors></div></td></tr>
<tr><td class="label"></td><td>Relationship status<sf:select class="form-control" path="relationship" name="relationship"> <option>Single</option>
  <option>In a relationship</option></sf:select><br/><div class="error"><sf:errors path="relationship"></sf:errors></div></td></tr>

<tr><td class="label"></td><td>Hobbies<sf:textarea rows="8" class="form-control"  path="hobbies" name="hobbies" type="text"/><br/><div class="error"><sf:errors path="hobbies"></sf:errors></div></td></tr>
<tr><td class="label">Upload a profile picture</td></tr>
<tr><td class="label"><input type="file" name="file" /></td></tr>

<tr><td><input class="btn btn-default" class="form-control"  value="Create Account" type="submit" /></td></tr>
</table>
</sf:form>
</div>
</div>
