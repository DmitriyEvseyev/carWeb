package com.dmitriyevseyev.carWeb.servlet.carServlet;

import com.dmitriyevseyev.carWeb.server.controller.CarController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.DeleteCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.servlet.ServletConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "delCarServlet", value = "/delCarServlet")
public class DelCarServlet extends HttpServlet {
    @Override
    public void init() {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
        System.out.println("idCar DelCarServlet - " + idList);

        for (Integer id : idList) {
            try {
                CarController.getInstance().removeCar(id);
            } catch (NotFoundException e) {
                getServletContext().getRequestDispatcher(ServletConstants.NOT_CAR_ADDRESS).forward(req, resp);
            } catch (DeleteCarExeption e) {
                System.out.println("DeleteCarExeption. " + e.getMessage());
            }
        }
        HttpSession session = req.getSession();
        session.setAttribute("idD", idDealer);
        resp.sendRedirect(ServletConstants.PATH_CARS);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
