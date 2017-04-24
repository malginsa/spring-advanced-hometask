<html>
<head>
    <title>
        Movie Theater Booking Service
    </title>
</head>
<body>
<h3>
    Your account refilling
</h3>
<a href="http://localhost:8080/mainMenu">Main Page</a><br>
<hr>
<label>Please, enter amount to refill:</label>
<form action="/account/refill" method="get">
    <p><input name="amount" type="number" step="any"></p>
    <p><input type="submit"></p>
</form>
</body>
</html>
