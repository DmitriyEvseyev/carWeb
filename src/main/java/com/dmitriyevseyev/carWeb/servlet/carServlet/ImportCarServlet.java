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
import com.dmitriyevseyev.carWeb.servlet.JSONValidatorExeption;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> files = null;
        try {
            files = upload.parseRequest(req);
        } catch (FileUploadException e) {
            resp.sendError(503, e.getMessage());
        }
        String json = null;
        if (files != null) {
            json = files.get(0).getString();
        }

        String idDealer = null;

        Iterator iter = files.iterator();
        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();
            if (item.isFormField()) {
                idDealer = item.getString();
            }
        }

        JsonValidator jsonValidator = JsonValidator.getInstance();

        try {
            if (!jsonValidator.isValidImport(json).isEmpty()) {
                throw new JSONValidatorExeption(ServletConstants.VALIDATION_EXEPTION +
                        jsonValidator.isValidImport(json));
            } else {
                try {
                    EIBean.importObjects(json);
                    HttpSession session = req.getSession();
                    session.setAttribute("idD", idDealer);
                    resp.sendRedirect(ServletConstants.PATH_CARS);
                } catch (ImportExeption e) {
                    resp.sendError(503, e.getMessage());
                }
            }
        } catch (
                JSONValidatorExeption e) {
            resp.sendError(422, e.getMessage());
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}