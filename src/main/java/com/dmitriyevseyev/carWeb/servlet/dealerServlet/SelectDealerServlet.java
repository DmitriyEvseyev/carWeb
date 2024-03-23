package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.controller.CarController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.GetAllCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.GetDealerException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "selectDealerServlet", value = "/selectDealerServlet")

public class SelectDealerServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("---------------");
        HttpSession session = req.getSession();
        if (session.getAttribute("idD") != null) {
            Integer idD = Integer.valueOf(session.getAttribute("idD").toString());
            session.removeAttribute("idD");
            System.out.println("idD session - " + idD);
            selectCars(req, resp, idD);
        } else {
            System.out.println("------------------");
            Integer idDealer = Integer.valueOf(String.valueOf(req.getParameter("check")));
            System.out.println("idDealer(check) SelectDealerServlet = " + idDealer);
            selectCars(req, resp, idDealer);
        }
    }

    private void selectCars(HttpServletRequest req, HttpServletResponse resp, Integer idDealer) throws ServletException, IOException {
        CarDealership dealer = null;

        try {
            dealer = DealerController.getInstance().getDealer(idDealer);
        } catch (GetDealerException e) {
            System.out.println("GetDealerException. SelectDealerExeption. " + e.getMessage());
        } catch (NotFoundException e) {
            getServletContext().getRequestDispatcher("/jsp/dealerjsp/notfoundDealer.jsp").forward(req, resp);
        }

        List<Car> carList = null;
        try {
            carList = CarController.getInstance().getCarList(idDealer);
            System.out.println("carL - " + carList);
        } catch (GetAllCarExeption e) {
            System.out.println("GetAllCarExeption. SelectDealerExeption. " + e.getMessage());
        }

        req.setAttribute("carList", carList);
        req.setAttribute("dealer", dealer);

        try {
            getServletContext().getRequestDispatcher("/jsp/carjsp/getAll.jsp").forward(req, resp);
        } catch (ServletException e) {
            System.out.println("SelectDealerServlet. " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
