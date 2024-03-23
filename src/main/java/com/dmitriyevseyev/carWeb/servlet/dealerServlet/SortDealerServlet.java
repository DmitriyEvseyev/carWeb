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

@WebServlet(name = "sortDealerServlet", value = "/sortDealerServlet")

public class SortDealerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer code = Integer.valueOf(req.getParameter("sort"));
        System.out.println("sort - " + code);
        List<CarDealership> carDealerships = null;
        DealerController dealerContr = DealerController.getInstance();
        String columnName = "Name";
        String columnAddress = "Address";
        String criteriaA = "ASC";
        String criteriaD = "DESC";
        try {
            switch (code) {
                case (1):
                    carDealerships = dealerContr.getSortedByCriteria(columnName, criteriaA);
                    break;
                case (2):
                    carDealerships = dealerContr.getSortedByCriteria(columnName, criteriaD);
                    break;
                case (3):
                    carDealerships = dealerContr.getSortedByCriteria(columnAddress, criteriaA);
                    break;
                case (4):
                    carDealerships = dealerContr.getSortedByCriteria(columnAddress, criteriaD);
                    break;
            }
        } catch (GetAllDealerExeption e) {
            System.out.println("GetAllDealerExeption, SortDealerServlet - " + e.getMessage());
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
