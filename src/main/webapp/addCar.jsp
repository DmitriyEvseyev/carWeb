<%--
  Created by IntelliJ IDEA.
  User: VivoB
  Date: 11.02.2024
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AddCar</title>
</head>
<body>

<h3> Add car </h3>
<form action="addCarServlet" method="post">
    <input type="hidden" name="id" id="idRandom"/>

    <label>Name</label><br>
    <input name="name"  required /><br><br>

    <label>Date</label><br>
    <input name="date" placeholder="yyyy-mm-dd" pattern="\d{4}-\d{2}-\d{2}" required /><br><br>

    <label>Color</label><br>
    <input name="color" required /><br><br>

    <label>isAfterCrash</label><br>
    <input name="isAfterCrash" placeholder="false/true" required /><br><br>

    <input type="submit" value="Send"/>
</form>
<script>
    function myFunction(){
        return Math.floor(Math.random() * 100);
    }
    document.getElementById("idRandom").value = myFunction();
</script>
</body>
</html>
