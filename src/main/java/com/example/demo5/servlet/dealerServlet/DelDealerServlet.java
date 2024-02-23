package com.example.demo5.servlet.dealerServlet;

import com.example.demo5.controller.DealerList;
import com.example.demo5.model.CarDealership;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "delDealerServlet", value = "/delDealerServlet")
public class DelDealerServlet extends HttpServlet {
    @Override
    public void init() {
    }

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] idDealer = req.getParameterValues("check");
        System.out.print("String[] idDealer - ");
        for (String g : idDealer) {
            System.out.println(g);
        }
        System.out.println();

        ArrayList<Integer> idDealerList = new ArrayList<>();
        for (int i = 0; i < idDealer.length; i++) {
            idDealerList.add(Integer.valueOf(idDealer[i]));
        }
        System.out.println("ArrayList<Integer> idDealerList - " + idDealerList);

        ArrayList<CarDealership> newDealerList = (ArrayList<CarDealership>) DealerList.getInstance().getDealerL();

        for (int j = 0; j < newDealerList.size(); j++) {
            for (int i = 0; i < idDealerList.size(); i++) {
                if (newDealerList.get(j).getId() == idDealerList.get(i)) {
                    newDealerList.remove(j);
                }
            }
        }

        System.out.println("newDealerList - " + newDealerList);
        req.setAttribute("carDealerships", newDealerList);

        try {
            getServletContext().getRequestDispatcher("/jsp/dealerjsp/getDealer.jsp").forward(req, resp);
        } catch (ServletException e) {
            System.out.println("DelDealerServlet. " + e.getMessage());
        }
    }


    @Override
    public void destroy() {
        super.destroy();
    }
}

