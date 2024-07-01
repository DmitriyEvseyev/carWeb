package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.DeleteDealerExeption;
import com.dmitriyevseyev.carWeb.servlet.ServletConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "delDealerServlet", value = "/delDealerServlet")
public class DelDealerServlet extends HttpServlet {
    @Override
    public void init() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String[] idDealer = req.getParameterValues("idDealer");

        DealerController controller = null;
        try {
            controller = DealerController.getInstance();
            for (String id : idDealer) {
                controller.removeDealer(Integer.valueOf(id));
            }
        } catch (DeleteDealerExeption | DAOFactoryActionException e) {
            resp.sendError(503, e.getMessage());
        }

        resp.sendRedirect(ServletConstants.PATH_DEALER);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}

