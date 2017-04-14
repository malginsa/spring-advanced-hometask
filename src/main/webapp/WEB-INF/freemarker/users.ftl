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
<a href="http://localhost:8080/mainMenu">Main Page</a><br>
<hr>
<table>
    <tr>
        <th>FirstName</th>
        <th>LastName</th>
        <th>EMail</th>
        <th>Birthday</th>
        <th>Password</th>
        <th>Roles</th>
    </tr>
    <#list users as user>
        <tr>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.email}</td>
            <td>${user.bithday}</td>
            <td>${user.password}</td>
            <td><#list user.roles as role>${role}, </#list></td>
        </tr>
    </#list>
</table>
</body>
</html>
