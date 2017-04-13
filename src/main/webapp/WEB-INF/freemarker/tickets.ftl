<html>
<head>
    <title>
        Movie Theater
    </title>
</head>
<body>
<h3>
    List of tickets of ${userName} for ${eventName} on ${localDateTime}
</h3>
<a href="http://localhost:8080/mainMenu">Main Page</a><br>
<hr>
<table>
    <tr>
        <th>Seat</th>
        <th>Price</th>
    </tr>
    <#list tickets as ticket>
        <tr>
            <td>${ticket.seat}</td>
            <td>${ticket.price}</td>
        </tr>
    </#list>
</table>
</body>
</html>
