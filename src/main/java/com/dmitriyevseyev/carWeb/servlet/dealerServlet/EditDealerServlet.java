package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.controller.DealerList;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.UpdateDealerException;
import com.dmitriyevseyev.carWeb.servlet.ServletConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "editDealerServlet", value = "/editDealerServlet")

public class EditDealerServlet extends HttpServlet {
    @Override
    public void init() {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println(id);

        String name = request.getParameter("name");
        System.out.println(name);

        String adress = request.getParameter("adress");
        System.out.println(adress);
        DealerController controller = DealerController.getInstance();
        try {
            controller.updateCar(id, name, adress);
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

