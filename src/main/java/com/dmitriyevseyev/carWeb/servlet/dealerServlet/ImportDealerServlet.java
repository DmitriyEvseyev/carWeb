package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "importDealerServlet", value = "/importDealerServlet")
public class ImportDealerServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
