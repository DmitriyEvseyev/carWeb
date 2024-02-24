package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.client.controller.DealerList;
import com.dmitriyevseyev.carWeb.client.model.CarDealership;

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
        Integer id = Integer.valueOf(String.valueOf(request.getParameter("check")));
        System.out.println(" Integer idDealer = " + id);
        ArrayList<CarDealership> dealerList = (ArrayList<CarDealership>) DealerList.getInstance().getDealerL();

        CarDealership dealer = null;

        for (CarDealership c : dealerList) {
            if (c.getId().equals(id)) {
                dealer = c;
            }
        }
        System.out.println("Dealer from get - " + dealer);
        try {
            if (dealer != null) {
                request.setAttribute("dealer", dealer);
                getServletContext().getRequestDispatcher("/jsp/dealerjsp/editDealer.jsp").forward(request, response);
            } else {
                getServletContext().getRequestDispatcher("/jsp/dealerjsp/notfoundDealer.jsp").forward(request, response);
            }
        } catch (ServletException e) {
            System.out.println("EditCarServlet. " + e.getMessage());
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

        ArrayList<CarDealership> newDealerList = (ArrayList<CarDealership>) DealerList.getInstance().getDealerL();

        for (int i = 0; i < newDealerList.size(); i++) {
            if (newDealerList.get(i).getId() == id) {
                newDealerList.get(i).setName(name);
                newDealerList.get(i).setAdress(adress);
            }
        }

        System.out.println("newDealerList - " + newDealerList);
        req.setAttribute("carDealerships", newDealerList);

        try {
            getServletContext().getRequestDispatcher("/jsp/dealerjsp/getDealer.jsp").forward(req, resp);
        } catch (ServletException e) {
            System.out.println("EditDealerServlet. " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}

