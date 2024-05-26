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
import java.io.PrintWriter;
import java.util.List;

import com.dmitriyevseyev.carWeb.server.strategy.PrintableExportException;
import com.dmitriyevseyev.carWeb.server.strategy.StrategyNotFoundException;
import com.dmitriyevseyev.carWeb.servlet.ServletConstants;
import com.dmitriyevseyev.carWeb.shared.utils.JsonValidator;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> files = null;
        try {
            files = upload.parseRequest(req);
        } catch (FileUploadException e) {
            System.out.println("ImportDealerServlet, FileUploadException. " + e.getMessage());        }
        String json = null;
        if (files != null) {
            json = files.get(0).getString();
        }

        System.out.println("json - " + json);

        JsonValidator jsonValidator = JsonValidator.getInstance();
        if (!jsonValidator.isValidImport(json)) {
            getServletContext().getRequestDispatcher(ServletConstants.IMRORT_ERROR).forward(req, resp);
        } else {
            try {
                EIBean.importObjects(json);
            } catch (StrategyNotFoundException e) {
                resp.setContentType("text/html");
                PrintWriter pw = resp.getWriter();
                pw.println("<script type=\"text/javascript\">");
                pw.println("alert('Invalid Username or Password!');");
                pw.println("location='index.jsp'");
                pw.println("</script>");
                //        System.out.println("ImportDealerrServlet. " + e.getMessage());
            } catch (PrintableExportException e) {
                throw new RuntimeException(e);
            }
        }
        resp.sendRedirect(ServletConstants.PATH_DEALER);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
