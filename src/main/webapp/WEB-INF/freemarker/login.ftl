<div id="header">
    <h2>Welcome to the Movie Theater Booking Service</h2>
</div>
<div id="content">
    <fieldset>
        <legend>
            ${error?then('Incorrect credentials','')}
            Please, enter your firstname and password:
        </legend>
        <form name="login" action="login" method="post">
            username: <input type="text" name="username"/><br/>
            password: <input type="password" name="password"/><br/>
            <input type="submit" value="Login"/>
        </form>
    </fieldset>
    <br/>
</div>