package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.ejb.IExportImport;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportExeption;
import com.dmitriyevseyev.carWeb.servlet.JSONValidatorExeption;
import com.dmitriyevseyev.carWeb.servlet.ServletConstants;
import com.dmitriyevseyev.carWeb.shared.utils.JsonValidator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "importDealerServlet", value = "/importDealerServlet")
public class ImportDealerServlet extends HttpServlet {
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
            resp.sendError(503, e.getMessage());
        }
        String json = null;
        if (files != null) {
            json = files.get(0).getString();
        }

        System.out.println("IIIIIIIIImportDealerServlet json - " + json);


        JsonValidator jsonValidator = JsonValidator.getInstance();
        try {
            if (!jsonValidator.isValidImport(json).isEmpty()) {
                throw new JSONValidatorExeption(ServletConstants.VALIDATION_EXEPTION +
                        jsonValidator.isValidImport(json));
            } else {
                try {
                    EIBean.importObjects(json);
                    resp.sendRedirect(ServletConstants.PATH_DEALER);
                } catch (ImportExeption e) {
                    resp.sendError(503, e.getMessage());
                }
            }
        } catch (JSONValidatorExeption e) {
            resp.sendError(422, e.getMessage());
        }
    }


    @Override
    public void destroy() {
        super.destroy();
    }
}
