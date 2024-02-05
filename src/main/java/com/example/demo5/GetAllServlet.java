package com.example.demo5;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


@WebServlet(name = "getAllServlet", value = "/getAllServlet")
public class GetAllServlet extends HttpServlet {
    @Override
    public void init() {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<Car> carList = (ArrayList<Car>) CarList.getInstance().getCarL();

        System.out.println("carList - " + carList);

        request.setAttribute("carList", carList);

        try {
            getServletContext().getRequestDispatcher("/getAll.jsp").forward(request, response);
        } catch (ServletException e) {
            System.out.println("GetAllServlet. " + e.getMessage());
        }
            }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
