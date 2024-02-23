package com.example.demo5.servlet.carServlet;

import com.example.demo5.controller.CarList;
import com.example.demo5.model.Car;
import com.example.demo5.servlet.HelloServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "delCarServlet", value = "/delCarServlet")
public class DelCarServlet extends HelloServlet {
    @Override
    public void init() {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] ids = req.getParameterValues("check");
        System.out.print("String[] ids - ");
        for (String g : ids) {
            System.out.println(g);
        }
        System.out.println();

        ArrayList<Integer> idList = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            idList.add(Integer.valueOf(ids[i]));
        }
        System.out.println("ArrayList<Integer> id - " + idList);

        ArrayList<Car> newCarList = (ArrayList<Car>) CarList.getInstance().getCarL();

        for (int j = 0; j < newCarList.size(); j++) {
            for (int i = 0; i < idList.size(); i++) {
                if (newCarList.get(j).getId() == idList.get(i)) {
                    newCarList.remove(j);
                }
            }
        }

        System.out.println("newCarList - " + newCarList);
        req.setAttribute("carList", newCarList);

        try {
            getServletContext().getRequestDispatcher("/jsp/carjsp/getAll.jsp").forward(req, resp);
        } catch (ServletException e) {
            System.out.println("DelCarServlet. " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
