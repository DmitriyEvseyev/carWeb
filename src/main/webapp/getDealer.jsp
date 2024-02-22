<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: VivoB
  Date: 21.02.2024
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CarDealership</title>
    <link rel="stylesheet" href="css/dealer.css">
</head>
<body>
<h1> CarDealerships </h1>
<form>
    <table class="tab">
        <tr>
            <td><h3></h3></td>
            <td><h3> Name </h3></td>
            <td><h3> Adress </h3></td>
        </tr>
        <c:forEach var="dealer" items="${carDealerships}">
            <tr>
                <td><input type="checkbox" name="check" value="${dealer.id}"/></td>
                <td>${dealer.name}</td>
                <td>${dealer.adress}</td>
            </tr>
        </c:forEach>
        <script>
            checks = document.getElementsByName("check");

            function selectedCheckBox() {
                count = 0;
                for (i = 0; i < checks.length; i++) {
                    if (checks[i].checked) {
                        count++;
                    }
                }
                if (count === 0) {
                    document.getElementById("del").disabled = true;
                    document.getElementById("edit").disabled = true;
                    document.getElementById("sel").disabled = true;
                }
                if (count === 1) {
                    document.getElementById("del").disabled = false;
                    document.getElementById("edit").disabled = false;
                    document.getElementById("sel").disabled = false;
                }
                if (count > 1) {
                    document.getElementById("del").disabled = false;
                    document.getElementById("edit").disabled = true;
                    document.getElementById("sel").disabled = true;
                }
            }

            for (i = 0; i < checks.length; i++) {
                checks[i].addEventListener("click", selectedCheckBox);
            }
        </script>
    </table>
    <br/>
    <div class="buttons">
        <input type="submit" formmethod="post" formaction="delCarServlet" id="sel" disabled value="select"/>
        <input type="submit" formmethod="post" formaction="delCarServlet" id="del" disabled value="delete"/>
        <input type="submit" formmethod="get" formaction="editCarServlet" id="edit" disabled value="edit"/>
        <input type="submit" formmethod="get" formaction="addCarServlet" value="add"/>
    </div>
</form>
</body>
</html>
