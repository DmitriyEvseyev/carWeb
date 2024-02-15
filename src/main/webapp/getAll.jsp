<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Car manager</title>
    <style>.button-container-div {
        text-align: center;
    }
    </style>
</head>
<body>
<h2 align="center"> Cars </h2>


<style>
    table {
        border: 1px solid grey;
        border-collapse: collapse;
    }
    td {
        border: 1px solid grey;
    }
</style>
<form id="tab">
    <table align="center">
        <tr>
            <td><h3 align="center"></h3></td>
            <td><h3 align="center"> Name </h3></td>
            <td><h3 align="center"> Date </h3></td>
            <td><h3 align="center"> Color </h3></td>
            <td><h3 align="center"> isAfterCrash </h3></td>
        </tr>
        <c:forEach var="car" items="${carList}">
            <tr>
                <td align="center"><input class="check" type="checkbox" name="id" onclick="checkValue()" value="${car.id}"/></td>
                <td align="center">${car.name}</td>
                <td align="center">${String.valueOf(car.date)}</td>
                <td align="center">${car.color}</td>
                <td align="center">${String.valueOf(car.isAfterCrash())}</td>
            </tr>
        </c:forEach>
        <script>
            const form = document.getElementById("tab");
            console.log("form.elements - " + form.elements);
            console.log("length - " + form.length);
            console.log("name - " + form.name);
            console.log("action - " + form.action);
            console.log("method - " + form.method);
            const keyField = form.elements[0];
            console.log("keyField - " + keyField);
            console.log("keyField2 - " + form.elements[1]);

            var checks = document.getElementsByName("id");
            for (check of checks) {
                console.log("checked - " + check.checked);
            }
            function checkValue() {
                var checksss = document.getElementsByName("id");
                for (check of checksss) {
                    if (check.checked == false) {
                        document.getElementById("del").disabled = true;
                        document.getElementById("edit").disabled = true;
                    } else {
                        document.getElementById("del").disabled = false;
                        document.getElementById("edit").disabled = false;
                    }
                }
            }

            //document.getElementById("idCheck").addEventListener("click", checkValue);
        </script>
    </table>
    <br/>
    <div class="button-container-div">
        <button type="submit" formmethod="post" formaction="delCarServlet" id="del" disabled>delete</button>
        <button type="submit" formmethod="get" formaction="editCarServlet" id="edit" disabled>edit</button>
        <button type="submit" formmethod="get" formaction="addCarServlet">add</button>
    </div>

</form>
</body>
</html>
