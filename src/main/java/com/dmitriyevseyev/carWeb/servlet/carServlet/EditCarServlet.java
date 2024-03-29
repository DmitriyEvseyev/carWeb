package com.dmitriyevseyev.carWeb.servlet.carServlet;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.server.controller.CarController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.UpdateCarException;
import com.dmitriyevseyev.carWeb.servlet.ServletConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "editCarServlet", value = "/editCarServlet")

public class EditCarServlet extends HttpServlet {
    @Override
    public void init() {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer idDealer = Integer.parseInt(request.getParameter("idDealer"));
        System.out.println(" Integer idDealer EditCarServlet = " + idDealer);


        Integer id = Integer.valueOf(String.valueOf(request.getParameter("check")));
        System.out.println(" Integer id EditCarServlet = " + id);

        Car car = null;
        try {
            car = CarController.getInstance().getCar(id);
        } catch (UpdateCarException e) {
            System.out.println("UpdateCarException. " + e.getMessage());
        }
        System.out.println("CAR from get - " + car);

        request.setAttribute("car", car);
        request.setAttribute("idDealer", idDealer);
        try {
            getServletContext().getRequestDispatcher("/jsp/carjsp/editCar.jsp").forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

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

        Boolean isAfterCrash = req.getParameter("isAfterCrash") != null;
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

        System.out.println("CAR EditCarServlet - " + car);

        try {
            CarController.getInstance().updateCar(car);
        } catch (UpdateCarException e) {
            System.out.println("UpdateCarException. " + e.getMessage());
        }
        HttpSession session = req.getSession();
        session.setAttribute("idD", idDealer);
        resp.sendRedirect(ServletConstants.PATH_CARS);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
