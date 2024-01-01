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
        List<PatternDto> patterns=patternBean.findAllPatterns();
        request.setAttribute("patterns",patterns);
        request.getRequestDispatcher("/WEB-INF/pages/patterns.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] patternIdStrings= request.getParameterValues("pattern_ids");
        if(patternIdStrings != null){
            List<Long> patternIds = new ArrayList<>();
            for(String id: patternIdStrings){
                patternIds.add(Long.parseLong(id));
            }
            patternBean.deletePatternsByIds(patternIds);

        }
        response.sendRedirect(request.getContextPath()+"/Patterns");
    }
}
