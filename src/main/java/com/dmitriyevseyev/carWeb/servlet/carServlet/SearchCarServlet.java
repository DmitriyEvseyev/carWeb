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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "searchCarServlet", value = "/searchCarServlet")

public class SearchCarServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String nameColumn = req.getParameter("column");
        String namePattern = req.getParameter("pattern");
        String startDate = req.getParameter("startDate");
        String enddate = req.getParameter("endDate");
        Date startDatePattern = null;
        Date endDatePattern = null;

        try {
            if (startDate.length() > 0 && enddate.length() > 0) {
                startDatePattern = formatter.parse(startDate);
                endDatePattern = formatter.parse(enddate);
            }
        } catch (ParseException e) {
            resp.sendError(503, "ParseException. " + e.getMessage());
        }

        Integer idDealer = Integer.valueOf(String.valueOf(req.getParameter("idDealer")));
        CarDealership dealer = null;
        try {
            dealer = DealerController.getInstance().getDealer(idDealer);
        } catch (NotFoundException e) {
            resp.sendError(503, e.getMessage());
        }

        List<Car> carList = null;
        CarController carContr = CarController.getInstance();
        String criteria = "ASC";

        try {
            if (namePattern.length() > 0 && startDatePattern == null && endDatePattern == null) {
                if (nameColumn.equals("isAfterCrash")) {
                    carList = carContr.getFilteredByCrashPattern(dealer.getId(), nameColumn, namePattern, criteria);
                } else {
                    carList = carContr.getFilteredByPattern(dealer.getId(), nameColumn, namePattern, criteria);
                }

            } else if (namePattern.length() == 0 && startDatePattern != null && endDatePattern != null) {
                carList = carContr.getFilteredByDatePattern(dealer.getId(), nameColumn, startDatePattern, endDatePattern, criteria);
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
