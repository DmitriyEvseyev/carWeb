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
    <link rel="stylesheet" href="css/dealercss/dealer.css">
</head>
<body>
<h1> CarDealerships </h1>
<form id="fo">
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
            console.log("checks" + checks);

            function selectedCheckBox() {
                count = 0;
                for (i = 0; i < checks.length; i++) {
                    console.log(checks[i]);
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
        <input type="submit" formmethod="post" formaction="selectDealerServlet" id="sel" disabled value="select"/>
        <input type="submit" formmethod="get" formaction="delDealerServlet" id="del" disabled value="delete"
               onclick="return delCar()"/>
        <input type="submit" formmethod="get" formaction="jsp/dealerjsp/editDealer.jsp" id="edit" disabled
               value="edit" onclick="editCar()"/>
        <input type="submit" formmethod="get" formaction="addDealerServlet" value="add"/>
    </div>
</form>
<script>
    function delCar() {
        if (confirm("Are you really want to delete dealer with cars?")) {
            return true;
        } else {
            return false;
        }
    }

    function editCar() {
        const form = document.getElementById("fo");
        check = document.getElementsByName("check");
        var nameDealer;
        var adressDealer;
        for (i = 0; i < check.length; i++) {
            if (check[i].checked) {
                row = check[i].parentNode.parentNode;
                nameDealer = row.cells[1].innerHTML;
                adressDealer = row.cells[2].innerHTML;
            }
        }
        var inputName = document.createElement("input");
        inputName.setAttribute("name", "nameD");
        inputName.setAttribute("value", nameDealer);
        inputName.setAttribute("type", "hidden")
        form.appendChild(inputName);

        var inputAdress = document.createElement("input");
        inputAdress.setAttribute("name", "adressD");
        inputAdress.setAttribute("value", adressDealer);
        inputAdress.setAttribute("type", "hidden");
        form.appendChild(inputAdress);

        form.submit();
    }

</script>
</body>
</html>
