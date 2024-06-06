package com.dmitriyevseyev.carWeb.servlet.carServlet;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.CarController;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.GetAllCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.servlet.ServletConstants;

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
        Integer idDealer = Integer.valueOf(String.valueOf(req.getParameter("idDealer")));
        CarDealership dealer = null;

        try {
            dealer = DealerController.getInstance().getDealer(idDealer);
        } catch (NotFoundException e) {
            resp.sendError(503, e.getMessage());
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
                    carList = carContr.getSortedByCriteria(dealer.getId(), columnName, criteriaA);
                    break;
                case (2):
                    carList = carContr.getSortedByCriteria(dealer.getId(), columnName, criteriaD);
                    break;
                case (3):
                    carList = carContr.getSortedByCriteria(dealer.getId(), columnDate, criteriaA);
                    break;
                case (4):
                    carList = carContr.getSortedByCriteria(dealer.getId(), columnDate, criteriaD);
                    break;
                case (5):
                    carList = carContr.getSortedByCriteria(dealer.getId(), columnColor, criteriaA);
                    break;
                case (6):
                    carList = carContr.getSortedByCriteria(dealer.getId(), columnColor, criteriaD);
                    break;
                case (7):
                    carList = carContr.getSortedByCriteria(dealer.getId(), columnCrash, criteriaA);
                    break;
                case (8):
                    carList = carContr.getSortedByCriteria(dealer.getId(), columnCrash, criteriaD);
                    break;
            }
        } catch (GetAllCarExeption e) {
            resp.sendError(503, e.getMessage());
        }
        req.setAttribute("carList", carList);
        req.setAttribute("dealer", dealer);

        getServletContext().getRequestDispatcher(ServletConstants.CARS_PAGE_ADDRESS).forward(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
