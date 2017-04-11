<html>
<head>
    <title>
        Movie Theater
    </title>
    <h3>
        List of users
    </h3>
</head>
<body>
<a href="http://localhost:8080/index">Main Page</a><br>
<hr>
<table>
    <tr>
        <th>FirstName</th>
        <th>LastName</th>
        <th>EMail</th>
        <th>Birthday</th>
    </tr>
    <#list users as user>
        <tr>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.email}</td>
            <td>${user.bithday}</td>
        </tr>
    </#list>
</table>
</body>
</html>
