package com.example.demo5;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;
import java.util.Locale;

@WebServlet(name = "editCarServlet", value = "/editCarServlet")

public class EditCarServlet extends HelloServlet {
    @Override
    public void init() {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.valueOf(String.valueOf(request.getParameter("id")));
        System.out.println(" Integer id = " + id);
        ArrayList<Car> newCarList = (ArrayList<Car>) CarList.getInstance().getCarL();

        Car car = null;

        for (Car c : newCarList) {
            if (c.getId().equals(id)) {
                car = c;
            }
        }
        System.out.println("CAR from get - " + car);
        try {
            if (car != null) {
                request.setAttribute("car", car);
                getServletContext().getRequestDispatcher("/editCar.jsp").forward(request, response);
            } else {
                getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
            }
        } catch (ServletException e) {
            System.out.println("EditCarServlet. " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

        Car car = null;
        int id = Integer.parseInt(req.getParameter("id"));
        System.out.println(id);

        String name = req.getParameter("name");
        System.out.println(name);

       // System.out.println(req.getParameter("date"));
        String date = (req.getParameter("date"));
        System.out.println(date);

        String color = req.getParameter("color");
        System.out.println(color);

        Boolean isAfterCrash = Boolean.valueOf(req.getParameter("isAfterCrash"));
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

        ListIterator<Car> iter = newCarList.listIterator();
        while (iter.hasNext()) {
            if (iter.next().getId().equals(car.getId())) {
                iter.remove();
            }
        }
        newCarList.add(car);

        System.out.println("newCarList - " + newCarList);
        req.setAttribute("carList", newCarList);

        try {
            getServletContext().getRequestDispatcher("/getAll.jsp").forward(req, resp);
        } catch (ServletException e) {
            System.out.println("EditCarServlet. " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
