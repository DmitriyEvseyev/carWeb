package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.controller.CarList;
import com.dmitriyevseyev.carWeb.controller.DealerList;
import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.controller.ServerCarController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.GetAllCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.GetDealerException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "selectDealerServlet", value = "/selectDealerServlet")

public class SelectDealerServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer idDealer = Integer.valueOf(String.valueOf(req.getParameter("check")));
        System.out.println(" Integer idDealer SelectDealerServlet = " + idDealer);
        CarDealership dealer = null;
        DealerController delContr = DealerController.getInstance();

        try {
            dealer = delContr.getDealer(idDealer);
        } catch (GetDealerException e) {
            System.out.println("GetDealerException. SelectDealerExeption. " + e.getMessage());
        } catch (NotFoundException e) {
            getServletContext().getRequestDispatcher("/jsp/dealerjsp/notfoundDealer.jsp").forward(req, resp);
        }
        HashMap<Integer, Car> carHashMap = dealer.getCarMap();

        ArrayList<Car> carL = null;
        try {
            carL = (ArrayList<Car>) ServerCarController.getInstance().getCarList(idDealer);
        } catch (GetAllCarExeption e) {
            System.out.println("GetAllCarExeption. SelectDealerExeption. " + e.getMessage());
        }

        for (Car car : carL) {
            carHashMap.put(car.getId(), car);
        }
        System.out.println("newCarList SelectDealerServlet - " + carHashMap);
        List<Car> carList = new ArrayList<>(carHashMap.values());
        System.out.println("carList  SelectDealerServlet - " + carList);
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
