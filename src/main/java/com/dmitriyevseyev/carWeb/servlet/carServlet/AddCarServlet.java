package com.dmitriyevseyev.carWeb.servlet.carServlet;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.server.controller.CarController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.AddCarExeption;

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

@WebServlet(name = "addCarServlet", value = "/addCarServlet")

public class AddCarServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer idDealer = Integer.parseInt(request.getParameter("idDealer"));
        request.setAttribute("idDealer", idDealer);
        try {
            getServletContext().getRequestDispatcher(ServletConstants.ADD_CAR_ADDRESS).forward(request, response);
        } catch (ServletException e) {
            System.out.println("AddCarServlet. " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Car car = null;

        int idDealer = Integer.parseInt(req.getParameter("idDealer"));
        String name = req.getParameter("name");
        String date = (req.getParameter("date"));
        String color = req.getParameter("color");
        Boolean isAfterCrash = req.getParameter("isAfterCrash") != null;

        try {
            car = Car.builder()
                    .idDealer(idDealer)
                    .name(name)
                    .date(formatter.parse(date))
                    .color(color)
                    .isAfterCrash(isAfterCrash)
                    .build();
        } catch (ParseException e) {
            System.out.println("ParseException. " + e.getMessage());
        }

        try {
            CarController.getInstance().addCar(car);
        } catch (AddCarExeption e) {
            System.out.println("AddCarExeption. " + e.getMessage());
        }

        HttpSession session = req.getSession();
        session.setAttribute("idD", idDealer);
        resp.sendRedirect(ServletConstants.PATH_CARS);
    }
}
