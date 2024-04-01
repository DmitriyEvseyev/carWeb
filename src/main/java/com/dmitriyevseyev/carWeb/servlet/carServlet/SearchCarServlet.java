package com.dmitriyevseyev.carWeb.servlet.carServlet;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.CarController;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.GetAllCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.GetDealerException;
import com.dmitriyevseyev.carWeb.servlet.ServletConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "searchCarServlet", value = "/searchCarServlet")

public class SearchCarServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String namePattern = req.getParameter("name");
        String colorPattern = req.getParameter("color");
        System.out.println("namePattern - " + namePattern);
        System.out.println("colorPattern - " + colorPattern);
        System.out.println("req.getParameter(\"idDealer\")" + req.getParameter("idDealer"));
        Integer idDealer = Integer.valueOf(String.valueOf(req.getParameter("idDealer")));
        System.out.println("idDealer SearchCarServlet = " + idDealer);
        CarDealership dealer = null;
        try {
            dealer = DealerController.getInstance().getDealer(idDealer);
        } catch (GetDealerException e) {
            System.out.println("GetDealerException. SearchCarServlet. " + e.getMessage());
        } catch (NotFoundException e) {
            getServletContext().getRequestDispatcher(ServletConstants.NOT_DEALER_ADDRESS).forward(req, resp);
        }
        List<Car> carList = null;
        CarController carContr = CarController.getInstance();
        String criteria = "ASC";
        String columnName = "Name";
        String columnColor = "Color";
        try {
            if (namePattern != null) {
                carList = carContr.getFilteredByPattern(dealer.getId(),columnName, namePattern, criteria);
            } else {
                carList = carContr.getFilteredByPattern(dealer.getId(),columnColor, colorPattern, criteria);
            }
        } catch (GetAllCarExeption e) {
            System.out.println("GetAllCarExeption, SearchCarServlet - " + e.getMessage());
        }
        req.setAttribute("carList", carList);
        req.setAttribute("dealer", dealer);

        try {
            getServletContext().getRequestDispatcher(ServletConstants.CARS_PAGE_ADDRESS).forward(req, resp);
        } catch (ServletException e) {
            System.out.println("SelectDealerServlet. " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
