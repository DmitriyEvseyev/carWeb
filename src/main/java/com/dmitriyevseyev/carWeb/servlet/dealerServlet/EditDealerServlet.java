package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.GetDealerException;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.UpdateDealerException;
import com.dmitriyevseyev.carWeb.servlet.ServletConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "editDealerServlet", value = "/editDealerServlet")

public class EditDealerServlet extends HttpServlet {
    @Override
    public void init() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idDealer = req.getParameter("idDealer");
        CarDealership dealer = null;
        try {
            dealer = DealerController.getInstance().getDealer(Integer.valueOf(idDealer));
        } catch (GetDealerException e) {
            System.out.println("GetDealerException. EditDealerExeption. " + e.getMessage());
        } catch (NotFoundException e) {
            getServletContext().getRequestDispatcher(ServletConstants.NOT_DEALER_ADDRESS).forward(req, resp);
        }
        req.setAttribute("dealer", dealer);
        getServletContext().getRequestDispatcher(ServletConstants.EDIT_DEALER_ADDRESS).forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        DealerController controller = DealerController.getInstance();
        try {
            controller.updateDealer(id, name, address);
        } catch (UpdateDealerException e) {
            throw new RuntimeException("EditDealerServlet. RuntimeException. " + e.getMessage());
        }

        response.sendRedirect(ServletConstants.PATH_DEALER);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}

