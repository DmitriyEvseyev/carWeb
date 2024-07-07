package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
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




        List<CarDealership> carDealerships = null;
        DealerController dealerContr;
        String criteria = "ASC";

        try {
            dealerContr = DealerController.getInstance();
            carDealerships = dealerContr.getFilteredByPattern(column, pattern, criteria);
        } catch (GetAllDealerExeption | DAOFactoryActionException e) {
            resp.sendError(503, e.getMessage());
        }

        req.setAttribute("carDealerships", carDealerships);
        getServletContext().getRequestDispatcher(ServletConstants.DEALERS_PAGE_ADDRESS).forward(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
