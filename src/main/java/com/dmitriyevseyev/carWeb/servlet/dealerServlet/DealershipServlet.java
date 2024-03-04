package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.controller.DealerList;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.GetAllDealerExeption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "dealershipServlet", value = "/dealershipServlet")
public class DealershipServlet extends HttpServlet {
    @Override
    public void init() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        List<CarDealership> carDealerships = null;
        try {
            carDealerships =  DealerController.getInstance().getAllDealers();
        } catch (GetAllDealerExeption e) {
            throw new RuntimeException("DealershipServlet. " + e.getMessage());
        }
        System.out.println("DealershipServlet - " + carDealerships);
        req.setAttribute("carDealerships", carDealerships);
        try {
            getServletContext().getRequestDispatcher("/jsp/dealerjsp/getDealer.jsp").forward(req, resp);
        } catch (ServletException e) {
            System.out.println("DealershipServlet. " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("DealershipServlet/forward." + e.getMessage());
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
