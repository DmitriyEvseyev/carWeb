package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.GetAllDealerExeption;
import com.dmitriyevseyev.carWeb.servlet.ServletConstants;

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
        String column = req.getParameter("column");
        String pattern = req.getParameter("pattern");
        System.out.println("column - " + column);
        System.out.println("pattern - " + pattern);

        List<CarDealership> carDealerships = null;
        DealerController dealerContr = DealerController.getInstance();
        String criteria = "ASC";

        try {
            carDealerships = dealerContr.getFilteredByPattern(column, pattern, criteria);
        } catch (GetAllDealerExeption e) {
            System.out.println("GetAllDealerExeption, SearchDealerServlet - " + e.getMessage());
        }
        System.out.println("DealershipServlet - " + carDealerships);
        req.setAttribute("carDealerships", carDealerships);

        getServletContext().getRequestDispatcher(ServletConstants.DEALERS_PAGE_ADDRESS).forward(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
