<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Car manager</title>
    <link rel="stylesheet" href="css/carcss/gAll.css">
</head>
<body>
<h1> Cars / Dealer - "${dealer.name}" </h1>
<form class="select">
    <input type="hidden" name="idDealer" value="${dealer.id}"/>
    Filter by: <select name="column" id="mySelect" onChange="check(this);">
    <option>name</option>
    <option>date</option>
    <option>color</option>
    <option>isAfterCrash</option>
</select>
    <div id="div" style="display:block;" >
        <input type="text"  name="pattern"/>
        <button type="submit" formmethod="get" formaction="searchCarServlet">
            <span>&#128269</span>
        </button>
    </div>
    <div id="date" style="display:none;">
        <input type="date" name="startDate"/>
        <input type="date" name="endDate"/>
        <button type="submit" formmethod="get" formaction="searchCarServlet">
            <span>&#128269</span>
        </button>
    </div>
    <script>
        function check(elem) {
            if (elem.selectedIndex == 1) {
                document.getElementById("date").style.display = 'block';
                document.getElementById("div").style.display = 'none';
            } else {
                document.getElementById("date").style.display = 'none';
                document.getElementById("div").style.display = 'block';
            }
        }
    </script>
</form>
<form>
    <input type="hidden" name="idDealer" value="${dealer.id}"/>
    <table class="tab">
        <tr>
            <td><h3></h3></td>
            <td>
                <h3> Name
                    <form>
                        <input type="hidden" value="1" name="sort"/>
                        <input type="hidden" name="idDealer" value="${dealer.id}"/>
                        <button class="button1" type="submit" formmethod="get" formaction="sortCarServlet">
                            <span>▲</span>
                        </button>
                    </form>
                    <form>
                        <input type="hidden" value="2" name="sort"/>
                        <input type="hidden" name="idDealer" value="${dealer.id}"/>
                        <button class="button2" type="submit" formmethod="get" formaction="sortCarServlet">
                            <span>▼</span>
                        </button>
                    </form>
                </h3>
            </td>
            <td>
                <h3> Date
                    <form>
                        <input type="hidden" value="3" name="sort"/>
                        <input type="hidden" name="idDealer" value="${dealer.id}"/>
                        <button class="button3" type="submit" formmethod="get" formaction="sortCarServlet">
                            <span>▲</span>
                        </button>
                    </form>
                    <form>
                        <input type="hidden" value="4" name="sort"/>
                        <input type="hidden" name="idDealer" value="${dealer.id}"/>
                        <button class="button4" type="submit" formmethod="get" formaction="sortCarServlet">
                            <span>▼</span>
                        </button>
                    </form>
                </h3>
            </td>
            <td>
                <h3> Color
                    <form>
                        <input type="hidden" value="5" name="sort"/>
                        <input type="hidden" name="idDealer" value="${dealer.id}"/>
                        <button class="button5" type="submit" formmethod="get" formaction="sortCarServlet">
                            <span>▲</span>
                        </button>
                    </form>
                    <form>
                        <input type="hidden" value="6" name="sort"/>
                        <input type="hidden" name="idDealer" value="${dealer.id}"/>
                        <button class="button6" type="submit" formmethod="get" formaction="sortCarServlet">
                            <span>▼</span>
                        </button>
                    </form>
                </h3>
            </td>
            <td>
                <h3> Crash
                    <form>
                        <input type="hidden" value="7" name="sort"/>
                        <input type="hidden" name="idDealer" value="${dealer.id}"/>
                        <button class="button7" type="submit" formmethod="get" formaction="sortCarServlet">
                            <span>▲</span>
                        </button>
                    </form>
                    <form>
                        <input type="hidden" value="8" name="sort"/>
                        <input type="hidden" name="idDealer" value="${dealer.id}"/>
                        <button class="button8" type="submit" formmethod="get" formaction="sortCarServlet">
                            <span>▼</span>
                        </button>
                    </form>
                </h3>
            </td>
        </tr>
        <c:forEach var="car" items="${carList}">
            <tr>
                <td><input type="checkbox" name="check" value="${car.id}"/></td>
                <td>${car.name}</td>
                <td name="date">${car.date.getTime()}</td>
                <td>${car.color}</td>
                <td>${car.isAfterCrash()}</td>
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
                    document.getElementById("exp").disabled = true;
                }
                if (count === 1) {
                    document.getElementById("del").disabled = false;
                    document.getElementById("edit").disabled = false;
                    document.getElementById("exp").disabled = false;
                }
                if (count > 1) {
                    document.getElementById("del").disabled = false;
                    document.getElementById("edit").disabled = true;
                    document.getElementById("exp").disabled = false;
                }
            }

            for (i = 0; i < checks.length; i++) {
                checks[i].addEventListener("click", selectedCheckBox);
            }
        </script>
        <script>
            dates = document.getElementsByName("date");
            for (i = 0; i < dates.length; i++) {
                da = new Date(Number(dates[i].innerHTML));
                console.log(da.getFullYear() + "-" + da.getMonth() + "-" + da.getDate());
                month = da.getMonth() + 1;
                if (month < 10) {
                    month = "0" + month;
                    console.log("da.getMonth()+1 -- " + month);
                }
                day = da.getDate();
                if (day < 10) {
                    day = "0" + day;
                    console.log("da.getDate() -- " + day);
                }
                dates[i].innerHTML = (da.getFullYear() + "-" + month + "-" + day);
            }
        </script>
    </table>
    <br/>
    <div class="buttons">
        <input type="submit" formmethod="get" formaction="dealershipServlet" value="Back"/>
        <input type="submit" formmethod="post" formaction="delCarServlet" id="del" disabled value="Delete"/>
        <input type="submit" formmethod="get" formaction="editCarServlet" id="edit" disabled value="Edit"/>
        <input type="submit" formmethod="get" formaction="addCarServlet" value="Add"/>
        <input type="submit" formmethod="get" formaction="selectDealerServlet" value="Reset"/>
    </div>
    <div class="exp">
        <input type="text" name="fileName" id="fileName" placeholder="file name"/>
        <input type="submit" formmethod="post" formaction="exportCarServlet" id="exp" disabled
               value="Export"/>
    </div>
</form>
</body>
</html>
