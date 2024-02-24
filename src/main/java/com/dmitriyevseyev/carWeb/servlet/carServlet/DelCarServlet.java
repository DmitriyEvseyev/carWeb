package com.dmitriyevseyev.carWeb.servlet.carServlet;

import com.dmitriyevseyev.carWeb.client.controller.DealerList;
import com.dmitriyevseyev.carWeb.client.model.Car;
import com.dmitriyevseyev.carWeb.client.model.CarDealership;
import com.dmitriyevseyev.carWeb.servlet.HelloServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "delCarServlet", value = "/delCarServlet")
public class DelCarServlet extends HelloServlet {
    @Override
    public void init() {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer idDealer = Integer.parseInt(req.getParameter("idDealer"));
        System.out.println(" Integer idDealer from DelCarServlet = " + idDealer);

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
        System.out.println("idCar  DelCarServlet - " + idList);

        CarDealership dealer = DealerList.getInstance().searchDealer(idDealer);
        HashMap<Integer, Car> carHashMap = dealer.getCarMap();

        for (Integer id : idList) {
            carHashMap.remove(id);
        }

        List<Car> carList = new ArrayList<>(carHashMap.values());
        System.out.println("carList from DelCarServlet - " + carList);
        req.setAttribute("carList", carList);
        req.setAttribute("dealer", dealer);

        try {
            getServletContext().getRequestDispatcher("/jsp/carjsp/getAll.jsp").forward(req, resp);
        } catch (
                ServletException e) {
            System.out.println("DelCarServlet. " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
