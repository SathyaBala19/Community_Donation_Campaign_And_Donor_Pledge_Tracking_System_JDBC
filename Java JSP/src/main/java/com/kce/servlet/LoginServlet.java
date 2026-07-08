package com.kce.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        if (email.equalsIgnoreCase("administrator@kce.ac.in")) {

            Cookie cookie = new Cookie("user", "Admin");
            response.addCookie(cookie);

            response.sendRedirect("AdminServlet");

        } else {

            String user = email.split("@")[0];

            Cookie cookie = new Cookie("user", user);
            response.addCookie(cookie);

            response.sendRedirect("UserServlet");
        }
    }
}