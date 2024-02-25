package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.controller.DealerList;
import com.dmitriyevseyev.carWeb.model.CarDealership;

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

        int id = Integer.parseInt(req.getParameter("id"));
        System.out.println(id);

        String name = req.getParameter("name");
        System.out.println(name);

        String adress = req.getParameter("adress");
        System.out.println(adress);

        CarDealership dealer = CarDealership.builder()
                 .id(id)
                 .name(name)
                 .adress(adress)
                 .carMap(new HashMap<>())
                 .build();

        System.out.println("Dealer - " + dealer);

        ArrayList<CarDealership> newDealerList = (ArrayList<CarDealership>) DealerList.getInstance().getDealerL();
        newDealerList.add(dealer);
        System.out.println("newDealerList - " + newDealerList);
        req.setAttribute("carDealerships", newDealerList);

        try {
            getServletContext().getRequestDispatcher("/jsp/dealerjsp/getDealer.jsp").forward(req, resp);
        } catch (ServletException e) {
            System.out.println("AddDealerServlet. " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }


}
