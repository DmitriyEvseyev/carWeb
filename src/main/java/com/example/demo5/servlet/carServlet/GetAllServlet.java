package com.example.demo5.servlet.carServlet;

import com.example.demo5.controller.CarList;
import com.example.demo5.model.Car;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


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
    public void destroy() {
        super.destroy();
    }
}
