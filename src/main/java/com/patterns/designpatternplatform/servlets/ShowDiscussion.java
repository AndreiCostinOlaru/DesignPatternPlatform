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
        //retrieve all users and set them as an attribute for the JSP page
        List<UserDto> users=userBean.findAllUsers();
        request.setAttribute("users",users);
        Long discussionId=Long.parseLong(request.getParameter("id"));//retrieve discussion ID from the request parameters
        commentBean.displayCommentThread(discussionId);//display the comment thread for the discussion
        List<CommentDto> comments=commentBean.getComments(); //retrieve comments and set them as an attribute for the JSP page
        if (comments != null && !comments.isEmpty()) {
            request.setAttribute("comments", comments);
        }

        // retrieve discussion details and set them as an attribute for the JSP page
        DiscussionDto discussion=discussionBean.findById(discussionId);
        request.setAttribute("discussion",discussion);
        // retrieve pattern details and set them as an attribute for the JSP page
        List<Pattern> patternList=new ArrayList<>();
        patternList.add(discussion.getPattern());
        PatternDto patternDto=patternBean.copyPatternsToDto(patternList).get(0);
        request.setAttribute("pattern",patternDto);
        // retrieve the message parameter from the request for display purposes
        String message=request.getParameter("msg");
        if(message !=null && message.equals("submitted")){
            request.setAttribute("message","Already submitted a review!");
        }
        request.getRequestDispatcher("/WEB-INF/pages/showdiscussion.jsp").forward(request,response); //forward the request to the showdiscussion.jsp page
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
