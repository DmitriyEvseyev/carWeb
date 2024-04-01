package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.GetAllDealerExeption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "searchDealerServlet", value = "/searchDealerServlet")

public class SearchDealerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String namePattern = req.getParameter("name");
        String addressPattern = req.getParameter("address");
        System.out.println("name - " + namePattern);
        System.out.println("address - " + addressPattern);
        List<CarDealership> carDealerships = null;
        DealerController dealerContr = DealerController.getInstance();
        String criteria = "ASC";
        String columnName = "Name";
        String columnAddress = "Address";
        try {
            if (namePattern != null) {
                carDealerships = dealerContr.getFilteredByPattern(columnName, namePattern, criteria);
            } else {
                carDealerships = dealerContr.getFilteredByPattern(columnAddress, addressPattern, criteria);
            }
        } catch (GetAllDealerExeption e) {
            System.out.println("GetAllDealerExeption, SearchDealerServlet - " + e.getMessage());
        }
        System.out.println("DealershipServlet - " + carDealerships);
        req.setAttribute("carDealerships", carDealerships);

        getServletContext().getRequestDispatcher("/jsp/dealerjsp/getDealer.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
