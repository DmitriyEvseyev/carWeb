<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Authorization</title>
    <style>
        .button-container-div {
        text-align: center;
    }
    </style>
</head>
<body>
<form action="hello-servlet">
    <h1 align="center"> User name <input name="username" required /></h1>
    <h1 align="center"> Password <input type="password" name="password" required /></h1>
    <br/>
    <div class="button-container-div">
        <button> OK </button>
    </div>
</form>
</body>
</html>