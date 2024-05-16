package com.dmitriyevseyev.carWeb.servlet.carServlet;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import com.dmitriyevseyev.carWeb.ejb.IExportImport;
import com.dmitriyevseyev.carWeb.server.strategy.StrategyNotFoundException;
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
        String f = req.getParameter("idDealer");
        System.out.println("FFFFFFFff - " + f);

        String y = req.getParameter("rrr");
        System.out.println("sdfsdf - " + y);

        int idDealer = Integer.parseInt(req.getParameter("idDealer"));
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> files = null;
        try {
            files = upload.parseRequest(req);
        } catch (FileUploadException e) {
            System.out.println("ImportCarServlet, FileUploadException. " + e.getMessage());        }
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
                System.out.println("ImportCarServlet. " + e.getMessage());
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