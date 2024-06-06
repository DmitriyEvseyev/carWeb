package com.dmitriyevseyev.carWeb.servlet.carServlet;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.server.controller.CarController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
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
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Integer idDealer = Integer.parseInt(request.getParameter("idDealer"));
        Integer id = Integer.valueOf(String.valueOf(request.getParameter("check")));

        Car car = null;
        try {
            car = CarController.getInstance().getCar(id);
        } catch (NotFoundException e) {
            response.sendError(503, e.getMessage());
        }

        request.setAttribute("car", car);
        request.setAttribute("idDealer", idDealer);

        getServletContext().getRequestDispatcher(ServletConstants.EDIT_CAR_ADDRESS).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Car car = null;
        int id = Integer.parseInt(req.getParameter("id"));

        int idDealer = Integer.parseInt(req.getParameter("idDealer"));

        String name = req.getParameter("name");

        String date = (req.getParameter("date"));

        String color = req.getParameter("color");

        Boolean isAfterCrash = req.getParameter("isAfterCrash") != null;

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
            resp.sendError(503, "ParseException. " + e.getMessage());
        }

        try {
            CarController.getInstance().updateCar(car);
        } catch (UpdateCarException e) {
            resp.sendError(503, e.getMessage());
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
