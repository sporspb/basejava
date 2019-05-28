package com.spor.webapp.web;

import com.spor.webapp.Config;
import com.spor.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends javax.servlet.http.HttpServlet {
    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("resumes", storage.getAllSorted());
        response.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
    }
}
