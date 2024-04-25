package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.ejb.ExportImportBean;
import com.dmitriyevseyev.carWeb.ejb.IExportImport;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String[] idDealer = req.getParameterValues("idDealer");
        List<Integer> dealersIds = new ArrayList<>();
        for (String id : idDealer) {
            dealersIds.add(Integer.valueOf(id));
        }
        String fileName = req.getParameter("fileName");

        String exportList = exportImportBean.exportObjects(dealersIds, new ArrayList<>());

        resp.setContentType("text/html");
        resp.setHeader("Content-disposition", "attachment; filename = " + fileName + ".json");

        if (exportList != null) {
            try (ServletOutputStream out = resp.getOutputStream()) {
                byte[] bytes;
                bytes = exportList.getBytes();
                out.write(bytes);
                out.flush();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
