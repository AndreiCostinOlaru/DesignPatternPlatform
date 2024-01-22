package com.patterns.designpatternplatform.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);//forward the request to the login.jsp page
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("message", "Username or password incorrect");//set an error message attribute for incorrect username or password
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);//forward the request back to the login.jsp page with the error message
    }
}
