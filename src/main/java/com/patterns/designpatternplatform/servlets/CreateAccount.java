package com.patterns.designpatternplatform.servlets;
import com.patterns.designpatternplatform.ejb.UserBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "CreateAccount", value = "/CreateAccount")
public class CreateAccount extends HttpServlet {

    @Inject
    UserBean userBean;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("userGroups", new String[] {"WRITE_DBOARDS", "WRITE_PATTERN"}); // Set userGroups attribute for the JSP page and forward the request
        request.getRequestDispatcher("/WEB-INF/pages/createaccount.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the form
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String[] userGroups = request.getParameterValues("user_groups");//retrieve user groups as an array
        if (userGroups == null) {
            userGroups = new String[0];
        }
        userBean.createUser(username, email, password, Arrays.asList(userGroups));//create a new user using the UserBean
        response.sendRedirect(request.getContextPath() + "/Login");//redirect to the Login page after successfully creating the user
    }
}
