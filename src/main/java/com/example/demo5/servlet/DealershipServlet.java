package com.example.demo5.servlet;

import com.example.demo5.controller.DealerList;
import com.example.demo5.model.CarDealership;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "dealershipServlet", value = "/dealershipServlet")
public class DealershipServlet extends HttpServlet {
    @Override
    public void init() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<CarDealership> carDealerships = (ArrayList<CarDealership>) DealerList.getInstance().getDealerL();
        System.out.println("carDealerships - " + carDealerships);
        req.setAttribute("carDealerships", carDealerships);
        try {
            getServletContext().getRequestDispatcher("/getDealer.jsp").forward(req, resp);
        } catch (ServletException e) {
            System.out.println("DealershipServlet. " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
