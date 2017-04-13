<html>
<head>
    <title>
        Movie Theater Booking Service
    </title>
</head>
<body>
<h3>
    Book tickets
</h3>
<a href="http://localhost:8080/mainMenu">Main Page</a>
<br>
<hr>
<label>Please, enter parameters for booking:</label>
<form action="/book/doBookTickets" method="get">
    <p>User Id <input name="userId"></p>
    <p>Event Name <input name="eventName"></p>
    <p>Date and Time <input name="localDateTime"></p>
    <p>seat <input name="seat"></p>
    <p><input type="submit"></p>
</form>
</body>
</html>
