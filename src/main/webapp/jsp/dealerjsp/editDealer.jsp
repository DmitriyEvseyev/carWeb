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
    <style>
        H2 {
            text-align: center
        }

        .tab {
            border: 1px solid grey;
            border-collapse: collapse;

            margin-left: auto;
            margin-right: auto;
            text-align: center;

        }

        .tab td {
            border: 1px solid grey;
            font-size: 20px;
            font-weight: bold;
        }

        .but {
            text-align: center;
        }

        input {
            font-size: 20px;
            font-family: "Times New Roman";
            text-align: center;
        }
    </style>
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
            <td><input name="address" value="${dealer.adress}" required/></td>
        </tr>
    </table>
    <br/>
    <div class="but">
        <input type="submit"   value="Ok"/>
        <input type="reset" value="Cancel"/>
    </div>
</form>
<script>

</script>
</body>
</html>