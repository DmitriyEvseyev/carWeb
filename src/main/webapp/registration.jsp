<%--
  Created by IntelliJ IDEA.
  User: VivoB
  Date: 14.03.2024
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

     <link rel="stylesheet" href="css/log.css">

    <link rel="stylesheet" href="https://unicons.iconscout.com/release/v4.0.0/css/line.css">

    <title>Registration Form</title>
</head>
<body>
<div class="container">
    <div class="forms">
        <div class="form signup">
            <span class="title">Registration</span>

            <form action="#">
                <div class="input-field">
                    <input type="text" placeholder="Enter your name" name="name" required>
                    <i class="uil uil-user"></i>
                </div>
                <div class="input-field">
                    <input type="password" name="password" class="password" placeholder="Create a password" required>
                    <i class="uil uil-lock icon"></i>
                </div>
                <div class="input-field">
                    <input type="password"  name="password2" class="password" placeholder="Confirm a password" required>
                    <i class="uil uil-lock icon"></i>
                    <i class="uil uil-eye-slash showHidePw"></i>
                </div>

                <div class="input-field button">
                    <input type="submit" formaction="userRegServlet" formmethod="post" value="Signup">
                </div>
            </form>

            <div class="login-signup">
                    <span class="text">Already registered?
                        <a href="index.jsp" class="text login-link">Login Now</a>
                    </span>
            </div>
        </div>
    </div>
</div>
</body>
</html>
