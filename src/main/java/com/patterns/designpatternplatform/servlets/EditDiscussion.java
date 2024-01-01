package com.patterns.designpatternplatform.servlets;

import com.patterns.designpatternplatform.common.DiscussionDto;
import com.patterns.designpatternplatform.common.PatternDto;
import com.patterns.designpatternplatform.common.UserDto;
import com.patterns.designpatternplatform.ejb.DiscussionBean;
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

@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"WRITE_DBOARDS"}))
@WebServlet(name = "EditDiscussion", value = "/EditDiscussion")
public class EditDiscussion extends HttpServlet {

    @Inject
    UserBean userBean;

    @Inject
    PatternBean patternBean;
    @Inject
    DiscussionBean discussionBean;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserDto> users=userBean.findAllUsers();
        List<PatternDto> patterns=patternBean.findAllPatterns();
        request.setAttribute("users",users);
        request.setAttribute("patterns",patterns);
        Long discussionId=Long.parseLong(request.getParameter("id"));
        DiscussionDto discussionDto=discussionBean.findById(discussionId);
        PatternDto patternDto=patternBean.findById(discussionDto.getPattern().getId());
        request.setAttribute("discussion",discussionDto);
        request.setAttribute("pattern",patternDto);
        request.getRequestDispatcher("/WEB-INF/pages/editdiscussion.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name=request.getParameter("discussionName");
        String description=request.getParameter("description");
        String code=request.getParameter("code");
        long discussionId=Long.parseLong(request.getParameter("discussionId"));
        long patternId=Long.parseLong(request.getParameter("pattern"));
        discussionBean.updateDiscussion(name,discussionId,description,code,patternId);
        response.sendRedirect(request.getContextPath()+"/Discussions");
    }
}
