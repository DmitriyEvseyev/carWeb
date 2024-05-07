package com.dmitriyevseyev.carWeb.servlet.dealerServlet;

import com.dmitriyevseyev.carWeb.ejb.IExportImport;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload2.jakarta.JakartaServletDiskFileUpload;

@WebServlet(name = "importDealerServlet", value = "/importDealerServlet")
public class ImportDealerServlet extends HttpServlet {
    @EJB
    private IExportImport EIBean;

    @Override
    public void init() throws ServletException {
            }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
//        System.out.println("isMultipart - " + isMultipart);
//
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        // Configure a repository (to ensure a secure temp location is used)
//        ServletContext servletContext = this.getServletConfig().getServletContext();
//        File repository = (File) servletContext.getAttribute("jakarta.servlet.context.tempdir"); // Or "javax.servlet.context.tempdir" for javax
//        factory.setRepository(repository);
//
//// Create a new file upload handler
//        JakartaServletDiskFileUpload upload = new JakartaServletDiskFileUpload(factory);
//
//// Parse the request
//        List<DiskFileItem> items = upload.parseRequest(req);




        System.out.println("ssssssssss");

        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> files = null;
        try {
            files = upload.parseRequest(req);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        String xml = null;
        if (files != null) {
            xml = files.get(0).getString();
        }

        System.out.println("xml - " + xml);

//        resp.addHeader(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.NOT_ERROR_VALUE);
//        int userId = (int) req.getSession().getAttribute(ServletConstants.ATTRIBUTE_USER_ID);
//        try {
//            EIBean.importObjects(xml, userId);
//        } catch (ImportException e) {
//            resp.setHeader(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
//        } catch (PrintableImportException e) {
//            resp.setHeader(ServletConstants.ATTRIBUTE_ERROR, e.getMessage());
//        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
