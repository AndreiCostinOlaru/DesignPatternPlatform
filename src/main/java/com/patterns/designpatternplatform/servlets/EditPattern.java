package com.patterns.designpatternplatform.servlets;

import com.patterns.designpatternplatform.common.PatternDto;
import com.patterns.designpatternplatform.common.UserDto;
import com.patterns.designpatternplatform.ejb.PatternBean;
import com.patterns.designpatternplatform.ejb.UserBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"WRITE_PATTERN"}))
@WebServlet(name = "EditPattern", value = "/EditPattern")
public class EditPattern extends HttpServlet {

    @Inject
    UserBean userBean;

    @Inject
    PatternBean patternBean;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserDto> users=userBean.findAllUsers();
        request.setAttribute("users",users);
        Long patternId=Long.parseLong(request.getParameter("id"));
        PatternDto pattern=patternBean.findById(patternId);
        request.setAttribute("pattern",pattern);
        request.getRequestDispatcher("/WEB-INF/pages/editpattern.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name=request.getParameter("patternName");
        String type=request.getParameter("patternType");
        String scope=request.getParameter("patternScope");
        String description=request.getParameter("description");
        String code=request.getParameter("code");
        long userId=Long.parseLong(request.getParameter("owner_id"));
        long patternId=Long.parseLong(request.getParameter("patternId"));

        patternBean.updatePattern(name,type,scope,userId,patternId,description,code);
        response.sendRedirect(request.getContextPath()+"/Patterns");
    }
}
