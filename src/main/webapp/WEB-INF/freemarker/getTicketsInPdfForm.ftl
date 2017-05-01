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
<a href="http://localhost:8080/mainMenu">Main Page</a><br>
<hr>
<label>Please, enter parameters:</label>
<!--<form action="/booking/getTicketsInPdf" method="get" accept="application/pdf">-->
<form action="/booking" method="get" accept="application/pdf">
    <p>Event Name <input name="eventName"></p>
    <p>Date and Time <input name="localDateTime"></p>
    <p><input type="submit"></p>
    <header></header>
</form>
</body>
</html>
