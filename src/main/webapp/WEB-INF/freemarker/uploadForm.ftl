<html>
<head>
    <title>
        Movie Theater Booking Service
    </title>
</head>
<body>
<h3>
    Upload files
</h3>
<a href="http://localhost:8080/index">Main Page</a><br>
<hr>
<label>Please, select json-files</label>
<form class="form-horizontal"
      enctype="multipart/form-data"
      action="/doUploadMultipartFile"
      method="post">
    <div class="form-actions">
        <p><input type="file" name="users"/> </p>
        <p><input type="file" name="events"/> </p>
        <button class="btn btn-primary" type="submit"> Upload them</button>
    </div>
</form>
<hr>
</body>
</html>
