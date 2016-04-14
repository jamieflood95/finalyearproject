<div class="container">
	<form method="POST"
		action='${pageContext.request.contextPath}/houseupload'
		enctype="multipart/form-data">


		Please select an image to upload : <input type="file" name="file" /> <input
			type="submit" value="Upload image" accept="image/*" />

	</form>
</div>