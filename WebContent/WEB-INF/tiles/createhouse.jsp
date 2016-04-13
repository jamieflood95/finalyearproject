<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript">
<!--

function onDeleteClick(event) {
	
	var doDelete = confirm("Are you sure you want to delete this house?");
	
	if(doDelete == false) {
		event.preventDefault();
	}
}

function onReady() {
	$("#delete").click(onDeleteClick);
}

$(document).ready(onReady);
//-->
</script>

<div class="container">

<h2>Create/edit house</h2>
<div class="form-group">

<sf:form id="details" method="post" action="${pageContext.request.contextPath}/docreate"	commandName="house">
<table class="house-search">
<sf:input type="hidden" name="id" path="id" />
<tr><td class="label"></td><td>Address<sf:input rows="4"  class="form-control" path="address" name="address" type="text"></sf:input><br /><sf:errors path="address" cssClass="error"></sf:errors></td></tr>
<tr><td class="label"></td><td>Rent per month<sf:input rows="4"  class="form-control" path="rent" name="rent" type="text"></sf:input><br /><sf:errors path="rent" cssClass="error"></sf:errors></td></tr>
<tr><td class="label"></td><td>Rooms available<sf:select class="form-control" path="rooms" name="rooms"><option>1</option><option>2</option><option>3</option><option>4</option><option>5</option><option>6</option><option>7</option><option>8</option><option>9</option><option>10</option></sf:select><br /><sf:errors path="rooms" cssClass="error"></sf:errors></td></tr>
<tr><td class="label"></td><td>Description<sf:textarea rows="8" class="form-control" path="description" name="description" type="text"></sf:textarea><br /><sf:errors path="description" cssClass="error"></sf:errors></td></tr>
<tr><td class="label"></td><td><input class="btn btn-primary btn-lg save" class="control" value="Save" type="submit" /><c:if test="${house.id != 0}"><input class="btn btn-primary btn-lg sharp" class="delete control" name="delete" id="delete" value="Delete" type="submit" /></c:if></td></tr>

</table>
</sf:form>
</div>
</div>