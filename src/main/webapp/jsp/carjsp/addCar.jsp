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
    <link rel="stylesheet" href="../../css/carcss/addC.css">
</head>
<body>
<h2> Add car </h2>
<form action="addCarServlet" method="post">
    <input type="hidden" name="id" id="idRandom"/>
    <table class="tab">
        <tr>
            <td> Name</td>
            <td><input name="name" required/></td>
        </tr>
        <tr>
            <td> Date</td>
            <td><input type="date" name="date" id="d" required/></td>
        </tr>
        <tr>
            <td> Color</td>
            <td><input name="color" required/></td>
        </tr>
        <tr>
            <td> isAfterCrash</td>
            <td><input type="checkbox" name="isAfterCrash"/></td>
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


    document.getElementById("d").addEventListener("change", function () {
        d = this.value;
        da = new Date(d)
        console.log(da);
    });
</script>
</body>
</html>
