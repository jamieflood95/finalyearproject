<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>


<script type="text/javascript">

function onLoad() {
	$("#password").keyup(checkPasswordsMatch);
	$("#confirmPassword").keyup(checkPasswordsMatch);
	
	$("#details").submit(canSubmit);
}

function canSubmit() {
	var password = $("#password").val();
	var confirmPassword = $("#confirmPassword").val();
	
	if(password != confirmPassword) {
		alert("Passwords do not match");
		return false
	} else {
		return true;
	}
}

function checkPasswordsMatch() {
	var password = $("#password").val();
	var confirmPassword = $("#confirmPassword").val();
	
	if(password.length > 3 || confirmPassword > 3) {
		if(password == confirmPassword) {
			$("#matchpass").text("Passwords match");
			$("#matchpass").addClass("valid");
			$("#matchpass").removeClass("error");
		}
		else {
			$("#matchpass").text("Passwords do not match");
			$("#matchpass").addClass("error");
			$("#matchpass").removeClass("valid");
		}
	}
	
	
}

$(document).ready(onLoad);


</script>