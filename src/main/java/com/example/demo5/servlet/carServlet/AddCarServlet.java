package com.example.demo5.servlet.carServlet;

import com.example.demo5.controller.CarList;
import com.example.demo5.controller.DealerList;
import com.example.demo5.model.Car;
import com.example.demo5.model.CarDealership;
import com.example.demo5.servlet.HelloServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "addCarServlet", value = "/addCarServlet")

public class AddCarServlet extends HelloServlet {
    @Override
    public void init() {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("idDealer"));
        System.out.println(" Integer idDealer = " + id);
        request.setAttribute("idDealer", id);
        try {
            getServletContext().getRequestDispatcher("/jsp/carjsp/addCar.jsp").forward(request, response);
        } catch (ServletException e) {
            System.out.println("AddCarServlet. " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Car car = null;
        int id = Integer.parseInt(req.getParameter("id"));
        System.out.println(id);

        int idDealer = Integer.parseInt(req.getParameter("idDealer"));
        System.out.println(idDealer);

        String name = req.getParameter("name");
        System.out.println(name);

        String date = (req.getParameter("date"));
        System.out.println(date);

        String color = req.getParameter("color");
        System.out.println(color);

        Boolean isAfterCrash = req.getParameter("isAfterCrash")!=null;;

        System.out.println(isAfterCrash);

        try {
            car = Car.builder()
                    .id(id)
                    .idDealer(idDealer)
                    .name(name)
                    .date(formatter.parse(date))
                    .color(color)
                    .isAfterCrash(isAfterCrash)
                    .build();
        } catch (ParseException e) {
            System.out.println("ParseException. " + e.getMessage());
        }

        System.out.println("CAR - " + car);

        List<CarDealership> dealerList = DealerList.getInstance().getDealerL();
        CarDealership dealer = null;

        for (CarDealership c : dealerList) {
            if (c.getId().equals(idDealer)) {
                dealer = c;
            }
        }
        HashMap<Integer, Car> carHashMap = dealer.getCarMap();
        carHashMap.put(car.getId(), car);

        List<Car> carList = new ArrayList<>(carHashMap.values());
        System.out.println("carList = (List<Car>) carHashMap.values() - " + carList);
        req.setAttribute("carList", carList);
        req.setAttribute("dealer", dealer);

        try {
            getServletContext().getRequestDispatcher("/jsp/carjsp/getAll.jsp").forward(req, resp);
        } catch (ServletException e) {
            System.out.println("AddCarServlet. " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
