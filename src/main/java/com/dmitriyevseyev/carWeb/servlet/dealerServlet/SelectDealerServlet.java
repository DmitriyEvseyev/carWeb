package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.client.controller.CarList;
import com.dmitriyevseyev.carWeb.client.controller.DealerList;
import com.dmitriyevseyev.carWeb.client.model.Car;
import com.dmitriyevseyev.carWeb.client.model.CarDealership;

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

        CarDealership dealer = DealerList.getInstance().searchDealer(idDealer);
        HashMap<Integer, Car> carHashMap = dealer.getCarMap();

        ArrayList<Car> carL = (ArrayList<Car>) CarList.getInstance().getCarL();

        for (Car car : carL) {
            if (car.getIdDealer().equals(idDealer)){
                carHashMap.put(car.getId(), car);
            }
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
