package com.spor.webapp.web;

import com.spor.webapp.Config;
import com.spor.webapp.model.Resume;
import com.spor.webapp.storage.SqlStorage;
import com.spor.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResumeServlet extends javax.servlet.http.HttpServlet {
    private Storage storage = new SqlStorage(com.spor.webapp.Config.get().getUrl(), com.spor.webapp.Config.get().getLogin(), Config.get().getPassword());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        /*
        response.setContentType("text/html; charset=UTF-8");
        String name = request.getParameter("name");
        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + '!');
        */
        PrintWriter out = response.getWriter();

        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
            out.println("<link rel=\"stylesheet\" href=\"css/style.css\">");
            out.println("<title>Resumes Table</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>Resumes Table</h3>");
            out.println("</body>");

            List<Resume> list = storage.getAllSorted();

            out.println("<table border=\"1\">");
            out.println("<tr>");
            out.println("<td>uuid</td>");
            out.println("<td>fullname");
            out.println("</tr>");
            for (Resume resume : list) {
                out.println("<tr>");
                out.println("<td>" + resume.getUuid() + "</td>");
                out.println("<td>" + resume.getFullName());
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</html>");
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }
}
