package com.dmitriyevseyev.carWeb.servlet.carServlet;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.dmitriyevseyev.carWeb.ejb.IExportImport;
import com.dmitriyevseyev.carWeb.server.strategy.PrintableExportException;
import com.dmitriyevseyev.carWeb.server.strategy.StrategyNotFoundException;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportExeption;
import com.dmitriyevseyev.carWeb.servlet.ServletConstants;
import com.dmitriyevseyev.carWeb.shared.utils.JsonValidator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "importCarServlet", value = "/importCarServlet")
public class ImportCarServlet extends HttpServlet {
    @EJB
    private IExportImport EIBean;

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> files = null;
        try {
            files = upload.parseRequest(req);
        } catch (FileUploadException e) {
            System.out.println("ImportCarServlet, FileUploadException. " + e.getMessage());
        }
        String json = null;
        if (files != null) {
            json = files.get(0).getString();
        }

        System.out.println("json - " + json);

        String idDealer = null;

        Iterator iter = files.iterator();
        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();
            if (item.isFormField()) {
                idDealer = item.getString();
                System.out.println("value - " + idDealer);
            }
        }

        JsonValidator jsonValidator = JsonValidator.getInstance();
        if (!jsonValidator.isValidImport(json)) {
            getServletContext().getRequestDispatcher(ServletConstants.IMRORT_ERROR).forward(req, resp);
        } else {
            try {
                EIBean.importObjects(json);
            } catch (ImportExeption e) {
                throw new RuntimeException(e);
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