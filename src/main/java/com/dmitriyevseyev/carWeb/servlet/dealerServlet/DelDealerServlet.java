package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.server.controller.DealerController;
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
        String[] idDealer = req.getParameterValues("check");
        System.out.print("String[] idDealer - ");
        for (String g : idDealer) {
            System.out.println(g);
        }
        System.out.println();

        DealerController controller = DealerController.getInstance();

        for (String id : idDealer) {
            try {
                controller.removeDealer(Integer.valueOf(id));
            } catch (NotFoundException e) {
                getServletContext().getRequestDispatcher("/jsp/dealerjsp/notfoundDealer.jsp").forward(req, resp);
            } catch (DeleteDealerExeption e) {
                System.out.println("DelDealerServlet. DeleteDealerExeption. " + e.getMessage());
            }
        }
        resp.sendRedirect(ServletConstants.PATH_DEALER);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}

