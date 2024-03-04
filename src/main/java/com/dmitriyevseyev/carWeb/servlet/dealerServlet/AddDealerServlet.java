package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.controller.DealerList;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.AddDealerExeption;
import com.dmitriyevseyev.carWeb.servlet.ServletConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(name = "addDealerServlet", value = "/addDealerServlet")

public class AddDealerServlet extends HttpServlet {
    @Override
    public void init() {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            getServletContext().getRequestDispatcher("/jsp/dealerjsp/addDealer.jsp").forward(request, response);
        } catch (ServletException e) {
            System.out.println("AddDealerServlet. " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        System.out.println(name);

        String adress = req.getParameter("adress");
        System.out.println(adress);

        CarDealership dealer = CarDealership.builder()
                .name(name)
                .adress(adress)
                .build();

        System.out.println("Dealer - " + dealer);

        try {
            DealerController.getInstance().addDealer(dealer);
        } catch (AddDealerExeption e) {
            throw new RuntimeException(String.format("AddDealerExeption/ doPost. " + e.getMessage()));
        }
        resp.sendRedirect(ServletConstants.PATH_DEALER);
    }

    @Override
    public void destroy() {
        super.destroy();
    }


}
