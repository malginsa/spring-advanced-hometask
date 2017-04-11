<html>
<head>
    <title>
        Movie Theater Booking Service
    </title>
</head>
<body>
<h3>
    Get tickets
</h3>
<a href="http://localhost:8080/index">Main Page</a><br>
<hr>
<label>Please, enter parameters:</label>
<form action="/book/getTicketsInPdf" method="get">
    <p>Event Name <input name="eventName"></p>
    <p>Date and Time <input name="localDateTime"></p>
    <p><input type="submit"></p>
    <header></header>
</form>
</body>
</html>
