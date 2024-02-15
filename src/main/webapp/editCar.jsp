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
    <input type="text" name="date" id="fff" value="${car.date}" required/><br><br>

    <label>Color</label><br>
    <input name="color" value="${car.color}" required/><br><br>

    <label>isAfterCrash</label><br>
    <input name="isAfterCrash" value="${String.valueOf(car.isAfterCrash())}" required/><br><br>

    <input type="submit" value="Send"/>
</form>
</body>
</html>