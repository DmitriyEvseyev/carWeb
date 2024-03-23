package com.dmitriyevseyev.carWeb.servlet.carServlet;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.CarController;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.GetAllCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.GetAllDealerExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.GetDealerException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "sortCarServlet", value = "/sortCarServlet")

public class SortCarServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer code = Integer.valueOf(req.getParameter("sort"));
        System.out.println("sort - " + code);
        Integer idDealer = Integer.valueOf(String.valueOf(req.getParameter("idDealer")));
        System.out.println("idDealer SortCarServlet = " + idDealer);
        CarDealership dealer = null;

        try {
            dealer = DealerController.getInstance().getDealer(idDealer);
        } catch (GetDealerException e) {
            System.out.println("GetDealerException. SelectDealerExeption. " + e.getMessage());
        } catch (NotFoundException e) {
            getServletContext().getRequestDispatcher("/jsp/dealerjsp/notfoundDealer.jsp").forward(req, resp);
        }
        List<Car> carList = null;
        CarController carContr = CarController.getInstance();
        String columnName = "Name";
        String columnDate = "Date";
        String columnColor = "Color";
        String columnCrash = "isAfterCrash";
        String criteriaA = "ASC";
        String criteriaD = "DESC";

        try {
            switch (code) {
                case (1):
                    carList = carContr.getSortedByCriteria(idDealer,columnName, criteriaA);
                    break;
                case (2):
                    carList = carContr.getSortedByCriteria(idDealer, columnName, criteriaD);
                    break;
                case (3):
                    carList = carContr.getSortedByCriteria(idDealer, columnDate, criteriaA);
                    break;
                case (4):
                    carList = carContr.getSortedByCriteria(idDealer, columnDate, criteriaD);
                    break;
                case (5):
                    carList = carContr.getSortedByCriteria(idDealer,columnColor, criteriaA);
                    break;
                case (6):
                    carList = carContr.getSortedByCriteria(idDealer, columnColor, criteriaD);
                    break;
                case (7):
                    carList = carContr.getSortedByCriteria(idDealer, columnCrash, criteriaA);
                    break;
                case (8):
                    carList = carContr.getSortedByCriteria(idDealer, columnCrash, criteriaD);
                    break;
            }
        } catch (GetAllCarExeption e) {
            System.out.println("GetAllCarExeption, SortCarServlet - " + e.getMessage());
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
