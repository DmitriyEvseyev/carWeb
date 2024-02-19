<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit car</title>
</head>
<body>
<h3> Edit car </h3>
<form action="editCarServlet" method="post">
    <input type="hidden" value="${car.id}" name="id"/>

    <label>Name</label><br>
    <input name="name" id="name" value="${car.name}" required/><br><br>

    <label>Date</label><br>
    <input type="date" name="date" id="dt" required /><br><br>

    <label>Color</label><br>
    <input name="color" value="${car.color}" required/><br><br>

    <label>isAfterCrash</label><br>
    <input type="checkbox" name="isAfterCrash" id="isAC" /><br><br>

    <input type="submit" value="Send"/>
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