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

<%--<p><a href='<c:url value="/create" />'>Create new</a></p>
--%>
<style>
    table {
        border: 1px solid grey;
        border-collapse: collapse;
    }

    td {
        border: 1px solid grey;
    }
</style>
<form>
    <div class="button-container-div">
        <button type="submit" formmethod="post" formaction="delCarServlet">delete</button>
        <button type="submit" formmethod="get" formaction="editCarServlet">edit</button>
    </div>
    <table align="center">
        <tr>
            <td><h3 align="center"> action </h3></td>
            <td><h3 align="center"> Id </h3></td>
            <td><h3 align="center"> Name </h3></td>
            <td><h3 align="center"> Date </h3></td>
            <td><h3 align="center"> Color </h3></td>
            <td><h3 align="center"> isAfterCrash </h3></td>
        </tr>
        <c:forEach var="car" items="${carList}">
            <tr>
                <td align="center"><input type="checkbox" name="id" value="${car.id}"/></td>
                <td align="center">${car.id}</td>
                <td align="center">${car.name}</td>
                <td align="center">${car.date}</td>
                <td align="center">${car.color}</td>
                <td align="center">${String.valueOf(car.isAfterCrash())}</td>
            </tr>
        </c:forEach>
    </table>
    <br/>

</form>
</body>
</html>
