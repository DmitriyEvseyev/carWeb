package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

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

@WebServlet(name = "addDealerServlet", value = "/addDealerServlet")

public class AddDealerServlet extends HttpServlet {
    @Override
    public void init() {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext().getRequestDispatcher(ServletConstants.ADD_DEALER_ADDRESS).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        CarDealership dealer = CarDealership.builder()
                .name(name)
                .address(address)
                .build();
        try {
            DealerController.getInstance().addDealer(dealer);
        } catch (AddDealerExeption e) {
            resp.sendError(503, e.getMessage());
        }
        resp.sendRedirect(ServletConstants.PATH_DEALER);
    }

    @Override
    public void destroy() {
        super.destroy();
    }


}
