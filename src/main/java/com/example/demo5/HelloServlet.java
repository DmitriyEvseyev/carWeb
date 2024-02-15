package com.example.demo5;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            getServletContext().getRequestDispatcher("/getAllServlet").forward(request, response);
        } catch (ServletException e) {
            System.out.println("HelloServlet. " + e.getMessage());
        }
    }

    public void destroy() {
    }
}