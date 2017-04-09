<html>
<title>
    Movie Theater
</title>
<head>
    <h3>
        List of tickets
    </h3>
</head>
<body>
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
