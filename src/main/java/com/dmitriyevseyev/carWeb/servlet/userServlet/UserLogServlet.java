package com.dmitriyevseyev.carWeb.servlet.userServlet;

import com.dmitriyevseyev.carWeb.server.controller.UserController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "userLogServlet", value = "/userLogServlet")

public class UserLogServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("name");
        String userPassword = req.getParameter("password");
        System.out.println("userName - " + userName + " ; userPassword - " + userPassword);

        boolean isCorrect = UserController.getInstance().isUserExistServer(userName, userPassword);
        System.out.println("isCorrect - " + isCorrect);
        if (isCorrect == true) {
            getServletContext().getRequestDispatcher("/dealershipServlet").forward(req, resp);
        } else {
            getServletContext().getRequestDispatcher("/jsp/dealerjsp/notfoundDealer.jsp").forward(req, resp);

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void destroy() {

    }


}
