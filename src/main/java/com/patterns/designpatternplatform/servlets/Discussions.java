package com.patterns.designpatternplatform.servlets;

import com.patterns.designpatternplatform.common.DiscussionDto;
import com.patterns.designpatternplatform.common.PatternDto;
import com.patterns.designpatternplatform.ejb.DiscussionBean;
import com.patterns.designpatternplatform.ejb.PatternBean;
import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@DeclareRoles({"WRITE_DBOARDS", "WRITE_PATTERN"})
@ServletSecurity(httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed ={"WRITE_DBOARDS"})})
@WebServlet(name = "Discussions", value = "/Discussions")
public class Discussions extends HttpServlet {

   @Inject
   DiscussionBean discussionBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<DiscussionDto> discussions=discussionBean.findAllDiscussions(); //retrieve all discussions and set them as an attribute for the JSP page
        request.setAttribute("discussions",discussions);
        request.getRequestDispatcher("/WEB-INF/pages/discussions.jsp").forward(request,response);//forward the request to the discussions.jsp page
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] discussionIdStrings= request.getParameterValues("discussion_ids");//retrieve discussion IDs from the form

        // check if discussion IDs are provided
        if(discussionIdStrings != null){
            List<Long> discussionIds = new ArrayList<>();
            for(String id: discussionIdStrings){
                discussionIds.add(Long.parseLong(id)); //convert string IDs to long and add them to the list
            }
            discussionBean.deleteDiscussionsByIds(discussionIds);//delete discussions by their IDs using the DiscussionBean
        }
        response.sendRedirect(request.getContextPath()+"/Discussions");//redirect to the Discussions page after successful deletion

    }
}
