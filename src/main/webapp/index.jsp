<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<!-- Coding By CodingNepal - codingnepalweb.com -->
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="css/log.css">
    <!-- ===== Iconscout CSS ===== -->
    <link rel="stylesheet" href="https://unicons.iconscout.com/release/v4.0.0/css/line.css">

    <title>Login Form</title>
</head>
<body>

<div class="container">
    <div class="forms">
        <div class="form login">
            <span class="title">Login</span>

            <form action="#">
                <div class="input-field">
                    <input type="text" name="name" placeholder="Enter your name" required>
                    <i class="uil uil-envelope icon"></i>
                </div>
                <div class="input-field">
                    <input type="password" name="password" class="password" placeholder="Enter your password" required>
                    <i class="uil uil-lock icon"></i>
                    <i class="uil uil-eye-slash showHidePw"></i>
                </div>

                <div class="input-field button">
                    <input type="submit" value="Login" formaction="userLogServlet">
                </div>
            </form>

            <div class="login-signup">
                    <span class="text">Not a member?
                        <a href="registration.jsp">Signup Now</a>
                    </span>
            </div>
        </div>
    </div>
</div>
</body>
</html>