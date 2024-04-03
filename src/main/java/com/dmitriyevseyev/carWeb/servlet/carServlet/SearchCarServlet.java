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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "searchCarServlet", value = "/searchCarServlet")

public class SearchCarServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String namePattern = req.getParameter("name");
        String colorPattern = req.getParameter("color");
        String startDate = req.getParameter("startDate");
        String enddate = req.getParameter("endDate");
        Date startDatePattern = null;
        Date endDatePattern = null;
        try {
            if (startDate != null && enddate != null) {
                startDatePattern = formatter.parse(startDate);
                endDatePattern = formatter.parse(enddate);
            }
        } catch (
                ParseException e) {
            System.out.println("ParseException. " + e.getMessage());
        }

        System.out.println("namePattern - " + namePattern);
        System.out.println("colorPattern - " + colorPattern);
        System.out.println("startDatePattern - " + startDatePattern);
        System.out.println("endDatePattern - " + endDatePattern);
        System.out.println("req.getParameter(\"idDealer\")" + req.getParameter("idDealer"));

        Integer idDealer = Integer.valueOf(String.valueOf(req.getParameter("idDealer")));
        System.out.println("idDealer SearchCarServlet = " + idDealer);
        CarDealership dealer = null;
        try {
            dealer = DealerController.getInstance().getDealer(idDealer);
        } catch (
                GetDealerException e) {
            System.out.println("GetDealerException. SearchCarServlet. " + e.getMessage());
        } catch (
                NotFoundException e) {
            getServletContext().getRequestDispatcher(ServletConstants.NOT_DEALER_ADDRESS).forward(req, resp);
        }

        List<Car> carList = null;
        CarController carContr = CarController.getInstance();
        String criteria = "ASC";
        String columnName = "Name";
        String columnColor = "Color";
        String columnDate = "Date";

        Integer code = null;
        if (namePattern != null && colorPattern == null &&
                startDatePattern == null && endDatePattern == null) {
            code = 1;
        } else if (namePattern == null && colorPattern != null &&
                startDatePattern == null && endDatePattern == null) {
            code = 2;
        } else if (namePattern == null && colorPattern == null &&
                startDatePattern != null && endDatePattern != null) {
            code = 3;
        }

        try {
            switch (code) {
                case (1):
                    carList = carContr.getFilteredByPattern(dealer.getId(), columnName, namePattern, criteria);
                    break;
                case (2):
                    carList = carContr.getFilteredByPattern(dealer.getId(), columnColor, colorPattern, criteria);
                    break;
                case (3):
                    carList = carContr.getFilteredByDatePattern(dealer.getId(), columnDate, startDatePattern, endDatePattern, criteria);
            }
        } catch (
                GetAllCarExeption e) {
            System.out.println("GetAllCarExeption, SearchCarServlet - " + e.getMessage());
        }
        req.setAttribute("carList", carList);
        req.setAttribute("dealer", dealer);

        try {
            getServletContext().getRequestDispatcher(ServletConstants.CARS_PAGE_ADDRESS).forward(req, resp);
        } catch (
                ServletException e) {
            System.out.println("SelectDealerServlet. " + e.getMessage());
        }

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
