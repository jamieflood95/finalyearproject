
<form method="POST" action='${pageContext.request.contextPath}/houseupload'
    enctype="multipart/form-data">


    Please select a file to upload : <input type="file" name="file" />
    <input type="submit" value="upload" accept="image/*" />

</form>