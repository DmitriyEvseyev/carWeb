<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Authorization</title>
    <link rel="stylesheet" href="css/log.css">
</head>
<body>
<form action="hello-servlet">
    <h1> User name <input name="username" required/></h1>
    <h1> Password <input type="password" name="password" required/></h1>
    <br/>
    <div class="buttons">
        <input type="submit" value="OK"/>
        <input type="reset" value="Cancel"/>
    </div>
</form>
</body>
</html>