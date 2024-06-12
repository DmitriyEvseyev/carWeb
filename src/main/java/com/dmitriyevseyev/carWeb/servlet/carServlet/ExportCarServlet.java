package com.dmitriyevseyev.carWeb.servlet.carServlet;

import com.dmitriyevseyev.carWeb.ejb.IExportImport;

import com.dmitriyevseyev.carWeb.server.strategy.export.ExportExeption;


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

@WebServlet(name = "exportCarServlet", value = "/exportCarServlet")

public class ExportCarServlet extends HttpServlet {
    @EJB
    private IExportImport exportImportBean;

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String[] idCar = req.getParameterValues("check");
        List<Integer> carsIds = new ArrayList<>();
        for (String id : idCar) {
            carsIds.add(Integer.valueOf(id));
        }
        String fileName = req.getParameter("fileName");

        String exportList = null;
        try {
            exportList = exportImportBean.exportObjects(new ArrayList<>(), carsIds);
        } catch (ExportExeption e) {
            resp.sendError(503, e.getMessage());
        }

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
