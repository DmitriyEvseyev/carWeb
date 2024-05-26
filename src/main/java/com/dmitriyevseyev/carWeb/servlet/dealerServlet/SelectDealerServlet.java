package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.controller.CarController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.GetAllCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.GetDealerException;
import com.dmitriyevseyev.carWeb.servlet.ServletConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "selectDealerServlet", value = "/selectDealerServlet")

public class SelectDealerServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("idD") != null) {
            Integer idD = Integer.valueOf(session.getAttribute("idD").toString());
            session.removeAttribute("idD");
            selectCars(req, resp, idD);
        } else {
            Integer idDealer = Integer.valueOf(String.valueOf(req.getParameter("idDealer")));
            selectCars(req, resp, idDealer);
        }
    }

    private void selectCars(HttpServletRequest req, HttpServletResponse resp, Integer idDealer) throws ServletException, IOException {
        CarDealership dealer = null;
        try {
            dealer = DealerController.getInstance().getDealer(idDealer);
        } catch (NotFoundException e) {
            getServletContext().getRequestDispatcher(ServletConstants.NOT_DEALER_ADDRESS).forward(req, resp);
        }

        List<Car> carList = null;
        try {
            carList = CarController.getInstance().getCarList(idDealer);
        } catch (GetAllCarExeption e) {
            System.out.println("GetAllCarExeption. SelectDealerExeption. " + e.getMessage());
        }
        req.setAttribute("carList", carList);
        req.setAttribute("dealer", dealer);
        try {
            getServletContext().getRequestDispatcher(ServletConstants.CARS_PAGE_ADDRESS).forward(req, resp);
        } catch (ServletException e) {
            System.out.println("SelectDealerServlet. " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
