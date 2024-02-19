package com.example.demo5;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

@WebServlet(name = "addCarServlet", value = "/addCarServlet")

public class AddCarServlet extends HelloServlet {
    @Override
    public void init() {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            getServletContext().getRequestDispatcher("/addCar.jsp").forward(request, response);
        } catch (ServletException e) {
            System.out.println("EditCarServlet. " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Car car = null;
        int id = Integer.parseInt(req.getParameter("id"));
        System.out.println(id);

        String name = req.getParameter("name");
        System.out.println(name);

        System.out.println(req.getParameter("date"));
        String date = (req.getParameter("date"));
        System.out.println(date);

        String color = req.getParameter("color");
        System.out.println(color);

        Boolean isAfterCrash = req.getParameter("isAfterCrash")!=null;;

        System.out.println(isAfterCrash);

        try {
            car = Car.builder()
                    .id(id)
                    .name(name)
                    .date(formatter.parse(date))
                    .color(color)
                    .isAfterCrash(isAfterCrash)
                    .build();
        } catch (ParseException e) {
            System.out.println("ParseException. " + e.getMessage());
        }

        System.out.println("CAR - " + car);

        ArrayList<Car> newCarList = (ArrayList<Car>) CarList.getInstance().getCarL();
        newCarList.add(car);
        System.out.println("newCarList - " + newCarList);
        req.setAttribute("carList", newCarList);

        try {
            getServletContext().getRequestDispatcher("/getAll.jsp").forward(req, resp);
        } catch (ServletException e) {
            System.out.println("AddCarServlet. " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }


}
