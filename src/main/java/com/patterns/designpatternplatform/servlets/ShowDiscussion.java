package com.patterns.designpatternplatform.servlets;

import com.patterns.designpatternplatform.common.CommentDto;
import com.patterns.designpatternplatform.common.DiscussionDto;
import com.patterns.designpatternplatform.common.PatternDto;
import com.patterns.designpatternplatform.common.UserDto;
import com.patterns.designpatternplatform.ejb.CommentBean;
import com.patterns.designpatternplatform.ejb.DiscussionBean;
import com.patterns.designpatternplatform.ejb.PatternBean;
import com.patterns.designpatternplatform.ejb.UserBean;
import com.patterns.designpatternplatform.entities.Pattern;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ShowDiscussion", value = "/ShowDiscussion")
public class ShowDiscussion extends HttpServlet {
    @Inject
    CommentBean commentBean;

    @Inject
    PatternBean patternBean;

    @Inject
    DiscussionBean discussionBean;
    @Inject
    UserBean userBean;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserDto> users=userBean.findAllUsers();
        request.setAttribute("users",users);
        Long discussionId=Long.parseLong(request.getParameter("id"));
        commentBean.displayCommentThread(discussionId);
        List<CommentDto> comments=commentBean.getComments();
        if (comments != null && !comments.isEmpty()) {
            request.setAttribute("comments", comments);
        }
        DiscussionDto discussion=discussionBean.findById(discussionId);
        request.setAttribute("discussion",discussion);
        List<Pattern> patternList=new ArrayList<>();
        patternList.add(discussion.getPattern());
        PatternDto patternDto=patternBean.copyPatternsToDto(patternList).get(0);
        request.setAttribute("pattern",patternDto);
        String message=request.getParameter("msg");
        if(message !=null && message.equals("submitted")){
            request.setAttribute("message","Already submitted a review!");
        }
        request.getRequestDispatcher("/WEB-INF/pages/showdiscussion.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
