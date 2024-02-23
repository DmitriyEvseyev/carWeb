package com.example.demo5.servlet.dealerServlet;

import com.example.demo5.controller.CarList;
import com.example.demo5.controller.DealerList;
import com.example.demo5.model.Car;
import com.example.demo5.model.CarDealership;

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
        Integer id = Integer.valueOf(String.valueOf(req.getParameter("check")));
        System.out.println(" Integer idDealer = " + id);

        List<CarDealership> dealerList = DealerList.getInstance().getDealerL();
        CarDealership dealer = null;

        for (CarDealership c : dealerList) {
            if (c.getId().equals(id)) {
                dealer = c;
            }
        }
        HashMap<Integer, Car> carHashMap = dealer.getCarMap();

        ArrayList<Car> carL = (ArrayList<Car>) CarList.getInstance().getCarL();

        for (Car car : carL) {
            if (car.getIdDealer().equals(id)){
                carHashMap.put(car.getId(), car);
            }
        }
        System.out.println("newCarList for dealer - " + carHashMap);
        List<Car> carList = new ArrayList<>(carHashMap.values());
        System.out.println("carList = (List<Car>) carHashMap.values() - " + carList);
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
