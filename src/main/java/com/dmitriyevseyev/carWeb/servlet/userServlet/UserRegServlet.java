package com.dmitriyevseyev.carWeb.servlet.userServlet;

import com.dmitriyevseyev.carWeb.model.User;
import com.dmitriyevseyev.carWeb.server.controller.UserController;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.ManagerDAO;
import com.dmitriyevseyev.carWeb.server.dao.postgreSQL.PostgreSQLManagerDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carWeb.server.exceptions.user.AddUserExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.user.UserNotFoundExeption;
import com.dmitriyevseyev.carWeb.servlet.ServletConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "userRegServlet", value = "/userRegServlet")

public class UserRegServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userName = req.getParameter("name");
        String userPassword = req.getParameter("password");
        String userPass2 = req.getParameter("password2");

        UserController userCont;
        try {
            ManagerDAO managerDAO = PostgreSQLManagerDAO.getInstance(ServletConstants.PATH_SQL);
            userCont = UserController.getInstance();

        if (userPassword.equals(userPass2) == false) {
            resp.setContentType("text/html");
            PrintWriter pw = resp.getWriter();
            pw.println("<script type=\"text/javascript\">");
            pw.println("alert('Passwords are different!');");
            pw.println("location='registration.jsp'");
            pw.println("</script>");
        } else if (userCont.isUserNameExistServer(userName) == true) {
            resp.setContentType("text/html");
            PrintWriter pw = resp.getWriter();
            pw.println("<script type=\"text/javascript\">");
            pw.println("alert('A user with that name already exists!');");
            pw.println("location='registration.jsp'");
            pw.println("</script>");
        } else {
            User user = User.builder()
                    .userName(userName)
                    .password(userPassword)
                    .build();
            try {
                userCont.addUser(user);
            } catch (AddUserExeption e) {
                resp.sendError(503,  e.getMessage());
            }
            resp.sendRedirect(ServletConstants.PATH_DEALER);
        }
        } catch (DAOFactoryActionException | UserNotFoundExeption e) {
            resp.sendError(503,  e.getMessage());
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
