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

<form class="sel">
    Filter by: <select name="column">
    <option>name</option>
    <option>address</option>
</select>
    <input type="search" name="pattern" required &#]128269/>
    <button type="submit" formmethod="get" formaction="searchDealerServlet">
        <span>&#128269</span>
    </button>
</form>

<form id="fo">
    <table class="tab">
        <tr>
            <td><h3></h3></td>
            <td>
                <h3> Name
                    <form>
                        <input type="hidden" value="1" name="sort"/>
                        <button class="button1" type="submit" formmethod="get" formaction="sortDealerServlet">
                            <span>▲</span>
                        </button>
                    </form>
                    <form>
                        <input type="hidden" value="2" name="sort"/>
                        <button class="button2" type="submit" formmethod="get" formaction="sortDealerServlet">
                            <span>▼</span>
                        </button>
                    </form>
                </h3>
            </td>
            <td>
                <h3> Address
                    <form>
                        <input type="hidden" value="3" name="sort"/>
                        <button class="button3" type="submit" formmethod="get" formaction="sortDealerServlet">
                            <span>▲</span>
                        </button>
                    </form>
                    <form>
                        <input type="hidden" value="4" name="sort"/>
                        <button class="button4" type="submit" formmethod="get" formaction="sortDealerServlet">
                            <span>▼</span>
                        </button>
                    </form>
                </h3>
            </td>
        </tr>
        <c:forEach var="dealer" items="${carDealerships}">
            <tr>
                <td><input type="checkbox" name="idDealer" value="${dealer.id}"/></td>
                <td>${dealer.name}</td>
                <td>${dealer.adress}</td>
            </tr>
        </c:forEach>
        <script>
            checks = document.getElementsByName("idDealer");
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
        <input type="submit" formmethod="get" formaction="selectDealerServlet" id="sel" disabled value="Select"/>
        <input type="submit" formmethod="get" formaction="delDealerServlet" id="del" disabled value="Delete"
               onclick="return delCar()"/>
        <input type="submit" formmethod="get" formaction="editDealerServlet" id="edit" disabled value="Edit"/>
        <input type="submit" formmethod="get" formaction="addDealerServlet" value="Add"/>
        <input type="submit" formmethod="get" formaction="dealershipServlet" value="Reset"/>
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
</script>
</body>
</html>
