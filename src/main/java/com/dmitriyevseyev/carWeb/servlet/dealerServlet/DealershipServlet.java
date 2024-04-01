package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.GetAllDealerExeption;
import com.dmitriyevseyev.carWeb.servlet.ServletConstants;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CarDealership> carDealerships = null;
        try {
            carDealerships = DealerController.getInstance().getAllDealers();
        } catch (GetAllDealerExeption e) {
            throw new RuntimeException("DealershipServlet. " + e.getMessage());
        }
        System.out.println("DealershipServlet - " + carDealerships);
        req.setAttribute("carDealerships", carDealerships);

        getServletContext().getRequestDispatcher(ServletConstants.DEALERS_PAGE_ADDRESS).forward(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
