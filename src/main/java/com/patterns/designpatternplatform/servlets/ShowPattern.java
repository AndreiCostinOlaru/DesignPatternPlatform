package com.patterns.designpatternplatform.servlets;

import com.patterns.designpatternplatform.common.PatternDto;
import com.patterns.designpatternplatform.common.PatternPhotoDto;
import com.patterns.designpatternplatform.common.UserDto;
import com.patterns.designpatternplatform.ejb.PatternBean;
import com.patterns.designpatternplatform.ejb.UserBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShowPattern", value = "/ShowPattern")
public class ShowPattern extends HttpServlet {

    @Inject
    UserBean userBean;

    @Inject
    PatternBean patternBean;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve all users and set them as an attribute for the JSP page
        List<UserDto> users=userBean.findAllUsers();
        request.setAttribute("users",users);
        Long patternId=Long.parseLong(request.getParameter("id"));//retrieve pattern ID from the request parameters
        PatternDto pattern=patternBean.findById(patternId); //retrieve pattern details and set them as an attribute for the JSP page
        request.setAttribute("pattern",pattern);
        String message=request.getParameter("msg");//retrieve the message parameter from the request for display purposes
        if(message !=null && message.equals("submitted")){
            request.setAttribute("message","Already submitted a review!");
        }
        request.getRequestDispatcher("/WEB-INF/pages/showpattern.jsp").forward(request,response);//forward the request to the showpattern.jsp page
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
