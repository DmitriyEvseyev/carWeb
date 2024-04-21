package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.ejb.IExportImport;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "exportDealerServlet", value = "/exportDealerServlet")

public class ExportDealerServlet extends HttpServlet {
    @EJB
    private IExportImport exportImportBean;

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] idDealer = req.getParameterValues("idDealer");
        List<Integer> dealersIds = new ArrayList<>();
        for (String id : idDealer) {
            dealersIds.add(Integer.valueOf(id));
        }
        System.out.println("ExportDealerServlet, id: " + dealersIds);

        String json = exportImportBean.exportObjects(dealersIds, new ArrayList<>());
        System.out.println("json - " + json);

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
