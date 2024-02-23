<%--
  Created by IntelliJ IDEA.
  User: VivoB
  Date: 23.02.2024
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AddDealer</title>
    <link rel="stylesheet" href="css/dealercss/addDealer.css">
</head>
<body>
<h2> Add dealer </h2>
<form action="addDealerServlet" method="post">
    <input type="hidden" name="id" id="idRandom"/>

    <table class="tab">
        <tr>
            <td> Name</td>
            <td><input name="name" required/></td>
        </tr>
        <tr>
            <td> Adress</td>
            <td><input name="adress" required/></td>
        </tr>
    </table>
    <br/>
    <div class="but">
        <input type="submit" value="Send"/>
        <input type="reset" value="Cancel"/>
    </div>
</form>
<script>
    function myFunction() {
        return Math.floor(Math.random() * 100);
    }

    document.getElementById("idRandom").value = myFunction();
</script>
</body>
</html>

