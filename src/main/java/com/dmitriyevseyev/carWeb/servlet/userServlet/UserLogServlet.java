package com.dmitriyevseyev.carWeb.servlet.userServlet;

import com.dmitriyevseyev.carWeb.server.controller.UserController;
import com.dmitriyevseyev.carWeb.server.dao.interfaces.ManagerDAO;
import com.dmitriyevseyev.carWeb.server.dao.postgreSQL.PostgreSQLManagerDAO;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carWeb.server.exceptions.user.UserNotFoundExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.user.UserPasswordExeption;
import com.dmitriyevseyev.carWeb.servlet.ServletConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "userLogServlet", value = "/userLogServlet")

public class UserLogServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userName = req.getParameter("name");
        String userPassword = req.getParameter("password");

        boolean isCorrect = false;

        try {
            ManagerDAO managerDAO = PostgreSQLManagerDAO.getInstance(ServletConstants.PATH_SQL);
            isCorrect = UserController.getInstance().isUserExistServer(userName, userPassword);
        } catch (UserNotFoundExeption | DAOFactoryActionException | UserPasswordExeption e) {
            resp.sendError(503,  e.getMessage());
        }

        if (isCorrect == true) {
            resp.sendRedirect(ServletConstants.PATH_DEALER);
        } else {
            resp.setContentType("text/html");
            PrintWriter pw = resp.getWriter();
            pw.println("<script type=\"text/javascript\">");
            pw.println("alert('Invalid Username or Password!');");
            pw.println("location='index.jsp'");
            pw.println("</script>");
        }
    }

    @Override
    public void destroy() {
    }
}
