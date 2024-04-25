<%--
  Created by IntelliJ IDEA.
  User: VivoB
  Date: 23.02.2024
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit dealer</title>
    <link rel="stylesheet" href="css/dealercss/editDealer.css">
</head>
<body>
<h2> Edit dealer </h2>
<form action="editDealerServlet" method="post">
    <input type="hidden" value="${dealer.id}" name="id"/>
    <table class="tab">
        <tr>
            <td> Name</td>
            <td><input name="name" id="name" value="${dealer.name}" required/></td>
        </tr>
        <tr>
            <td> Adress</td>
            <td><input name="address" value="${dealer.address}" required/></td>
        </tr>
    </table>
    <br/>
    <div class="but">
        <input type="submit"   value="Ok"/>
        <input type="reset" value="Cancel"/>
    </div>
</form>
</body>
</html>