package com.patterns.designpatternplatform.servlets;

import com.patterns.designpatternplatform.common.PatternDto;
import com.patterns.designpatternplatform.common.PatternPhotoDto;
import com.patterns.designpatternplatform.ejb.DiscussionBean;
import com.patterns.designpatternplatform.ejb.PatternBean;
import com.patterns.designpatternplatform.ejb.PhotoBean;
import com.patterns.designpatternplatform.entities.Discussion;
import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@DeclareRoles({"WRITE_DBOARDS", "WRITE_PATTERN"})
@ServletSecurity(httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed ={"WRITE_PATTERN"})})
@WebServlet(name = "Patterns", value = "/Patterns")
public class Patterns extends HttpServlet {

   @Inject
    PatternBean patternBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve all patterns and set them as an attribute for the JSP page
        List<PatternDto> patterns=patternBean.findAllPatterns();
        request.setAttribute("patterns",patterns);
        request.getRequestDispatcher("/WEB-INF/pages/patterns.jsp").forward(request,response); //forward the request to the patterns.jsp page
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve pattern IDs from the form
        String[] patternIdStrings= request.getParameterValues("pattern_ids");
        //check if pattern IDs are present in the request, convert pattern ID strings to long and add to the list
        if(patternIdStrings != null){
            List<Long> patternIds = new ArrayList<>();
            for(String id: patternIdStrings){
                patternIds.add(Long.parseLong(id));
            }

            patternBean.deletePatternsByIds(patternIds);//delete patterns by their IDs using PatternBean
        }
        response.sendRedirect(request.getContextPath()+"/Patterns");//redirect to the Patterns page after successful deletion

    }
}
