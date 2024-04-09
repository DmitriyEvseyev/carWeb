<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit car</title>
    <link rel="stylesheet" href="css/carcss/editC.css">
</head>
<body>
<h2> Edit car </h2>
<form action="editCarServlet" method="post">
    <input type="hidden" name="id" value="${car.id}" />
    <input type="hidden" name="idDealer" value="${idDealer}"/>
    <table class="tab">
        <tr>
            <td> Name</td>
            <td><input name="name" id="name" value="${car.name}" required/></td>
        </tr>
        <tr>
            <td> Date</td>
            <td><input type="date" name="date" id="dt" required/></td>
        </tr>
        <tr>
            <td> Color</td>
            <td><input name="color" value="${car.color}" required/></td>
        </tr>
        <tr>
            <td> isAfterCrash</td>
            <td><input type="checkbox" name="isAfterCrash" id="isAC"/></td>
        </tr>
    </table>
    <br/>
    <div class="but">
        <input type="submit" value="Ok"/>
        <input type="reset" value="Cancel"/>
    </div>
    <script>
        date = new Date(${car.date.getTime()});
        month = date.getMonth() + 1;
        if (month < 10) {
            month = "0" + month;
            console.log("date.getMonth()+1 -- " + month);
        }
        day = date.getDate();
        if (day < 10) {
            day = "0" + day;
            console.log("date.getDate() -- " + day);
        }
        console.log(date.getFullYear() + "-" + month + "-" + day)
        document.getElementById("dt").value = (date.getFullYear() + "-" + month + "-" + day);
    </script>
    <script>
        is = (${String.valueOf(car.isAfterCrash())});
        console.log(is);
        if (is) {
            document.getElementById("isAC").checked = true;
        } else document.getElementById("isAC").checked = false;

    </script>
</form>
</body>
</html>